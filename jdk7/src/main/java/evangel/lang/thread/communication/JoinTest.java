package evangel.lang.thread.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 在 t1.join() 时会一直阻塞到 t1 执行完毕，所以最终主线程会等待 t1 和 t2 线程执行完毕。
 */
public class JoinTest {
	private static final Logger LOGGER = LogManager
			.getLogger(JoinTest.class);

	private static void join() throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("running");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("running2");
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
		t2.start();
		// 等待线程1终止
		t1.join();
		// 等待线程2终止
		t2.join();
		LOGGER.info("main over");
	}

	public static void main(String[] args) {
		try {
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
