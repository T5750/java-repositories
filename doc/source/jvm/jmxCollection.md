# JMX

## Java Management Extensions (JMX)
>The JMX technology provides the tools for building distributed, Web-based, modular and dynamic solutions for managing and monitoring devices, applications, and service-driven networks. By design, this standard is suitable for adapting legacy systems, implementing new management and monitoring solutions, and plugging into those of the future. 

## JMX架构图
![jmx-min](https://s0.wailian.download/2019/08/19/jmx-min.png)

1. 基础层：主要是MBean，被管理的资源
2. 适配层：MBeanServer，主要是提供对资源的注册和管理
3. 接入层：提供远程访问的入口

类型 | 描述
---|------
standard MBean | 这种类型的MBean最简单，能管理的资源（包括属性，方法，时间）必须定义在接口中，然后MBean必须实现这个接口。它的命名也必须遵循一定的规范，例如MBean为`JmxHello`，则接口必须为`JmxHelloMBean`
dynamic MBean | 必须实现`javax.management.DynamicMBean`接口，所有的属性，方法都在运行时定义
open MBean | 此MBean的规范还不完善，正在改进中
model MBean | 与标准和动态MBean相比，可以不用写MBean类，只需使用`javax.management.modelmbean.RequiredModelMBean`即可。`RequiredModelMBean`实现了`ModelMBean`接口，而`ModelMBean`扩展了`DynamicMBean`接口，因此与`DynamicMBean`相似，Model MBean的管理资源也是在运行时定义的。与`DynamicMBean`不同的是，`DynamicMBean`管理的资源一般定义在`DynamicMBean`中（运行时才决定管理那些资源），而model MBean管理的资源并不在MBean中，而是在外部（通常是一个类），只有在运行时，才通过set方法将其加入到model MBean中

## Tomcat
```
# 相关 JMX 代理侦听开关
-Dcom.sun.management.jmxremote=true
# 服务器端的IP
-Djava.rmi.server.hostname
# 相关 JMX 代理侦听请求的端口
-Dcom.sun.management.jmxremote.port=911
# 指定是否使用 SSL 通讯
-Dcom.sun.management.jmxremote.ssl=false
# 指定是否需要密码验证
-Dcom.sun.management.jmxremote.authenticate=false
```

## Results
- `JmxHelloAgent`
- `JmxClient`

### JMX
```
nohup java -jar -Xms512M -Xmx512M -Xmn256M -XX:PermSize=96M -XX:MaxPermSize=96M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xloggc:logs/gc.log -XX:+PrintGCTimeStamps -Djava.rmi.server.hostname=192.168.1.110 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=911 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false spring-boot.jar &
```

### jstatd
```
nohup ${JAVA_HOME}/bin/jstatd -J-Djava.rmi.server.hostname=192.168.1.110 -J-Djava.security.policy=${JAVA_HOME}/bin/jstatd.all.policy -J-Dcom.sun.management.jmxremote.authenticate=false -J-Dcom.sun.management.jmxremote.ssl=false -J-Dcom.sun.management.jmxremote.port=911 &
```

## References
- [JMX超详细解读](https://www.cnblogs.com/dongguacai/p/5900507.html)
- [使用Java VisualVM监控远程JVM](https://www.jianshu.com/p/2a6658e94ae2)
- [Java Management Extensions (JMX) Technology](https://www.oracle.com/technetwork/java/javase/tech/javamanagement-140525.html)