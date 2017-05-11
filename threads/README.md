# Java 多线程

## Life Cycle of a Thread

![Life Cycle of a Thread](http://img.my.csdn.net/uploads/201705/10/1494385217_1840.jpg)

 - **新建状态**:
使用 new 关键字和 Thread 类或其子类建立一个线程对象后，该线程对象就处于新建状态。它保持这个状态直到程序 start() 这个线程。
 - **就绪状态**:
当线程对象调用了start()方法之后，该线程就进入就绪状态。就绪状态的线程处于就绪队列中，要等待JVM里线程调度器的调度。
 - **运行状态**:
如果就绪状态的线程获取 CPU 资源，就可以执行 run()，此时线程便处于运行状态。处于运行状态的线程最为复杂，它可以变为阻塞状态、就绪状态和死亡状态。
 - **阻塞状态**:
如果一个线程执行了sleep（睡眠）、suspend（挂起）等方法，失去所占用资源之后，该线程就从运行状态进入阻塞状态。在睡眠时间已到或获得设备资源后可以重新进入就绪状态。可以分为三种：
    * 等待阻塞：运行状态中的线程执行 wait() 方法，使线程进入到等待阻塞状态。
    * 同步阻塞：线程在获取 synchronized 同步锁失败(因为同步锁被其他线程占用)。
    * 其他阻塞：通过调用线程的 sleep() 或 join() 发出了 I/O 请求时，线程就会进入到阻塞状态。当sleep() 状态超时，join() 等待线程终止或超时，或者 I/O 处理完毕，线程重新转入就绪状态。
 - **死亡状态**:
一个运行状态的线程完成任务或者其他终止条件发生时，该线程就切换到终止状态。

## Thread Methods

Sr.No. | Method
----|------
1 | public void start()
2 | public void run()
3 | public final void setName(String name)
4 | public final void setPriority(int priority)
5 | public final void setDaemon(boolean on)
6 | public final void join(long millisec)
7 | public void interrupt()
8 | public final boolean isAlive()

Sr.No. | Method
----|------
1 | public static void yield()
2 | public static void sleep(long millisec)
3 | public static boolean holdsLock(Object x)
4 | public static Thread currentThread()
5 | public static void dumpStack()

Sr.No. | Method | Description
----|------|------
1 | public void wait() | Causes the current thread to wait until another thread invokes the notify().
2 | public void notify() | Wakes up a single thread that is waiting on this object's monitor.
3 | public void notifyAll() | Wakes up all the threads that called wait( ) on the same object.
4 | public void suspend() | This method puts a thread in the suspended state and can be resumed using resume() method.
5 | public void stop() | This method stops a thread completely.
6 | public void resume() | This method resumes a thread, which was suspended using suspend() method.

## Links

- [Java - Multithreading](http://www.tutorialspoint.com/java/java_multithreading.htm)
- [Java 多线程编程](http://www.runoob.com/java/java-multithreading.html)
- [java多线程总结](http://www.cnblogs.com/rollenholt/archive/2011/08/28/2156357.html)

## Copyright

Copyright 2016-2017 evangel_z.