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
3. 从META-INF/spring.factories中加载并实例化SpringApplicationRunListener（运行监听器）的实现类，并放入SpringApplicationRunListeners的实例
4. 调用运行监听器的starting()，发布starting的应用事件
5. 准备环境
   1. attatch()获取应用启动参数和系统参数
   2. 调用运行监听器的enviromentPrepared()，发布enviromentPrepared应用事件。这会触发监听了此事件的应用监听器将自定义的各种application.yml配置绑定到Enviroment里。
   3. 剩下的不重要...
   4. 返回一个配置好的环境
6. 设置spring.beaninfo.ignore为true
7. 打印banner
8. 创建一个applicationContext（应用上下文）一般Servlet的应用上下文则是创建的AnnotationConfigServletWebServerApplicationContext实例。这个应用上下文中包含了AnnotatedBeanDefinitionReader实例和ClassPathBeanDefinitionScanner实例。
9. 设置ApplicationStartup，用于诊断应用启动快慢情况，一般不用。
10. 准备上下文
    1. postProcessApplicationContext()预设上下文的一些加载器
    2. 将之前从spring.factories里加载的初始化器应用到上下文上
    3. 调用所有监听器的contextPrepared()
    4. 调用BootstrapContext的close()，发布一个关闭BootstrapContext的事件
    5. 调用所有监听器的contextLoaded()
11.
