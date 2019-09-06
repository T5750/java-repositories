## Java 8 HotSpot VM Options

### Deprecated and Removed Options
Options | Description | Type
---|-----|---
-Xincgc | Enables incremental garbage collection | deprecated
-Xrunlibname | Loads the specified debugging/profiling library | This option was superseded by the `-agentlib` option.
-XX:CMSIncrementalDutyCycle=percent | Sets the percentage of time (0 to 100) between minor collections that the concurrent collector is allowed to run. | deprecated
-XX:CMSIncrementalDutyCycleMin=percent | Sets the percentage of time (0 to 100) between minor collections that is the lower bound for the duty cycle when `-XX:+CMSIncrementalPacing` is enabled. | deprecated
-XX:+CMSIncrementalMode | Enables the incremental mode for the CMS collector. | deprecated
-XX:CMSIncrementalOffset=percent | Sets the percentage of time (0 to 100) by which the incremental mode duty cycle is shifted to the right within the period between minor collections. | deprecated
-XX:+CMSIncrementalPacing | Enables automatic adjustment of the incremental mode duty cycle based on statistics collected while the JVM is running. | deprecated
-XX:CMSIncrementalSafetyFactor=percent | Sets the percentage of time (0 to 100) used to add conservatism when computing the duty cycle. | deprecated
-XX:CMSInitiatingPermOccupancyFraction=percent | Sets the percentage of the permanent generation occupancy (0 to 100) at which to start a GC. | deprecated
-XX:MaxPermSize=size | Sets the maximum permanent generation space size (in bytes). | deprecated
-XX:PermSize=size | Sets the space (in bytes) allocated to the permanent generation that triggers a garbage collection if it is exceeded. | deprecated
-XX:+UseSplitVerifier | Enables splitting of the verification process. By default, this option was enabled in the previous releases, and verification was split into two phases: type referencing (performed by the compiler) and type checking (performed by the JVM runtime). | deprecated
-XX:+UseStringCache | Enables caching of commonly allocated strings. | removed

### Metaspace
```
java -XX:+PrintFlagsFinal -version |grep JVMParamName
# Windows
java -XX:+PrintFlagsFinal -version |findstr JVMParamName
java -XX:+PrintFlagsFinal -version |grep Metaspace
jinfo -flag MetaspaceSize pid
```

### CMS
#### CMS Collection Phases
Phase | Description
---|-----
(1) Initial Mark(Stop the World Event) | Objects in old generation are “marked” as reachable including those objects which may be reachable from young generation. Pause times are typically short in duration relative to minor collection pause times.
(2) Concurrent Marking | Traverse the tenured generation object graph for reachable objects concurrently while Java application threads are executing. Starts scanning from marked objects and transitively marks all objects reachable from the roots. The mutators are executing during the concurrent phases 2, 3, and 5 and any objects allocated in the CMS generation during these phases (including promoted objects) are immediately marked as live.
(3) Remark(Stop the World Event) | Finds objects that were missed by the concurrent mark phase due to updates by Java application threads to objects after the concurrent collector had finished tracing that object.
(4) Concurrent Sweep | Collects the objects identified as unreachable during marking phases. The collection of a dead object adds the space for the object to a free list for later allocation. Coalescing of dead objects may occur at this point. Note that live objects are not moved.
(5) Resetting | Prepare for next concurrent collection by clearing data structures. 

