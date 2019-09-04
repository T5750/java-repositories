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
java -XX:+PrintFlagsFinal -version |grep Metaspace
jinfo -flag MetaspaceSize pid
```

### References
- [Java Platform, Standard Edition Tools Reference](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)
- [JVM参数MetaspaceSize的误解](https://www.jianshu.com/p/b448c21d2e71)