package evangel.lang.thread.communication;

import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

/**
 * CyclicBarrier 中文名叫做屏障或者是栅栏，也可以用于线程间通信。<br/>
 * 由于其中一个线程休眠了五秒，所有其余所有的线程都得等待这个线程调用 await()
 */
public class CyclicBarrierTest {
	private static final Logger LOGGER = Logger.getRootLogger();

	private static void cyclicBarrier() throws Exception {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
		new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("thread run");
				try {
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
				LOGGER.info("thread end do something");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("thread run");
				try {
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
				LOGGER.info("thread end do something");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("thread run");
				try {
					Thread.sleep(5000);
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
				LOGGER.info("thread end do something");
			}
		}).start();
		LOGGER.info("main thread");
	}

	public static void main(String[] args) {
		try {
			cyclicBarrier();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
