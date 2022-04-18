# ddd自问自答
## 如何区分factory和repository

factory位于领域层，repository接口位于领域层，实现位于基础设施层。\
factory的职责是【创建一个新对象h】；repository的职责是将持久化组件封装起来，对外提供统一便捷的对象获取和状态修改的接口。repository不是把数据从数据库取出来还原成对象，而是解决的对象在业务流程和持久化组件之间的转换问题

### ddd与现实对照理解

**ddd：** 工厂创建一个新对象

**现实：** 工厂生产一件新产品

**ddd：** 工厂负责对象的创建和复杂对象的重建，仓库负责把对象从持久化组件中取出交给工厂重建或业务流程处理

**现实：** 工厂负责产品的生产和零件组装，仓库管理员负责把产品或零件从仓库中取出交给工厂组装或门店销售

### 名词解释

在ddd中：

1. 【对象】泛指【实体（entity）】或【值对象（value object）】
2. 【工厂（factory）】、【仓库（repository）】、【领域服务（domain service）】不是【对象】
3. ddd中的【对象】不是编程语言中的对象（Object）
4. 【业务流程】指【对象】或【领域服务（domain service）】中的方法流程
5. 【持久化组件】指关系型数据库、缓存组件、文件等可持久化的组件

## ddd中如何区分entity和value object

对于同一类的两个对象a和b，对象a的所有属性均与对象b一样，如果对象a和对象b此时仍需要被看作两个不同的东西，则这种类是entity；如果此时对象a和对象b可以看作一个东西，则这种类是value object

### 例

```Java
class Person {
    id,
    name,
    age
}
```

两个具有相同name、age的Person对象，他们仍需被看作两个不同的东西，所以Person应是entity

```Java
class Money {
    amount,
    currency
}
```

两个具有相同amount、currency的Money对象，他们可以被看作一个东西，所以Money应是value object

### 注

在不同上下文之间，同样属性的类可能由entity变为value object，也可能反过来。

## 如何实现工厂（factory）

### 工厂类

工厂类是一种特殊的领域服务（domain service），如果“创建某实体”这个功能不属于任何其他实体，那这个实体就由一个单独的工厂类来创建。

### 工厂方法

工厂方法是聚合根中的创建其他实体或者复杂值对象的方法，创建需要返回创建的结果，查询实体是另一个流程。为了满足不同的业务需要，创建同一实体的工厂方法可以有多个，但是应尽量重用。