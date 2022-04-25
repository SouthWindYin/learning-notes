# spring中一些特殊的组件

## spring是如何使用servlet的Filter的

spring的web包中提供了一个Filter的代理类，名叫DelegatingFilterProxy。其作用是：

1. 通过spring容器管理servlet Filter的生命周期
2. 如果Filter中需要用到spring bean，可以直接注入。还包括spring的配置信息也可以注入

使用单独spring框架的时候，需要把DelegatingFilterProxy配置在web.xml和spring bean配置文件中。但是如果结合springboot，配置就会简单很多，这样更常用。

## spring中的cglib

spring框架中默认使用了cglib做aop的动态代理，spring框架默认将cglib的代码打包进了spring-core包中，但是没有提供源代码查看，spring官方推荐自己去cglib官网查看cglib的源代码。

``` markdown
Package org.springframework.cglib Description
Spring's repackaging of net.sf.cglib 3 (for internal use only).
This repackaging technique avoids any potential conflicts with dependencies on CGLIB at the application level or from other third-party libraries and frameworks.

As this repackaging happens at the classfile level, sources and Javadoc are not available here. See the original CGLIB 3 Javadoc for details when working with these classes.

打包org.springframework.cglib的备注
Spring把net.sf.cglib 3重新打包进了spring的源代码。
这样的重新打包可以避免spring应用依赖第三方cglib库或框架可能带来的冲突。
因为是把字节码文件重新打包进来的，所以spring不提供源代码和文档。当要使用CGLIB 3的功能的时候，细节请参阅CGLIB 3官网的文档。
```
