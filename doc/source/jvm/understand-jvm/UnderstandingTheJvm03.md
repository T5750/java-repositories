## 深入理解Java虚拟机 Chapter03

### 垃圾收集器
![jvm-gc-min](https://s0.wailian.download/2018/11/01/jvm-gc-min.jpg)

### 垃圾收集器参数总结

参数 | 描述
---|------
UseSerialGC | 虚拟机运行在Client模式下的默认值，打开此开关后，使用Serial + Serial Old 的收集器组合进行内存回收
UseParNewGC | 打开此开关后，使用ParNew + Serial Old 的收集器组合进行内存回收
UseConcMarkSweepGC | 打开此开关后，使用ParNew + CMS + Serial Old 的收集器组合进行内存回收。Serial Old 收集器将作为CMS 收集器出现Concurrent Mode Failure失败后的后备收集器使用
UseParallelGC | 虚拟机运行在Server 模式下的默认值，打开此开关后，使用Parallel Scavenge + Serial Old（PS MarkSweep）的收集器组合进行内存回收
UseParallelOldGC | 打开此开关后，使用Parallel Scavenge + Parallel Old 的收集器组合进行内存回收
SurvivorRatio | 新生代中Eden 区域与Survivor 区域的容量比值，默认为8，代表Eden:Survivor=8:1
PretenureSizeThreshold | 直接晋升到老年代的对象大小，设置这个参数后，大于这个参数的对象将直接在老年代分配
MaxTenuringThreshold | 晋升到老年代的对象年龄。每个对象在坚持过一次Minor GC 之后，年龄就加1，当超过这个参数值时就进入老年代
UseAdaptiveSizePolicy | 动态调整Java 堆中各个区域的大小以及进入老年代的年龄
HandlePromotionFailure | 是否允许分配担保失败，即老年代的剩余空间不足以应付新生代的整个Eden 和Survivor 区的所有对象都存活的极端情况
ParallelGCThreads | 设置并行GC 时进行内存回收的线程数
GCTimeRatio | GC 时间占总时间的比率，默认值为99，即允许1% 的GC 时间。仅在使用Parallel Scavenge 收集器时生效
MaxGCPauseMillis | 设置GC 的最大停顿时间。仅在使用Parallel Scavenge 收集器时生效
CMSInitiatingOccupancyFraction | 设置CMS 收集器在老年代空间被使用多少后触发垃圾收集。默认值为68%，仅在使用CMS 收集器时生效
UseCMSCompactAtFullCollection | 设置CMS 收集器在完成垃圾收集后是否要进行一次内存碎片整理。仅在使用CMS 收集器时生效
CMSFullGCsBeforeCompaction | 设置CMS 收集器在进行若干次垃圾收集后再启动一次内存碎片整理。仅在使用CMS 收集器时生效