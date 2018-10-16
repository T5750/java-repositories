# Java™ Platform Standard Ed. 7

## `java.util`
### `HashMap`

#### Base 1.7
- `HashMap`底层是基于`数组 + 链表`组成的。1.7 中的数据结构图：

![HashMap1.7-min](http://www.wailian.work/images/2018/10/16/HashMap1.7-min.jpg)
![HashMap1.7-impl-min](http://www.wailian.work/images/2018/10/16/HashMap1.7-impl-min.jpg)
- `HashMap`中比较核心的几个成员变量
    1. 初始化桶大小，因为底层是数组，所以这是数组默认的大小。
    1. 桶最大值。
    1. 默认的负载因子（0.75）
    1. table 真正存放数据的数组。
    1. Map 存放数量的大小。
    1. 桶大小，可在初始化时显式指定。
    1. 负载因子，可在初始化时显式指定。
- 给定的默认容量为 16，负载因子为 0.75。Map 在使用过程中不断的往里面存放数据，当数量达到了 16 * 0.75 = 12 就需要将当前 16 的容量进行扩容，而扩容这个过程涉及到 rehash、复制数据等操作，所以非常消耗性能。

##### put 方法
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
- 如果 key 为空，则 put 一个空值进去。
- 根据 key 计算出 hashcode。
- 根据计算出的 hashcode 定位出所在桶。
- 如果桶是一个链表则需要遍历判断里面的 hashcode、key 是否和传入 key 相等，如果相等则进行覆盖，并返回原来的值。
- 如果桶是空的，说明当前位置没有数据存入；新增一个 Entry 对象写入当前位置。

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
- 当调用 addEntry 写入 Entry 时需要判断是否需要扩容。如果需要就进行两倍扩充，并将当前的 key 重新 hash 并定位。
- 而在 createEntry 中会将当前位置的桶传入到新建的桶中，如果当前桶有值就会在位置形成链表。

##### get 方法
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
- 首先也是根据 key 计算出 hashcode，然后定位到具体的桶中。
- 判断该位置是否为链表。
- 不是链表就根据 key、key 的 hashcode 是否相等来返回值。
- 为链表则需要遍历直到 key 及 hashcode 相等时候就返回值。
- 啥都没取到就直接返回 null 。

#### Base 1.8
- 1.7 的实现大家看出需要优化的点没有？其实一个很明显的地方就是：
>当 Hash 冲突严重时，在桶上形成的链表会变的越来越长，这样在查询时的效率就会越来越低；时间复杂度为 O(N)。

- 因此 1.8 中重点优化了这个查询效率。1.8 `HashMap`结构图：

![HashMap1.8-min](http://www.wailian.work/images/2018/10/16/HashMap1.8-min.jpg)
- 核心的成员变量和 1.7 大体上都差不多，还是有几个重要的区别：
    - `TREEIFY_THRESHOLD`用于判断是否需要将链表转换为红黑树的阈值。
    - HashEntry 修改为 Node。

##### put 方法
![HashMap1.8-put-min](http://www.wailian.work/images/2018/10/16/HashMap1.8-put-min.jpg)
1. 判断当前桶是否为空，空的就需要初始化（resize 中会判断是否进行初始化）。
1. 根据当前 key 的 hashcode 定位到具体的桶中并判断是否为空，为空表明没有 Hash 冲突就直接在当前位置创建一个新桶即可。
1. 如果当前桶有值（ Hash 冲突），那么就要比较当前桶中的 key、key 的 hashcode 与写入的 key 是否相等，相等就赋值给 e,在第 8 步的时候会统一进行赋值及返回。
1. 如果当前桶为红黑树，那就要按照红黑树的方式写入数据。
1. 如果是个链表，就需要将当前的 key、value 封装成一个新节点写入到当前桶的后面（形成链表）。
1. 接着判断当前链表的大小是否大于预设的阈值，大于时就要转换为红黑树。
1. 如果在遍历过程中找到 key 相同时直接退出遍历。
1. 如果 e != null 就相当于存在相同的 key,那就需要将值覆盖。
1. 最后判断是否需要进行扩容。

##### get 方法
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
- 首先将 key hash 之后取得所定位的桶。
- 如果桶为空则直接返回 null 。
- 否则判断桶的第一个位置(有可能是链表、红黑树)的 key 是否为查询的 key，是就直接返回 value。
- 如果第一个不匹配，则判断它的下一个是红黑树还是链表。
- 红黑树就按照树的查找方式返回值。
- 不然就按照链表的方式遍历匹配返回值。

1.8 中对大链表做了优化，修改为红黑树之后查询效率直接提高到了 O(logn)。但是 HashMap 原有的问题也都存在，比如在并发场景下使用时容易出现死循环。
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
HashMap 扩容的时候会调用 resize() 方法，就是这里的并发操作容易在一个桶上形成环形链表；这样当获取一个不存在的 key 时，计算出的 index 正好是环形链表的下标就会出现死循环。如下图：
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
强烈建议使用第一种 EntrySet 进行遍历。第一种可以把 key value 同时取出，第二种还得需要通过 key 取一次 value，效率较低。

## `java.util.concurrent`
### `ConcurrentHashMap`
#### Base 1.7
1.7 由 Segment 数组、HashEntry 组成，和 HashMap 一样，仍然是数组加链表。结构图：

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
Segment 是 ConcurrentHashMap 的一个内部类，主要的组成如下：
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
和 HashMap 非常类似，唯一的区别就是其中的核心数据如 value ，以及链表都是 volatile 修饰的，保证了获取时的可见性。

原理上来说：ConcurrentHashMap 采用了分段锁技术，其中 Segment 继承于 ReentrantLock。不会像 HashTable 那样不管是 put 还是 get 操作都需要做同步处理，理论上 ConcurrentHashMap 支持 CurrencyLevel (Segment 数组数量)的线程并发。每当一个线程占用锁访问一个 Segment 时，不会影响到其他的 Segment。

##### put 方法

##### get 方法

#### Base 1.8

##### put 方法

##### get 方法

- [一文让你彻底理解 Java HashMap 和 ConcurrentHashMap](http://www.codeceo.com/article/java-hashmap-concurrenthashmap.html)
