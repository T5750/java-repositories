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
`CtClass` provides methods for introspection. The introspective ability of Javassist is compatible with that of the Java reflection API. `CtClass` provides `getName()`, `getSuperclass()`, `getMethods()`, and so on. `CtClass` also provides methods for modifying a class definition. It allows to add a new field, constructor, and method. Instrumenting a method body is also possible.

### 4.1 Inserting source text at the beginning/end of a method body
The `String` object passed to the methods `insertBefore()`, `insertAfter()`, `addCatch()`, and `insertAt()` are compiled by the compiler included in Javassist. Since the compiler supports language extensions, several identifiers starting with `$` have special meaning:

identifiers | meaning
---|-----
`$0, $1, $2, ...` | `this` and actual parameters
`$args` | An array of parameters. The type of `$args` is `Object[]`.
`$$` | All actual parameters. For example, `m($$)` is equivalent to `m($1,$2,...)`
`$cflow(...)` | `cflow` variable
`$r` | The result type. It is used in a cast expression.
`$w` | The wrapper type. It is used in a cast expression.
`$_` | The resulting value
`$sig` | An array of `java.lang.Class` objects representing the formal parameter types.
`$type` | A `java.lang.Class` object representing the formal result type.
`$class` | A `java.lang.Class` object representing the class currently edited.

#### $0, $1, $2, ...
`$1` represents the first parameter, `$2` represents the second parameter, and so on.

`$0` is equivalent to `this`. If the method is static, `$0` is not available.

```
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
CtMethod m = cc.getDeclaredMethod("move");
m.insertBefore("{ System.out.println($1); System.out.println($2); }");
cc.writeFile();
```

#### $$
If `move()` does not take any parameters, then `move($$)` is equivalent to `move()`.

`$$` can be used with another method. If you write an expression:
```
exMove($$, context)
```
then this expression is equivalent to:
```
exMove($1, $2, $3, context)
```

#### $cflow
To use `$cflow`, first declare that `$cflow` is used for monitoring calls to the method `fact()`:
```
CtMethod cm = ...;
cm.useCflow("fact");
```
Then, `$cflow(fact)` represents the depth of the recursive calls to the method specified by `cm`. The value of `$cflow(fact)` is 0 (zero) when the method is first called whereas it is 1 when the method is recursively called within the method. For example,
```
cm.insertBefore("if ($cflow(fact) == 0)"
	+ "    System.out.println(\"fact \" + $1);");
```

#### $r
`$r` represents the result type (return type) of the method.
- If the result type is `int`, then `($r)` converts from `java.lang.Integer` to `int`
- If the result type is `void`, then `($r)` does not convert a type
- Even if the result type is `void`, the following `return` statement is valid: `return ($r)result;`. This `return` statement is regarded as the equivalent of the `return` statement without a resulting value: `return;`

#### $w
If the type of the expression following `($w)` is not a primitive type, then `($w)` does nothing.

#### $_
The variable `$_` represents the resulting value of the method. The type of that variable is the type of the result type (the return type) of the method. If the result type is `void`, then the type of `$_` is `Object` and the value of `$_` is `null`.

Note that the value of `$_` is never thrown to the caller; it is rather discarded.

#### addCatch()
`addCatch()` inserts a code fragment into a method body so that the code fragment is executed when the method body throws an exception and the control returns to the caller. In the source text representing the inserted code fragment, the exception value is referred to with the special variable `$e`.
```
CtClass etype = ClassPool.getDefault().get("java.io.IOException");
m.addCatch("{ System.out.println($e); throw $e; }", etype);
```
Note that the inserted code fragment must end with a `throw` or `return` statement.

### 4.2 Altering a method body
`CtMethod` and `CtConstructor` provide `setBody()` for substituting a whole method body. They compile the given source text into Java bytecode and substitutes it for the original method body. If the given source text is `null`, the substituted body includes only a `return` statement, which returns zero or null unless the result type is `void`.

Note that `$_` is not available.

