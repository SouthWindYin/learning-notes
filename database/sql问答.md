# sql问答

## union和union all的区别

union会把查出来的所有结果去重，union all一样的也会显示出来，不会去重

``` sql
select 1 as r
union
select 1 as r
union
select 2 as r 
```

结果是
|行号|r|
|-|-|
|1|1|
|2|2|

``` sql
select 1 as r
union all
select 1 as r
union all
select 2 as r 
```

结果是
|行号|r|
|-|-|
|1|1|
|2|1|
|3|2|

当union和union all交替使用时，以最后一个union位置为准

``` sql
select 1 as r
union all
select 1 as r
union all
select 2 as r 
union
select 1 as r
union all
select 2 as r
union
select 2 as r --这里是最后一个union，则到这个语句之前的所有结果去重，不管前面出现了多少union或union all。
union all
select 1 as r
union all
select 1 as r
--最后一个union后面还有两个union all，则把那两个结果加到后面
```

结果是
|行号|r|
|-|-|
|1|1|
|2|2|
|3|1|
|4|1|
