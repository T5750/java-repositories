package evangel.lang.thread.communication;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * CountDownLatch 可以实现 join 相同的功能，但是更加的灵活。
 */
public class CountDownLatchTest {
	private static final Logger LOGGER = Logger.getRootLogger();

	private static void countDownLatch() throws Exception {
		int thread = 3;
		long start = System.currentTimeMillis();
		final CountDownLatch countDown = new CountDownLatch(thread);
		for (int i = 0; i < thread; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					LOGGER.info("thread run");
					try {
						Thread.sleep(2000);
						countDown.countDown();
						LOGGER.info("thread end");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		countDown.await();
		long stop = System.currentTimeMillis();
		LOGGER.info("main over total time=" + (stop - start));
	}

	public static void main(String[] args) {
		try {
			countDownLatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
