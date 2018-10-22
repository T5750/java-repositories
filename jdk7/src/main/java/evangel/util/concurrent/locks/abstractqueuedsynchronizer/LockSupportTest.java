package evangel.util.concurrent.locks.abstractqueuedsynchronizer;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的park/unpark和Object的wait/notify，虽然两者用法不同，但是有一点，
 * LockSupport的park和Object的wait一样也能响应中断。
 */
public class LockSupportTest {
	public static void main(String[] args) throws InterruptedException {
		final Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				LockSupport.park();
				System.out.println("thread " + Thread.currentThread().getId()
						+ " awake!");
			}
		});
		t.start();
		Thread.sleep(3000);
		// 2. 中断
		t.interrupt();
	}
}
