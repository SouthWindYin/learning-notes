# maven核心概念

要理解maven是在干什么，就要理解maven的核心概念：lifecycle、phase、goal、plugins等概念

## 生命周期lifecycle和步骤phase

maven默认有三个内建的生命周期：clean、default、site，每个生命周期包含了一个多步骤组成的序列。比如clean生命周期包含了pre-clean、clean、post-clean三个phase，default最多，详细参见[maven官方文档-生命周期](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#lifecycle-reference)。

## 目标goal和插件plugins

一个goal可以类比于一个maven的函数，它可以影响对应的phase的功能。一个plugin中包含多个goal，可以分别配置。  
maven的很多phase默认对应着某些内建plugins的goal，详见[官方文档-内建生命周期绑定](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#built-in-lifecycle-bindings)。

## mvn命令是怎么执行的

mvn命令形如`mvn [options] [<goal(s)>] [<phase(s)>]`，我们平时执行的`mvn clean install`命令，就只指定了两个lifecycle的phase，没有指定goal。需注意这里的clean不是lifecycle。goal的命令形如`<plugins>:<goal>`。  
执行特定phase的时候，会自动按顺序执行该生命周期指定phase前面的所有phase。比如`mvn package`命令，就会执行default生命周期中前17个phase，但是并不是每个phase都有操作，所以实际上并不会太多。`mvn clean install`命令就执行了clean生命周期的前2个phase和default生命周期的前22个phase。  

### mvn命令的参数

mvn参数实际上并不需要写在goal和phase之前，写在任何位置都可以，习惯上更多写在后面，只要用短横线开头的就代表是参数。比如`mvn clean install -D maven.test.skip=true`、`mvn clean -U package -Dmaven.test.skip=true -Pdev`都是合法的写法。  
这里建议参数-D和后面的值maven.test.skip=true最好中间加一个空格，比较易读且在PowerShell中不会出错（-Dmaven.test.skip=true的写法在PowerShell中应该写为-D'maven.test.skip'=true）。


