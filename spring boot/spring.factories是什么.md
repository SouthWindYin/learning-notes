# spring.factories文件有什么用

使用类似于[Java spi](../java/javaspi机制.md)的机制来加载指定接口的实现类\
在spring应用中的任意依赖包中可以配置META-INF/spring.factories文件，其中写上

``` properties
# 注释
interface-name1=instance-name1,instance-name2
interface-name2=instance-name3
```

则可以实例化对应接口的实现。
