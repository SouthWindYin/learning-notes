# maven多模块项目parent标签设置问题

使用spring官网的initializer创建项目时，会自动创建pom文件，其中parent标签一般是

``` xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.3</version>
    <relativePath /> <!-- lookup parent from repository -->
</parent>
```

当我们要自行创建多模块项目时，子模块的parent标签一般直接改成父模块的groupId、artifactId、version。这时在IDE中开发是没问题的，但是如果我们用mvn程序打包父项目的时候，就会报

``` info
[ERROR]     Non-resolvable parent POM for space.f2s:redenvelope:0.0.1-SNAPSHOT: Could not find artifact space.f2s.cloud:f2s-cloud:pom:0.0.1-SNAPSHOT and 'parent.relativePath' points at no local POM @ line 5, column 10 -> [Help 2]
```

找不到父pom。这是因为我们在改parent标签时忽略了一些东西

## parent标签结构

* groupId
* artifactId
* version
* relativePath

前三个代表父级pom的名称，没什么好说的。  
关键在于第四个relativePath，spring initializer生成的maven项目中，是一个

``` xml
<relativePath />
```

代表的是从仓库中找pom，而这里的子项目并不能从仓库中找pom，而应该从相对路径上一级直接找pom。对于没有install过的项目来说，本地仓库中其实是没有父pom的，所以第一次mvn install会报错找不到pom。  
解决办法是删除掉这个标签，没有relativePath标签会默认在上一级相对路径找pom。  
如果父pom不是在默认的上一级路径的，应该用完整的相对路径指明pom的位置

``` xml
<relativePath>父pom的相对路径</relativePath>
```
