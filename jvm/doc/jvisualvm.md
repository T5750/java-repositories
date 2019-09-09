# Java VisualVM

## What's included
- 内存分析篇
    - 内存堆Heap
    - 永久保留区域PermGen
- CPU分析篇
- 线程分析篇

## Plugins
- Visual GC
- BTrace Workbench
- VisualVM-MBeans
    - `java.lang:type=GarbageCollector,name=ConcurrentMarkSweep`
    - `java.lang:type=GarbageCollector,name=ParNew`
    - `java.lang:type=GarbageCollector,name=PS MarkSweep`
    - `java.lang:type=GarbageCollector,name=PS Scavenge`
    - `java.lang:type=Runtime`
    - `java.nio:type=BufferPool,name=direct`

## References
- [性能分析神器VisualVM](http://www.cnblogs.com/wade-xu/p/4369094.html)