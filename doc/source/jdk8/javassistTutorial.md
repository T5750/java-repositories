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


### 3.2 Class loading in Java


### 3.3 Using javassist.Loader


### 3.4 Writing a class loader


### 3.5 Modifying a system class


### 3.6 Reloading a class at runtime


## 4. Introspection and customization



## 5. Bytecode level API



## 6. Generics



## 7. Varargs



## 8. J2ME



## 9. Boxing/Unboxing



## 10. Debug


## References
- [Getting Started with Javassist](http://www.javassist.org/tutorial/tutorial.html)