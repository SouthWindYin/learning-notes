# spring中一些特殊的组件

## spring是如何使用servlet的Filter的

spring的web包中提供了一个Filter的代理类，名叫DelegatingFilterProxy。其作用是：

1. 通过spring容器管理servlet Filter的生命周期
2. 如果Filter中需要用到spring bean，可以直接注入。还包括spring的配置信息也可以注入

使用单独spring框架的时候，需要把DelegatingFilterProxy配置在web.xml和spring bean配置文件中。但是如果结合springboot，配置就会简单很多，这样更常用。
