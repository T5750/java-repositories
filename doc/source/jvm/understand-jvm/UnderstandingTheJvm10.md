## 深入理解Java虚拟机 Chapter10

### Javac编译器
Javac的编译过程

![jvm-javac-min-min](https://s0.wailian.download/2019/04/22/jvm-javac-min-min.png)

Javac编译过程的主体代码：`com.sun.tools.javac.main.JavaCompiler`

![jvm-javac-code-min-min](https://s0.wailian.download/2019/04/22/jvm-javac-code-min-min.png)

### 插入式注解处理器
```
javac -encoding UTF-8 evangel/hzcourse/chapter10/namecheck/NameChecker.java
javac -encoding UTF-8 evangel/hzcourse/chapter10/namecheck/NameCheckProcessor.java
javac -processor evangel.hzcourse.chapter10.namecheck.NameCheckProcessor -encoding UTF-8 evangel/hzcourse/chapter10/namecheck/BADLY_NAMED_CODE.java
```