#### Substituting source text for an existing expression
Javassist allows modifying only an expression included in a method body. `javassist.expr.ExprEditor` is a class for replacing an expression in a method body. The users can define a subclass of `ExprEditor` to specify how an expression is modified.
```
CtMethod cm = ... ;
cm.instrument(
    new ExprEditor() {
        public void edit(MethodCall m)
                      throws CannotCompileException
        {
            if (m.getClassName().equals("Point")
                          && m.getMethodName().equals("move"))
                m.replace("{ $1 = 0; $_ = $proceed($$); }");
        }
    });
```
Note that the substituted code is not an expression but a statement or a block. It cannot be or contain a try-catch statement.

If the given block is an empty block, that is, if `replace("{}")` is executed, then the expression is removed from the method body. If you want to insert a statement (or a block) before/after the expression, a block like the following should be passed to `replace()`:
```
{ before-statements;
  $_ = $proceed($$);
  after-statements; }
```

#### javassist.expr.MethodCall
A `MethodCall` object represents a method call. 
- The method `replace()` in `MethodCall` substitutes a statement or a block for the method call. It receives source text representing the substitued statement or block, in which the identifiers starting with `$` have special meaning as in the source text passed to `insertBefore()`.

#### javassist.expr.ConstructorCall
A `ConstructorCall` object represents a constructor call such as `this()` and `super` included in a constructor body.
- The method `replace()` in `ConstructorCall` substitutes a statement or a block for the constructor call. It receives source text representing the substituted statement or block, in which the identifiers starting with `$` have special meaning as in the source text passed to `insertBefore()`.

#### javassist.expr.FieldAccess
A `FieldAccess` object represents field access.
- The method `edit()` in `ExprEditor` receives this object if field access is found.
- The method `replace()` in `FieldAccess` receives source text representing the substitued statement or block for the field access.

#### javassist.expr.NewExpr
A `NewExpr` object represents object creation with the `new` operator (not including array creation).
- The method `edit()` in `ExprEditor` receives this object if object creation is found.
- The method `replace()` in `NewExpr` receives source text representing the substitued statement or block for the object creation.

#### javassist.expr.NewArray
A `NewArray` object represents array creation with the `new` operator.
- The method `edit()` in `ExprEditor` receives this object if array creation is found.
- The method `replace()` in `NewArray` receives source text representing the substitued statement or block for the array creation.

#### javassist.expr.Instanceof
A `Instanceof` object represents an `instanceof` expression.
- The method `edit()` in `ExprEditor` receives this object if an instanceof expression is found.
- The method `replace()` in `Instanceof` receives source text representing the substitued statement or block for the expression.

#### javassist.expr.Cast
A `Cast` object represents an expression for explicit type casting.
- The method `edit()` in `ExprEditor` receives this object if explicit type casting is found.
- The method `replace()` in `Cast` receives source text representing the substitued statement or block for the expression.

#### javassist.expr.Handler
A `Handler` object represents a `catch` clause of `try-catch` statement.
- The method `edit()` in `ExprEditor` receives this object if a `catch` is found.
- The method `insertBefore()` in `Handler` compiles the received source text and inserts it at the beginning of the `catch` clause.

### 4.3 Adding a new method or field
#### Adding a method
Javassist allows the users to create a new method and constructor from scratch. `CtNewMethod` and `CtNewConstructor` provide several factory methods, which are static methods for creating `CtMethod` or `CtConstructor` objects. Especially, `make()` creates a `CtMethod` or `CtConstructor` object from the given source text.
```
CtClass point = ClassPool.getDefault().get("Point");
CtMethod m = CtNewMethod.make(
                 "public int xmove(int dx) { x += dx; }",
                 point);
point.addMethod(m);
```
```
CtClass point = ClassPool.getDefault().get("Point");
CtMethod m = CtNewMethod.make(
                 "public int ymove(int dy) { $proceed(0, dy); }",
                 point, "this", "move");
```
```
CtClass cc = ... ;
CtMethod m = new CtMethod(CtClass.intType, "move",
                          new CtClass[] { CtClass.intType }, cc);
cc.addMethod(m);
m.setBody("{ x += $1; }");
cc.setModifiers(cc.getModifiers() & ~Modifier.ABSTRACT);
```
Since Javassist makes a class abstract if an abstract method is added to the class, you have to explicitly change the class back to a non-abstract one after calling `setBody()`.

