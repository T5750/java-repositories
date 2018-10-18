package evangel.util.concurrent.threadpoolexecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
	/**
	 * 关闭线程池
	 * 
	 * @param pool
	 * @param start
	 */
	public static void shutdown(ThreadPoolExecutor pool, long start) {
		pool.shutdown();
		try {
			// 每隔一秒钟检查一次是否执行完毕（状态为 TERMINATED）
			while (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
				// LOGGER.info("线程还在执行。。。");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		// LOGGER.info("一共处理了【{}】", (end - start));
		System.out.println("一共处理了【" + (end - start) + "】");
	}
}
