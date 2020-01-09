## 线程通信

### 等待通知机制
>等待通知模式是Java中比较经典的线程通信方式。

两个线程通过对同一对象调用等待`wait()`和通知`notify()`方法来进行通讯。
- 示例：`TwoThreadWaitNotify`

需要注意的细节，如下
1. 使用`wait()`、`notify()`和`notifyAll()`时需要先对调用对象加锁。
1. 调用`wait()`方法后，线程状态由`RUNNING`变为`WAITING`，并将当前线程放置到对象的等待队列。
1. `notify()`或`notifyAll()`方法调用后，等待线程依旧不会从`wait()`返回，需要调用`notify()`或`notifAll()`的线程释放锁之后，等待线程才有机会从`wait()`返回。
1. `notify()`方法将等待队列中的一个等待线程从等待队列中移到同步队列中，而`notifyAll()`方法则是将等待队列中所有的线程全部移到同步队列，被移动的线程状态由`WAITING`变为`BLOCKED`。
1. 从`wait()`方法返回的前提是获得了调用对象的锁。

`WaitNotify.java`运行过程

![WaitNotify-min](https://s1.wailian.download/2020/01/09/WaitNotify-min.png)

### 等待通知的经典范式
等待方遵循如下原则
1. 获取对象的锁。
1. 如果条件不满足，那么调用对象的`wait()`方法，被通知后仍要检查条件。
1. 条件满足则执行对应的逻辑。

```
synchronized(对象) {
	while(条件不满足) {
		对象.wait();
	}
	对应的处理逻辑
}
```

通知方遵循如下原则
1. 获得对象的锁。
1. 改变条件。
1. 通知所有等待在对象上的线程。

```
synchronized(对象) {
	改变条件
	对象.notifyAll();
}
```

### `join()`方法
- 示例：`JoinTest`

从源码可以看出，`join()`也是利用的等待通知机制。核心逻辑：
```
while (isAlive()) {
    wait(0);
}
```

### `volatile`共享内存
因为Java是采用共享内存的方式进行线程通信的，所以可以采用以下方式用主线程关闭A线程
- 示例：`VolatileTest`

### `CountDownLatch`并发工具
- 示例：`CountDownLatchTest`

`CountDownLatch`也是基于`AQS(AbstractQueuedSynchronizer)`实现的：
- 初始化一个`CountDownLatch`时告诉并发的线程，然后在每个线程处理完毕之后调用`countDown()`方法。
- 该方法会将`AQS`内置的一个`state`状态`-1`。
- 最终在主线程调用`await()`方法，它会阻塞直到`state == 0`的时候返回。

### `CyclicBarrier`并发工具
- 示例：`CyclicBarrierTest`

`CyclicBarrier`中文名叫做屏障或者是栅栏，也可以用于线程间通信。它可以等待N个线程都达到某个状态后继续运行的效果。
- 首先初始化线程参与者。
- 调用`await()`将会在所有参与者线程都调用之前等待。
- 直到所有参与者都调用了`await()`后，所有线程从`await()`返回继续后续逻辑。

### 线程响应中断
- 示例：`StopThread`

可以采用中断线程的方式来通信，调用了`thread.interrupt()`方法其实就是将thread中的一个标志属性置为了`true`。

并不是说调用了该方法就可以中断线程，如果不对这个标志进行响应其实是没有什么作用(这里对这个标志进行了判断)。

**但是如果抛出了`InterruptedException`异常，该标志就会被JVM重置为`false`。**

### 线程池`awaitTermination()`方法
- 示例：`AwaitTerminationTest`

使用`awaitTermination()`方法的前提需要关闭线程池，如调用了`shutdown()`方法。

调用`shutdown()`之后线程池会停止接受新任务，并且会平滑的关闭线程池中现有的任务。

### 管道通信
- 示例：`PipedTest`

Java虽说是基于内存通信的，但也可以使用管道通信。

需要注意的是，输入流和输出流需要首先建立连接。这样线程B就可以收到线程A发出的消息了。

### References
- [深入理解线程通信](https://crossoverjie.top/2018/03/16/java-senior/thread-communication/)
- [Java并发编程的艺术](http://www.hzcourse.com/web/refbook/detail/6119/208)