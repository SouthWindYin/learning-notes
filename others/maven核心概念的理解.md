# maven核心概念

maven的本质是对源代码执行函数。要理解maven是在干什么，就要理解maven的核心概念：plugins、goal、lifecycle、phase。

## 插件plugins和目标goal

goal就是maven的函数，plugin就是goal的集合，可以看做函数包。由goal去对源代码进行管理。  

## 生命周期lifecycle和阶段phase

源代码编译的过程maven分为三个生命周期：clean、default、site，每个生命周期包含了一个多个phase组成的序列。goal可以作用于特定的phase，从而实现每个阶段需要对源代码完成的任务，比如编译、测试、部署等等。

## mvn命令

mvn命令本质上是执行goals，形如`mvn [options] [<goal(s)>] [<phase(s)>]`

* 其中参数是以`-`开头的。例如：`-Dmaven.test.skip=true`
* 目标goal形如`<plugins>:<goal>`。例如：`compiler:compile`、`surefire:test`、`deploy:deploy`
* 多个阶段phase以空格隔开。例如：`clean install`、`package`

maven有一个默认的生命周期绑定机制，对于某些phase，会默认执行某些goal。这是由于maven有一些内建插件造成的。详见[官方文档-内建生命周期绑定](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#built-in-lifecycle-bindings)  
所以mvn命令可以用`mvn [<phase(s)>]`的命令来执行goal，意思就是执行phase对应的默认goal。    
指定phases时只能每个生命周期指定一个phase，比如`mvn clean install`即clean生命周期指定的clean phase，default生命周期指定的install phase，且没有指定site生命周期的phase。
指定phase的时候，会把该生命周期从开始到指定phase中所有对应的goals按顺序执行一遍。例如：`mvn compile`命令，对应default生命周期的前7个phases，会按顺序执行每个phase对应的goals。如果phase没有对应goal，就跳过该phase。  
根据pom.xml中的packaging参数不同，phase对应的goal会有变化。例如：packaging为`war`的时候，`package` phase对应的goal为`war:war`，而packaging为`jar`的时候，`package` phase对应的goal为`jar:jar`。

### mvn命令的参数

mvn参数实际上并不需要写在goal和phase之前，写在任何位置都可以，习惯上更多写在后面，只要用短横线开头的就代表是参数。比如`mvn clean install -D maven.test.skip=true`、`mvn clean -U package -Dmaven.test.skip=true -Pdev`都是合法的写法。  
这里建议参数-D和后面的值maven.test.skip=true最好中间加一个空格，比较易读且在PowerShell中不会出错（-Dmaven.test.skip=true的写法在PowerShell中应该写为-D'maven.test.skip'=true）。

## 插件参数配置 

在pom.xml中可以在plugins标签中配置goal对应的参数，常用的是配置jdk编译版本等。

## 附录1：生命周期列表

### Clean Lifecycle

* pre-clean
* clean
* post-clean

### Default Lifecycle

* validate
* initialize
* generate-sources
* process-sources
* generate-resources
* process-resources
* compile
* process-classes
* generate-test-sources
* process-test-sources
* generate-test-resources
* process-test-resources
* test-compile
* process-test-classes
* test
* prepare-package
* package
* pre-integration-test
* integration-test
* post-integration-test
* verify
* install
* deploy
  
### Site Lifecycle

* pre-site
* site
* post-site
* site-deploy

