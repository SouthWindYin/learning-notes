# spring security问答

## spring security是干嘛的

权限验证框架，基于spring framework的。

## spring security怎么起作用的

spring security提供了15个filter，让请求来的时候从spring security的15个filter中挨个走一遍，达到过滤请求的目的。这15个filter完成了权限验证的所有工作。

## 开发人员要做什么

这15个filter已经默认配好了，但是需要开发人员根据实际情况修改配置，比如登陆方式、登出方式、跨域设置、header设置等。

## 如何配置这15个filter

现在流行的方法是，自定义一个继承了*WebSecurityConfigurerAdapter*的配置类，重写configure()，通过*HttpSecurity*对象来配置。那15个filter每个都使用一个或多个继承自*SecurityConfigurerAdapter*的*Configurer*来配置它，例如：*ExpressionUrlAuthorizationConfigurer*对应使用*FormLoginConfigurer*来配置。

### 具体配置方法（以配置*ExpressionUrlAuthorizationConfigurer*为例）

``` Java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http // 1. 每个配置都从HttpSecurity对象开始
    .formLogin() // 2. 选择一个Configurer来配置，这里选择的是FormLoginConfigurer
    .loginProcessingUrl("/login") // 3. 对上一步选择的Configurer进行配置，每个Configurer可配置的内容不一样
    .permitAll() // 也是对第2步选择的Configurer进行配置
    ... // 直到第2步选择的Configurer配置完为止
    .and() // 4. 开启新的一个配置，and()返回一个HttpSecurity对象，这步等同于第1步
    ...
    ;
}
```

注：在某些设置中，http.authorizeRequests()方法会返回*ExpressionInterceptUrlRegistry*对象，不是*Configurer*，却占据了第2步的位置。这是因为*ExpressionInterceptUrlRegistry*实际上是*ExpressionUrlAuthorizationConfigurer*的子类，也是*Configurer*。

## 如何配置登录用户验证

写一个实现UserDetailsService接口的类，实现loadUserByUsername(String username)方法

``` Java
@Component
public class LoginService implements UserDetailsService{

	@Autowired
	private F2sUserMapper userMapper;
	@Autowired
	private F2sUserRoleMapper urMapper;
	@Autowired
	private F2sRoleMapper roleMapper;
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<F2sUser> userList=userMapper.select(c->c.where(F2sUserDynamicSqlSupport.username,isEqualTo(username)));
		if(userList.size()<1) {
			throw new UsernameNotFoundException("【"+username+"】没找到该用户");
		} else {
			F2sUser user=userList.get(0);
			
			List<F2sUserRole> urList=urMapper.select(c->c.where(F2sUserRoleDynamicSqlSupport.userId, isEqualTo(user.getId())));
			List<Integer> ids=urList.stream().map(F2sUserRole::getRoleId).collect(Collectors.toList());
			
			List<RoleAuthority> gaList;
			if(!CollectionUtils.isEmpty(ids)) {
				List<F2sRole> roleList=roleMapper.select(c->c.where(F2sUserRoleDynamicSqlSupport.userId, isEqualTo(user.getId())));
				// 组装成spring security的user并返回
				gaList=roleList.stream().map(r->new RoleAuthority(r.getRoleName())).collect(Collectors.toList());
			} else {
				gaList=Arrays.asList();
			}
			LoginUser loginUser = new LoginUser(user.getId().intValue(),user.getUsername(),user.getPassword(),user.getDisabled()==LoginUser.USER_DISABLED,gaList);
			
			// 放入redis
			ValueOperations<String,Object> vo=redisTemplate.opsForValue();
			vo.set("f2s:login:user:"+loginUser.getId(), loginUser);
			return loginUser;
		}
	}
}
```

我们返回的是自定义的实现了UserDetails的LoginUser类，其中包含实现了GrantedAuthority接口的角色类RoleAuthority的List。LoginUser里把需要的登陆用户属性放进该类，比如id、username等。

## 如何使用基于JSR-250注解的权限验证

