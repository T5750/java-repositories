# Java™ Platform Standard Ed. 7

## `java.lang.ref`
### `WeakReference`
`WeakReference`的一个特点是它何时被回收是不可确定的，因为这是由GC运行的不确定性所确定的。所以，一般用`WeakReference`引用的对象是有价值被cache，而且很容易被重新被构建，且很消耗内存的对象。

#### 示例
- `WeakReferenceTest`

## References
- [Java WeakReference的理解与使用](https://www.tuicool.com/articles/imyueq)
