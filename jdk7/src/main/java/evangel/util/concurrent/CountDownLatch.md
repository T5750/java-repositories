# Java™ Platform Standard Ed. 7

## `java.lang.concurrent`
### `CountDownLatch`
`CountDownLatch`这个类能够使一个线程等待其他线程完成各自的工作后再执行。例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有的框架服务之后再执行。

`CountDownLatch`是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。

![CountDownLatch-min](http://www.wailian.work/images/2018/10/17/CountDownLatch-min.png)
```
//Constructs a CountDownLatch initialized with the given count.
public void CountDownLatch(int count) {...}
```
构造器中的计数值（`count`）实际上就是闭锁需要等待的线程数量。这个值只能被设置一次，而且`CountDownLatch`没有提供任何机制去重新设置这个计数值。

主线程必须在启动其他线程后立即调用`CountDownLatch.await()`方法。这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。

通知机制是通过`CountDownLatch.countDown()`方法来完成的；每调用一次这个方法，在构造函数中初始化的`count`值就减1。当`count`的值等于0，主线程就能通过`await()`方法，恢复执行自己的任务。

#### 应用场景
1. 实现最大的并行性：有时我们想同时启动多个线程，实现最大程度的并行性。例如，我们想测试一个单例类。如果我们创建一个初始计数为1的`CountDownLatch`，并让所有线程都在这个锁上等待，那么我们可以很轻松地完成测试。我们只需调用一次`countDown()`方法就可以让所有的等待线程同时恢复执行。
1. 开始执行前等待n个线程完成各自任务：例如应用程序启动类要确保在处理用户请求前，所有N个外部系统已经启动和运行了。
1. 死锁检测：一个非常方便的使用场景是，你可以使用n个线程访问共享资源，在每次测试阶段的线程数目是不同的，并尝试产生死锁。

示例：`CountDownLatchTest`

### `CyclicBarrier`

#### 应用场景
`CyclicBarrier`可以用于多线程计算数据，最后合并计算结果的应用场景。比如我们用一个Excel保存了用户所有银行流水，每个Sheet保存一个帐户近一年的每笔银行流水，现在需要统计用户的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日均银行流水，最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流水。

#### `CyclicBarrier`和`CountDownLatch`的区别
- `CountDownLatch`的计数器只能使用一次。而`CyclicBarrier`的计数器可以使用`reset()`方法重置。所以`CyclicBarrier`能处理更为复杂的业务场景，比如如果计算发生错误，可以重置计数器，并让线程们重新执行一次。
- `CyclicBarrier`还提供其他有用的方法，比如`getNumberWaiting`方法可以获得`CyclicBarrier`阻塞的线程数量。`isBroken`方法用来知道阻塞的线程是否被中断。比如以下代码执行完之后会返回`true`。

## References
- [什么时候使用CountDownLatch](http://www.importnew.com/15731.html)
- [并发工具类（二）同步屏障CyclicBarrier](http://ifeve.com/concurrency-cyclicbarrier/)