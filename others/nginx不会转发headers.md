# 如何处理客户端访问nginx反向代理的服务器跳转路径不正确的问题

## 问题描述

有一个spring boot服务端，使用了spring securiy进行权限验证，未登录访问应用的任意路径会跳转到 *`/login`* 页面。\
服务端程序使用nginx代理8081端口至backend三级域名的443端口，并且配置了该二级域名下所有80端口的请求重定向至https的443端口，证书已配置。\
当客户端访问 *`域名/abc`* 时，跳转到了 *`127.0.0.1/login`* 而不是 *`域名/login`*

## 寻找解决方案思路

### 检查浏览器请求

点开浏览器f12，查看网络标签，发现共请求了三次：

1. 请求 *`域名/abc`* 响应为301，*`Location`* 响应头中显示重定向到 *`https://域名/abc`*
2. 重定向请求 *`https://域名/abc`*，*`Host`*请求头为 *`https://域名/abc`* 响应为302，*`Location`* 响应头中显示重定向到 *`http://127.0.0.1:8081/login`*
3. 重定向请求 *`http://127.0.0.1:8081/login`* 返回404

问题出现在第2步，第2步的响应中 *`Location`* 不是正确的重定向地址 *`https://域名/login`* 。

### 响应是如何生成的

响应由应用服务器生成的。由于登录认证未通过，则肯定是spring security框架中的某个地方负责拦截了请求，并且在响应头中填上了 *`Location`* 。\
为了追踪后端程序是如何生成响应头的，只能在本地启动后端服务，使用debug模式跟踪代码。

### 写代码查看响应

在 *`C:\Windows\System32\drivers\etc\hosts`* 里将 *`127.0.0.1`* 映射成服务器域名地址，然后启动本地服务器，访问 *`域名/abc`*\
通过断点跟踪代码，发现spring security中将请求中的 *`Host`* 放进了响应中的 *`Location`* 于是增加一个自定义的 *`Filter`* 来打印请求中的 *`Host`* 头

``` Java
@Slf4j
public class MySecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(request.getHeader("Host"));
        log.info("Host:"+request.getHeader("Host"));
        filterChain.doFilter(request, response);
1   }

}
```

在spring security配置中添加这个自定义的 *`Filter`*

``` Java
@Configuration
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new MySecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        ...
    }
}

```

提交代码，在生产环境中启动，查看后端log\
发现在log中打印出的 *`Host`* 是 *`127.0.0.1:8081/login`* \
至此问题明确了，浏览器的三个请求中，只有请求2是进入了后端服务器的，而浏览器显示请求2的 *`Host`* 头是 *`https://域名/abc`* ，说明是nginx反向代理时把 *`Host`* 头改写了。

## 问题解决

在nginx的 *`proxy_pass`* 指令中，若不做任何配置，会使用后面的URL重写 *`Host`*

``` nginx
server{
    location / {
        # Host请求头的值变成这里的地址
        proxy_pass http://127.0.0.1:$dest$request_uri;
    }

    listen 443 ssl;
    server_name *.example.com;
    ...
}
```

必须在nginx配置中自己配置 *`Host`*

``` nginx
server{
    location / {
        proxy_pass http://127.0.0.1:$dest$request_uri;
        proxy_set_header Host $host;
    }

    listen 443 ssl;
    server_name *.example.com;
    ...
}
```

重启nginx之后，即可正常跳转后端服务器
