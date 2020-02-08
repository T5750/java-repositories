# Javassist/ASM Audit Log

With Spring and Hibernate on your stack, your application’s bytecode is likely enhanced or manipulated at runtime. Bytecode is the instruction set of the Java Virtual Machine (JVM), and all languages that run on the JVM must eventually compile down to bytecode. Bytecode is manipulated for a variety of reasons:

**Program analysis:**
- find bugs in your application
- examine code complexity
- find classes with a specific annotation

**Class generation:**
- lazy load data from a database using proxies

**Security:**
- restrict access to certain APIs
- code obfuscation

**Transforming classes without the Java source code:**
- code profiling
- code optimization

**And finally, adding logging to applications.**

There are several tools that can be used to manipulate bytecode, ranging from very low-level tools such as ASM, which require you to work at the bytecode level, to high level frameworks such as AspectJ, which allow you to write pure Java.

![javassist_several_tools](https://s1.wailian.download/2020/02/07/javassist_several_tools-min.png)

## Audit Log Example
- `BankTransactions`
- `ImportantLog`

There are two main advantages of using bytecode and annotations to perform the logging:
1. The logging is separated from the business logic, which helps keep the code clean and simple.
2. It is easy to remove the audit logging without modifying the source code.

## Where do we actually modify the bytecode?
We can use a core Java feature introduced in 1.5 to manipulate the bytecode. This feature is called a [Java agent](http://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html).

### A typical Java process
![javassist_typical_Java_process](https://s1.wailian.download/2020/02/07/javassist_typical_Java_process-min.png)

The command `java` is executed with the class containing our main method as the one input parameter. This starts a Java runtime environment, uses a `ClassLoader` to load the input class, and invokes the main method on the class.

### Java agent
![javassist_Java_agent](https://s1.wailian.download/2020/02/07/javassist_Java_agent-min.png)

The command `java` is run with two input parameters.
- The first is the JVM argument `-javaagent`, pointing to the agent jar.
- The second is the class containing our main method.

The `javaagent` flag tells the JVM to first load the agent. The agent’s main class must be specified in the manifest of the agent jar. Once the class is loaded, the premain method on the class is invoked. This premain method acts as a setup hook for the agent. It allows the agent to register a class transformer. When a class transformer is registered with the JVM, that transformer will receive the bytes of every class prior to the class being loaded in the JVM. This provides the class transformer with the opportunity to modify the bytes of a class as needed. Once the class transformer has modified the bytes, it returns the modified bytes back to the JVM. These bytes are then verified and loaded by the JVM.

```
public class JavassistAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Starting the agent");
        inst.addTransformer(new ImportantLogClassTransformer());
    }
}
```
The `premain` method prints out a message and then registers a class transformer. The class transformer must implement the method `transform`, which is invoked for every class loaded into the JVM. It provides the byte array of the class as input to the method, which then returns the modified byte array. If the class transformer decides not to modify the bytes of the specific class, then it can return `null`.
```
public class ImportantLogClassTransformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader, String className,
        Class classBeingRedefined, ProtectionDomain protectionDomain,
        byte[] classfileBuffer) throws IllegalClassFormatException {
        // manipulate the bytes here
        return modified bytes;
    }
}
```

## How do we modify the bytes using Javassist?
[Javassist](http://www.csg.ci.i.u-tokyo.ac.jp/~chiba/javassist/) is a bytecode manipulation framework with both a high level and low level API.

![javassist_diagram](https://s1.wailian.download/2020/02/07/javassist_diagram-min.png)

Javassist uses a `CtClass` object to represent a class. These `CtClass` objects can be obtained from a `ClassPool` and are used to modify Classes. The `ClassPool` is a container of `CtClass` objects implemented as a `HashMap` where the key is the name of the class and the value is the `CtClass` object representing the class. The default `ClassPool` uses the same classpath as the underlying JVM. Therefore, in some cases, you may need to add classpaths or class bytes to a `ClassPool`.

Similar to a Java class which contains fields, methods, and constructors, a `CtClass` object contains `CtFields`, `CtConstructors`, and `CtMethods`. All of these objects can be modified.

Below are a few of the ways to modify a method:

![javassist_modify_method](https://s1.wailian.download/2020/02/07/javassist_modify_method-min.png)

The transform method of the Class transformer needs to perform the following steps:
1. Convert byte array to a `CtClass` object
2. Check each method of `CtClass` for the annotation `@ImportantLog`
3. If `@ImportantLog` annotation is present on the method, then
    - Get important parameter method indexes
    - Add logging statement to beginning of the method

### Tips
As you write Java code using Javassist, be wary of the following gotchas:
- The JVM uses slashes(`/`) between packages while Javassist uses dots(`.`).
- When inserting more than one line of Java code, the code needs to go inside brackets.
- When referencing method parameter values using `$1`, `$2`, etc, know that `$0` is reserved for `this`. This means the value of the first parameter to your method is `$1`.
- Annotations are given a visible and invisible tag. Invisible annotations cannot be seen at runtime.

```
java -javaagent:jdk8.jar -cp .;C:\Users\hero\.m2\repository\org\javassist\javassist\3.26.0-GA\javassist-3.26.0-GA.jar t5750.module.log.BankTransactions
```

Debug: IDE -> VM options: `-javaagent:jdk8.jar`

### Summary
`ImportantLogClassTransformer`
- On the positive side, the amount of code written is pretty minimal and we did not actually have to write bytecode to use Javassist.
- The big drawback is that writing Java code in quotes can become tedious.

## How do we modify the bytes using ASM?
[ASM](http://asm.ow2.org/) is a bytecode manipulation framework that has a small memory footprint and is relatively fast. I consider ASM to be the industry standard for bytecode manipulation, as even Javassist uses ASM under the hood. ASM provides both object and event-based libraries, but here I’ll focus on the event-based model.

In ASM’s event-based model, all of these class components can be considered events.

![asm_Classes](https://s1.wailian.download/2020/02/07/asm_Classes-min.png)

The class events for ASM can be found on a `ClassVisitor`. In order “see” these events, you must create a classVisitor that overrides the desired components you want to see.

![asm_ClassVisitor](https://s1.wailian.download/2020/02/08/asm_ClassVisitor-min.png)

![asm_diagram](https://s1.wailian.download/2020/02/08/asm_diagram-min.png)

![asm_diagram_BankTrans](https://s1.wailian.download/2020/02/07/asm_diagram_BankTrans-min.png)

In addition to a class visitor, we need something to parse the class and generate events.
- ASM provides an object called a `ClassReader` for this purpose. The reader parses the class and produces events.
- After the class has been parsed, we need a `ClassWriter` to consume the events, converting them back to a class byte array.

```
public byte[] transform(ClassLoader loader, String className,
    Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
    byte[] classfileBuffer) throws IllegalClassFormatException {
    ClassReader cr = new ClassReader(classfileBuffer);
    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
    ClassVisitor cv = new LogMethodClassVisitor(cw, className);
    cr.accept(cv, 0);
    return cw.toByteArray();
}
```
The `accept` call to the `ClassReader` says parse the class.
```
public class LogMethodClassVisitor extends ClassVisitor {
    private String className;
           
    public LogMethodClassVisitor(ClassVisitor cv, String pClassName) {
        super(Opcodes.ASM6, cv);
        className = pClassName;
    }
                                                         
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
        String signature, String[] exceptions) {
        //put logic in here
    }
}
```
Note that `visitAnnotation` returns an `AnnotationVisitor`.
```
public class PrintMessageMethodVisitor extends MethodVisitor {
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        // 1. check method for annotation @ImportantLog
        // 2. if annotation present, then get important method param indexes
    }
                                                 
    @Override
    public void visitCode() {
        // 3. if annotation present, add logging to beginning of the method
    } 
}
```

### Tips
As you write Java code using ASM, be wary of the following gotchas:
- In the event-model, the events for a class or method will always occur in a particular order. For example, the annotations on a method will always be visited before the actual code.
- When referencing method parameter values using `$1`, `$2`, etc., know that `$0` is reserved for `this`. This means the value of the first parameter to your method is `$1`.

```
java -javaagent:jdk8.jar -cp .;C:\Users\hero\.m2\repository\org\ow2\asm\asm\6.0\asm-6.0.jar;C:\Users\hero\.m2\repository\org\ow2\asm\asm\6.0\asm-util-6.0.jar t5750.module.log.BankTransactions
```

## Differences
One of the major differences between Javassist and ASM can be seen above. With ASM, you have to write code at the bytecode level when modifying methods, meaning you need to have a good understanding of how the JVM works. You need to know exactly what is on your stack and the local variables at a given moment of time. While writing at the bytecode level opens up the door in terms of functionality and optimization, it does mean ASM has a long developer ramp up time.

## References
- [Diving Into Bytecode Manipulation: Creating an Audit Log With ASM and Javassist](https://blog.newrelic.com/engineering/diving-bytecode-manipulation-creating-audit-log-asm-javassist/)