# ddd中如何区分entity和value object

对于同一类的两个对象a和b，对象a的所有属性均与对象b一样，如果对象a和对象b此时仍需要被看作两个不同的东西，则这种类是entity；如果此时对象a和对象b可以看作一个东西，则这种类是value object

## 例

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

## 注

在不同上下文之间，同样属性的类可能由entity变为value object，也可能反过来。