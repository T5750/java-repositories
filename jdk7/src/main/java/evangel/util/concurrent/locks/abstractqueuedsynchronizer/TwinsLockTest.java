package evangel.util.concurrent.locks.abstractqueuedsynchronizer;

import java.util.concurrent.locks.Lock;

/**
 * 1. 打印线程 Worker在两次睡眠之间打印自身线程，如果一个时刻只能有两个线程同时访问，那么打印出来的内容将是成对出现。 <br/>
 * ​2. 分隔线程 不停的打印换行，能让Worker的输出看起来更加直观。
 */
public class TwinsLockTest {
	public void test() {
		final Lock lock = new TwinsLock();
		class Worker extends Thread {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try {
						Thread.sleep(1000L);
						System.out.println(Thread.currentThread());
						Thread.sleep(1000L);
					} catch (Exception ex) {
					} finally {
						lock.unlock();
					}
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			Worker w = new Worker();
			w.start();
		}
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000L);
						System.out.println();
					} catch (Exception ex) {
					}
				}
			}
		}.start();
		try {
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final TwinsLockTest lockTest = new TwinsLockTest();
		lockTest.test();
	}
}
