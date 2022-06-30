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

