# 并发编程笔记

## （一）基础篇
### 1.1 线程安全
- 线程安全：当多个线程访问某一个类（对象或方法）时，这个类始终都能表现出正确的行为，那么这个类（对象或方法）就是线程安全的。
- `synchronized`：可以在任意对象及方法上加锁，而加锁的这段代码称为“互斥区”或“临界区”。

### 1.2 多个线程多个锁
- 关键字`synchronized`取得的锁都是对象锁，而不是把一段代码（方法）当做锁，所以代码中哪个线程先执行`synchronized`关键字的方法，哪个线程就持有该方法所属对象的锁（Lock），两个对象，线程获得的就是两个不同的锁，它们互不影响。
- 在静态方法上加`synchronized`关键字，表示锁定.class类，类一级别的锁（独占.class类）。

### 1.3 对象锁的同步和异步
- 同步：`synchronized`，概念：共享。需要满足两个特性：
   * 原子性（同步）
   * 可见性
- 异步：`asynchronized`，概念：独立

### 1.4 脏读
- 加锁考虑业务整体性，即为`setValue`/`getValue`方法同时加锁`synchronized`，保持业务（service）的原子性。示例：`DirtyRead`

### 1.5 `synchronized`其他概念
- `synchronized`拥有锁重入的功能。示例：`SyncDubbo2`
- 出现异常，锁自动释放。示例：`SyncException`

### 1.6 `synchronized`代码块
- 使用`synchronized`代码块优化执行时间，也就是通常所说的减小锁的粒度。示例：`Optimize`
- `synchronized`可以使用任意的`Object`进行加锁。示例：`ObjectLock`
- 不要使用`String`的常量加锁，会出现死循环问题。示例：`StringLock`
- 锁对象的改变问题，当使用一个对象进行加锁时，要注意对象本身发生改变时，那么持有的锁就不同。示例：`ModifyLock`
- 死锁问题。示例：`DeadLock`

### 1.7 `volatile`关键字的概念
- `volatile`：使变量在多个线程间可见。
- 一个线程可以执行的操作有：使用（use）、赋值（assign）、装载（load）、存储（store）、锁定（lock）、解锁（unlock）。
- 主内存可以执行的操作有：读（read）、写（write）、锁定（lock）、解锁（unlock），每个操作都是原子的。
- `volatile`的作用是强制线程到主内存（共享内存）里去读取变量，而不去线程工作内存区里去读取，从而实现了多个线程间的变量可见。
- `volatile`不具备原子性，性能比`synchronized`强很多，不会造成阻塞。注意：一般`volatile`用于只针对多个线程可见的变量操作，并不能代替`synchronized`的同步功能。
- 实现原子性建议使用atomic类的系列对象（atomic类只保证本身方法原子性，并不保证多次操作的原子性）

### 2.1 线程之间通信
- 使用`wait`/`notify`方法实现线程间的通信（注意这两个方法都是`Object`类的方法）
    1. `wait`和`notify`必须配合`synchronized`关键字使用
    1. `wait`方法释放锁，`notify`方法不释放锁

### 2.2 使用`wait`/`notify`模拟`Queue`
- `BlockingQueue`：是一个队列，并且支持阻塞的机制。示例：`MyQueue`

### 2.3 `ThreadLocal`
- `ThreadLocal`：线程局部变量，是一种多线程间并发访问变量的解决方案。
- `ThreadLocal`完全不提供锁，而使用以空间换时间的手段，为每个线程提供变量的独立副本，以保障线程安全。
- 从性能上说，`ThreadLocal`不具有绝对的优势，但作为一套与锁完全无关的线程安全解决方案，可在一定程度上减少锁竞争。
- 示例：`ConnThreadLocal`

### 2.4 单例&多线程
- 两种比较经典的单例模式：
    - double check instance。示例：`DoubleSingleton`
    - static inner class。示例：`Singletion`

## （二）中级篇
### 3.1 同步类容器
- 同步类容器都是线程安全的，但在某些场景下可能需要加锁来保护复合操作。复合类操作如：迭代、跳转，以及条件运算。这些复合操作在多线程并发地修改容器时，可能会表现出意外的行为，最经典的便是`ConcurrentModificationException`，原因是当容器迭代的过程中，被并发地修改了内容，这是由于早期迭代器设计时并没有考虑并发修改的问题。
- 同步类容器：古老的`Vector`、`HashTable`。这些容器的同步功能由JDK的`Collections.synchronized***`等工厂方法去创建实现的。
- 示例：`Tickets`

### 3.2 并发类容器
- `ConcurrentHashMap`、`ArrayBlockingQueue`、`PriorityBlockingQueue`、`SynchronousQueue`

### 4.1 `ConcurrentMap`
- `ConcurrentMap`接口有两个重要的实现：
   - `ConcurrentHashMap`
   - `ConcurrentSkipListMap`（支持并发排序功能，弥补`ConcurrentHashMap`）
- `ConcurrentHashMap`内部使用段（`Segment`）来表示这些不同的部分，每个段其实就是一个小的`HashTable`，它们有自己的锁。

### 4.2 Copy-On-Write容器
- Copy-On-Write简称COW，是一种用于程序设计中的优化策略。
- JDK里的COW容器有两种：`CopyOnWriteArrayList`和`CopyOnWriteArraySet`。
- COW容器即写时复制的容器。COW容器也是一种读写分离的思想，读和写不同的容器。适合读多写少。

### 5.1 并发`Queue`
- 以`ConcurrentLinkedQueue`为代表的高性能队列
- 以`BlockingQueue`接口为代表的阻塞队列

