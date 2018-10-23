# Java™ Platform Standard Ed. 7

## `java.lang.concurrent`
### `CountDownLatch`
`CountDownLatch`这个类能够使一个线程等待其他线程完成各自的工作后再执行。例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有的框架服务之后再执行。

`CountDownLatch`是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。

![CountDownLatch-min](http://www.wailian.work/images/2018/10/17/CountDownLatch-min.png)

`CountDownLatch`类只提供了一个构造器：
```
public CountDownLatch(int count) { };  //参数count为计数值
```
`CountDownLatch`类中，3个最重要的方法：
```
public void await() throws InterruptedException { };   //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };  //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
public void countDown() { };  //将count值减1
```
构造器中的计数值（`count`）实际上就是闭锁需要等待的线程数量。这个值只能被设置一次，而且`CountDownLatch`没有提供任何机制去重新设置这个计数值。

主线程必须在启动其他线程后立即调用`CountDownLatch.await()`方法。这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。

通知机制是通过`CountDownLatch.countDown()`方法来完成的；每调用一次这个方法，在构造函数中初始化的`count`值就减1。当`count`的值等于0，主线程就能通过`await()`方法，恢复执行自己的任务。

#### 应用场景
1. 实现最大的并行性：有时我们想同时启动多个线程，实现最大程度的并行性。例如，我们想测试一个单例类。如果我们创建一个初始计数为1的`CountDownLatch`，并让所有线程都在这个锁上等待，那么我们可以很轻松地完成测试。我们只需调用一次`countDown()`方法就可以让所有的等待线程同时恢复执行。
1. 开始执行前等待n个线程完成各自任务：例如应用程序启动类要确保在处理用户请求前，所有N个外部系统已经启动和运行了。
1. 死锁检测：一个非常方便的使用场景是，你可以使用n个线程访问共享资源，在每次测试阶段的线程数目是不同的，并尝试产生死锁。

#### 示例
- `CountDownLatchTest`

### `CyclicBarrier`
`CyclicBarrier`的字面意思是可循环使用（Cyclic）的屏障（Barrier）。它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
```
public class CyclicBarrier {
	private static class Generation {
		boolean broken = false;
	}
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition trip = lock.newCondition();
	private final int parties;
	private int count;
	private final Runnable barrierCommand;
	private Generation generation = new Generation();
	...
}
```
- `lock`：`CyclicBarrier`是基于`ReentrantLock`实现的
- `trip`：`lock`上的Condition条件，当线程未全部就位时，到达栅栏的线程将被添加到该条件队列
- `parties`：常量，代表线程的数量，在构造时传入，当前就位线程数`count == parties`时，栅栏打开
- `count`：当前就位的线程数。当`count == parties`时，栅栏打开
- `barrierCommand`：command线程，在所有线程就位之后且未被唤醒期间执行
- `Generation`：代，栅栏的年代/年龄
- `generation`：当线程全部就位，栅栏每次打开或者栅栏重置reset后，年代改变

`CyclicBarrier`提供了2个构造器：
```
public CyclicBarrier(int parties, Runnable barrierAction) { }
public CyclicBarrier(int parties) { }
```
参数`parties`指让多少个线程或者任务等待至barrier状态；参数`barrierAction`为当这些线程都达到barrier状态时会执行的内容。

`CyclicBarrier`中，最重要是`await`方法，它有2个重载版本：
```
public int await() throws InterruptedException, BrokenBarrierException { };
public int await(long timeout, TimeUnit unit)throws InterruptedException,BrokenBarrierException,TimeoutException { };
```
- 第一个版本比较常用，用来挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务；
- 第二个版本是让这些线程等待至一定的时间，如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务。

#### 应用场景
`CyclicBarrier`可以用于多线程计算数据，最后合并计算结果的应用场景。比如我们用一个Excel保存了用户所有银行流水，每个Sheet保存一个帐户近一年的每笔银行流水，现在需要统计用户的日均银行流水，先用多线程处理每个sheet里的银行流水，都执行完之后，得到每个sheet的日均银行流水，最后，再用barrierAction用这些线程的计算结果，计算出整个Excel的日均银行流水。

#### 示例
- `CyclicBarrierTest`，`CyclicBarrierRunnableTest`，`CyclicBarrierAwaitTest`，`CyclicBarrierReusingTest`，`CyclicBarrierIsBrokenTest`

### `Semaphore`
`Semaphore`翻译成字面意思为信号量，`Semaphore`可以控同时访问的线程个数，通过`acquire()`获取一个许可，如果没有就等待，而`release()`释放一个许可。

`Semaphore`类提供了2个构造器：
```
public Semaphore(int permits) {          // 参数permits表示许可数目，即同时可以允许多少线程进行访问
    sync = new NonfairSync(permits);
}
public Semaphore(int permits, boolean fair) {    // 这个多了一个参数fair表示是否是公平的，即等待时间越久的越先获取许可
    sync = (fair)? new FairSync(permits) : new NonfairSync(permits);
}
```
`Semaphore`类中，比较重要的几个方法：
```
public void acquire() throws InterruptedException {  }     // 用来获取一个许可，若无许可能够获得，则会一直等待，直到获得许可。
public void acquire(int permits) throws InterruptedException { }    // 获取permits个许可
public void release() { }          // 用来释放许可。
public void release(int permits) { }    // 释放permits个许可

public boolean tryAcquire() { };    // 尝试获取一个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException { };  // 尝试获取一个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false
public boolean tryAcquire(int permits) { }; // 尝试获取permits个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException { }; // 尝试获取permits个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false

public int availablePermits() {return sync.getPermits();} // 得到可用的许可数目
```

#### 应用场景
`Semaphore`可以用于做流量控制，特别公用资源有限的应用场景，比如数据库连接。假如有一个需求，要读取几万个文件的数据，因为都是IO密集型任务，我们可以启动几十个线程并发的读取，但是如果读到内存后，还需要存储到数据库中，而数据库的连接数只有10个，这时我们必须控制只有十个线程同时获取数据库连接保存数据，否则会报错无法获取数据库连接。这个时候，我们就可以使用`Semaphore`来做流控。

#### 示例
- `SemaphoreTest`，`SemaphoreProducerConsumer`

### 总结
- `CountDownLatch`和`CyclicBarrier`都能够实现线程之间的等待，只不过它们侧重点不同：
    - `CountDownLatch`一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；
    - 而`CyclicBarrier`一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；
    - 另外，`CountDownLatch`是不能够重用的，而`CyclicBarrier`是可以重用的。
    - `CountDownLatch`的计数器只能使用一次。而`CyclicBarrier`的计数器可以使用`reset()`方法重置。所以`CyclicBarrier`能处理更为复杂的业务场景，比如如果计算发生错误，可以重置计数器，并让线程们重新执行一次。
    - `CyclicBarrier`还提供其他有用的方法，比如`getNumberWaiting`方法可以获得`CyclicBarrier`阻塞的线程数量。`isBroken`方法用来知道阻塞的线程是否被中断。
- `Semaphore`其实和锁有点类似，它一般用于控制对某组资源的访问权限。

### `Exchanger`
`Exchanger`（交换者）是一个用于线程间协作的工具类。`Exchanger`用于进行线程间的数据交换。它提供一个同步点，在这个同步点两个线程可以交换彼此的数据。这两个线程通过`exchange`方法交换数据， 如果第一个线程先执行`exchange`方法，它会一直等待第二个线程也执行`exchange`，当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。

`Exchanger`类中，重要的方法：
```
public V exchange(V x) throws InterruptedException { }
public V exchange(V x, long timeout, TimeUnit unit)throws InterruptedException, TimeoutException { } // 设置最大等待时长
```

#### 应用场景
`Exchanger`可以用于遗传算法，遗传算法里需要选出两个人作为交配对象，这时候会交换两人的数据，并使用交叉规则得出2个交配结果。`Exchanger`也可以用于校对工作。比如我们需要将纸制银流通过人工的方式录入成电子银行流水，为了避免错误，采用AB岗两人进行录入，录入到Excel之后，系统需要加载这两个Excel，并对这两个Excel数据进行校对，看看是否录入的一致。

#### 示例
- `ExchangerTest`

## References
- [什么时候使用CountDownLatch](http://www.importnew.com/15731.html)
- [并发工具类（二）同步屏障CyclicBarrier](http://ifeve.com/concurrency-cyclicbarrier/)
- [Java并发编程：CountDownLatch、CyclicBarrier和Semaphore](http://www.cnblogs.com/dolphin0520/p/3920397.html)
- [并发工具类（四）两个线程进行数据交换的Exchanger](http://ifeve.com/concurrency-exchanger/)