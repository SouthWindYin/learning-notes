# java spi机制

全称Service Provider Inteface，是jdk内置的一种服务发现的机制。\
由jdk定义接口，第三方编写实现。在第三方提供的jar包的META-INF/services目录下创建以服务接口命名的文件，文件内容就是具体实现类。这样jdk会自动的加载该实现类并实例化。\
jdk中查找接口实现的工具类是java.util.ServiceLoader

## spi流程

1. 有关组织定义接口标准
2. 第三方提供实现，并配置“META-INF/services/接口名称”文件
3. 开发者使用ServiceLoader对接口进行load，从而获取到所有的第三方实现类实例

## 示例场景

jdk中定义了java.sql.Driver接口，但是并没有实现类。\
mysql提供的jar包mysql-connector-java-6.0.6.jar中，可以找到META-INF/services/java.sql.Driver文件，其内容是com.mysql.cj.jdbc.Driver，即Driver接口的实现类名\
用ServiceLoader来加载java.sql.Driver接口，则可以得到mysql的mysql.cj.jdbc.Driver实例。
