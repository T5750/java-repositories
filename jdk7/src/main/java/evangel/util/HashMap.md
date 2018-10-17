# Java™ Platform Standard Ed. 7

## `java.util`
### `HashMap`

#### Base 1.7
- `HashMap`底层是基于`数组+链表`组成的。1.7`HashMap`结构图：

![HashMap1.7-min](http://www.wailian.work/images/2018/10/16/HashMap1.7-min.jpg)
```
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16 // Step 1

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30; // Step 2

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f; // Step 3

    /**
     * An empty table instance to share when the table is not inflated.
     */
    static final Entry<?,?>[] EMPTY_TABLE = {};

    /**
     * The table, resized as necessary. Length MUST Always be a power of two.
     */
    transient Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE; // Step 4

    /**
     * The number of key-value mappings contained in this map.
     */
    transient int size; // Step 5

    /**
     * The next size value at which to resize (capacity * load factor).
     * @serial
     */
    // If table == EMPTY_TABLE then this is the initial capacity at which the
    // table will be created when inflated.
    int threshold; // Step 6

    /**
     * The load factor for the hash table.
     *
     * @serial
     */
    final float loadFactor; // Step 7
```

`HashMap`中比较核心的几个成员变量
1. `DEFAULT_INITIAL_CAPACITY`：初始化桶大小，因为底层是数组，所以这是数组默认的大小。
1. `MAXIMUM_CAPACITY`：桶最大值。
1. `DEFAULT_LOAD_FACTOR`：默认的负载因子（0.75）
1. `table`：真正存放数据的数组。
1. `size`：`Map`存放数量的大小。
1. `threshold`：桶大小，可在初始化时显式指定。
1. `loadFactor`：负载因子，可在初始化时显式指定。

```
public HashMap() {
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
}
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    threshold = initialCapacity;
    init();
}
```
给定的默认容量为16，负载因子为0.75。`Map`在使用过程中不断的往里面存放数据，当数量达到了`16*0.75=12`就需要将当前16的容量进行扩容，而扩容这个过程涉及到`rehash`、复制数据等操作，所以非常消耗性能。

根据代码可以看到其实真正存放数据的是`transient Entry<K,V>[] table = (Entry<K,V>[]) EMPTY_TABLE;`

`Entry`是`HashMap`中的一个内部类，其成员变量：
- `key`：写入时的键。
- `value`：就是值。
- `next`：用于实现链表结构。
- `hash`：存放的是当前`key`的hashcode。

##### `put` 方法
```
public V put(K key, V value) {
    if (table == EMPTY_TABLE) {
        inflateTable(threshold);
    }
    if (key == null)
        return putForNullKey(value);
    int hash = hash(key);
    int i = indexFor(hash, table.length);
    for (Entry<K,V> e = table[i]; e != null; e = e.next) {
        Object k;
        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }
    modCount++;
    addEntry(hash, key, value, i);
    return null;
}
```
- 判断当前数组是否需要初始化。
- 如果`key`为空，则put一个空值进去。
- 根据`key`计算出hashcode。
- 根据计算出的hashcode定位出所在桶。
- 如果桶是一个链表则需要遍历判断里面的hashcode、`key`是否和传入`key`相等，如果相等则进行覆盖，并返回原来的值。
- 如果桶是空的，说明当前位置没有数据存入；新增一个`Entry`对象写入当前位置。

```
void addEntry(int hash, K key, V value, int bucketIndex) {
    if ((size >= threshold) && (null != table[bucketIndex])) {
        resize(2 * table.length);
        hash = (null != key) ? hash(key) : 0;
        bucketIndex = indexFor(hash, table.length);
    }
    createEntry(hash, key, value, bucketIndex);
}
void createEntry(int hash, K key, V value, int bucketIndex) {
    Entry<K,V> e = table[bucketIndex];
    table[bucketIndex] = new Entry<>(hash, key, value, e);
    size++;
}
```
- 当调用`addEntry`写入`Entry`时需要判断是否需要扩容。
- 如果需要就进行两倍扩充，并将当前的`key`重新`hash`并定位。
- 而在`createEntry`中会将当前位置的桶传入到新建的桶中，如果当前桶有值就会在位置形成链表。

##### `get` 方法
```
public V get(Object key) {
    if (key == null)
        return getForNullKey();
    Entry<K,V> entry = getEntry(key);
    return null == entry ? null : entry.getValue();
}
final Entry<K,V> getEntry(Object key) {
    if (size == 0) {
        return null;
    }
    int hash = (key == null) ? 0 : hash(key);
    for (Entry<K,V> e = table[indexFor(hash, table.length)];
         e != null;
         e = e.next) {
        Object k;
        if (e.hash == hash &&
            ((k = e.key) == key || (key != null && key.equals(k))))
            return e;
    }
    return null;
}
```
- 首先也是根据`key`计算出hashcode，然后定位到具体的桶中。
- 判断该位置是否为链表。
- 不是链表就根据`key`、`key`的hashcode是否相等来返回值。
- 为链表则需要遍历直到`key`及hashcode相等时候就返回值。
- 啥都没取到就直接返回`null`。

#### Base 1.8
- 1.7的实现大家看出需要优化的点没有？其实一个很明显的地方就是：
>当Hash冲突严重时，在桶上形成的链表会变的越来越长，这样在查询时的效率就会越来越低；时间复杂度为`O(N)`。

- 因此1.8中重点优化了这个查询效率。1.8`HashMap`结构图：

![HashMap1.8-min](http://www.wailian.work/images/2018/10/16/HashMap1.8-min.jpg)

核心成员变量和1.7大体上都差不多，还是有几个重要的区别：
- `TREEIFY_THRESHOLD`用于判断是否需要将链表转换为红黑树的阈值。
- `HashEntry`修改为`Node`。

##### `put` 方法
```
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
	Node<K,V>[] tab; Node<K,V> p; int n, i;
	if ((tab = table) == null || (n = tab.length) == 0) // Step 1
		n = (tab = resize()).length;
	if ((p = tab[i = (n - 1) & hash]) == null) // Step 2
		tab[i] = newNode(hash, key, value, null);
	else {
		Node<K,V> e; K k;
		if (p.hash == hash &&
			((k = p.key) == key || (key != null && key.equals(k)))) // Step 3
			e = p;
		else if (p instanceof TreeNode) // Step 4
			e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
		else {
			for (int binCount = 0; ; ++binCount) { // Step 5
				if ((e = p.next) == null) {
					p.next = newNode(hash, key, value, null);
					if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st // Step 6
						treeifyBin(tab, hash);
					break;
				}
				if (e.hash == hash && // Step 7
					((k = e.key) == key || (key != null && key.equals(k))))
					break;
				p = e;
			}
		}
		if (e != null) { // existing mapping for key // Step 8
			V oldValue = e.value;
			if (!onlyIfAbsent || oldValue == null)
				e.value = value;
			afterNodeAccess(e);
			return oldValue;
		}
	}
	++modCount;
	if (++size > threshold) // Step 9
		resize();
	afterNodeInsertion(evict);
	return null;
}
```
1. 判断当前桶是否为空，空的就需要初始化（`resize`中会判断是否进行初始化）。
1. 根据当前`key`的hashcode定位到具体的桶中并判断是否为空，为空表明没有Hash冲突就直接在当前位置创建一个新桶即可。
1. 如果当前桶有值（Hash冲突），那么就要比较当前桶中的`key`、`key`的hashcode与写入的`key`是否相等，相等就赋值给e,在第8步的时候会统一进行赋值及返回。
1. 如果当前桶为红黑树，那就要按照红黑树的方式写入数据。
1. 如果是个链表，就需要将当前的`key`、`value`封装成一个新节点写入到当前桶的后面（形成链表）。
1. 接着判断当前链表的大小是否大于预设的阈值，大于时就要转换为红黑树。
1. 如果在遍历过程中找到`key`相同时直接退出遍历。
1. 如果`e != null`就相当于存在相同的`key`,那就需要将值覆盖。
1. 最后判断是否需要进行扩容。

##### `get` 方法
```
public V get(Object key) {
    Node<K,V> e;
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}
final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        if ((e = first.next) != null) {
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}
```
- 首先将key hash之后取得所定位的桶。
- 如果桶为空则直接返回`null`。
- 否则判断桶的第一个位置(有可能是链表、红黑树)的`key`是否为查询的`key`，是就直接返回`value`。
- 如果第一个不匹配，则判断它的下一个是红黑树还是链表。
- 红黑树就按照树的查找方式返回值。
- 不然就按照链表的方式遍历匹配返回值。

1.8 中对大链表做了优化，修改为红黑树之后查询效率直接提高到了`O(logn)`。但是`HashMap`原有的问题也都存在，比如在并发场景下使用时容易出现死循环。
```
final HashMap<String, String> map = new HashMap<String, String>();
for (int i = 0; i < 1000; i++) {
    new Thread(new Runnable() {
        @Override
        public void run() {
            map.put(UUID.randomUUID().toString(), "");
        }
    }).start();
}
```
`HashMap`扩容的时候会调用`resize()`方法，就是这里的并发操作容易在一个桶上形成环形链表；这样当获取一个不存在的`key`时，计算出的index正好是环形链表的下标就会出现死循环。如下图：

![EndlessLoop-min](http://www.wailian.work/images/2018/10/16/EndlessLoop-min.jpg)

#### 遍历方式
`HashMap`的遍历方式，通常有以下几种：
```
Iterator<Map.Entry<String, Integer>> entryIterator = map.entrySet().iterator();
	while (entryIterator.hasNext()) {
		Map.Entry<String, Integer> next = entryIterator.next();
		System.out.println("key=" + next.getKey() + " value=" + next.getValue());
	}
        
Iterator<String> iterator = map.keySet().iterator();
	while (iterator.hasNext()){
		String key = iterator.next();
		System.out.println("key=" + key + " value=" + map.get(key));
	}
```
`强烈建议`使用第一种`EntrySet`进行遍历。第一种可以把key value同时取出，第二种还得需要通过`key`取一次`value`，效率较低。

>简单总结下`HashMap`：无论是1.7还是1.8其实都能看出JDK没有对它做任何的同步操作，所以并发会出问题，甚至1.7中出现死循环导致系统不可用（1.8已经修复死循环问题）。

## `java.util.concurrent`
### `ConcurrentHashMap`
#### Base 1.7
1.7由`Segment`数组、`HashEntry`组成，和`HashMap`一样，仍然是`数组+链表`。1.7`ConcurrentHashMap`结构图：

![ConcurrentHashMap1.7-min](http://www.wailian.work/images/2018/10/16/ConcurrentHashMap1.7-min.jpg)

核心成员变量：
```
/**
 * Segment 数组，存放数据时首先需要定位到具体的 Segment 中。
 */
final Segment<K,V>[] segments;
transient Set<K> keySet;
transient Set<Map.Entry<K,V>> entrySet;
```
`Segment`是`ConcurrentHashMap`的一个内部类，主要的组成如下：
```
static final class Segment<K,V> extends ReentrantLock implements Serializable {
       private static final long serialVersionUID = 2249069246763182397L;
       // 和 HashMap 中的 HashEntry 作用一样，真正存放数据的桶
       transient volatile HashEntry<K,V>[] table;
       transient int count;
       transient int modCount;
       transient int threshold;
       final float loadFactor;
}
```
```
static final class HashEntry<K,V> {
	final int hash;
	final K key;
	volatile V value;
	volatile HashEntry<K,V> next;
	
	HashEntry(int hash, K key, V value, HashEntry<K,V> next) {
		this.hash = hash;
		this.key = key;
		this.value = value;
		this.next = next;
	}
```
和`HashMap`非常类似，唯一的区别就是其中的核心数据如`value`，以及链表都是`volatile`修饰的，保证了获取时的可见性。

原理上来说：`ConcurrentHashMap`采用了分段锁技术，其中`Segment`继承于`ReentrantLock`。不会像`HashTable`那样不管是`put`还是`get`操作都需要做同步处理，理论上`ConcurrentHashMap`支持CurrencyLevel(`Segment`数组数量)的线程并发。每当一个线程占用锁访问一个`Segment`时，不会影响到其他的`Segment`。

##### `put` 方法
```
public V put(K key, V value) {
    Segment<K,V> s;
    if (value == null)
        throw new NullPointerException();
    int hash = hash(key);
    int j = (hash >>> segmentShift) & segmentMask;
    if ((s = (Segment<K,V>)UNSAFE.getObject          // nonvolatile; recheck
         (segments, (j << SSHIFT) + SBASE)) == null) //  in ensureSegment
        s = ensureSegment(j);
    return s.put(key, hash, value, false);
}
```
首先是通过`key`定位到`Segment`，之后在对应的`Segment`中进行具体的`put`。
```
final V put(K key, int hash, V value, boolean onlyIfAbsent) {
    HashEntry<K,V> node = tryLock() ? null :
        scanAndLockForPut(key, hash, value);
    V oldValue;
    try {
        HashEntry<K,V>[] tab = table;
        int index = (tab.length - 1) & hash;
        HashEntry<K,V> first = entryAt(tab, index);
        for (HashEntry<K,V> e = first;;) {
            if (e != null) {
                K k;
                if ((k = e.key) == key ||
                    (e.hash == hash && key.equals(k))) {
                    oldValue = e.value;
                    if (!onlyIfAbsent) {
                        e.value = value;
                        ++modCount;
                    }
                    break;
                }
                e = e.next;
            }
            else {
                if (node != null)
                    node.setNext(first);
                else
                    node = new HashEntry<K,V>(hash, key, value, first);
                int c = count + 1;
                if (c > threshold && tab.length < MAXIMUM_CAPACITY)
                    rehash(node);
                else
                    setEntryAt(tab, index, node);
                ++modCount;
                count = c;
                oldValue = null;
                break;
            }
        }
    } finally {
        unlock();
    }
    return oldValue;
}
```
虽然`HashEntry`中的`value`是用`volatile`关键词修饰的，但是并不能保证并发的原子性，所以`put`操作时仍然需要加锁处理。

首先第一步的时候会尝试获取锁，如果获取失败肯定就有其他线程存在竞争，则利用`scanAndLockForPut()`自旋获取锁。

```
private HashEntry<K,V> scanAndLockForPut(K key, int hash, V value) {
	HashEntry<K,V> first = entryForHash(this, hash);
	HashEntry<K,V> e = first;
	HashEntry<K,V> node = null;
	int retries = -1; // negative while locating node
	while (!tryLock()) { // Step 1
		HashEntry<K,V> f; // to recheck first below
		if (retries < 0) {
			if (e == null) {
				if (node == null) // speculatively create node
					node = new HashEntry<K,V>(hash, key, value, null);
				retries = 0;
			}
			else if (key.equals(e.key))
				retries = 0;
			else
				e = e.next;
		}
		else if (++retries > MAX_SCAN_RETRIES) { // Step 2
			lock();
			break;
		}
		else if ((retries & 1) == 0 &&
				 (f = entryForHash(this, hash)) != first) {
			e = first = f; // re-traverse if entry changed
			retries = -1;
		}
	}
	return node;
}
```
1. 尝试自旋获取锁。
1. 如果重试的次数达到了`MAX_SCAN_RETRIES`则改为阻塞锁获取，保证能获取成功。
```
final V put(K key, int hash, V value, boolean onlyIfAbsent) {
	HashEntry<K,V> node = tryLock() ? null :
		scanAndLockForPut(key, hash, value); // Step 1
	V oldValue;
	try {
		HashEntry<K,V>[] tab = table;
		int index = (tab.length - 1) & hash;
		HashEntry<K,V> first = entryAt(tab, index);
		for (HashEntry<K,V> e = first;;) {
			if (e != null) {
				K k;
				if ((k = e.key) == key || // Step 2
					(e.hash == hash && key.equals(k))) {
					oldValue = e.value;
					if (!onlyIfAbsent) {
						e.value = value;
						++modCount;
					}
					break;
				}
				e = e.next;
			}
			else { // Step 3
				if (node != null)
					node.setNext(first);
				else
					node = new HashEntry<K,V>(hash, key, value, first);
				int c = count + 1;
				if (c > threshold && tab.length < MAXIMUM_CAPACITY)
					rehash(node);
				else
					setEntryAt(tab, index, node);
				++modCount;
				count = c;
				oldValue = null;
				break;
			}
		}
	} finally {
		unlock(); // Step 4
	}
	return oldValue;
}
```
再结合图看看`put`的流程。
1. 将当前`Segment`中的`table`通过`key`的hashcode定位到`HashEntry`。
1. 遍历该`HashEntry`，如果不为空则判断传入的`key`和当前遍历的`key`是否相等，相等则覆盖旧的`value`。
1. 不为空则需要新建一个`HashEntry`并加入到`Segment`中，同时会先判断是否需要扩容。
1. 最后会解除在1中所获取当前`Segment`的锁。

##### `get` 方法
```
public V get(Object key) {
    Segment<K,V> s; // manually integrate access methods to reduce overhead
    HashEntry<K,V>[] tab;
    int h = hash(key);
    long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
    if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null &&
        (tab = s.table) != null) {
        for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile
                 (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
             e != null; e = e.next) {
            K k;
            if ((k = e.key) == key || (e.hash == h && key.equals(k)))
                return e.value;
        }
    }
    return null;
}
```
`get`逻辑比较简单：

只需要将`key`通过Hash之后定位到具体的`Segment`，再通过一次Hash定位到具体的元素上。

由于`HashEntry`中的`value`属性是用`volatile`关键词修饰的，保证了内存可见性，所以每次获取时都是最新值。

`ConcurrentHashMap`的`get`方法是非常高效的，**因为整个过程都不需要加锁**。

#### Base 1.8
1.8`ConcurrentHashMap`结构图：

![ConcurrentHashMap1.8-min](http://www.wailian.work/images/2018/10/17/ConcurrentHashMap1.8-min.jpg)

其中抛弃了原有的`Segment`分段锁，而采用了`CAS+synchronized`来保证并发安全性。
```
static class Node<K,V> implements Map.Entry<K,V> {
	final int hash;
	final K key;
	volatile V val;
	volatile Node<K,V> next;

	Node(int hash, K key, V val, Node<K,V> next) {
		this.hash = hash;
		this.key = key;
		this.val = val;
		this.next = next;
	}

	public final K getKey()       { return key; }
	public final V getValue()     { return val; }
	public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
	public final String toString(){ return key + "=" + val; }
	public final V setValue(V value) { throw new UnsupportedOperationException(); }
```
也将1.7中存放数据的`HashEntry`改为`Node`，但作用都是相同的。其中，`val next`都用了`volatile`修饰，保证了可见性。

##### `put` 方法
```
final V putVal(K key, V value, boolean onlyIfAbsent) {
	if (key == null || value == null) throw new NullPointerException();
	int hash = spread(key.hashCode());
	int binCount = 0;
	for (Node<K,V>[] tab = table;;) { // Step 1
		Node<K,V> f; int n, i, fh;
		if (tab == null || (n = tab.length) == 0) // Step 2
			tab = initTable();
		else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) { // Step 3
			if (casTabAt(tab, i, null,
						 new Node<K,V>(hash, key, value, null)))
				break;                   // no lock when adding to empty bin
		}
		else if ((fh = f.hash) == MOVED) // Step 4
			tab = helpTransfer(tab, f);
		else {
			V oldVal = null;
			synchronized (f) { // Step 5
				if (tabAt(tab, i) == f) {
					if (fh >= 0) {
						binCount = 1;
						for (Node<K,V> e = f;; ++binCount) {
							K ek;
							if (e.hash == hash &&
								((ek = e.key) == key ||
								 (ek != null && key.equals(ek)))) {
								oldVal = e.val;
								if (!onlyIfAbsent)
									e.val = value;
								break;
							}
							Node<K,V> pred = e;
							if ((e = e.next) == null) {
								pred.next = new Node<K,V>(hash, key,
														  value, null);
								break;
							}
						}
					}
					else if (f instanceof TreeBin) {
						Node<K,V> p;
						binCount = 2;
						if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
													   value)) != null) {
							oldVal = p.val;
							if (!onlyIfAbsent)
								p.val = value;
						}
					}
				}
			}
			if (binCount != 0) {
				if (binCount >= TREEIFY_THRESHOLD) // Step 6
					treeifyBin(tab, i);
				if (oldVal != null)
					return oldVal;
				break;
			}
		}
	}
	addCount(1L, binCount);
	return null;
}
```
1. 根据`key`计算出hashcode。
1. 判断是否需要进行初始化。
1. f即为当前`key`定位出的`Node`，如果为空表示当前位置可以写入数据，利用CAS尝试写入，失败则自旋保证成功。
1. 如果当前位置的`hashcode == MOVED == -1`,则需要进行扩容。
1. 如果都不满足，则利用`synchronized`锁写入数据。
1. 如果数量大于`TREEIFY_THRESHOLD`则要转换为红黑树。

##### `get` 方法
```
public V get(Object key) {
	Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
	int h = spread(key.hashCode());
	if ((tab = table) != null && (n = tab.length) > 0 &&
		(e = tabAt(tab, (n - 1) & h)) != null) {
		if ((eh = e.hash) == h) {
			if ((ek = e.key) == key || (ek != null && key.equals(ek)))
				return e.val;
		}
		else if (eh < 0)
			return (p = e.find(h, key)) != null ? p.val : null;
		while ((e = e.next) != null) {
			if (e.hash == h &&
				((ek = e.key) == key || (ek != null && key.equals(ek))))
				return e.val;
		}
	}
	return null;
}
```
- 根据计算出来的hashcode寻址，如果就在桶上那么直接返回值。
- 如果是红黑树那就按照树的方式获取值。
- 就不满足那就按照链表的方式遍历获取值。
>1.8在1.7的数据结构上做了大的改动，采用红黑树之后可以保证查询效率（`O(logn)`），甚至取消了`ReentrantLock`改为了`synchronized`，这样可以看出在新版的JDK中对`synchronized`优化是很到位的。

## References
- [HashMap? ConcurrentHashMap? 相信看完这篇没人能难住你！](http://www.codeceo.com/article/java-hashmap-concurrenthashmap.html)
