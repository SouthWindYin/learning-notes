# Jakarta EE

jakarta ee是一系列技术的规范，是很多个规范组成的。jakarta ee的版本跟spring boot的版本意思差不多，都是规范的其中每个组件的最小版本，只要其中的所有组件都更新到了某个版本之上，jakarta ee版本就升级了。

## 三种配置

整个jakarta ee标准分为三种配置（三个profile），最大最全的是Jakarta EE Platform，包含所有的jakarta规范，小一点的是Jakarta EE Web Profile，只包含了web相关的规范，最小的是Jakarta EE Core Profile，只包含了最少的可运行的规范。根据需求的不同可以使用不同的层级。core profile是为了最小化启动应用和最少内存占用而设计的。

## spring社区和jakarta的关系

spring boot最新的版本是spring boot 3.x，而在springboot进入3时，开发团队是以jakarta ee为基础进行开发的，并且支持了jakarta ee 10。所以了解jakarta ee 9 10对未来是有帮助的。[spring blog](https://spring.io/blog/2021/09/02/a-java-17-and-jakarta-ee-9-baseline-for-spring-framework-6)

## jakarta ee 10 的特点

为了云原生而设计的规范

## CDI和spring的关系

CDI全称Context and Dependency Injection，spring众所周知是以DI Dependency Injection为基础的。两者的区别主要在于CDI的注入大多以代理对象的形式，默认以懒加载的形式注入，所以对原对象的修改不好处理。而spring默认单例，应用启动时直接注入原对象本身，可以方便的对其修改。两者主要是风格不同，使用上spring更加方便和好理解，所以市场选择了spring。但是最新的CDI比spring还要轻量了，市场会不会变化现在还说不清楚。

## 一些报告

[jakarta ee正在复兴，份额仅次于spring/spring boot](https://zhuanlan.zhihu.com/p/410944115)

## GlassFish 

是Eclipse开发的实现Jakarta ee规范的应用服务器（application server），也可以叫Jakarta EE容器（Jakarta ee container）。GlassFish是由jakarta ee规范定义者开发的，所以它总是会支持最新的jakarta ee规范。但是坏处是Glassfish缺乏商业支持，对于大型的需要长期维护的项目来说，使用glassfish服务器是有风险的，最好是用有商业支持的其他应用服务器，比如Red Hat的WildFly。

## tomcat和glassfish的区别

jakarta ee定义了三种应用服务器规范（platform、web profile、core profile），所以只有实现了这三种的任意一种的全部规范，才能称为一个应用服务器。而tomcat主要实现的是jakarta servlet规范（还有Jakarta Servlet, Jakarta Server Pages, Jakarta Expression Language, Jakarta WebSocket, Jakarta Annotations and Jakarta Authentication），所以它只能称为一个web服务器或者servlet容器，不能叫应用服务器。

但是由于tomcat小，和可以嵌入其他应用的特性，spring boot用它为基础开发web应用就特别“实惠”，所以得到了市场的青睐。

## jakarta ee应用和spring应用的区别

* spring boot可以打包成jar一键运行，使用内嵌的tomcat servlet容器；而jakarta ee必须要有应用服务器，虽然应用服务器现在可以以embedded的方式启动，但是还是比spring boot多了一步。
* 开发的过程来说，spring不用了解太多应用整体架构，就可以直接就某一处业务逻辑开始编码，因为spring boot已经默认配置好了运行的一切条件。而jakarta ee应用要对一些规范有个初步的理解，要知道应用服务器的基本使用方法，才能开始编码，相对复杂一些。
* jakarta ee的应用不依赖于具体实现，开发都是用jakarta ee规范，实现取决于用什么应用服务器，这更具有可移植性。spring开发每一个组件都要自己引入项目，代码对组件的依赖度很高，开发到后期的时候，移植的成本是巨大的。
* jakarta ee的war应用包很小，不占空间，因为里面只有代码，几乎没有组件实现的jar包（jakarta ee应用也可以加入第三方jar）。spring的应用需要把所有的组件放进jar包中，所以包很大。