# 并发编程笔记

## 基础篇（一）
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

## 中级篇（二）
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
- Future、Master-Worker何生产者-消费者模型。

### 6.2 Future模式
- Future模式有点类似于Ajax请求时，页面是异步地进行后台处理，用户无需一直等待请求的结果，可以继续浏览或操作其它内容。

### 6.3 Master-Worker模式
- Master-Worker模式是常用的并行计算模式。核心思想是系统由两类进程协作：Master进程和Worker进程。Master负责接收和分配任务，Worker负责处理子任务。当各个Worker子进程处理完成后，会将结果返回Master，由Master做归纳何总结。其好处是能将一个大任务分解成若干个小任务，并行执行，从而提高系统的吞吐量。

