# jmeter使用教程

## 基本使用

打开jmeter时，会默认创建一个`测试计划`，一个窗口是一个测试计划。在测试计划上点右键，添加`线程组`，在右边输入线程数、ramp-up时间（在多少秒内启动全部线程）、循环次数等参数。

在线程组上添加`取样器`-`HTTP请求`，就可以在里面配置每个线程发送的请求。

只配置了发送请求的部分还不能看到结果，必须在测试计划中添加`监听器`-`查看结果树`，这样就可以在开始后看到发送的请求的结果。

## 读取响应的结果

使用响应结果的方法是在`HTTP请求`模块下添加`后置处理器`，一般使用`正则表达式提取器`、`JSON提取器`、`CS/JQuery提取器`较多。将获取到的值放入变量中，在后面的请求中用${variable}来获取值。

## 从文件中读取信息

有时需要从文件中读取准备好的账号密码等信息，这时可以在线程组中添加`配置元件`-`CSV数据文件设置`，在其中设置读取csv文件，放入变量中。需要注意的是，在CSV数据文件设置中设定的变量，是一个线程使用一行数据的。比如