#### Mutual recursive methods
Javassist cannot compile a method if it calls another method that has not been added to a class. (Javassist can compile a method that calls itself recursively.) To add mutual recursive methods to a class, you need a trick shown below. Suppose that you want to add methods `m()` and `n()` to a class represented by `cc`:
```
CtClass cc = ... ;
CtMethod m = CtNewMethod.make("public abstract int m(int i);", cc);
CtMethod n = CtNewMethod.make("public abstract int n(int i);", cc);
cc.addMethod(m);
cc.addMethod(n);
m.setBody("{ return ($1 <= 0) ? 1 : (n($1 - 1) * $1); }");
n.setBody("{ return m($1); }");
cc.setModifiers(cc.getModifiers() & ~Modifier.ABSTRACT);
```

- You must first make two abstract methods and add them to the class.
- Then you can give the method bodies to these methods even if the method bodies include method calls to each other.
- Finally you must change the class to a not-abstract class since `addMethod()` automatically changes a class into an abstract one if an abstract method is added.

#### Adding a field
Javassist also allows the users to create a new field.
```
CtClass point = ClassPool.getDefault().get("Point");
CtField f = new CtField(CtClass.intType, "z", point);
// point.addField(f);
point.addField(f, "0"); // initial value is 0
```
```
CtClass point = ClassPool.getDefault().get("Point");
CtField f = CtField.make("public int z = 0;", point);
point.addField(f);
```

#### Removing a member
To remove a field or a method, call `removeField()` or `removeMethod()` in `CtClass`. A `CtConstructor` can be removed by `removeConstructor()` in `CtClass`.

### 4.4 Annotations
`CtClass`, `CtMethod`, `CtField` and `CtConstructor` provides a convenient method `getAnnotations()` for reading annotations. It returns an annotation-type object.
```
public @interface Author {
    String name();
    int year();
}
```

To use `getAnnotations()`, annotation types such as `Author` must be included in the current class path. **They must be also accessible from a `ClassPool` object.** If the class file of an annotation type is not found, Javassist cannot obtain the default values of the members of that annotation type.

### 4.5 Runtime support classes
In most cases, a class modified by Javassist does not require Javassist to run. However, some kinds of bytecode generated by the Javassist compiler need runtime support classes, which are in the `javassist.runtime` package (for details, please read the API reference of that package). Note that the `javassist.runtime` package is the only package that classes modified by Javassist may need for running. The other Javassist classes are never used at runtime of the modified classes.

### 4.6 Import
All the class names in source code must be fully qualified (they must include package names). However, the `java.lang` package is an exception
```
ClassPool pool = ClassPool.getDefault();
pool.importPackage("java.awt");
CtClass cc = pool.makeClass("Test");
CtField f = CtField.make("public Point p;", cc);
cc.addField(f);
```

Note that `importPackage()` **does not** affect the `get()` method in `ClassPool`. Only the compiler considers the imported packages. The parameter to `get()` must be always a fully qualified name.

### 4.7 Limitations
In the current implementation, the Java compiler included in Javassist has several limitations with respect to the language that the compiler can accept. Those limitations are:
- The new syntax introduced by J2SE 5.0 (including enums and generics) has not been supported. Annotations are supported by the low level API of Javassist. See the `javassist.bytecode.annotation` package (and also `getAnnotations()` in `CtClass` and `CtBehavior`). Generics are also only partly supported.
- Array initializers, a comma-separated list of expressions enclosed by braces `{` and `}`, are not available unless the array dimension is one.
- Inner classes or anonymous classes are not supported. Note that this is a limitation of the compiler only. It cannot compile source code including an anonymous-class declaration. Javassist can read and modify a class file of inner/anonymous class.
- Labeled `continue` and `break` statements are not supported.
- The compiler does not correctly implement the Java method dispatch algorithm. The compiler may confuse if methods defined in a class have the same name but take different parameter lists.
- The users are recommended to use `#` as the separator between a class name and a static method or field name.

