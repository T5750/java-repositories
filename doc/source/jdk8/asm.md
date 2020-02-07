# ASM

## Introduction
[ASM](https://asm.ow2.io/) is an all purpose Java bytecode manipulation and analysis framework. It can be used to modify existing classes or to dynamically generate classes, directly in binary form. ASM provides some common bytecode transformations and analysis algorithms from which custom complex transformations and code analysis tools can be built. ASM offers similar functionality as other Java bytecode frameworks, but is focused on [performance](https://asm.ow2.io/performance.html). Because it was designed and implemented to be as small and as fast as possible, it is well suited for use in dynamic systems (but can of course be used in a static way too, e.g. in compilers).

ASM is used in many projects, including:
- the **[OpenJDK](http://openjdk.java.net/)**, to generate the [lambda call sites](http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/687fd7c7986d/src/share/classes/java/lang/invoke/InnerClassLambdaMetafactory.java), and also in the [Nashorn](https://en.wikipedia.org/wiki/Nashorn_(JavaScript_engine)) [compiler](http://hg.openjdk.java.net/jdk8/jdk8/nashorn/file/096dc407d310/src/jdk/nashorn/internal/codegen/ClassEmitter.java),
- the **[Groovy](http://www.groovy-lang.org/)** compiler and the **[Kotlin](https://kotlinlang.org/)** [compiler](https://github.com/JetBrains/kotlin/blob/v1.2.30/compiler/backend/src/org/jetbrains/kotlin/codegen/ClassBuilder.java),
- **[Cobertura](http://cobertura.github.io/cobertura/)** and **[Jacoco](http://www.eclemma.org/jacoco/)**, to [instrument](https://github.com/cobertura/cobertura/blob/v1_9_4/src/net/sourceforge/cobertura/instrument/ClassInstrumenter.java) [classes](https://github.com/jacoco/jacoco/blob/v0.8.1/org.jacoco.core/src/org/jacoco/core/instr/Instrumenter.java) in order to measure code coverage,
- **[CGLIB](https://github.com/cglib/cglib)**, to dynamically generate [proxy](https://github.com/cglib/cglib/blob/RELEASE_3_2_6/cglib/src/main/java/net/sf/cglib/core/ClassEmitter.java) classes (which are used in other projects such as **[Mockito](http://site.mockito.org/)** and **[EasyMock](http://easymock.org/)**),
- **[Gradle](https://gradle.org/)**, to [generate](https://github.com/gradle/gradle/blob/v4.6.0/subprojects/core/src/main/java/org/gradle/api/internal/AsmBackedClassGenerator.java) some classes at runtime.

## ASM API Basics
The ASM API provides two styles of interacting with Java classes for transformation and generation: event-based and tree-based.

### Overview

Package | Description
---|---
`org.objectweb.asm` | Provides a small and fast bytecode manipulation framework.
`org.objectweb.asm.commons` | Provides some useful class and method adapters.
`org.objectweb.asm.signature` | Provides support for type signatures.
`org.objectweb.asm.tree` | Provides an ASM visitor that constructs a tree representation of the classes it visits.
`org.objectweb.asm.tree.analysis` | Provides a framework for static code analysis based on the asm.tree package.
`org.objectweb.asm.util` | Provides ASM visitors that can be useful for programming and debugging purposes.

### Event-based API
This API is heavily **based on the Visitor pattern** and is **similar in feel to the SAX parsing model** of processing XML documents. It is comprised, at its core, of the following components:
- `ClassReader` – helps to read class files and is the beginning of transforming a class
- `ClassVisitor` – provides the methods used to transform the class after reading the raw class files
- `ClassWriter` – is used to output the final product of the class transformation

The `ClassVisitor` methods in the event-based API are called in the following order:
```
visit
visitSource?
visitOuterClass?
( visitAnnotation | visitAttribute )*
( visitInnerClass | visitField | visitMethod )*
visitEnd
```

### Tree-based API
This API is a **more object-oriented** API and is **analogous to the JAXB model** of processing XML documents.

It's still based on the event-based API, but it introduces the `ClassNode` root class. This class serves as the entry point into the class structure.

## Working With the Event-based ASM API
the `ClassVisitor` class contains all the necessary visitor methods to create or modify all the parts of a class.

### Working With Fields
```
@Override
public FieldVisitor visitField(int access, String name, String desc,
        String signature, Object value) {
    if (name.equals(fieldName)) {
        isFieldPresent = true;
    }
    return tracer.visitField(access, name, desc, signature, value);
}

@Override
public void visitEnd() {
    if (!isFieldPresent) {
        FieldVisitor fv = tracer.visitField(access, fieldName,
                Type.BOOLEAN_TYPE.toString(), null, null);
        if (fv != null) {
            fv.visitEnd();
        }
    }
    tracer.visitEnd();
}
```
The `visitEnd` method is the last method called in order of the visitor methods. This is the recommended position to **carry out the field insertion logic**.

**It's important to be sure that all the ASM components used come from the `org.objectweb.asm` package**
```
public byte[] addField() {
    addFieldAdapter = new AddFieldAdapter("aNewBooleanField",
            org.objectweb.asm.Opcodes.ACC_PUBLIC, writer);
    reader.accept(addFieldAdapter, 0);
    return writer.toByteArray();
}
```

### Working With Methods
For most practical uses, however, we can either **modify an existing method to make it more accessible** (perhaps make it public so that it can be overridden or overloaded) or **modify a class to make it extensible**.
```
public MethodVisitor visitMethod(int access, String name, String desc,
        String signature, String[] exceptions) {
    if (name.equals("toUnsignedString0")) {
        return cv.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc,
                signature, exceptions);
    }
    return cv.visitMethod(access, name, desc, signature, exceptions);
}
```
```
public byte[] publicizeMethod() {
    pubMethAdapter = new PublicizeMethodAdapter(writer);
    reader.accept(pubMethAdapter, 0);
    return writer.toByteArray();
}
```

### Working With Classes
Along the same lines as modifying methods, we **modify classes by intercepting the appropriate visitor method**. In this case, we intercept `visit`, which is the very first method in the visitor hierarchy:
```
public void visit(int version, int access, String name, String signature,
        String superName, String[] interfaces) {
    String[] holding = new String[interfaces.length + 1];
    holding[holding.length - 1] = CLONEABLE_INTERFACE;
    System.arraycopy(interfaces, 0, holding, 0, interfaces.length);
    tracer.visit(V1_5, access, name, signature, superName, holding);
}
```

## Using the Modified Class
In addition to simply writing the output of `writer.toByteArray` to disk as a class file, there are some other ways to interact with our customized `Integer` class.

### Using the TraceClassVisitor
The ASM library provides the `TraceClassVisitor` utility class that we'll use to **introspect the modified class**. Thus we can **confirm that our changes have happened**.

Because the `TraceClassVisitor` is a `ClassVisitor`, we can use it as a drop-in replacement for a standard `ClassVisitor`
```
public MethodVisitor visitMethod(int access, String name, String desc,
        String signature, String[] exceptions) {
    if (name.equals("toUnsignedString0")) {
        return tracer.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc,
                signature, exceptions);
    }
    return tracer.visitMethod(access, name, desc, signature, exceptions);
}

public void visitEnd() {
    tracer.visitEnd();
    System.out.println(tracer.p.getText());
}
```
All the visiting will now be done with our tracer, which then can print out the content of the transformed class, showing any modifications we've made to it.

### Using Java Instrumentation
This is a more elegant solution that allows us to work with the JVM at a closer level via [Instrumentation](https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/package-summary.html).

To instrument the `java.lang.Integer` class, we **write an agent that will be configured as a command line parameter with the JVM**. The agent requires two components:
- A class that implements a method named `premain`
- An implementation of [ClassFileTransformer](https://docs.oracle.com/javase/7/docs/api/java/lang/instrument/ClassFileTransformer.html) in which we'll conditionally supply the modified version of our class

Building and packaging our code so far produces the jar that we can load as an agent. To use our customized `Integer` class in a hypothetical `YourClass.class`:
```
java -javaagent:jdk8.jar -cp . t5750.asm.instrument.PremainTest
```

## References
- [A Guide to Java Bytecode Manipulation with ASM](https://www.baeldung.com/java-asm)
- [ASM](https://asm.ow2.io/)
- [ASM 6 Developer Guide](https://asm.ow2.io/developer-guide.html)