package evangel.util.concurrent.locks.reentrantlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 非公平的结果一个线程连续获取锁的情况非常多，而公平的结果连续获取的情况基本没有。
 */
public class ReentrantLockTest {
	private static Lock fairLock = new ReentrantLock(true);
	private static Lock unfairLock = new ReentrantLock();
	private static CountDownLatch _latch;

	public void fair() {
		System.out.println("fair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(fairLock));
			thread.setName("fair version " + i);
			thread.start();
		}
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	public void unfair() {
		System.out.println("unfair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(unfairLock, _latch));
			thread.setName("unfair version " + i);
			thread.start();
		}
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	private static class Job implements Runnable {
		private Lock lock;
		private CountDownLatch _latch;

		public Job(Lock lock, CountDownLatch _latch) {
			this.lock = lock;
			this._latch = _latch;
		}

		public Job(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				lock.lock();
				try {
					System.out.println("Lock by: "
							+ Thread.currentThread().getName());
				} finally {
					lock.unlock();
				}
			}
			if (_latch != null) {
				_latch.countDown();
			}
		}
	}

	public static void main(String[] args) {
		_latch = new CountDownLatch(5);
		final ReentrantLockTest lockTest = new ReentrantLockTest();
		lockTest.unfair();
		try {
			_latch.await();
			lockTest.fair();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
