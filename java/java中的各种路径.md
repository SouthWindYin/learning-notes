# java中的各种路径

## classpath

classpath是jre运行时的起始路径，也就是java程序运行时的“.”当前路径，即程序中的所有相对路径都是从classpath开始计算的。

### 例

假如classpath是D:/javaprogram/abc，程序中打印相对路径

``` java
Object.class.getResource("/").toString();
```

“.”就是D:/javaprogram/abc，

## 获取文件资源的方法

### Object.class.getClassLoader().getResource("").toString()

ClassLoader获取资源是从file协议下的本项目的路径开始算的，比如`file:/C:/Users/Henry/Desktop/demo-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/`、`file:/D:/项目/f2s-cloud/backend-demo/target/classes/`，

### Object.class.getResource("").toString()

### Paths.get(".").toAbsolutePath().toString()