## 5. Bytecode level API
Javassist also provides lower-level API for directly editing a class file.

If you want to just produce a simple class file, `javassist.bytecode.ClassFileWriter` might provide the best API for you. It is much faster than `javassist.bytecode.ClassFile` although its API is minimum.

### 5.1 Obtaining a ClassFile object
```
BufferedInputStream fin = new BufferedInputStream(new FileInputStream("Point.class"));
ClassFile cf = new ClassFile(new DataInputStream(fin));
```
You can create a new class file from scratch.
```
ClassFile cf = new ClassFile(false, "test.Foo", null);
cf.setInterfaces(new String[] { "java.lang.Cloneable" });
FieldInfo f = new FieldInfo(cf.getConstPool(), "width", "I");
f.setAccessFlags(AccessFlag.PUBLIC);
cf.addField(f);
cf.write(new DataOutputStream(new FileOutputStream("Foo.class")));
```

### 5.2 Adding and removing a member
`ClassFile` provides `addField()` and `addMethod()` for adding a field or a method (note that a constructor is regarded as a method at the bytecode level). It also provides `addAttribute()` for adding an attribute to the class file.

Note that `FieldInfo`, `MethodInfo`, and `AttributeInfo` objects include a link to a `ConstPool` (constant pool table) object. The `ConstPool` object must be common to the `ClassFile` object and a `FieldInfo` (or `MethodInfo` etc.) object that is added to that `ClassFile` object. In other words, a `FieldInfo` (or `MethodInfo` etc.) object must not be shared among different `ClassFile` objects.

To remove a field or a method from a `ClassFile` object, you must first obtain a `java.util.List` object containing all the fields of the class. `getFields()` and `getMethods()` return the lists. A field or a method can be removed by calling `remove()` on the `List` object. An attribute can be removed in a similar way. Call `getAttributes()` in `FieldInfo` or `MethodInfo` to obtain the list of attributes, and remove one from the list.

### 5.3 Traversing a method body
To examine every bytecode instruction in a method body, `CodeIterator` is useful.
```
ClassFile cf = ... ;
MethodInfo minfo = cf.getMethod("move");    // we assume move is not overloaded.
CodeAttribute ca = minfo.getCodeAttribute();
CodeIterator ci = ca.iterator();
while (ci.hasNext()) {
    int index = ci.next();
    int op = ci.byteAt(index);
    System.out.println(Mnemonic.OPCODE[op]);
}
```

A `CodeIterator` object allows you to visit every bytecode instruction one by one from the beginning to the end. The following methods are part of the methods declared in `CodeIterator`:
- `void begin()`: Move to the first instruction.
- `void move(int index)`: Move to the instruction specified by the given index.
- `boolean hasNext()`: Returns true if there is more instructions.
- `int next()`: Returns the index of the next instruction. **Note that it does not return the opcode of the next instruction.**
- `int byteAt(int index)`: Returns the unsigned 8bit value at the index.
- `int u16bitAt(int index)`: Returns the unsigned 16bit value at the index.
- `int write(byte[] code, int index)`: Writes a byte array at the index.
- `void insert(int index, byte[] code)`: Inserts a byte array at the index. Branch offsets etc. are automatically adjusted.

### 5.4 Producing a bytecode sequence
A `Bytecode` object represents a sequence of bytecode instructions. It is a growable array of bytecode.
```
ConstPool cp = ...;    // constant pool table
Bytecode b = new Bytecode(cp, 1, 0);
b.addIconst(3);
b.addReturn(CtClass.intType);
CodeAttribute ca = b.toCodeAttribute();
```

