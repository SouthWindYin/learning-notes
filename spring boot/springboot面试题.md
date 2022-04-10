# springboot面试题 自答

## springboot是什么

springboot是一个简化spring开发配置的框架，用springboot可以不配置spring所需的xml，使用注解的方式定义spring bean。并且很多框架springboot提供了自动配置，autoconfiguration类，加快了框架搭建的速度。springboot也提供了方便的启动web应用的方式，不用显示的使用web服务器（springboot默认提供了）。

## spring boot与spring cloud的区别

springboot是用来开发单体应用的，spring cloud是用来开发微服务的，也就是多个单体应用组成的分布式应用。常用的方案是微服务架构中的每一个单体应用都是springboot应用。

## spring boot启动流程

### 创建SpringApplication实例

1. 判断是servlet还是webflux应用
2. 从META-INF/spring.factories中加载并实例化BootstrapRegistryInitializer（初始化器）的实现类
3. 从META-INF/spring.factories中加载并实例化ApplicationContextInitializer（应用上下文初始化器）的实现类
4. 从META-INF/spring.factories中加载并实例化ApplicationListener（应用监听器）的实现类

#### 注

从spring.factories加载各种实现类的机制参见[spring.factories是什么](./spring.factories是什么.md)

### SpringApplication.run()

1. 创建一个BootstrapContext
2. 打开[headless模式](./springboot问答.md)
3. 从META-INF/spring.factories中加载并实例化SpringApplicationRunListener（应用上下文初始化器）的实现类
