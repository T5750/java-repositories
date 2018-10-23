package evangel.keyword.volatiletest;

/**
 * 当我们三个线程(t1,t2,main)同时对一个 int 进行累加时会发现最终的值都会小于 30000。
 */
public class VolatileInc implements Runnable {
	private static volatile int count = 0; // 使用 volatile 修饰基本数据内存不能保证原子性

	// private static AtomicInteger count = new AtomicInteger() ;
	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			count++;
			// count.incrementAndGet() ;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		VolatileInc volatileInc = new VolatileInc();
		Thread t1 = new Thread(volatileInc, "t1");
		Thread t2 = new Thread(volatileInc, "t2");
		t1.start();
		// t1.join();
		t2.start();
		// t2.join();
		for (int i = 0; i < 10000; i++) {
			count++;
			// count.incrementAndGet();
		}
		System.out.println("最终count=" + count);
	}
}