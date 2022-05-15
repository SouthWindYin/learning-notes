# sql优化通用法则

1. 尽量减少磁盘访问，io很慢。
2. 减少返回数据量，网络也很慢。
3. 减少交互次数，使用批量sql，·数据库连接很珍贵。
4. 减少服务器cpu开销，减少数据库排序操作和全表查询，减少内存、cpu操作。
5. 利用cpu多线程，使用表分区，增加并行操作

## sql预处理步骤

1. 客户端通过mysql接口发送sql到服务器
2. mysql服务器检查是否命中查询缓存，如果命中直接返回（mysql8.0默认关闭了查询缓存）
3. mysql服务器进行sql解析，由sql优化器生成对应的执行计划
4. 根据执行计划调用引擎api查询
5. 结果返回客户端

### 优化

对于读写频繁的数据库，可以关闭查询缓存。因为只有完全一样的sql才会命中缓存，命中率不是很高，而且查询之外的操作都会更新缓存，影响性能，每次检查缓存时都要对缓存加锁，也影响性能。  

### sql优化器的优化规则

0. 要先打开sql优化器
1. 重新定义表的关联规则，根据统计信息来改写成大表关联小表
2. 将外连接转换为内连接
3. 把子查询优化为关联查询，减少表的查询次数
4. 提前终止查询

#### 不会被sql优化器优化的规则，则需要手动写sql避免

1. not in语句改写为join关联查询

## 如何发现并处理慢sql

### mysql中使用慢查询日志来看，首先看是否打开慢查询日志的开关

``` sql
show variables like '%slow_query%';
```

如果slow_query_log的value是OFF，则没打开。如果打开了，则去slow_query_log_file的值的文件中看慢查询

### 查询超过多少秒记录sql到慢查询日志中

``` sql
show variables like '%long_query_time%';
```

### 查看是否打开记录没使用索引的sql开关

``` sql
show variables like '%log_queries_not_using_indexes%';
```

### 打开/关闭慢查询日志开关

``` sql
SET GLOBAL slow_query_log=ON/OFF;
SET GLOBAL long_query_time=5;
SET GLOBAL log_queries_not_using_indexes=ON/OFF;
```

设置全局变量的形式重启数据库后即失效。也可以在启动配置中设置该参数。

### 慢查询分析工具

mysql自带的mysqldumpslow工具，第三方的pt-query-digest。

### 查询正在执行的sql

``` sql
show processlist;
-- 也可以使用
select * from information_schema.PROCESSLIST;
```

time字段显示执行时间，command字段显示操作类型一般看QUERY就行，info中有正在执行的sql。

### 处理慢sql

知道哪个sql慢之后，可以使用explain分析sql是否使用索引。主要看explain结果中的type字段，常用的值有6个，all、index、range、ref、eq_ref、const。从左到右效率越来越高，要优化sql直到type尽量靠右。

* all：全表扫描，效率最低。找到结果了也不会停下，直到把所有记录都检查过。
* index：全索引扫描，效率比全表稍高。select列中全部是索引，只查了索引，没查表，但是是全部扫描的。
* range：部分索引扫描。查找了部分索引，常见于where中带有“<”、“<=”、“>”、“>=”、“between”、“in”、“or”
* ref：重复索引。使用了索引，但是索引列的值不唯一，找到值后要继续小范围扫描，但不是全索引扫描。例如：非唯一索引的条件查找where age=50，因为有多条记录age是50，所以是重复索引。like 'prefix%'找某前缀的索引列。
* eq_ref：关联唯一索引。当查关联表的主键或者唯一索引时，会用到这个类型，速度仅次于const
* const：常数级索引最快。对于查主表的主键或是有unique约束的索引会使用const类型，只查找一个索引值，最快。

## 手动优化sql

当知道哪个sql比较慢之后

## sql查询语句解析顺序

from、on、join、where、group by、having、select、distinct、order by、limit
