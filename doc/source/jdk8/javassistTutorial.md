# Javassist Tutorial

## 1. Reading and writing bytecode
The class `Javassist.CtClass` is an abstract representation of a class file. A `CtClass` (compile-time class) object is a handle for dealing with a class file.
```
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("test.Rectangle");
cc.setSuperclass(pool.get("test.Point"));
cc.writeFile();
```

### Defining a new class
To define a new class from scratch, `makeClass()` must be called on a `ClassPool`.
```
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.makeClass("Point");
```

### Frozen classes
If a `CtClass` object is converted into a class file by `writeFile()`, `toClass()`, or `toBytecode()`, Javassist freezes that `CtClass` object. Further modifications of that `CtClass` object are not permitted. This is for warning the developers when they attempt to modify a class file that has been already loaded since the JVM does not allow reloading a class.

To disallow pruning a particular `CtClass`, `stopPruning()` must be called on that object in advance

### Class search path
```
ClassPool pool = ClassPool.getDefault();
pool.insertClassPath("/usr/local/javalib");
```

## 2. ClassPool
A `ClassPool` object is a container of `CtClass` objects. Once a `CtClass` object is created, it is recorded in a `ClassPool` for ever. This is because a compiler may need to access the `CtClass` object later when it compiles source code that refers to the class represented by that `CtClass`.

### Avoid out of memory
```
CtClass cc = ... ;
cc.writeFile();
cc.detach();
```
```
ClassPool cp = new ClassPool(true);
// if needed, append an extra search path by appendClassPath()
ClassPool cp = new ClassPool();
cp.appendSystemPath();  // or append another path by appendClassPath()
```

### Cascaded ClassPools
Multiple `ClassPool` objects can be cascaded like `java.lang.ClassLoader`.
```
ClassPool parent = ClassPool.getDefault();
ClassPool child = new ClassPool(parent);
child.insertClassPath("./classes");
```
```
ClassPool parent = ClassPool.getDefault();
ClassPool child = new ClassPool(parent);
child.appendSystemPath();         // the same class path as the default one.
child.childFirstLookup = true;    // changes the behavior of the child.
```

### Changing a class name for defining a new class
```
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
cc.setName("Pair");
```
```
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
CtClass cc1 = pool.get("Point");   // cc1 is identical to cc.
cc.setName("Pair");
CtClass cc2 = pool.get("Pair");    // cc2 is identical to cc.
CtClass cc3 = pool.get("Point");   // cc3 is not identical to cc.
```

### Renaming a frozen class for defining a new class
Once a `CtClass` object is converted into a class file by `writeFile()` or `toBytecode()`, Javassist rejects further modifications of that `CtClass` object.
```
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
cc.writeFile();
CtClass cc2 = pool.getAndRename("Point", "Pair");
```
If `getAndRename()` is called, the `ClassPool` first reads `Point.class` for creating a new `CtClass` object representing `Point` class. However, it renames that `CtClass` object from `Point` to `Pair` before it records that `CtClass` object in a hash table. Thus `getAndRename()` can be executed after `writeFile()` or `toBytecode()` is called on the the `CtClass` object representing `Point` class.

## 3. Class loader
1. Get a `CtClass` object by calling `ClassPool.get()`,
2. Modify it, and
3. Call `writeFile()` or `toBytecode()` on that `CtClass` object to obtain a modified class file.

### 3.1 The toClass method in CtClass
The `CtClass` provides a convenience method `toClass()`, which requests the context class loader for the current thread to load the class represented by the `CtClass` object. To call this method, the caller must have appropriate permission; otherwise, a `SecurityException` may be thrown.
```
public class Hello {
    public void say() {
        System.out.println("Hello");
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("Hello");
        CtMethod m = cc.getDeclaredMethod("say");
        m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
        Class c = cc.toClass();
        Hello h = (Hello)c.newInstance();
        h.say();
    }
}
```

### 3.2 Class loading in Java
In Java, multiple class loaders can coexist and each class loader creates its own name space. Different class loaders can load different class files with the same class name. The loaded two classes are regarded as different ones. This feature enables us to run multiple application programs on a single JVM even if these programs include different classes with the same name.

**Note**: The JVM does not allow dynamically reloading a class. Once a class loader loads a class, it cannot reload a modified version of that class during runtime. Thus, you cannot alter the definition of a class after the JVM loads it. However, the JPDA (Java Platform Debugger Architecture) provides limited ability for reloading a class.

