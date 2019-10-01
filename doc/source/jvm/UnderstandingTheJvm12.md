## Chapter12

### 内存间交互操作
![jvm-memory-min](https://www.wailian.work/images/2019/04/23/jvm-memory-min.png)

- lock（锁定），作用于主内存的变量，它把一个变量标识为一条线程独占的状态。
- unlock（解锁），作用于主内存的变量，它把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定。
- read（读取），作用于主内存的变量，它把一个变量的值从主内存传输到线程的工作内存中，以便随后的load动作使用。
- load（载入），作用于工作内存的变量，它把read操作从主内存中得到的变量值放入工作内存的变量副本中。
- use（使用），作用于工作内存的变量，它把工作内存中一个变量的值传递给执行引擎，每当虚拟机遇到一个需要使用到变量的值的字节码指令时就会执行这个操作。
- assign（赋值），作用于工作内存的变量，它把一个从执行引擎接收到的值赋给工作内存的变量，每当虚拟机遇到一个给变量赋值的字节码指令时执行这个操作。
- store（存储），作用于工作内存的变量，它把工作内存中一个变量的值传送到主内存中，以便随后write操作使用。
- write（写入），作用于主内存的变量，它把Store操作从工作内存中得到的变量的值放入主内存的变量中。

### 对于volatile型变量的特殊规则
1. 保证可见性
1. 禁止指令重排序优化

### 先行发生原则
- 程序次序规则：在一个线程内，按照程序代码顺序，书写在前面的操作先于发生在书写在后面的操作。准确的说，应该是控制流顺序而不是程序代码顺序，因为要考虑分支、循环等结构。
- 管程锁定规则：一个unlock操作先行发生于后面对同一个锁的lock操作。
- volatile变量规则：对一个volatile变量的写操作先行发生于后面对这个变量的读操作，这里的“后面”是指时间上的先后顺序
- 线程启动规则：Thread对象的start方法先行发生于此线程的每一个动作
- 线程终止规则：线程中的所有操作都先于此线程的终止检测，我们可以通过Thread.join()方法结束。Thread.Alive()的返回值等手段检到线程已经终止执行
- 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过Thread.interrupted()方法检测到是否有中断发生
- 对象终结规则：一个对象的初始化完成（构造函数执行结束）先行发生于它的finalize()方法的开始
- 传递性：如果操作A先行发生于操作B，操作B先行发生于操作C，那就得出操作A先行发生于操作C的结论

### Java线程调度

Java线程优先级 | Windows线程优先级
-----|-----
1（Thread.MIN_PRIORITY） | THREAD_PRIORITY_LOWEST
2 | THREAD_PRIORITY_LOWEST
3 | THREAD_PRIORITY_BELOW_NORMAL
4 | THREAD_PRIORITY_BELOW_NORMAL
5（Thread.NORM_PRIORITY） | THREAD_PRIORITY_NORMAL
6 | THREAD_PRIORITY_ABOVE_NORMAL
7 | THREAD_PRIORITY_ABOVE_NORMAL
8 | THREAD_PRIORITY_HIGHEST
9 | THREAD_PRIORITY_HIGHEST
10（Thread.MAX_PRIORITY） | THREAD_PRIORITY_TIME_CRITICAL

### 状态转换
线程状态转换关系

![jvm-thread-min-min](https://www.wailian.work/images/2019/04/23/jvm-thread-min-min.png)