# java线程的6种状态

初始NEW、运行RUNNABLE、阻塞BLOCKED、等待WAITING、超时等待TIMED_WAITING、终止TERMINATED

## 状态详细说明

### 初始NEW

new一个thread出来，就是进入了初始状态  

### 运行RUNNABLE

调用线程的start()，线程就进入就运行状态，位于可运行线程池中。运行状态还分为就绪READY和运行中RUNNING两个状态，在cpu调度程序从可运行线程池选中某个线程后，该进程就变为RUNNING状态。

### 阻塞BLOCKED

多个线程争抢锁，没抢到锁的线程将处于阻塞BLOCKED状态

### 等待WAITING

调用线程的wait()方法时进入等待状态，直到调用notify()、notifyall()方法，该线程都不会被cpu调度程序选中运行

### 超时等待TIMED_WAITING

类似于Thread.sleep(long)、Object.wait(long)、TimeUnit.sleep()等方法，让线程等待固定的时间，时间到后会被自动唤醒进入运行RUNNABLE状态

### 终止TERMINATED

当线程的run()执行完毕时，或者主线程的main()执行完毕时就会进入终止TERMINATED状态。线程一旦终止就不能复生。终止的线程调用start()会抛出java.lang.IllegalThreadStateException异常

## 等待队列和同步队列