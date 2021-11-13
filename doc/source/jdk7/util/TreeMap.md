## TreeMap

### 1.介绍
- `TreeMap`是一个**有序的key-value集合**，它是通过红黑树实现的。
- `TreeMap`**继承于`AbstractMap`**，所以它是一个`Map`，即一个key-value集合。
- `TreeMap`实现了`NavigableMap`接口，意味着它**支持一系列的导航方法**。比如返回有序的key集合。
- `TreeMap`实现了`Cloneable`接口，意味着**它能被克隆**。
- `TreeMap`实现了`java.io.Serializable`接口，意味着它支持序列化。
- `TreeMap`基于**红黑树（Red-Black tree）实现**。该映射根据**其键的自然顺序进行排序**，或者根据**创建映射时提供的`Comparator`进行排序**，具体取决于使用的构造方法。
- `TreeMap`的基本操作`containsKey`、`get`、`put`和`remove`的时间复杂度是`log(n)`。
- 另外，`TreeMap`是**非同步**的。它的`iterator`方法返回的**迭代器是fail-fast**的。

`TreeMap`的构造函数
```
// 默认构造函数。使用该构造函数，TreeMap中的元素按照自然排序进行排列。
TreeMap()
// 创建的TreeMap包含Map
TreeMap(Map<? extends K, ? extends V> copyFrom)
// 指定Tree的比较器
TreeMap(Comparator<? super K> comparator)
// 创建的TreeSet包含copyFrom
TreeMap(SortedMap<K, ? extends V> copyFrom)
```

`TreeMap`的API
```
Entry<K, V>                ceilingEntry(K key)
K                          ceilingKey(K key)
void                       clear()
Object                     clone()
Comparator<? super K>      comparator()
boolean                    containsKey(Object key)
NavigableSet<K>            descendingKeySet()
NavigableMap<K, V>         descendingMap()
Set<Entry<K, V>>           entrySet()
Entry<K, V>                firstEntry()
K                          firstKey()
Entry<K, V>                floorEntry(K key)
K                          floorKey(K key)
V                          get(Object key)
NavigableMap<K, V>         headMap(K to, boolean inclusive)
SortedMap<K, V>            headMap(K toExclusive)
Entry<K, V>                higherEntry(K key)
K                          higherKey(K key)
boolean                    isEmpty()
Set<K>                     keySet()
Entry<K, V>                lastEntry()
K                          lastKey()
Entry<K, V>                lowerEntry(K key)
K                          lowerKey(K key)
NavigableSet<K>            navigableKeySet()
Entry<K, V>                pollFirstEntry()
Entry<K, V>                pollLastEntry()
V                          put(K key, V value)
V                          remove(Object key)
int                        size()
SortedMap<K, V>            subMap(K fromInclusive, K toExclusive)
NavigableMap<K, V>         subMap(K from, boolean fromInclusive, K to, boolean toInclusive)
NavigableMap<K, V>         tailMap(K from, boolean inclusive)
SortedMap<K, V>            tailMap(K fromInclusive)
```

### 2.数据结构
![TreeMap-min](https://s0.wailian.download/2018/10/29/TreeMap-min.jpg)

1. `TreeMap`实现继承于`AbstractMap`，并且实现了`NavigableMap`接口。
1. `TreeMap`的本质是R-B Tree(红黑树)，它包含几个重要的成员变量：`root`,`size`,`comparator`。
    - `root`是红黑数的根节点。它是`Entry`类型，`Entry`是红黑数的节点，它包含了红黑数的6个基本组成成分：`key`(键)、`value`(值)、`left`(左孩子)、`right`(右孩子)、`parent`(父节点)、`color`(颜色)。`Entry`节点根据`key`进行排序，`Entry`节点包含的内容为`value`。
    - 红黑数排序时，根据`Entry`中的`key`进行排序；`Entry`中的`key`比较大小是根据比较器`comparator`来进行判断的。
    - `size`是红黑数中节点的个数。


### 3.源码解析
//TODO

### 4.遍历方式
#### 4.1 遍历`TreeMap`的键值对
1. 根据`entrySet()`获取`TreeMap`的“键值对”的`Set`集合。
1. 通过`Iterator`迭代器遍历“第一步”得到的集合。
```
// 假设map是TreeMap对象，map中的key是String类型，value是Integer类型
Integer integ = null;
Iterator iter = map.entrySet().iterator();
while(iter.hasNext()) {
    Map.Entry entry = (Map.Entry)iter.next();
    // 获取key
    key = (String)entry.getKey();
    // 获取value
    integ = (Integer)entry.getValue();
}
```

#### 4.2 遍历`TreeMap`的键
1. 根据`keySet()`获取`TreeMap`的“键”的Set集合。
1. 通过`Iterator`迭代器遍历“第一步”得到的集合。
```
// 假设map是TreeMap对象，map中的key是String类型，value是Integer类型
String key = null;
Integer integ = null;
Iterator iter = map.keySet().iterator();
while (iter.hasNext()) {
    // 获取key
    key = (String)iter.next();
    // 根据key，获取value
    integ = (Integer)map.get(key);
}
```

#### 4.3 遍历`TreeMap`的值
1. 根据`value()`获取`TreeMap`的“值”的集合。
1. 通过`Iterator`迭代器遍历“第一步”得到的集合。
```
// 假设map是TreeMap对象，map中的key是String类型，value是Integer类型
Integer value = null;
Collection c = map.values();
Iterator iter= c.iterator();
while (iter.hasNext()) {
    value = (Integer)iter.next();
}
```

### 5.示例
- `TreeMapTest`，`TreeMapIteratorTest`，`TreeMapDemo`

### References
- [Java 集合系列12之 TreeMap详细介绍(源码解析)和使用示例](http://www.cnblogs.com/skywang12345/p/3310928.html)