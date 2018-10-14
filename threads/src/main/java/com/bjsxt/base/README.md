# 并发编程笔记

## 基础篇
### 1.1 线程安全
- 线程安全：当多个线程访问某一个类（对象或方法）时，这个类始终都能表现出正确的行为，那么这个类（对象或方法）就是线程安全的。
- `synchronized`：可以在任意对象及方法上加锁，而加锁的这段代码称为“互斥区”或“临界区”。

### 1.2 多个线程多个锁
- 关键字synchronized取得的锁都是对象锁，而不是把一段代码（方法）当做锁，所以代码中哪个线程先执行synchronized关键字的方法，哪个线程就持有该方法所属对象的锁（Lock），两个对象，线程获得的就是两个不同的锁，它们互不影响。
- 在静态方法上加synchronized关键字，表示锁定.class类，类一级别的锁（独占.class类）。

### 1.3 对象锁的同步和异步
- 同步：`synchronized`，概念：共享，需要满足两个特性：
   * 原子性（同步）
   * 可见性
- 异步：`asynchronized`，概念：独立

### 1.4 脏读
- 加锁考虑业务整体性，即为`setValue`/`getValue`方法同时加锁`asynchronized`，保持业务（service）的原子性。示例：`DirtyRead`

### 1.5 `synchronized`其他概念
- `synchronized`拥有锁重入的功能。示例：`SyncDubbo2`
- 出现异常，锁自动释放。示例：`SyncException`

### 1.6 `synchronized`代码块
- 使用`synchronized`代码块优化执行时间，也就是通常所说的减小锁的粒度。`Optimize`
- `synchronized`可以使用任意的`Object`进行加锁。`ObjectLock`
- 不要使用`String`的常量加锁，会出现死循环问题。`StringLock`
- 锁对象的改变问题，当使用一个对象进行加锁时，要注意对象本身发生改变时，那么持有的锁就不同。`ModifyLock`
- 死锁问题。`DeadLock`

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
- `BlockingQueue`：是一个队列，并且支持阻塞的机制。

### 2.3 `ThreadLocal`
- `ThreadLocal`：线程局部变量，是一种多线程间并发访问变量的解决方案。
- `ThreadLocal`完全不提供锁，而使用以空间换时间的手段，为每个线程提供变量的独立副本，以保障线程安全。
- 从性能上说，`ThreadLocal`不具有绝对的优势，但作为一套与锁完全无关的线程安全解决方案，可在一定程度上减少锁竞争。

### 2.4 单例&多线程
- 两种比较经典的单例模式：
    - double check instance
    - static inner class

