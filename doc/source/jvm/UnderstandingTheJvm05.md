## Chapter05

### Eclipse运行速度调优
最终`eclipse.ini`的配置
```
-vm
D:/_DevSpace/jdk1.6.0_21/bin/javaw.exe
-startup
plugins/org.eclipse.equinox.launcher_1.0.201.R35x_v20090715.jar
--launcher.library
plugins/org.eclipse.equinox.launcher.win32.win32.x86_1.0.200.v20090519
-product
org.eclipse.epp.package.jee.product
-showsplash
org.eclipse.platform
-vmargs
-Dcom.sun.management.jmxremote
-Dosgi.requiredJavaVersion=1.5
-Xverify:none
-Xmx512m
-Xms512m
-Xmn128m
-XX:PermSize=96m
-XX:MaxPermSize=96m
-XX:+DisableExplicitGC
-Xnoclassgc
-XX:+UseParNewGC
-XX:+UseConcMarkSweepGC
-XX:CMSInitiatingOccupancyFraction=85
```

### Eclipse Juno
![JvmEclipseOriginal-min-min](https://www.wailian.work/images/2019/04/16/JvmEclipseOriginal-min-min.png)

![JvmEclipseOptimize-min-min](https://www.wailian.work/images/2019/04/16/JvmEclipseOptimize-min-min.png)

![JvmEclipseFinal-min-min](https://www.wailian.work/images/2019/04/16/JvmEclipseFinal-min-min.png)
