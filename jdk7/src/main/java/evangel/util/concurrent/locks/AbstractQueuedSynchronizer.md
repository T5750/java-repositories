# Java™ Platform Standard Ed. 7

## `java.util.concurrent.locks`
### `AbstractQueuedSynchronizer`
#### 简介
提供了一个基于FIFO队列，可以用于构建锁或者其他相关同步装置的基础框架。子类对于状态的把握，需要使用这个同步器提供的以下三个方法对状态进行操作：
```
AbstractQueuedSynchronizer.getState()
AbstractQueuedSynchronizer.setState(int)
AbstractQueuedSynchronizer.compareAndSetState(int, int)
```

`Node`的主要包含以下成员变量：

属性名称 | 描述
----|------
`int waitStatus` | 表示节点的状态。
`Node prev` | 前驱节点，比如当前节点被取消，那就需要前驱节点和后继节点来完成连接。
`Node next` | 后继节点。
`Node nextWaiter` | 存储condition队列中的后继节点。
`Thread thread` | 入队列时的当前线程。
`Node SHARED` | 标记当前节点是共享模式
`Node EXCLUSIVE` | 标记当前节点是独占模式

`waitStatus`包含的状态有：
1. `CANCELLED`，值为1，表示当前的线程被取消；
1. `SIGNAL`，值为-1，表示当前节点的后继节点包含的线程需要运行，也就是unpark；
1. `CONDITION`，值为-2，表示当前节点在等待某一条件，也就是在condition队列中；
1. `PROPAGATE`，值为-3，表示当前场景下后续的`acquireShared`能够得以执行；
1. 值为0，表示当前节点在sync队列中，等待着获取锁，即表示初始状态。

节点成为sync队列和condition队列构建的基础，在同步器中就包含了sync队列。同步器拥有三个成员变量：sync队列的头结点`head`、sync队列的尾节点`tail`和状态`state`。对于锁的获取，请求形成节点，将其挂载在尾部，而锁资源的转移（释放再获取）是从头部开始向后进行。对于同步器维护的状态`state`，多个线程对其的获取将会产生一个链式的结构。

