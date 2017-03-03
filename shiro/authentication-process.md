# shiro是怎么验证Subject的
shiro验证分为三个步骤：

1. 收集Principals（身份）和Credentials（凭证）
2. 提交身份和凭证
3. 提交成功则允许访问，否则重新验证或阻止访问

## 收集Principals（身份）和Credentials（凭证）
    UsernamePasswordToken token = new UsernamePasswordToken(username,password);
    token.setRememberMe(true);

## 提交身份和凭证
    Subject curUser = SecurityUtils.getSubject();
    curUser.login(token);

## 提交后
    try{
        curUser.login(token);
    }catch(some exceptions){
        ...
    }catch(some exceptions){
        ...
    }

如果没有抛出任何异常，则为通过。通过之后程序任何地方调用curUser.isAuthenticated()都为true

# 详细验证过程
1. 程序构建了一个存有用户认证信息的AuthenticationToken（令牌）之后，调用subject.login(token)方法

2. Subject实例通常是DelegateSubject类或子类的实例，认证开始时会委托程序设置的securityManager实例调用securityManager.login(token)方法

3. securityManager实例接收到令牌之后会委托内置的Authenticator实例（通常是ModularRealmAuthenticator的实例）调用authenticator.authenticate(token)。ModularRealmAuthenticator会在认证过程中对设置的一个或多个Realm实例进行适配，它为shiro提供了一个可插拔的认证机制

4. 如果一个程序中配置了多个Realm，ModularRealmAuthenticator会根据配置的AuthenticationStrategy（认证策略）来进行多Realm的认证过程，Realm被调用后，AuthenticationStrategy将对每一个Realm结果进行响应

5. 判断每一个Realm是否支持提交的token，如果支持，Realm将调用getAuthenticationInfo(token)，该方法就是实际处理认证，我们通过覆盖doGetAuthenticationInfo方法来编写自定义的认证

# Realm验证过程
Realm实际上是一个特定安全DAO，Realm与数据源通常是一对一关系。

若Realm支持一个提交的AuthenticationToken，那么Authenticator将会调用该Realm的getAuthenticationInfo(token)方法，这代表了一个Realm与后备数据源的认证尝试。该方法按以下顺序进行：

1. 为主要的识别信息（账户识别信息）检查token

2. 基于principal在数据源中寻找相吻合的账户数据

3. 确保token支持的credentials匹配那些存储在数据源的凭证

4. 若credentials匹配，返回一个封装了shiro能够理解的账户数据的AuthenticationInfo实例

5. 若credentials不匹配，抛出AuthenticationException异常