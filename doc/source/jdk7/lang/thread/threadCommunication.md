## 线程通信

### 等待通知机制
>等待通知模式是Java中比较经典的线程通信方式。

两个线程通过对同一对象调用等待`wait()`和通知`notify()`方法来进行通讯。
- 示例：`TwoThreadWaitNotify`

有一些需要注意:
- `wait()`、`nofify()`、`nofityAll()`调用的前提都是获得了对象的锁(也可称为对象监视器)。
- 调用`wait()`方法后线程会释放锁，进入`WAITING`状态，该线程也会被移动到**等待队列**中。
- 调用`notify()`方法会将**等待队列**中的线程移动到**同步队列**中，线程状态也会更新为`BLOCKED`。
- 从`wait()`方法返回的前提是调用`notify()`方法的线程释放锁，`wait()`方法的线程获得锁。

等待通知有着一个经典范式：

线程A作为消费者：
1. 获取对象的锁。
1. 进入`while(判断条件)`，并调用`wait()`方法。
1. 当条件满足跳出循环执行具体处理逻辑。

线程B作为生产者：
1. 获取对象锁。
1. 更改与线程A共用的判断条件。
1. 调用`notify()`方法。

伪代码如下：
```
//Thread A
synchronized(Object){
    while(条件){
        Object.wait();
    }
    //do something
}
//Thread B
synchronized(Object){
    条件=false;//改变条件
    Object.notify();
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