`Bytecode` can be used to construct a method.
```
ClassFile cf = ...
Bytecode code = new Bytecode(cf.getConstPool());
code.addAload(0);
code.addInvokespecial("java/lang/Object", MethodInfo.nameInit, "()V");
code.addReturn(null);
code.setMaxLocals(1);
MethodInfo minfo = new MethodInfo(cf.getConstPool(), MethodInfo.nameInit, "()V");
minfo.setCodeAttribute(code.toCodeAttribute());
cf.addMethod(minfo);
```

### 5.5 Annotations (Meta tags)
Annotations are stored in a class file as runtime invisible (or visible) annotations attribute. These attributes can be obtained from `ClassFile`, `MethodInfo`, or `FieldInfo` objects. Call `getAttribute(AnnotationsAttribute.invisibleTag)` on those objects. For more details, see the javadoc manual of `javassist.bytecode.AnnotationsAttribute` class and the `javassist.bytecode.annotation` package.

Javassist also let you access annotations by the higher-level API. If you want to access annotations through `CtClass`, call `getAnnotations()` in `CtClass` or `CtBehavior`.

## 6. Generics
The lower-level API of Javassist fully supports generics introduced by Java 5. On the other hand, the higher-level API such as `CtClass` does not directly support generics. However, this is not a serious problem for bytecode transformation.

The generics of Java is implemented by the erasure technique. After compilation, all type parameters are dropped off.

Note that no type parameters are necessary. Since `get` returns an `Object`, an explicit type cast is needed at the caller site if the source code is compiled by Javassist. For example, if the type parameter `T` is `String`, then (`String`) must be inserted as follows:
```
Wrapper w = ...
String s = (String)w.get();
```

## 7. Varargs
Currently, Javassist does not directly support varargs. So to make a method with varargs, you must explicitly set a method modifier.
```
CtClass cc = /* target class */;
CtMethod m = CtMethod.make("public int length(int[] args) { return args.length; }", cc);
m.setModifiers(m.getModifiers() | Modifier.VARARGS);
cc.addMethod(m);
```

## 8. J2ME
If you modify a class file for the J2ME execution environment, you must perform preverification. Preverifying is basically producing stack maps, which is similar to stack map tables introduced into J2SE at JDK 1.6. Javassist maintains the stack maps for J2ME only if `javassist.bytecode.MethodInfo.doPreverify` is true.

You can also manually produce a stack map for a modified method. For a given method represented by a `CtMethod` object `m`, you can produce a stack map by calling the following methods:
```
m.getMethodInfo().rebuildStackMapForME(cpool);
```
Here, `cpool` is a `ClassPool` object, which is available by calling `getClassPool()` on a `CtClass` object. A `ClassPool` object is responsible for finding class files from given class pathes. To obtain all the `CtMethod` objects, call the `getDeclaredMethods` method on a `CtClass` object.

## 9. Boxing/Unboxing
Boxing and unboxing in Java are syntactic sugar. There is no bytecode for boxing or unboxing. So the compiler of Javassist does not support them.

For Javassist, however, you must explicitly convert a value type from `int` to `Integer`:
```
Integer i = new Integer(3);
```

## 10. Debug
Set `CtClass.debugDump` to a directory name. Then all class files modified and generated by Javassist are saved in that directory. To stop this, set `CtClass.debugDump` to null. The default value is null.
```
CtClass.debugDump = "./dump";
```
All modified class files are saved in `./dump`.

## Results
- `ReadWriteBytecodeTest`
- `ClassPoolTest`
- `ClassLoaderTest`
- `IntrospectionAndCustomizationTest`
- `BytecodeLevelApiTest`

## References
- [Getting Started with Javassist](http://www.javassist.org/tutorial/tutorial.html)
- [Dynamic Class Loading Example](https://examples.javacodegeeks.com/core-java/dynamic-class-loading-example/)