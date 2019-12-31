## atomic

### `atomic`包介绍
在`atomic`包里一共有12个类，四种原子更新方式，分别是：
- 原子更新基本类型
- 原子更新数组
- 原子更新引用
- 原子更新字段

`atomic`包里的类基本都是使用`Unsafe`实现的包装类。

### 原子更新基本类型类
用于通过原子的方式更新基本类型，`atomic`包提供了以下三个类：
- `AtomicBoolean`：原子更新布尔类型。
- `AtomicInteger`：原子更新整型。
- `AtomicLong`：原子更新长整型。

`AtomicInteger`的常用方法如下：
- `int addAndGet(int delta)`：以原子方式将输入的数值与实例中的值（`AtomicInteger`里的`value`）相加，并返回结果
- `boolean compareAndSet(int expect, int update)`：如果输入的数值等于预期值，则以原子方式将该值设置为输入的值。
- `int getAndIncrement()`：以原子方式将当前值加1，注意：这里返回的是自增前的值。
- `void lazySet(int newValue)`：最终会设置成`newValue`，使用`lazySet`设置值后，可能导致其他线程在之后的一小段时间内还是可以读到旧的值。
- `int getAndSet(int newValue)`：以原子方式设置为`newValue`的值，并返回旧值。

示例：`AtomicIntegerTest`

>`Unsafe`只提供了三种`CAS`方法：`compareAndSwapObject`，`compareAndSwapInt`和`compareAndSwapLong`，再看`AtomicBoolean`源码，发现其是先把`Boolean`转换成整型，再使用`compareAndSwapInt`进行`CAS`，所以原子更新`double`也可以用类似的思路来实现。

### 原子更新数组类
通过原子的方式更新数组里的某个元素，`atomic`包提供了以下三个类：
- `AtomicIntegerArray`：原子更新整型数组里的元素。
- `AtomicLongArray`：原子更新长整型数组里的元素。
- `AtomicReferenceArray`：原子更新引用类型数组里的元素。

`AtomicIntegerArray`类主要是提供原子的方式更新数组里的整型，其常用方法如下：
- `int addAndGet(int i, int delta)`：以原子方式将输入值与数组中索引`i`的元素相加。
- `boolean compareAndSet(int i, int expect, int update)`：如果当前值等于预期值，则以原子方式将数组位置`i`的元素设置成`update`值。

>注意：数组`value`通过构造方法传递进去，然后`AtomicIntegerArray`会将当前数组复制一份，所以当`AtomicIntegerArray`对内部的数组元素进行修改时，不会影响到传入的数组。

示例：`AtomicIntegerArrayTest`

### 原子更新引用类型
原子更新基本类型的`AtomicInteger`，只能更新一个变量，如果要原子的更新多个变量，就需要使用这个原子更新引用类型提供的类。`atomic`包提供了以下三个类：
- `AtomicReference`：原子更新引用类型。
- `AtomicReferenceFieldUpdater`：原子更新引用类型里的字段。
- `AtomicMarkableReference`：原子更新带有标记位的引用类型。可以原子的更新一个布尔类型的标记位和引用类型。构造方法是`AtomicMarkableReference(V initialRef, boolean initialMark)`

示例：`AtomicReferenceTest`

### 原子更新字段类
如果我们只需要某个类里的某个字段，那么就需要使用原子更新字段类，`atomic`包提供了以下三个类：
- `AtomicIntegerFieldUpdater`：原子更新整型的字段的更新器。
- `AtomicLongFieldUpdater`：原子更新长整型字段的更新器。
- `AtomicStampedReference`：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于原子的更数据和数据的版本号，可以解决使用`CAS`进行原子更新时，可能出现的ABA问题。

>原子更新字段类都是抽象类，每次使用都时候必须使用静态方法`newUpdater`创建一个更新器。原子更新类的字段的必须使用`public volatile`修饰符。

示例：`AtomicIntegerFieldUpdaterTest`

### References
- [Java中的Atomic包使用指南](http://ifeve.com/java-atomic/)