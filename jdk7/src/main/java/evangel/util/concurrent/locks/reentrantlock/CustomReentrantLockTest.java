package evangel.util.concurrent.locks.reentrantlock;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在非公平获取的过程中，“插队”现象非常严重，后续获取锁的线程根本不顾及sync队列中等待的线程，而是能获取就获取。反观公平获取的过程，
 * 锁的获取就类似线性化的，每次都由sync队列中等待最长的线程（链表的第一个，sync队列是由尾部结点添加，当前输出的sync队列是逆序输出）获取锁。
 */
public class CustomReentrantLockTest {
	private static Lock fairLock = new ReentrantLock2(true);
	private static Lock unfairLock = new ReentrantLock2(false);
	private static CountDownLatch _latch;

	public void fair() {
		System.out.println("fair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(fairLock)) {
				@Override
				public String toString() {
					return getName();
				}
			};
			thread.setName("" + i);
			thread.start();
		}
		// sleep 5000ms
	}

	public void unfair() {
		System.out.println("unfair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(unfairLock, _latch)) {
				@Override
				public String toString() {
					return getName();
				}
			};
			thread.setName("" + i);
			thread.start();
		}
		// sleep 5000ms
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
							+ Thread.currentThread().getName() + " and "
							+ ((ReentrantLock2) lock).getQueuedThreads()
							+ " waits.");
				} finally {
					lock.unlock();
				}
			}
			if (_latch != null) {
				_latch.countDown();
			}
		}
	}

	private static class ReentrantLock2 extends ReentrantLock {
		// Constructor Override
		private static final long serialVersionUID = 1773716895097002072L;

		public ReentrantLock2(boolean fair) {
			super(fair);
		}

		@Override
		public Collection<Thread> getQueuedThreads() {
			return super.getQueuedThreads();
		}
	}

	public static void main(String[] args) {
		_latch = new CountDownLatch(5);
		final CustomReentrantLockTest lockTest = new CustomReentrantLockTest();
		lockTest.unfair();
		try {
			_latch.await();
			lockTest.fair();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
