## Chapter04

### JDK的命令行工具

名称 | 主要作用
---|------
jps | JVM Process Status Tool，显示指定系统内所有的HotSpot虚拟机进程
jstat | JVM Statistics Monitoring Tool，用于收集HotSpot虚拟机各方面的运行数据
jinfo | Configuration Info for Java，显示虚拟机配置信息
jmap | Memory Map for Java，生成虚拟机的内存转储快照（heapdump文件）
jhat | JVM Heap Dump Browser，用于分析heapdump文件，它会建立一个HTTP/HTML服务器，让用户可以在浏览器上查看分析结果
jstack | Stack Trace for Java，显示虚拟机的线程快照

#### jps
jps：虚拟机进程状况工具

`jps [options] [hostid]`

选项 | 作用
---|------
-q | 只输出LVMID，省略主类的名称
-m | 输出虚拟机进程启动时传递给main()函数的参数
-l | 输出主类的全名，如果进程执行的是Jar包，输出Jar路径
-v | 输出虚拟机进程启动时的JVM参数

#### jstat
jstat：虚拟机统计信息监视工具

`jstat [options vmid [interval[s|ms]] [count] ]`

选项 | 作用
---|------
-class | 监视类装载，卸载数量，总空间以及类装载所耗费的时间
-gc | 监视Java堆状况，包括Eden区、2个survivor区、老年代、永久代等的容量、已用空间、GC时间合计等信息
-gccapacity | 监视内容与-gc基本相同，但输出主要关注Java堆各个区域的最大和最小空间
-gcutil | 监视内容与-gc基本相同，但输出主要关注已使用空间占总空间的百分比
-gccause | 与-gcutil功能一样，但是会额外输出导致上一次GC产生的原因
-gcnew | 监视新生代GC情况
-gcnewcapacity | 监视内容与-gcnew基本相同，但输出主要关注使用到的最大和最小空间
-gcold | 监视老年代GC情况
-gcoldcapacity | 监视内容与-gcold基本相同，但输出主要关注使用到的最大和最小空间
-gcpermcapacity | 输出永久代使用到的最大和最小空间
-complier | 输出JIT 编译器编译过的方法、耗时的信息
-printcompilation | 输出已经被JIT编译的方法

#### jinfo
jinfo：Java配置信息工具

`jinfo [option] pid`

#### jmap
jmap：Java内存映像工具

`jmap [option] vmid`

选项 | 作用
---|------
-dump | 生成堆转储快照，格式为：`-dump:[live,]format=b,file=<filename>`，其中live子参数说明是否只dump出存活的对象
-finalizerinfo | 显示在F-Queue队列等待Finalizer线程执行finalizer方法的对象
-heap | 显示Java堆详细信息，如使用哪种回收器，参数配置，分代状况等
-histo | 显示堆中对象的统计信息，GC使用的算法，heap的配置及wise heap的使用情况，可以用此来判断内存目前的使用情况以及垃圾回收情况
-permstat | 已ClassLoader为统计口径显示永久代内存状态
-F | 当-dump没有响应时，强制生成dump快照

#### jhat
jhat：虚拟机堆转储快照分析工具

`jhat [option] [dumpfile]`

#### jstack
jstack：Java堆栈跟踪工具

`jstack [option] vmid`

选项 | 作用
---|------
-F | 当正常输出请求不被响应时，强制输出线程堆栈
-l | 除堆栈外，显示关于锁的附加信息
-m | 如果调用到本地方法的话，可以显示C/C++的堆栈