# hashtable的基本概念

以key-value形式进行存储，可以在短时间内将key通过hash函数算出hash地址，以直接访问的数据结构。

## 概念

冲突、负载因子、hash函数

## 冲突解决

不同的key通过hash函数算出相同的hash地址，称为冲突，常用的处理方法有：开放定址法、再hash法、链地址法

## 负载因子

对于链地址法实现的hash table，才有负载因子的说法  
负载因子 = 总键值对数 / hash桶个数  
在java和redis中，hash table的总键值对数达到负载因子*容量时，就会触发rehash，对hash桶进行扩容，对已有的元素进行重排，以避免过多元素堆积到少数hash桶上，影响查找性能。java中HashMap默认负载因子是0.75，redis在执行rdb快照备份时负载因子是5，没备份时负载因子是1
