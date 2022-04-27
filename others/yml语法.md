# yml语法

* 大小写敏感
* 使用缩进表示层级关系
* 缩进只能用空格，不能用制表符
* 缩进空格数目不重要，只要相同层级左对齐即可

## 注释

单行注释用#表示

``` yml
# 这是一行注释
```

## 数据结构

### 对象

键值对的组合

* 冒号后有一个空格
* value的部分可以写另一个对象，以花括号的形式写进来

``` yml
url: www.baidu.com
host: { ip: 10.0.0.1 , port: 80 }
```

### 数组

* 相同的缩进，用“-”和一个空格开头  
* 嵌套数组缩进一个层级即可
* 也可以用中括号写成数组

``` yml
ip: 
  - 10.0.0.1
  - 10.0.0.2
  - 10.0.0.3

people:
  - age:
    - 12
    - 15
  - name:
    - henry
    - sam

ent: [ alibaba, baidu, google ]
```

### 常量

字符串、布尔值、整数、浮点数、Null、时间、日期

``` yml
# 写不写引号都可以，字符串中间有换行符会被转换成空格
string: 
  - 'string'
  - yoyoyo
  - two
    line
# true和false三种形式都可以用
bool: 
  - [ true, True, TRUE ]
  - [ false, False, FALSE ]
# 整数、二进制表示
int: [ 39, 0b1001_0011 ]
# 浮点数、科学计数法
float: [ 3.14, 3.333e+5 ]
# 用“~”符号表示空
null: ~
# 时间必须用ISO 8601格式，日期和时间用T连接，时间和时区用+连接
datetime: 2022-04-27T01:01:15+08:00
# 日期必须用ISO 8601格式，yyyy-MM-dd
date: 1996-06-22
```

### 特殊符号

两个感叹号“!!”表示强制转换类型

``` yml
name: !!str 88
```

---和...配合使用表示一个配置文件的开始和结束

``` yml
---
time: 1999-01-01
name: dude
...

---
time: 2018-03-03
name: test
...
```

“>”折叠换行符，“|”保留换行符

```yml
# 等于name: samsam
name: sam>
  sam
# 等于speak: hello\nworld
speak: hello|
  world
```

### 引用

使用“&”表示锚点，使用“*”引用

``` yml
url: &localhost
  ip: 127.0.0.1
  port: 80

# 等同于
# db:
#   ip: 127.0.0.1
#   port: 80
# 其中<<表示将结果并入db的下一级
db: 
  <<: *localhost

```