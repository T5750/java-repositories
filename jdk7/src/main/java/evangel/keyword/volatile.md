# Java™ Platform Standard Ed. 7

## `volatile`
### 内存可见性
由于Java内存模型(`JMM`)规定，所有的变量都存放在主内存中，而每个线程都有着自己的工作内存(高速缓存)。

线程在工作时，需要将主内存中的数据拷贝到工作内存中。这样对数据的任何操作都是基于工作内存(效率提高)，并且不能直接操作主内存以及其他线程工作内存中的数据，之后再将更新之后的数据刷新到主内存中。
>这里所提到的主内存可以简单认为是**堆内存**，而工作内存则可以认为是**栈内存**。

![mem-min](http://www.wailian.work/images/2018/10/23/mem-min.png)

所以，在并发运行时，可能会出现线程B所读取到的数据是线程A更新之前的数据。显然这肯定是会出问题的，因此`volatile`的作用出现了：
>当一个变量被`volatile`修饰时，任何线程对它的写操作都会立即刷新到主内存中，并且会强制让缓存了该变量的线程中的数据清空，必须从主内存重新读取最新数据。

_`volatile`修饰之后并不是让线程直接从主内存中获取数据，依然需要将变量拷贝到工作内存中。_

`volatile`并**不能**保证线程安全性！
>这是因为虽然`volatile`保证了内存可见性，每个线程拿到的值都是最新值，但`count++`这个操作并不是原子的，这里面涉及到获取值、自增、赋值的操作并不能同时完成。

- 所以想到达到线程安全可以使这三个线程串行执行(其实就是单线程，没有发挥多线程的优势)。
- 也可以使用`synchronize`或者是锁的方式来保证原子性。
- 还可以用`atomic`包中`AtomicInteger`来替换`int`，它利用了`CAS`算法来保证了原子性。

### 指令重排
`volatile`可以防止JVM进行指令重排优化。举一个伪代码：
```
int a=10 ;// 1
int b=20 ;// 2
int c= a+b ;// 3
```
一段特别简单的代码，理想情况下它的执行顺序是：`1>2>3`。但有可能经过JVM优化之后的执行顺序变为了`2>1>3`。

#### 指令重排的的应用
一个经典的使用场景就是双重懒加载的单例模式：
```
public class Singleton {
    private static volatile Singleton singleton;
    private Singleton() {
    }
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    // 防止指令重排
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```
如果不用，`singleton = new Singleton();`，这段代码其实是分为三步：
1. 分配内存空间。
1. 初始化对象。
1. 将`singleton`对象指向分配的内存地址。

加上`volatile`是为了让以上的三步操作顺序执行，反之，有可能第二步在第三步之前被执行，就有可能某个线程拿到的单例对象是还没有初始化的，以致于报错。

### 示例
- `VolatileTest`，`VolatileInc`，`Singleton`

### 术语定义
术语 | 英文单词 | 描述
---|---|-----
共享变量 |  | 在多个线程之间能够被共享的变量被称为共享变量。共享变量包括所有的实例变量，静态变量和数组元素。他们都被存放在堆内存中，`volatile`只作用于共享变量。
内存屏障 | Memory Barriers | 是一组处理器指令，用于实现对内存操作的顺序限制。
缓冲行 | Cache line | 缓存中可以分配的最小存储单位。处理器填写缓存线时会加载整个缓存线，需要使用多个主内存读周期。
原子操作 | Atomic operations | 不可中断的一个或一系列操作。
缓存行填充 | cache line fill | 当处理器识别到从内存中读取操作数是可缓存的，处理器读取整个缓存行到适当的缓存（L1，L2，L3的或所有）
缓存命中 | cache hit | 如果进行高速缓存行填充操作的内存位置仍然是下次处理器访问的地址时，处理器从缓存中读取操作数，而不是从内存。
写命中 | write hit | 当处理器将操作数写回到一个内存缓存的区域时，它首先会检查这个缓存的内存地址是否在缓存行中，如果存在一个有效的缓存行，则处理器将这个操作数写回到缓存，而不是写回到内存，这个操作被称为写命中。
写缺失 | write misses the cache | 一个有效的缓存行被写入到不存在的内存区域。

### 为什么要使用`volatile`
`volatile`变量修饰符如果使用恰当的话，它比`synchronized`的**使用和执行成本会更低**，因为它不会引起线程上下文的切换和调度。

### 总结
`volatile`在Java并发中用的很多，比如像`atomic`包中的`value`、以及`AbstractQueuedLongSynchronizer`中的`state`都是被定义为`volatile`来用于保证内存可见性。

## References
- [你应该知道的 volatile 关键字](https://crossoverjie.top/2018/03/09/volatile/)
- [聊聊并发（一）深入分析Volatile的实现原理](http://ifeve.com/volatile/)