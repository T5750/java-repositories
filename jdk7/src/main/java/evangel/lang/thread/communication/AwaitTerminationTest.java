package evangel.lang.thread.communication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 使用这个 awaitTermination() 方法的前提需要关闭线程池，如调用了 shutdown() 方法。<br/>
 * 调用了 shutdown() 之后线程池会停止接受新任务，并且会平滑的关闭线程池中现有的任务。
 */
public class AwaitTerminationTest {
	private static final Logger LOGGER = LogManager
			.getLogger(AwaitTerminationTest.class);

	private static void executorService() throws Exception {
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1,
				TimeUnit.MILLISECONDS, queue);
		poolExecutor.execute(new Runnable() {
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
		poolExecutor.execute(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("running2");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		poolExecutor.shutdown();
		while (!poolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
			LOGGER.info("线程还在执行。。。");
		}
		LOGGER.info("main over");
	}

	public static void main(String[] args) {
		try {
			executorService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
