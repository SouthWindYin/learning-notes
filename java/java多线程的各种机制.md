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

## java线程的6种状态

初始NEW、运行RUNNABLE、阻塞BLOCKED、等待WAITING、超时等待TIMED_WAITING、终止TERMINATED

### 初始NEW

new一个thread出来，就是进入了初始状态  

### 运行RUNNABLE

调用线程的start()，线程就进入就运行状态，位于可运行线程池中。运行状态还分为就绪READY和运行中RUNNING两个状态，在cpu调度程序从可运行线程池选中某个线程后，该进程就变为RUNNING状态。

### 阻塞BLOCKED

多个线程争抢锁，没抢到锁的线程将处于阻塞BLOCKED状态

### 等待WAITING

调用线程的wait()方法时进入等待状态，直到调用notify()、notifyAll()方法，该线程都不会被cpu调度程序选中运行

### 超时等待TIMED_WAITING

类似于Thread.sleep(long)、Object.wait(long)、TimeUnit.sleep()等方法，让线程等待固定的时间，时间到后会被自动唤醒进入运行RUNNABLE状态

### 终止TERMINATED

当线程的run()执行完毕时，或者主线程的main()执行完毕时就会进入终止TERMINATED状态。线程一旦终止就不能复生。终止的线程调用start()会抛出java.lang.IllegalThreadStateException异常

## 同步队列与等待队列

![](./images/%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%90%8C%E6%AD%A5%E9%98%9F%E5%88%97%E4%B8%8E%E7%AD%89%E5%BE%85%E9%98%9F%E5%88%97.jpg)