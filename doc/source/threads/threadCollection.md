# Java Multithreading

## Life Cycle of a Thread
![Life Cycle of a Thread](https://s0.wailian.download/2019/10/01/Thread_Life_Cycle-min.jpg)

- **New** − A new thread begins its life cycle in the new state. It remains in this state until the program starts the thread. It is also referred to as a born thread.
- **Runnable** − After a newly born thread is started, the thread becomes runnable. A thread in this state is considered to be executing its task.
- **Waiting** − Sometimes, a thread transitions to the waiting state while the thread waits for another thread to perform a task. A thread transitions back to the runnable state only when another thread signals the waiting thread to continue executing.
- **Timed Waiting** − A runnable thread can enter the timed waiting state for a specified interval of time. A thread in this state transitions back to the runnable state when that time interval expires or when the event it is waiting for occurs.
- **Terminated (Dead)** − A runnable thread enters the terminated state when it completes its task or otherwise terminates.

## Thread Methods

Sr.No. | Method | Description
----|------|------
1 | public void start() | Starts the thread in a separate path of execution, then invokes the run() method on this Thread object.
2 | public void run() | If this Thread object was instantiated using a separate Runnable target, the run() method is invoked on that Runnable object.
3 | public final void setName(String name) | Changes the name of the Thread object. There is also a getName() method for retrieving the name.
4 | public final void setPriority(int priority) | Sets the priority of this Thread object. The possible values are between 1 and 10.
5 | public final void setDaemon(boolean on) | A parameter of true denotes this Thread as a daemon thread.
6 | public final void join(long millisec) | The current thread invokes this method on a second thread, causing the current thread to block until the second thread terminates or the specified number of milliseconds passes.
7 | public void interrupt() | Interrupts this thread, causing it to continue execution if it was blocked for any reason.
8 | public final boolean isAlive() | Returns true if the thread is alive, which is any time after the thread has been started but before it runs to completion.

Sr.No. | Method | Description
----|------|------
1 | public static void yield() | Causes the currently running thread to yield to any other threads of the same priority that are waiting to be scheduled.
2 | public static void sleep(long millisec) | Causes the currently running thread to block for at least the specified number of milliseconds.
3 | public static boolean holdsLock(Object x) | Returns true if the current thread holds the lock on the given Object.
4 | public static Thread currentThread() | Returns a reference to the currently running thread, which is the thread that invokes this method.
5 | public static void dumpStack() | Prints the stack trace for the currently running thread, which is useful when debugging a multithreaded application.

Sr.No. | Method | Description
----|------|------
1 | public void wait() | Causes the current thread to wait until another thread invokes the notify().
2 | public void notify() | Wakes up a single thread that is waiting on this object's monitor.
3 | public void notifyAll() | Wakes up all the threads that called wait() on the same object.
4 | public void suspend() | This method puts a thread in the suspended state and can be resumed using resume() method.
5 | public void stop() | This method stops a thread completely.
6 | public void resume() | This method resumes a thread, which was suspended using suspend() method.

## Examples
- package: `com.tutorialspoint`
- `InterthreadCommunication`, `TestThread`, `TestThreadSolution`, `CallableThreadTest`, `TestThreadByRunnable`, `TestThreadByThread`, `ThreadClassDemo`, `ThreadSynchronization`, `ThreadWithoutSynchronization`

## References
- [Java - Multithreading](http://www.tutorialspoint.com/java/java_multithreading.htm)
- [Java 多线程编程](http://www.runoob.com/java/java-multithreading.html)
- [java多线程总结](http://www.cnblogs.com/rollenholt/archive/2011/08/28/2156357.html)