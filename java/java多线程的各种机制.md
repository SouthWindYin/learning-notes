# java多线程的各种机制

join()、wait()、notify()、锁、park()、中断、等待、阻塞

## join的用法

最简单的java程序是在一条主线程main中运行的，如果我们新建一个线程并start()，就会并发地运行两条线程中的代码。

``` java
public class Demo {

	public static void main(String[] args) {
		Thread t = new Thread(()->{
			for(int i=0;i<5;i++) {
				System.out.println("打印线程"+Thread.currentThread().getName());
			}
		});
		t.start();
		
		for(int i=0;i<5;i++) {
			System.out.println("打印线程"+Thread.currentThread().getName());
		}
	}
}
```

输出结果

``` txt
打印线程main
打印线程main
打印线程main
打印线程main
打印线程Thread-0
打印线程Thread-0
打印线程Thread-0
打印线程Thread-0
打印线程Thread-0
打印线程main
```

当我们使用join()的时候，意味着暂停当前的线程，把join进来的线程先执行完再继续当前的线程，从而将并发变成串行

``` java
public class Demo {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(()->{
			for(int i=0;i<5;i++) {
				System.out.println("打印线程"+Thread.currentThread().getName());
			}
		});
		t.start();
		t.join(); // 使用了join()，先执行完t线程中的逻辑再执行后续的main线程逻辑
		
		for(int i=0;i<5;i++) {
			System.out.println("打印线程"+Thread.currentThread().getName());
		}
	}
}
```

输出结果

``` txt
打印线程Thread-0
打印线程Thread-0
打印线程Thread-0
打印线程Thread-0
打印线程Thread-0
打印线程main
打印线程main
打印线程main
打印线程main
打印线程main
```

## 并发JMM内存模型

JMM内存模型（Java Memory Model）不是JVM内存模型，这是在分析并发程序才会使用到的模型，是一个抽象的模型，并不是真正的物理内存划分模型。

## java多线程程序设计要点

1. 尽量不要让一个任务的线程依赖另一个线程的中间结果，因为jvm有指令重排序优化，有可能会导致预想之外的结果。如果非要这样设计，一定要做好稳定性测试。
2. 设计多线程程序时，要注意顺序问题。有一个特性叫“线程内似串行”（Within-Thread As-If-Serial Semantics），如果同一个线程内具有数据依赖，某语句依赖前面的语句，那它一定是在前面的依赖之后执行的，如果某语句不依赖前面的语句，则有可能先于前面的语句执行。

## happens-before关系

数学上，happens-before关系是一种**严格偏序关系**，其定义如下：  

```
如果事件a和事件b满足：
*  若事件a和事件b在同一过程中，事件a在事件b之前发生
*  事件a发出的消息被事件b接收
则称`a happens-before b`；或`a，b具有happens-before关系`  
注：并发的过程之间不具有happens-before关系
```

在同一线程的java程序中存在多条指令，如果指令A写在指令B之前且指令A和指令B具有happens-before关系，则指令A一定先于指令B执行，而不会被重排序至指令B之后。

1. 程序顺序：如果指令A写在指令B前，且指令B依赖指令A，则A happens-before B
2. 构造先于回收：同一个对象的构造方法 happens-before 该对象的finalize()方法
3. 同步块：如果指令A和指令B以先后顺序在一个synchronize块中，则A happens-before B
4. 解锁先于加锁：对于一个监视器对象的解锁操作 happens-before 它随后的加锁
5. 写volatile：对volatile对象的写一定 happens-before 它后续的读
6. 线程启动优先：调用一个线程的start()方法一定 happens-before 该线程的任何动作
7. join返回：被join()进来的线程结束并返回时，其中的所有操作 happens-before 当前线程后续的操作
8. 默认初始化优先：任何对象的默认初始化发生在该对象任何其他操作之前