### 5.2 `ConcurrentLinkedQueue`
- `ConcurrentLinkedQueue`：通过无锁的方式，实现了高并发状态下的高性能，通常性能好于`BlockingQueue`。
- 它是一个基于链接节点的无界线程安全队列。该队列的元素遵循先进先出的原则。

### 5.3 `BlockingQueue`接口
- `ArrayBlockingQueue`：基于数组的阻塞队列实现，也叫有界队列。
- `LinkedBlockingQueue`：基于链表的阻塞队列，同`ArrayBlockingQueue`类似。它是一个无界队列。
- `SynchronousQueue`：一种没有缓冲的队列，生产者产生的数据直接会被消费者获取并消费。
- `PriorityBlockingQueue`：基于优先级的阻塞队列（优先级的判断通过构造函数传入的`Comparator`对象来决定，也就是传入队列的对象必须实现`Comparable`接口）。它是一个无界队列。
- `DelayQueue`：带有延迟时间的`Queue`，其中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。`DelayQueue`中的元素必须实现`Delayed`接口，`DelayQueue`是一个没有大小限制的队列，应用场景如：对缓存超时的数据进行移除、任务超时处理、空闲连接的关闭等。

### 6.1 多线程的设计模式
- Future、Master-Worker和生产者-消费者模型。

### 6.2 Future模式
- Future模式有点类似于Ajax请求时，页面是异步地进行后台处理，用户无需一直等待请求的结果，可以继续浏览或操作其它内容。

### 6.3 Master-Worker模式
- Master-Worker模式是常用的并行计算模式。核心思想是系统由两类进程协作：Master进程和Worker进程。Master负责接收和分配任务，Worker负责处理子任务。当各个Worker子进程处理完成后，会将结果返回Master，由Master做归纳和总结。其好处是能将一个大任务分解成若干个小任务，并行执行，从而提高系统的吞吐量。

### 6.4 生产者-消费者
- 通常由两类线程，即若干个生产者线程和若干个消费者线程。生产者线程负责提交用户请求，消费者线程负责具体处理生产者提交的任务，在生产者和消费者之间通过共享内存缓存区进行通信。

## （三）高级篇
### 7.1 `Executor`框架
- `Executors`创建线程池方法：
    - `newFixedThreadPool()`方法，该方法返回一个固定数量的线程池，线程数始终不变。当有一个任务提交时，若线程池空闲，则立即执行，若没有，则会被暂缓在一个任务队列中等待有空闲的线程去执行。
    - `newSingleThreadExecutor()`方法，创建一个线程的线程池，若空闲则执行，若没有空闲线程则暂缓在任务队列中。
    - `newCachedThreadPool()`方法，返回一个可根据实际情况调整线程个数的线程池，不限制最大线程数量，若有任务，则创建线程，若无任务则不创建线程。如果没有任务则线程在60s后自动回收（空闲时间60s）。
    - `newScheduledThreadPool()`方法，该方法返回一个`ScheduledExecutorService`对象，但该线程池可以指定线程的数量。
- 示例：`ScheduledJob`

### 7.2 自定义线程池
- 若`Executors`工厂类无法满足我们的需求，可用`ThreadPoolExecutor`类自定义线程。构造方法如下：
```
public ThreadPoolExecutor(int corePoolSize,
			int maximumPoolSize,
			long keepAliveTime,
			TimeUnit unit,
			BlockingQueue<Runnable> workQueue,
			ThreadFactory threadFactory,
			RejectedExecutionHandler handler) {...}
```
- 示例：`UseThreadPoolExecutor1`, `UseThreadPoolExecutor2`

### 7.3 自定义线程池使用详细
- 在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于`corePoolSize`，则优先创建线程。若大于`corePoolSize`，则会将任务加入队列，若队列已满，则在总线程数不大于`maximumPoolSize`的前提下，创建新的线程。若线程数大于`maximumPoolSize`，则执行拒绝策略。或其它自定义方式。
- 无界的任务队列时，`LinkedBlockingQueue`。与有界队列相比，除非系统资源耗尽，否则无界的任务队列不存在任务入队失败的情况。当有新任务到来，系统的线程数小于`corePoolSize`时，则新建线程执行任务。当达到`corePoolSize`后，就不会继续增加。若后续仍有新的任务加入，而没有空闲的线程资源，则任务直接进入队列等待。若任务创建和处理的速度差异很大，无界队列会保持快速增长，直到耗尽系统内存。
    - 无界队列，`corePoolSize==maximumPoolSize`
- JDK拒绝策略：
    - `AbortPolicy`：直接抛出异常阻止系统正常工作。
    - `CallerRunsPolicy`：只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务。
    - `DiscardOldestPolicy`：丢弃最老的一个请求，尝试再次提交当前任务。
    - `DiscardPolicy`：丢弃无法处理的任务，不给予任务处理。
- 如果需要自定义拒绝策略，可以实现`RejectedExecutionHandler`接口。
    1. 发送请求转到另一台服务器端
    1. 存数据库，后台跑job
- `UseCountDownLatch`, `UseCyclicBarrier`, `UseFuture`, `UseSemaphore`

### 优雅关机
- 通过JDK的ShutdownHook来完成优雅关机，这个钩子可以在以下几种场景被调用：
    - 程序正常退出
    - 使用System.exit()
    - 终端使用Ctrl+C触发的中断
    - 系统关闭
    - 使用kill pid命令干掉进程
- 注：在使用kill -9 pid是不会JVM注册的钩子不会被调用。
- 示例：`TestShutdownHook`
- [Java应用中使用ShutdownHook友好地清理现场](http://www.cnblogs.com/nexiyi/p/java_add_ShutdownHook.html)

示例：`TestHoldCount`, `UseCondition`, `UseManyCondition`, `UseReentrantLock`, `UseReentrantReadWriteLock`

## References
- 并发编程