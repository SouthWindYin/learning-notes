# spring boot问答

## aware接口有什么用？

当一个bean内部需要用到该bean的某些定义信息（比如beanName、applicationContext、beanFactory等）的时候，就要实现aware的接口（*BeanNameAware*、*ApplicationContextAware*、*BeanFactoryAware*），每个接口会有一个抽象的setXXX方法，可以在该set方法中使用bean的定义信息，存储或者展示等等。在springboot初始化该bean的时候就会放进去

## headless模式是什么？

在java的awt包中，有很多功能是会默认操作鼠标、键盘、显示器的，比如canvas和panel，调用他们的时候，会去调用显示器进行渲染。如果将java程序改为headless模式，那就只会进行计算输出，不会调用显示器进行绘制，这样就可以提高性能。所以服务器上用headless模式就很适合，可以提高性能（没有用到awt的相关功能的话就不能提高性能）。springboot启动默认为true。

## 配置相关的注解有哪些

- **@Import** 实例化指定的类，并将其放入spring容器\
- **@ImportResource** 指定spring的xml配置文件路径，并将里面配置好的bean放入spring容器\
- **@ConfigurationProperties(String prefix)** 从已加载的.properties或.yml配置文件中获取指定前缀的属性值，绑定至该类的成员变量上，但是不会自动实例化并放入spring容器，需要配合其他注解\
- **@EnableConfigurationProperties(Class value[])** 只能传入带有@ConfigurationProperties的类名数组，将这些类绑定配置文件的参数，实例化并放入spring容器，
- **@EnableAutoConfiguration** 通过 **@import** 导入了spring-boot-autoconfigure包下的127个自动配置类，涵盖了spring boot认证的流行的框架的自动配置

## BoostrapContext是什么

## ApplicationListener是什么

## SpringApplicationRunListener是什么

## Enviroment是什么

## ApplicationContext是什么

应用上下文是BeanFactory的一个实现，它扩展了BeanFactory的功能，包括国际化、文件访问、事件传播等等。springboot项目中优先用这个。

## SpringApplication是什么

## BeanFactory是什么

spring容器指的就是BeanFactory。是用来创建spring bean，存储bean的类。BeanFactory有很多实现，常见的包括XmlBeanFactory（用于根据xml中的配置生成bean）、ApplicationContext（springboot最常用的）等等

## BeanDefinition是什么
