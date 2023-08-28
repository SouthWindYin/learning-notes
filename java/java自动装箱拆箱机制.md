# java自动装箱拆箱机制

## 设计思想

包装类和包装类理应不相等
为了效率，-128~127的包装类Integer实例使用了常量池，故只要值相等，它们地址也相等

为了分析装箱拆箱机制，使用了openjdk的jol工具查看内存地址  
maven的pom中增加此依赖

``` xml
<dependency>
    <groupId>org.openjdk.jol</groupId>
    <artifactId>jol-core</artifactId>
    <version>0.16</version>
</dependency>
```

## 自动装箱

在将普通类型赋值给包装类的时候，会进行自动装箱，可见a和b的地址是一样的。  
而c和d通过new包装类的方式创建实例，则不会进行自动拆装箱

``` java
public class Demo {

	public static void main(String[] args) {
		int a=0;
		Integer b=0;
		Integer c=new Integer(0);
		Integer d=new Integer(0);
		
		System.out.println(GraphLayout.parseInstance(a).addresses());
		System.out.println(GraphLayout.parseInstance(b).addresses());
		System.out.println(GraphLayout.parseInstance(c).addresses());
		System.out.println(GraphLayout.parseInstance(d).addresses());
	}
}
```

输出结果

``` txt
[31876684936]
[31876684936]
[31876918472]
[31876918488]
```

## 自动拆箱

在两个基本类型的对象（包装类或者基本类型）进行比较的时候，会进行自动装箱的操作  
a和c的地址并不一样，但是在进行==比较的时候，自动把c拆箱成基本类型了。

``` java
public class Demo {

	public static void main(String[] args) {
		int a=0;
		Integer c=new Integer(0);
		Integer d=new Integer(0);
		
		System.out.println(a==c);
		System.out.println(c==d);
		
	}
}
```

输出结果

``` txt
true
false
```

