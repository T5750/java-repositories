# Java™ Platform Standard Ed. 7

## `java.lang`
### `ThreadLocal`
![ThreadLocal](http://www.wailian.work/images/2018/10/16/ThreadLocal.png)
- `ThreadLocal`类中维护`ThreadLocalMap`，用于存储每个线程的变量副本。`ThreadLocalMap`中`Entry`的键为线程对象，而值为对应线程的变量副本，`Entry(ThreadLocal k, Object v)`。采用空间换时间。
- `ThreadLocal`不是用来解决对象共享访问问题的，而主要是提供了保持对象的方法和避免参数传递的方便的对象访问方式。归纳了两点： 
    1. 每个线程中都有一个自己的`ThreadLocalMap`类对象，可以将线程自己的对象保持到其中，各管各的，线程可以正确的访问到自己的对象。 
    1. 将一个共用的`ThreadLocal`静态实例作为key，将不同对象的引用保存到不同线程的`ThreadLocalMap`中，然后在线程执行的各处通过这个静态`ThreadLocal`实例的get()方法取得自己线程保存的那个对象，避免了将这个对象作为参数传递的麻烦。
- 最常见的`ThreadLocal`使用场景为：解决数据库连接、Session管理等。
```
private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
	public Connection initialValue() {
		return DriverManager.getConnection(DB_URL);
	}
};

public static Connection getConnection() {
	return connectionHolder.get();
}
```
- Hibernate中典型的`ThreadLocal`的应用：
```
private static final ThreadLocal threadSession = new ThreadLocal();

public static Session getSession() throws InfrastructureException {
	Session s = (Session) threadSession.get();
	try {
		if (s == null) {
			s = getSessionFactory().openSession();
			threadSession.set(s);
		}
	} catch (HibernateException ex) {
		throw new InfrastructureException(ex);
	}
	return s;
}
```
- `ThreadLocal`内存泄漏的根源是：由于`ThreadLocalMap`的生命周期跟`Thread`一样长，如果没有手动删除对应key的value就会导致内存泄漏，而不是因为弱引用。
- 避免内存泄漏：每次使用完`ThreadLocal`，都调用它的`remove()`方法，清除数据。
- [正确理解ThreadLocal](http://www.iteye.com/topic/103804)
- [深入剖析ThreadLocal](http://www.cnblogs.com/dolphin0520/p/3920407.html)
- [深入分析 ThreadLocal 内存泄漏问题](http://blog.xiaohansong.com/2016/08/06/ThreadLocal-memory-leak/)

## `java.lang.ref`
### `WeakReference`
- `WeakReference`的一个特点是它何时被回收是不可确定的, 因为这是由GC运行的不确定性所确定的. 所以, 一般用`WeakReference`引用的对象是有价值被cache, 而且很容易被重新被构建, 且很消耗内存的对象.
- [Java WeakReference的理解与使用](https://www.tuicool.com/articles/imyueq)
