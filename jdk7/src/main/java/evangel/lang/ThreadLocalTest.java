package evangel.lang;

/**
 * http://www.cnblogs.com/dolphin0520/p/3920407.html<br/>
 * 首先，在每个线程Thread内部有一个ThreadLocal.ThreadLocalMap类型的成员变量threadLocals，
 * 这个threadLocals就是用来存储实际的变量副本的，键值为当前ThreadLocal变量，value为变量副本（即T类型的变量）。 <br/>
 * 初始时，在Thread里面，threadLocals为空，当通过ThreadLocal变量调用get()方法或者set()方法，
 * 就会对Thread类中的threadLocals进行初始化
 * ，并且以当前ThreadLocal变量为键值，以ThreadLocal要保存的副本变量为value，存到threadLocals。<br/>
 * 然后在当前线程里面，如果要使用副本变量，就可以通过get方法在threadLocals里面查找。
 */
public class ThreadLocalTest {
	ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
	ThreadLocal<String> stringLocal = new ThreadLocal<String>();

	public void set() {
		longLocal.set(Thread.currentThread().getId());
		stringLocal.set(Thread.currentThread().getName());
	}

	public long getLong() {
		return longLocal.get();
	}

	public String getString() {
		return stringLocal.get();
	}

	public static void main(String[] args) throws InterruptedException {
		final ThreadLocalTest test = new ThreadLocalTest();
		test.set();
		System.out.println(test.getLong());
		System.out.println(test.getString());
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				test.set();
				System.out.println(test.getLong());
				System.out.println(test.getString());
			}
		};
		thread1.start();
		thread1.join();
		System.out.println(test.getLong());
		System.out.println(test.getString());
	}
}
