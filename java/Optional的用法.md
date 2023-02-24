# Optional的用法

## orElse()和orElseGet()的区别

orElse()只能传入一个T对象，orElseGet()可以传入一串逻辑（一个lambda表达式）。 
orElseGet()方便在：有时候需要在Optional里的T值是null的时候，创建一个新T对象，新T对象还需要初始化一些数据，不是一个简单的new T()，这时候就更适合用orElseGet()。

``` java
public void demo() {
    Student stu=null;

    System.out.println(Optional.ofNullable(stu).orElse(new Student()));
    System.out.println(Optional.ofNullable(stu).orElseGet(()->{
        Student s=new Student();
        s.setName("demo");
        s.setAge(100);
        return s;
    }));
}
```

输出

``` console
Student(name=null, age=0)
Student(name=demo, age=100)
```