![AQS-Node-min](http://www.wailian.work/images/2018/10/25/AQS-Node-min.png)

#### API说明
方法名称 | 描述
----|------
`protected boolean tryAcquire(int arg)` | 独占模式获取状态。需要查询当前状态是否允许获取，然后再进行获取（使用`compareAndSetState`来做）状态。
`protected boolean tryRelease(int arg)`  | 独占模式释放状态。
`protected int tryAcquireShared(int arg)` | 共享模式获取状态。
`protected boolean tryReleaseShared(int arg)` | 共享模式释放状态。
`protected boolean isHeldExclusively()` | 在独占模式下，状态是否被占用。

实现这些方法必须是非阻塞而且是线程安全的，推荐使用该同步器的父类`java.util.concurrent.locks.AbstractOwnableSynchronizer`来设置当前的线程。

开始提到同步器内部基于一个FIFO队列，对于一个独占锁的获取和释放有以下伪码可以表示。
```
// 获取一个排他锁。
while(获取锁) {
    if (获取到) {
        退出while循环
    } else {
        if(当前线程没有入队列) {
            那么入队列
        }
        阻塞当前线程
    }
}
// 释放一个排他锁。
if (释放成功) {
    删除头节点
    激活原头节点的后继节点
}
```

#### 出队操作
```
private void setHead(Node node) { // 设置新的head节点
    head = node;
    node.thread = null;
    node.prev = null;
}
```

#### 入队操作
```
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode); // 1. 使用当前线程构造Node
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail; // (1)分配引用T指向尾节点
    if (pred != null) { // 这个if分支其实是一种优化：CAS操作失败的话才进入enq中的循环。
        node.prev = pred; // (2)将节点的前驱节点更新为尾节点（current.prev = tail）
        if (compareAndSetTail(pred, node)) { // (3)如果尾节点是T，那么将当尾节点设置为该节点（tail = current，原子更新）
            pred.next = node; // (4)T的后继节点指向当前节点（T.next = current）
            return node;
        }
    }
    enq(node);
    return node;
} 
private Node enq(final Node node) {
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize
            if (compareAndSetHead(new Node())) // (1)如果尾节点为空，那么原子化的分配一个头节点，并将尾节点指向头节点，这一步是初始化
                tail = head;
        } else {
            node.prev = t;
            if (compareAndSetTail(t, node)) { // (2)然后是重复在addWaiter中做的工作，但是在一个while(true)的循环中，直到当前节点入队为止
                t.next = node;
                return t;
            }
        }
    }
}
```

#### 独占模式获取
![acquire-min](http://www.wailian.work/images/2018/10/25/acquire-min.png)
```
public final void acquire(int arg) {
    // tryAcquire 由子类实现本身不会阻塞线程，如果返回 true，则线程继续。如果返回 false 那么就加入阻塞队列阻塞线程，并等待前继节点释放锁。
    if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        // acquireQueued返回true，说明当前线程被中断唤醒后获取到锁，重置其interrupt status为true。
        selfInterrupt();
}
```
1. 尝试获取（调用`tryAcquire`更改状态，需要保证原子性）；
1. 如果获取不到，将当前线程构造成节点`Node`并加入sync队列；
1. 再次尝试获取，如果没有获取到那么将当前线程从线程调度器上摘下，进入等待状态。
```
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) { // 等待前继节点释放锁，自旋re-check
            final Node p = node.predecessor(); // 获取前继
            if (p == head && tryAcquire(arg)) { // 前继是head，说明next就是node了，则尝试获取锁。
                setHead(node); // 前继出队，node成为head
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            // p != head 或者 p == head但是tryAcquire失败了，那么，应该阻塞当前线程等待前继唤醒。阻塞之前会再重试一次，还需要设置前继的waitStaus为SIGNAL。线程会阻塞在parkAndCheckInterrupt方法中。parkAndCheckInterrupt返回可能是前继unpark或线程被中断。
            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                // 说明当前线程是被中断唤醒的。注意：线程被中断之后会继续走到if处去判断，也就是会忽视中断。除非碰巧线程中断后acquire成功了，那么根据Java的最佳实践，需要重新设置线程的中断状态（acquire.selfInterrupt）。
                interrupted = true;
        }
    }
    finally {
        if (failed) // 出现异常
            cancelAcquire(node);
    }
}
```
`shouldParkAfterFailedAcquire`方法的作用是：
- 确定后继是否需要park;
- 跳过被取消的节点;
- 设置前继的`waitStatus`为`SIGNAL`。
```
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
	int ws = pred.waitStatus;
	if (ws == Node.SIGNAL) // 前继节点已经准备好unpark其后继了，所以后继可以安全的park
		return true;
	if (ws > 0) { // CANCELLED，跳过被取消的节点。
		do {
			node.prev = pred = pred.prev;
		} while (pred.waitStatus > 0);
		pred.next = node;
	} else { // 0 或 PROPAGATE (CONDITION用在ConditonObject，这里不会是这个值)
		compareAndSetWaitStatus(pred, ws, Node.SIGNAL); // 更新pred节点waitStatus为SIGNAL
	}
	return false;
}
```
`parkAndCheckInterrupt`就是用`LockSupport`来阻塞当前线程：
```
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);
    return Thread.interrupted();
}
```
线程被唤醒只可能是：被`unpark`，被中断或伪唤醒。被中断会设置`interrupted`，`acquire`方法返回前会`selfInterrupt`重置下线程的中断状态，如果是伪唤醒的话会for循环re-check。

#### 独占模式释放
```
public final boolean release(int arg) {
    // tryRelease由子类实现，通过设置state值来达到同步的效果。
    if (tryRelease(arg)) { // 1. 尝试释放状态
        Node h = head;
        // waitStatus为0说明是初始化的空队列
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h); // 2. 唤醒当前节点的后继节点所包含的线程
        return true;
    }
    return false;
}
```
```
private void unparkSuccessor(Node node) {
	int ws = node.waitStatus;
	if (ws < 0)
		compareAndSetWaitStatus(node, ws, 0); // 将状态设置为同步状态
	Node s = node.next; // 获取当前节点的后继节点，如果满足状态，那么进行唤醒操作
	if (s == null || s.waitStatus > 0) { // 如果没有满足状态，从尾部开始找寻符合要求的节点并将其唤醒
		s = null;
		for (Node t = tail; t != null && t != node; t = t.prev)
			if (t.waitStatus <= 0)
				s = t;
	}
	if (s != null)
		LockSupport.unpark(s.thread);
}
```

#### 共享模式获取
```
public final void acquireShared(int arg) {
    if (tryAcquireShared(arg) < 0) // 1. 尝试获取共享状态；
        doAcquireShared(arg); // 如果没有许可了则入队等待
}
private void doAcquireShared(int arg) { // 2. 获取失败进入sync队列；
    final Node node = addWaiter(Node.SHARED); // 添加队列
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) { // 等待前继释放并传递 3. 循环内判断退出队列条件；
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg); // 尝试获取
                if (r >= 0) { // 4. 获取共享状态成功； 退出队列的条件上，和独占锁之间的主要区别在于获取共享状态成功之后的行为，而如果共享状态获取成功之后会判断后继节点是否是共享模式，如果是共享模式，那么就直接对其进行唤醒操作，也就是同时激发多个线程并发的运行。
                    setHeadAndPropagate(node, r); // 获取成功则前继出队，跟独占不同的是会往后面节点传播唤醒的操作，保证剩下等待的线程能够尽快获取到剩下的许可。
                    p.next = null; // help GC
                    if (interrupted)
                        selfInterrupt();
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) // p != head || r < 0
                interrupted = true; 5. 获取共享状态失败。
        }
    }
    finally {
        if (failed)
            cancelAcquire(node);
    }
}
```
核心是这个`doAcquireShared`方法，跟独占模式的`acquireQueued`很像，主要区别在`setHeadAndPropagate`方法中，这个方法会将node设置为`head`。如果当前节点`acquire`到了之后发现还有许可可以被获取，则继续释放自己的后继，后继会将这个操作传递下去。这就是`PROPAGATE`状态的含义。
```
private void setHeadAndPropagate(Node node, int propagate) {
    Node h = head; // Record old head for check below
    setHead(node);
    // 尝试唤醒后继的节点：propagate > 0说明许可还有能够继续被线程acquire。或者之前的head被设置为PROPAGATE(PROPAGATE可以被转换为SIGNAL)说明需要往后传递。或者为null，我们还不确定什么情况。并且，后继节点是共享模式或者为如上为null。上面的检查有点保守，在有多个线程竞争获取/释放的时候可能会导致不必要的唤醒。
    if (propagate > 0 || h == null || h.waitStatus < 0) {
        Node s = node.next;
        // 后继结是共享模式或者s == null（不知道什么情况）。如果后继是独占模式，那么即使剩下的许可大于0也不会继续往后传递唤醒操作。即使后面有节点是共享模式。
        if (s == null || s.isShared())
            doReleaseShared(); // 唤醒后继节点
    }
} 
private void doReleaseShared() {
    for (;;) {
        Node h = head;
        if (h != null && h != tail) { // 队列不为空且有后继节点
            int ws = h.waitStatus;
            if (ws == Node.SIGNAL) { // 不管是共享还是独占只有节点状态为SIGNAL才尝试唤醒后继节点
                if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0)) // 将waitStatus设置为0
                    continue; // loop to recheck cases
                unparkSuccessor(h);// 唤醒后继节点
            } else if (ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE)) // 如果状态为0则更新状态为PROPAGATE，更新失败则重试
                continue; // loop on failed CAS
        }
        if (h == head) // loop if head changed
            break; // 如果过程中head被修改了则重试。
    }
}
```

#### 共享模式释放
```
public final boolean releaseShared(int arg) {
    if (tryReleaseShared(arg)) {
        doReleaseShared();
        return true;
    }
    return false;
}
```

#### `doAcquireNanos(int arg, long nanosTimeout)`
该方法提供了具备有超时功能的获取状态的调用，如果在指定的`nanosTimeout`内没有获取到状态，那么返回`false`，反之返回`true`。可以将该方法看做`acquireInterruptibly`的升级版，也就是在判断是否被中断的基础上增加了超时控制。
```
// 这个变量用在doAcquireNanos方法，也就是支持超时的获取版本。
static final long spinForTimeoutThreshold = 1000L;

private boolean doAcquireNanos(int arg, long nanosTimeout) throws InterruptedException {
    long lastTime = System.nanoTime();
    final Node node = addWaiter(Node.EXCLUSIVE);
    boolean failed = true;
    try {
        for (;;) {
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return true;
            }
            if (nanosTimeout <= 0)// 超时
                return false;
            // 如果超时时间很短的话，自旋效率会更高。
            if (shouldParkAfterFailedAcquire(p, node) && nanosTimeout > spinForTimeoutThreshold)
                LockSupport.parkNanos(this, nanosTimeout);
            long now = System.nanoTime();
            nanosTimeout -= now - lastTime;
            lastTime = now;
            if (Thread.interrupted())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```
1. 加入sync队列；
1. 条件满足直接返回；
1. 获取状态失败休眠一段时间；
1. 计算再次休眠的时间；
1. 休眠时间的判定。

![doAcquireNanos-min](http://www.wailian.work/images/2018/10/25/doAcquireNanos-min.png)

#### `acquireInterruptibly(int arg)`
该方法提供获取状态能力，当然在无法获取状态的情况下会进入sync队列进行排队，这类似`acquire`，但是和`acquire`不同的地方在于它能够在外界对当前线程进行中断的时候提前结束获取状态的操作，换句话说，就是在类似`synchronized`获取锁时，外界能够对当前线程进行中断，并且获取锁的这个操作能够响应中断并提前返回。
```
public final void acquireInterruptibly(int arg) throws InterruptedException {
	if (Thread.interrupted())
		throw new InterruptedException();
	if (!tryAcquire(arg))
		doAcquireInterruptibly(arg);
}
private void doAcquireInterruptibly(int arg)
	throws InterruptedException {
	final Node node = addWaiter(Node.EXCLUSIVE);
	boolean failed = true;
	try {
		for (;;) {
			final Node p = node.predecessor();
			if (p == head && tryAcquire(arg)) {
				setHead(node);
				p.next = null; // help GC
				failed = false;
				return;
			}
			if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) // 检测中断标志位
				throw new InterruptedException();
		}
	} finally {
		if (failed)
			cancelAcquire(node);
	}
}
```
1. 检测当前线程是否被中断；
1. 尝试获取状态；
1. 构造节点并加入sync队列；
1. 中断检测。

#### Tips
- `ReentrantLock`，`Semaphore`，`CountDownLatch`都有一个内部类`Sync`，都是继承自`AbstractQueuedSynchronizer`。
- `AQS`的核心是通过一个共享变量来同步状态，变量的状态由子类去维护，而`AQS`框架做的是：
    - 线程阻塞队列的维护
    - 线程阻塞和唤醒
- `acquire`方法用来获取锁，返回`true`说明线程获取成功继续执行，一旦返回`false`则线程加入到等待队列中，等待被唤醒，`release`方法用来释放锁。 一般来说实现的时候这两个方法被封装为`lock`和`unlock`方法。
- `CLH lock queue`其实就是一个FIFO的队列，队列中的每个结点（线程）只要等待其前继释放锁就可以了。
- 通常用`CLH lock queue`来实现自旋锁，所谓自旋锁简单来说就是线程通过循环来等待而不是睡眠。自旋的好处是线程不需要睡眠和唤醒，减小了系统调用的开销。
- `LockSupport`的`park/unpark`和`Object`的`wait/notify`：
    - 面向的对象不同；
    - 跟`Object`的`wait/notify`不同`LockSupport`的`park/unpark`不需要获取对象的监视器；
    - 实现的机制不同，因此两者没有交集。
    ```
    // 1次unpark给线程1个许可
    LockSupport.unpark(Thread.currentThread());
    // 如果线程非阻塞重复调用没有任何效果
    LockSupport.unpark(Thread.currentThread());
    // 消耗1个许可
    LockSupport.park(Thread.currentThread());
    // 阻塞
    LockSupport.park(Thread.currentThread());
    ```
- 对于`InterruptedException`如何处理最重要的一个原则就是**Don't swallow interrupts**，一般两种方法：
    - 继续设置interrupted status
    - 抛出新的`InterruptedException`
- `java.util.concurrent`中的许多可阻塞类，例如`ReentrantLock`，`Semaphore`，`CountDownLatch`，`FutureTask`，`SynchronousQueue`，`ReentrantReadWriteLock`都是基于`AQS`构建的。
    - `tryAcquire`：`ReentrantLock`，`CyclicBarrier`
    - `tryAcquireShared`：`Semaphore`，`CountDownLatch`
    - `Future.get`：`FutureTask`
    - `tryAcquire`，`tryAcquireShared`：`ReentrantReadWriteLock`

#### 示例
- `TwinsLockTest`，`SimpleLock`，`ClhSpinLock`，`LockSupportTest`，`MutexTest`

## References
- [AbstractQueuedSynchronizer的介绍和原理分析](http://ifeve.com/introduce-abstractqueuedsynchronizer/)
- [Java并发包源码学习之AQS框架（四）AbstractQueuedSynchronizer源码分析](https://www.cnblogs.com/zhanjindong/p/java-concurrent-package-aqs-AbstractQueuedSynchronizer.html)
- [Java并发包源码学习之AQS框架（一）概述](http://zhanjindong.com/2015/03/10/java-concurrent-package-aqs-overview)
- [Java并发包源码学习之AQS框架（二）CLH lock queue和自旋锁](http://zhanjindong.com/2015/03/11/java-concurrent-package-aqs-clh-and-spin-lock)
- [Java并发包源码学习之AQS框架（三）LockSupport和interrupt](http://zhanjindong.com/2015/03/14/java-concurrent-package-aqs-locksupport-and-thread-interrupt)