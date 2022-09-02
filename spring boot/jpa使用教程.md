# jpa使用教程

jpa是一种发展于hibernate的dao层框架，只用设计dao层的类，不用设计数据库表。

## specification的使用方法

Specification是一个接口，用于组合查询条件。可组合的条件包含等于、不等于、大于、小于、in、between、like等，是最灵活的一种查询方式。

### 使用教程

在repository接口上继承JpaSpecificationExecutor接口，就可以获得jpa提供的findAll、findOne等方法使用Specification动态查询的功能。

``` java
@Repository
public interface DemoJpaRepository extends JpaSpecificationExecutor<RoleDO> {
}
```

Specification接口中只有一个未实现的方法toPredicate()

```java
/**
* Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
* {@link Root} and {@link CriteriaQuery}.
*
* @param root root是不加任何过滤条件的查询结果
* @param query 
* @param criteriaBuilder where后面的查询条件
* @return a {@link Predicate}, may be {@literal null}.
*/
@Nullable
Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
```


