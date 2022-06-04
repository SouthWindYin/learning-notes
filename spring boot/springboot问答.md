# spring boot问答

## aware接口有什么用？

当一个bean内部需要用到该bean的某些定义信息（比如beanName、applicationContext、beanFactory等）的时候，就要实现aware的接口（*BeanNameAware*、*ApplicationContextAware*、*BeanFactoryAware*），每个接口会有一个抽象的setXXX方法，可以在该set方法中使用bean的定义信息，存储或者展示等等。在springboot初始化该bean的时候就会放进去  
aware英文意思是“注意到的、知道的”。在这里意思是“让bean知道XXX组件”，即为注入XXX组件。

## headless模式是什么？

在java的awt包中，有很多功能是会默认操作鼠标、键盘、显示器的，比如canvas和panel，调用他们的时候，会去调用显示器进行渲染。如果将java程序改为headless模式，那就只会进行计算输出，不会调用显示器进行绘制，这样就可以提高性能。所以服务器上用headless模式就很适合，可以提高性能（没有用到awt的相关功能的话就不能提高性能）。springboot启动默认为true。

## 配置相关的注解有哪些

- **@Import** 实例化指定的类，并将其放入spring容器\
- **@ImportResource** 指定spring的xml配置文件路径，并将里面配置好的bean放入spring容器\
- **@ConfigurationProperties(String prefix)** 从已加载的.properties或.yml配置文件中获取指定前缀的属性值，绑定至该类的成员变量上，但是不会自动实例化并放入spring容器，需要配合其他注解\
- **@EnableConfigurationProperties(Class value[])** 只能传入带有@ConfigurationProperties的类名数组，将这些类绑定配置文件的参数，实例化并放入spring容器，
- **@EnableAutoConfiguration** 通过 **@import** 导入了spring-boot-autoconfigure包下的127个自动配置类，涵盖了spring boot认证的流行的框架的自动配置

## BoostrapContext是什么

bootstrap英文意义为“引导程序”，bootstrap context意思是“引导程序的上下文”

## ApplicationListener是什么

spring框架中对于监听者模式的一种实现，ApplicationListener接口的实现类可以在指定ApplicationEvent被发布出来的时候执行。可以自定义ApplicationEvent，然后通过容器中已经有的ApplicationEventPublisher的bean的publishEvent(new 自定义Event)来发布事件。

## SpringApplicationRunListener是什么

是一种模板模式的接口，其中包含了springboot启动过程中的很多阶段，包括starting()、enviromentPrepared()、contextPrepared()、contextLoaded()、started()、running()、failed()。该接口的实现类可以定义在每个启动阶段执行特定的逻辑。默认的唯一实现是EventPublishingRunListener，负责在应用启动到该阶段的时候，发布对应的ApplicationEvent，由已经加载的那些ApplicationListener响应。

## Enviroment是什么

Enviroment是存储该应用的所有Profile（环境）和Property（配置）的，application.yml中的每一项配置就是一个Property，不同的Profile可以有同名的Property。

## ApplicationContext是什么

应用上下文是BeanFactory的一个实现，它扩展了BeanFactory的功能，包括国际化、文件访问、事件传播等等。springboot项目中优先用这个。

## SpringApplication是什么

用于从java main方法引导spring应用的类，创建SpringApplication的过程将加载spring应用所必须的那些组件。

## BeanFactory是什么

一般说的“spring容器”指的就是BeanFactory。是用来创建spring bean，存储bean的类。BeanFactory有很多实现，常见的包括XmlBeanFactory（用于根据xml中的配置生成bean）、ApplicationContext（springboot最常用的）等等

## BeanDefinition是什么

是描述某种spring bean的类。一个BeanDefinition中包括了Bean的名称、描述、依赖、scope、是否懒加载等设置信息。BeanFactory根据BeanDefinition来实例化Bean并加入容器中。

## BeanPostProcessor是什么

Bean后置处理器，在bean实例化并注入容器完成后，在bean的初始化方法initialization()调用前后使用。BeanPostProcessor接口有两个方法：postProcessBeforeInitialization()和postProcessAfterInitialization()。

## BootstrapRegistryInitializer是什么

初始化BootstrapRegistry之前调用的，springboot默认启动过程中并没有实现类。

## ApplicationContextInitializer是什么

用于在容器刷新之前调用该接口实现的initialize方法的接口。一般用于需要对应用程序上下文进行编程初始化的web应用中。加载ApplicationContextInitializer的方法是从spring.factories中读取再实例化。

## spring中有哪些核心概念

### spring bean

是spring中的核心，是spring容器管理的对象，一般是单例模式放在spring容器中。开发中常用的service、controller、配置等对象都是spring bean  

### BeanFactory接口

就是spring的本质，容器。不同的BeanFactory实现有不同的功能，但是都具有最基本的获取bean的功能。常见的实现是springboot中的ApplicationContext。  

### BeanDefinition接口

spring容器创建spring bean对象的模板，里面存储了spring bean的元信息，比如是否单例、class name、依赖的其他bean的名称、是否懒加载、是否可自动注入等信息。所以创建spring bean不是通过new的方式创建的，而是通过BeanDefinition和反射来创建的。



