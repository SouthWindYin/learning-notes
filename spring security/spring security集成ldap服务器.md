# spring security authentication server集成ldap服务器

## 认证流程是怎样的

* 前端页面输入用户名密码
* spring auth server用应用中配置好的PasswordEncoder加密前端填的密码
* spring auth server使用加密的密码去请求ldap服务器
* ldap服务器将请求中的密码进行base64编码
* ldap服务器将base64编码后的密码与条目中的userPassword中对比，如果一致则返回成功

注：ldap把密码看作普通的key/value的，没有所谓的加密等功能，只进行了base64编码，加密功能都是spring security提供的

### 密码“abc123”在应用和数据库中的存储对比
| PasswordEncoder | 算法加密后 | ldap实际存储值（base64编码后） | 
| --------------- | --------- | ---------------------------- |
|NoOpPasswordEncoder|`abc123`|`YWJjMTIz`|
|BCryptPasswordEncoder<sub>①</sub>| `$2a$10$da/hq5bcD8WZGTGOkZpH5u7RbwFpPBvcr21qVwmAkjOND8FgpBye.`| `JDJhJDEwJGRhL2hxNWJjRDhXWkdUR09rWnBINXU3UmJ3RnBQQnZjcjIxcVZ3bUFrak9ORDhGZ3BCeWUu`|
|DelegatingPasswordEncoder<sub>②</sub>|`{bcrypt}$2a$10$da/hq5bcD8WZGTGOkZpH5u7RbwFpPBvcr21qVwmAkjOND8FgpBye.`|`e2JjcnlwdH0kMmEkMTAkZGEvaHE1YmNEOFdaR1RHT2tacEg1dTdSYndGcFBCdmNyMjFxVndtQWtqT05EOEZncEJ5ZS4=`|

注 ①：bcrypt算法加密同一个密码每次结果不一样，需要用BCryptPasswordEncoder的matches(CharSequence rawPassword, String encodedPassword)方法来验证密码是否正确，不能直接对原文加密对比
注②：DelegatingPasswordEncoder中包含了很多加密算法，故需要在加密后的密码前加上使用的算法名