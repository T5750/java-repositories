# Java™ Platform Standard Ed. 7

## `java.util`
### `LinkedHashMap`
具有顺序的`LinkedHashMap`底层是继承于`HashMap`实现的，由一个双向链表所构成。

`LinkedHashMap`的排序方式有两种：
- 根据写入顺序排序。
- 根据访问顺序排序。

其中根据访问顺序排序时，每次`get`都会将访问的值移动到链表末尾，这样重复操作就能的到一个按照访问顺序排序的链表。

#### 数据结构
```
private transient Entry<K,V> header; // 是这个双向链表的头结点
private final boolean accessOrder; // 默认按照插入顺序排序，为 true 时按照访问顺序排序
 
private static class Entry<K,V> extends HashMap.Entry<K,V> {
	// These fields comprise the doubly linked list used for iteration.
	Entry<K,V> before, after;

	Entry(int hash, K key, V value, HashMap.Entry<K,V> next) {
		super(hash, key, value, next);
	}
	...
}
```
其中`Entry`继承于`HashMap`的`Entry`，并新增了上下节点的指针，也就形成了双向链表。利用了头节点和其余的各个节点之间通过`Entry`中的`after`和`before`指针进行关联。

![LinkedHashMap-min](http://www.wailian.work/images/2018/10/19/LinkedHashMap-min.jpg)

![LinkedHashMap2](http://www.wailian.work/images/2018/10/19/LinkedHashMap2.gif)

#### 构造方法
`LinkedHashMap`的构造方法:
```
public LinkedHashMap() {
	super();
	accessOrder = false;
}
```

`HashMap`实现：
```
public HashMap(int initialCapacity, float loadFactor) {
	if (initialCapacity < 0)
		throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
	if (initialCapacity > MAXIMUM_CAPACITY)
		initialCapacity = MAXIMUM_CAPACITY;
	if (loadFactor <= 0 || Float.isNaN(loadFactor))
		throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
	this.loadFactor = loadFactor;
	threshold = initialCapacity;
	init();
}
```

`init()`由`LinkedHashMap`来实现：
```
// 对 header 进行了初始化。
void init() {
	header = new Entry<>(-1, null, null, null);
	header.before = header.after = header;
}
```

#### `put` 方法
主体的实现都是借助于`HashMap`来完成的，只是对其中的`recordAccess()`,`addEntry()`,`createEntry()`进行了重写。`LinkedHashMap`的实现：
```
// 就是判断是否是根据访问顺序排序，如果是则需要将当前这个 Entry 移动到链表的末尾
void recordAccess(HashMap<K,V> m) {
	LinkedHashMap<K,V> lm = (LinkedHashMap<K,V>)m;
	if (lm.accessOrder) {
		lm.modCount++;
		remove();
		addBefore(lm.header);
	}
}
// 调用了 HashMap 的实现，并判断是否需要删除最少使用的 Entry(默认不删除)
void addEntry(int hash, K key, V value, int bucketIndex) {
	super.addEntry(hash, key, value, bucketIndex);

	// Remove eldest entry if instructed
	Entry<K,V> eldest = header.after;
	if (removeEldestEntry(eldest)) {
		removeEntryForKey(eldest.key);
	}
}
void createEntry(int hash, K key, V value, int bucketIndex) {
	HashMap.Entry<K,V> old = table[bucketIndex];
	Entry<K,V> e = new Entry<>(hash, key, value, old);
	// 就多了这一步，将新增的 Entry 加入到 header 双向链表中
	table[bucketIndex] = e;
	e.addBefore(header);
	size++;
}
// 写入到双向链表中
private void addBefore(Entry<K,V> existingEntry) {
	after  = existingEntry;
	before = existingEntry.before;
	before.after = this;
	after.before = this;
}
```

#### `get` 方法
```
public V get(Object key) {
	Entry<K,V> e = (Entry<K,V>)getEntry(key);
	if (e == null)
		return null;
	e.recordAccess(this);
	return e.value;
}
void recordAccess(HashMap<K,V> m) {
	LinkedHashMap<K,V> lm = (LinkedHashMap<K,V>)m;
	if (lm.accessOrder) {
		lm.modCount++;
		remove();
		addBefore(lm.header);
	}
}
```
`clear()`清空：
```
// 只需要把指针都指向自己即可，原本那些 Entry 没有引用之后就会被 JVM 自动回收。
public void clear() {
	super.clear();
	header.before = header.after = header;
}
```

#### 示例
- `LinkedHashMapTest`

#### 总结
总的来说`LinkedHashMap`其实就是对`HashMap`进行了拓展，使用了双向链表来保证了顺序性。

## References
- [LinkedHashMap 底层分析](https://crossoverjie.top/2018/02/06/LinkedHashMap/)