# java中的各种路径

## classpath

classpath是项目运行时的起始路径，也就是java程序运行时的“.”当前路径，即程序中的所有相对路径、全限定类名都是从classpath开始计算的。

### 例

假如classpath是D:/javaprogram/abc，程序中打印相对路径

``` java
Object.class.getResource("/").toString();
```

“.”就是classpath加上Object这个类的包名

## 获取文件资源的方法

### Object.class.getClassLoader().getResource("").toString()

ClassLoader获取资源是从file协议下的本项目的classpath开始算的，比如`file:/C:/Users/Henry/Desktop/demo-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/`、`file:/D:/项目/f2s-cloud/backend-demo/target/classes/`  

#### 注：

getResource()方法返回的是URL对象，所以该字符串是`协议:路径`的格式。  
这里的感叹号意味着该路径并不是单纯的文件路径，而是jar包中还有一段路径才到classpath。

### Object.class.getClassLoader().getResource("/").toString()

返回null

### Demo.class.getResource("").toString()

Class的getResource()方法是从本项目classpath加该类的包路径开始算的，比如`file:/C:/Users/herui/eclipse-workspace/demo/target/classes/demo/`、`file:/C:/Users/Henry/Desktop/demo-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/demo/`。  
如果这里的Demo类不是本项目中的类，是依赖里的，getResource()会返回null。

### Demo.class.getResource("/").toString()

同ClassLoader的getResource("")，为项目的根路径

### Paths.get(".").toAbsolutePath().toString()

同ClassLoader的getResource("")，相对路径也是从项目根路径开始算的。但是返回值不是URL对象，所以没有协议前缀。
