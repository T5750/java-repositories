package evangel.util.concurrent.locks.abstractqueuedsynchronizer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * SimpleLock类实现了一个最简单非重入的互斥锁的功能，实际上它就是ThreadPoolExecutor$Worker的实现
 */
public class SimpleLock extends AbstractQueuedSynchronizer {
	private static final long serialVersionUID = -7316320116933187634L;

	public SimpleLock() {
	}

	@Override
	protected boolean tryAcquire(int unused) {
		if (compareAndSetState(0, 1)) {
			setExclusiveOwnerThread(Thread.currentThread());
			return true;
		}
		return false;
	}

	@Override
	protected boolean tryRelease(int unused) {
		setExclusiveOwnerThread(null);
		setState(0);
		return true;
	}

	public void lock() {
		acquire(1);
	}

	public boolean tryLock() {
		return tryAcquire(1);
	}

	public void unlock() {
		release(1);
	}

	public boolean isLocked() {
		return isHeldExclusively();
	}

	public static void main(String[] args) throws InterruptedException {
		final SimpleLock lock = new SimpleLock();
		lock.lock();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					lock.lock();
					System.out.println(Thread.currentThread().getId()
							+ " acquired the lock!");
					lock.unlock();
				}
			}).start();
			// 简单的让线程按照for循环的顺序阻塞在lock上
			Thread.sleep(100);
		}
		System.out.println("main thread unlock!");
		lock.unlock();
	}
}