#### Dynamic Class Loading Example
ClassLoader working mechanism:
1. A `ClassLoader` instance checks if the class was already loaded.
2. If not loaded, it delegate the search for the class or resource to its parent class loader before attempting to find the class or resource itself.
3. If parent class loader cannot load class, it attempt to load the class or resource by itself.

### 3.3 Using javassist.Loader
Javassist provides a class loader `javassist.Loader`. This class loader uses a `javassist.ClassPool` object for reading a class file.
```
public class MyTranslator implements Translator {
    void start(ClassPool pool)
        throws NotFoundException, CannotCompileException {}
    void onLoad(ClassPool pool, String classname)
        throws NotFoundException, CannotCompileException
    {
        CtClass cc = pool.get(classname);
        cc.setModifiers(Modifier.PUBLIC);
    }
}
```
If the users want to modify a class on demand when it is loaded, the users can add an event listener to a `javassist.Loader`.

The event-listener class must implement `javassist.Translator`:
- The method `start()` is called when this event listener is added to a `javassist.Loader` object by `addTranslator() `in `javassist.Loader`.
- The method `onLoad()` is called before `javassist.Loader` loads a class. `onLoad()` can modify the definition of the loaded class.

Note that `onLoad()` does not have to call `toBytecode()` or `writeFile()` since `javassist.Loader` calls these methods to obtain a class file.
```
public class Main2 {
  public static void main(String[] args) throws Throwable {
     Translator t = new MyTranslator();
     ClassPool pool = ClassPool.getDefault();
     Loader cl = new Loader();
     cl.addTranslator(pool, t);
     cl.run("MyApp", args);
  }
}
```

`javassist.Loader` searches for classes in a different order from `java.lang.ClassLoader`. `ClassLoader` first delegates the loading operations to the parent class loader and then attempts to load the classes only if the parent class loader cannot find them. On the other hand, `javassist.Loader` attempts to load the classes before delegating to the parent class loader. It delegates only if:
- the classes are not found by calling `get()` on a `ClassPool` object, or
- the classes have been specified by using `delegateLoadingOf()` to be loaded by the parent class loader.

### 3.4 Writing a class loader
```
public class SampleLoader extends ClassLoader {
    /* Call MyApp.main().
     */
    public static void main(String[] args) throws Throwable {
        SampleLoader s = new SampleLoader();
        Class c = s.loadClass("MyApp");
        c.getDeclaredMethod("main", new Class[] { String[].class })
         .invoke(null, new Object[] { args });
    }

    private ClassPool pool;

    public SampleLoader() throws NotFoundException {
        pool = new ClassPool();
        pool.insertClassPath("./class"); // MyApp.class must be there.
    }

    /* Finds a specified class.
     * The bytecode for that class can be modified.
     */
    protected Class findClass(String name) throws ClassNotFoundException {
        try {
            CtClass cc = pool.get(name);
            // modify the CtClass object here
            byte[] b = cc.toBytecode();
            return defineClass(name, b, 0, b.length);
        } catch (NotFoundException e) {
            throw new ClassNotFoundException();
        } catch (IOException e) {
            throw new ClassNotFoundException();
        } catch (CannotCompileException e) {
            throw new ClassNotFoundException();
        }
    }
}
```

### 3.5 Modifying a system class
The system classes like `java.lang.String` cannot be loaded by a class loader other than the system class loader.

the system classes must be **statically** modified.
```
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("java.lang.String");
CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
f.setModifiers(Modifier.PUBLIC);
cc.addField(f);
cc.writeFile(".");
```
```
java -Xbootclasspath/p:. MyApp arg1 arg2...
```
_Note: Applications that use this technique for the purpose of overriding a system class in `rt.jar` should not be deployed as doing so would contravene the Java 2 Runtime Environment binary code license._

### 3.6 Reloading a class at runtime
If the JVM is launched with the JPDA (Java Platform Debugger Architecture) enabled, a class is dynamically reloadable. After the JVM loads a class, the old version of the class definition can be unloaded and a new one can be reloaded again. That is, the definition of that class can be dynamically modified during runtime. However, the new class definition must be somewhat compatible to the old one. **The JVM does not allow schema changes between the two versions.** They have the same set of methods and fields.

Javassist provides a convenient class for reloading a class at runtime. For more information, see the API documentation of `javassist.tools.HotSwapper`.

## 4. Introspection and customization



## 5. Bytecode level API



## 6. Generics



## 7. Varargs



## 8. J2ME



## 9. Boxing/Unboxing



## 10. Debug


## References
- [Getting Started with Javassist](http://www.javassist.org/tutorial/tutorial.html)
- [Dynamic Class Loading Example](https://examples.javacodegeeks.com/core-java/dynamic-class-loading-example/)