package evangel.keyword.atomicoperation;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class SpinLock {
	private AtomicReference<Thread> sign = new AtomicReference<>();

	public void lock() {
		Thread current = Thread.currentThread();
		// 一直while()循环，直到 cas 操作成功为止
		while (!sign.compareAndSet(null, current)) {
		}
	}

	public void unlock() {
		Thread current = Thread.currentThread();
		sign.compareAndSet(current, null);
	}

	public static void main(String[] args) {
		final SpinLock spinLock = new SpinLock();
		spinLock.lock();
		try {
			for (int i = 0; i < 10; i++) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						spinLock.lock();
						System.out.println(Thread.currentThread().getId()
								+ " acquired the spinLock!");
						spinLock.unlock();
					}
				}).start();
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main thread unlock!");
		spinLock.unlock();
	}
}