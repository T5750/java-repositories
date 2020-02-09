# Java Instrumentation

## Introduction
[Java Instrumentation API](https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/Instrumentation.html) provides the ability to add byte-code to existing compiled Java classes.

Java Instrumentation will give a demonstration of how powerful Java is. Most importantly, this power can be realized by a developer for innovative means.
- For example using Java instrumentation, we can access a class that is loaded by the Java classloader from the JVM and modify its bytecode by inserting our custom code, all these done at runtime.
- Don’t worry about security, these are governed by the same security context applicable for Java classes and respective classloaders.

## Key Components
- Agent – is a jar file containing agent and transformer class files.
- Agent Class – A java class file, containing a method named `premain`.
- Manifest – `manifest.mf` file containing the `Premain-Class` property.
- Transformer – A Java class file implementing the interface `ClassFileTransformer`.

## What Is a Java Agent
In general, a java agent is just a specially crafted jar file. **It utilizes the [Instrumentation API](https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/Instrumentation.html) that the JVM provides to alter existing byte-code that is loaded in a JVM.**

For an agent to work, we need to define two methods:
- `premain` – will statically load the agent using -javaagent parameter at JVM startup
- `agentmain` – will dynamically load the agent into the JVM using the [Java Attach API](https://docs.oracle.com/javase/7/docs/jdk/api/attach/spec/com/sun/tools/attach/package-summary.html)

### Instrumentation Activity Sequence
![Java-Instrumentation-Activity-Flow](https://s1.wailian.download/2020/02/08/Java-Instrumentation-Activity-Flow-min.jpg)

## Loading a Java Agent
We have two types of load:
- static – makes use of the `premain` to load the agent using `-javaagent` option
- dynamic – makes use of the `agentmain` to load the agent into the JVM using the [Java Attach API](https://docs.oracle.com/javase/7/docs/jdk/api/attach/spec/com/sun/tools/attach/package-summary.html)

### Static Load
Loading a Java agent at application startup is called static load. Static load modifies the byte-code at startup time before any code is executed.

Keep in mind that the static load uses the premain method, which will run before any application code runs, to get it running we can execute:
```
java -javaagent:agent.jar -jar application.jar
```

`Launcher` -> args: `StartMyAtmApplication 2 7 8`

### Dynamic Load
**The procedure of loading a Java agent into an already running JVM is called dynamic load.** The agent is attached using the [Java Attach API](https://docs.oracle.com/javase/7/docs/jdk/api/attach/spec/com/sun/tools/attach/package-summary.html).

A more complex scenario is when we already have our ATM application running in production and we want to add the total time of transactions dynamically without downtime for our application.
```
VirtualMachine jvm = VirtualMachine.attach(jvmPid);
jvm.loadAgent(agentFile.getAbsolutePath());
jvm.detach();
```

- Starting the Application: `java -jar application.jar StartMyAtmApplication`
- Attaching Java Agent: `java -jar application.jar LoadAgent`
	* `Launcher` -> args: `LoadAgent`
- Check Application Logs

### APIs
- `addTransformer` – adds a transformer to the instrumentation engine
- `getAllLoadedClasses` – returns an array of all classes currently loaded by the JVM
- `retransformClasses` – facilitates the instrumentation of already loaded classes by adding byte-code
- `removeTransformer` – unregisters the supplied transformer
- `redefineClasses` – redefine the supplied set of classes using the supplied class files, meaning that the class will be fully replaced, not modified as with `retransformClasses`

## Creating a Java Agent
1. Create the `premain` and `agentmain` Methods: `Premain-transformClass(String className, Instrumentation instrumentation)`
2. Defining Our `ClassFileTransformer`: `AtmTransformer`
3. Creating an Agent Manifest File
    - We can find the full list of manifest attributes in the [Instrumentation Package](https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/package-summary.html) official documentation.
    ```
    Agent-Class: com.baeldung.instrumentation.agent.MyInstrumentationAgent
    Can-Redefine-Classes: true
    Can-Retransform-Classes: true
    Premain-Class: com.baeldung.instrumentation.agent.MyInstrumentationAgent
    ```

## References
- [Guide to Java Instrumentation](https://www.baeldung.com/java-instrumentation)
- [Java Instrumentation](https://javapapers.com/core-java/java-instrumentation/)