### G1
参数及默认值 | 描述信息
---|-----
-XX:MaxGCPauseMillis=200 | 期望的最大停顿时间。千万不要对这个参数有误解，认为设置它为10，就能控制每次停顿时间都不会10ms。这种极端的设置只会适得其反，导致每次Mixed GC只能回收很少一部分区域，从而让情况不断恶化，发生FullGC的概率大大提高
-XX:G1HeapRegionSize=ergo | 堆的Region的大小，整个堆最多划分为2048个Region，Region的大小一定是在1～32M之间，并且是2的N次方。 所以，如果堆的尺寸小于2G，那么Region数量就会少于2048
-XX:GCPauseTimeInterval=ergo | 最大停顿时间间隔的目标，G1默认没有设置这个目标，允许G1在一些极端的情况下连续执行垃圾回收
-XX:ParallelGCThreads=ergo | 并行阶段最大的线程数，JVM根据CPU核心数N进行计算，如果N<8，那么`ParallelGCThreads=N`；如果N>=8，那么`ParallelGCThreads=N*5/8`
-XX:ConcGCThreads=ergo | 并发阶段最大的线程数，默认值是-XX:ParallelGCThreads的值除以4
-XX:+G1UseAdaptiveIHOP | G1是否使用自适应初始化堆占用百分比
-XX:InitiatingHeapOccupancyPercent=45 | 设置触发全局并发标记周期的整个Java堆占用百分比阈值，即Java堆占用这么多空间后，就会进入初始化标记->并发标记->重新标记->清理的生命周期
-XX:G1NewSizePercent=5 | 表示年轻代占用堆大小的最小百分比，还有另一个参数`-XX:G1MaxNewSizePercent=60`，表示年轻代占用堆大小的最大百分比，JVM会在这两个百分比之间变化
-XX:G1HeapWastePercent=5 | 设置你允许浪费的堆的百分比，如果可回收百分比低于这个百分比，那么G1就不会触发Mixed GC
-XX:G1MixedGCLiveThresholdPercent=85 | 如果老年代Region中存活对象超过这个比例，不会被选入CSet，Mixed GC也就不会回收这个Region。因为存活对象太多，回收价值不大
-XX:G1MixedGCCountTarget=8 | 全局并发标记周期后，对存活对象上限为G1MixedGCLiveThresholdPercent的老年代Region执行Mixed GC的次数上限，默认值是8次，即最多不能超过8次Mixed GC
-XX:G1ReservePercent=10 | 设置作为空闲空间的预留内存百分比，以减少晋升失败、降低堆空间溢出的风险。默认值是10%。增加或减少百分比时，请确保对总的Java堆调整相同的量
-XX:G1OldCSetRegionThresholdPercent=10 | 设置Mixed GC期间要回收的老年代Region数量上限，即一次Mixed GC中最多可以被选入CSet中的老年代Region数量。默认值是Java堆的10%

>备注：值为ergo意味着其具体值与环境有关。

#### G1 Collection Phases
Phase | Description
---|-----
(1) Initial Mark(Stop the World Event) | This is a stop the world event. With G1, it is piggybacked on a normal young GC. Mark survivor regions (root regions) which may have references to objects in old generation.
(2) Root Region Scanning | Scan survivor regions for references into the old generation. This happens while the application continues to run. The phase must be completed before a young GC can occur.
(3) Concurrent Marking | Find live objects over the entire heap. This happens while the application is running. This phase can be interrupted by young generation garbage collections.
(4) Remark(Stop the World Event) | Completes the marking of live object in the heap. Uses an algorithm called snapshot-at-the-beginning (SATB) which is much faster than what was used in the CMS collector.
(5) Cleanup(Stop the World Event and Concurrent) | Performs accounting on live objects and completely free regions. (Stop the world) Scrubs the Remembered Sets. (Stop the world) Reset the empty regions and return them to the free list. (Concurrent)
(*) Copying(Stop the World Event) | These are the stop the world pauses to evacuate or copy live objects to new unused regions. This can be done with young generation regions which are logged as [GC pause (young)]. Or both young and old generation regions which are logged as [GC Pause (mixed)].

### References
- [Java Platform, Standard Edition Tools Reference](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)
- [JVM参数MetaspaceSize的误解](https://www.jianshu.com/p/b448c21d2e71)
- [剖析G1（Garbage First）](https://www.jianshu.com/p/7dd309cc3442)
- [Getting Started with the G1 Garbage Collector](https://www.oracle.com/technetwork/tutorials/tutorials-1876574.html)