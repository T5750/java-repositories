# JVM

## Contents
### Java HotSpot VM Options
- [Java 6 HotSpot VM Options](doc/jvmJdk6.md)
- [Java 7 HotSpot VM Options](doc/jvmJdk7.md)
- [Java 8 HotSpot VM Options](doc/jvmJdk8.md)

### 深入理解Java虚拟机：JVM高级特性与最佳实践
- [1 走进Java](doc/UnderstandingTheJvm.md)
- [2 Java内存区域与内存溢出异常](doc/UnderstandingTheJvm02.md)
    - [运行时数据区域](doc/UnderstandingTheJvm02.md#运行时数据区域)
- [3 垃圾收集器与内存分配策略](doc/UnderstandingTheJvm03.md)
    - [垃圾收集器参数总结](doc/UnderstandingTheJvm03.md#垃圾收集器参数总结)
- [4 虚拟机性能监控与故障处理工具](doc/UnderstandingTheJvm04.md)
    - [JDK的命令行工具](doc/UnderstandingTheJvm04.md#JDK的命令行工具)
        * [jps：虚拟机进程状况工具](doc/UnderstandingTheJvm04.md#jps)
        * [jstat：虚拟机统计信息监视工具](doc/UnderstandingTheJvm04.md#jstat)
        * [jinfo：Java配置信息工具](doc/UnderstandingTheJvm04.md#jinfo)
        * [jmap：Java内存映像工具](doc/UnderstandingTheJvm04.md#jmap)
        * [jhat：虚拟机堆转储快照分析工具](doc/UnderstandingTheJvm04.md#jhat)
        * [jstack：Java堆栈跟踪工具](doc/UnderstandingTheJvm04.md#jstack)
- [5 调优案例分析与实战](doc/UnderstandingTheJvm05.md)
    - [Eclipse运行速度调优](doc/UnderstandingTheJvm05.md#Eclipse运行速度调优)
- [6 类文件结构](doc/UnderstandingTheJvm06.md)
    - [Class类文件的结构](doc/UnderstandingTheJvm06.md#Class类文件的结构)
        * [常量池](doc/UnderstandingTheJvm06.md#常量池)
        * [访问标志](doc/UnderstandingTheJvm06.md#访问标志)
        * [字段表集合](doc/UnderstandingTheJvm06.md#字段表集合)
        * [方法表集合](doc/UnderstandingTheJvm06.md#方法表集合)
        * [属性表集合](doc/UnderstandingTheJvm06.md#属性表集合)
- [7 虚拟机类加载机制](doc/UnderstandingTheJvm07.md)
    - [类加载的时机](doc/UnderstandingTheJvm07.md#类加载的时机)
    - [双亲委派模型](doc/UnderstandingTheJvm07.md#双亲委派模型)
- [8 虚拟机字节码执行引擎](doc/UnderstandingTheJvm08.md)
    - [运行时栈帧结构](doc/UnderstandingTheJvm08.md#运行时栈帧结构)
    - [基于栈的解释器执行过程](doc/UnderstandingTheJvm08.md#基于栈的解释器执行过程)
- [9 类加载及执行子系统的案例与实战](doc/UnderstandingTheJvm09.md)
    - [Tomcat：正统的类加载架构](doc/UnderstandingTheJvm09.md#Tomcat)
    - [实现远程执行功能](doc/UnderstandingTheJvm09.md#实现远程执行功能)
- [10 早期（编译期）优化](doc/UnderstandingTheJvm10.md)
    - [Javac编译器](doc/UnderstandingTheJvm10.md#Javac编译器)
    - [插入式注解处理器](doc/UnderstandingTheJvm10.md#插入式注解处理器)
- [11 晚期（运行期）优化](doc/UnderstandingTheJvm11.md)
    - [解释器与编译器](doc/UnderstandingTheJvm11.md#解释器与编译器)
    - [编译优化技术](doc/UnderstandingTheJvm11.md#编译优化技术)
- [12 Java内存模型与线程](doc/UnderstandingTheJvm12.md)
    - [内存间交互操作](doc/UnderstandingTheJvm12.md#内存间交互操作)
    - [对于volatile型变量的特殊规则](doc/UnderstandingTheJvm12.md#对于volatile型变量的特殊规则)
    - [先行发生原则](doc/UnderstandingTheJvm12.md#先行发生原则)
    - [Java线程调度](doc/UnderstandingTheJvm12.md#Java线程调度)
    - [状态转换](doc/UnderstandingTheJvm12.md#状态转换)
- [13 线程安全与锁优化](doc/UnderstandingTheJvm13.md)
    - [线程安全](doc/UnderstandingTheJvm13.md#线程安全)
    - [锁优化](doc/UnderstandingTheJvm13.md#锁优化)

### JVM虚拟机优化笔记
- [JVM虚拟机优化笔记](doc/jvmNote.md)

### JVM网摘笔记
- [JVM网摘笔记](../doc/source/jvm/jvmCollection.md)
    - [垃圾收集算法](../doc/source/jvm/jvmCollection.md#垃圾收集算法)
    - [垃圾收集器](../doc/source/jvm/jvmCollection.md#垃圾收集器)
    - [Java内存模型](../doc/source/jvm/jvmCollection.md#Java内存模型)
    - [编译过程](../doc/source/jvm/jvmCollection.md#编译过程)

### VisualVM
- [VisualVM](../doc/source/jvm/jvisualvm.md)

### JMX
- [JMX](../doc/source/jvm/jmxCollection.md)

## Runtime Environment
- [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- [OpenJDK 7](http://download.java.net/openjdk/jdk7/promoted/b147/openjdk-7-fcs-src-b147-27_jun_2011.zip)
- [Cygwin](https://cygwin.com/)
- [VisualGC](https://www.oracle.com/technetwork/java/visualgc-136680.html)
- [BTrace](https://github.com/btraceio/btrace)
- [Eclipse Juno](https://www.eclipse.org/juno/)
- [WinHex](http://www.winhex.com/winhex/index-m.html)
- [Retrotranslator](http://retrotranslator.sourceforge.net/)
- [Ideal Graph Visualizer](http://ssw.jku.at/General/Staff/TW/igv.html)