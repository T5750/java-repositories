package evangel.util.concurrent.locks.abstractqueuedsynchronizer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 排他锁的实现，一次只能一个线程获取到锁。
 */
public class MutexTest {
	public static void main(String[] args) {
		final Mutex mutex = new Mutex();
		mutex.lock();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
		for (int i = 0; i < 15; i++) {
			MyTask myTask = new MyTask(i, mutex);
			executor.execute(myTask);
			System.out.println("线程池中线程数目：" + executor.getPoolSize()
					+ "，队列中等待执行的任务数目：" + executor.getQueue().size()
					+ "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
		}
		executor.shutdown();
		System.out.println("main thread unlock!");
		mutex.unlock();
	}
}

class MyTask implements Runnable {
	private int taskNum;
	private Mutex mutex;

	public MyTask(int num, Mutex mutex) {
		this.taskNum = num;
		this.mutex = mutex;
	}

	@Override
	public void run() {
		try {
			mutex.lock();
			System.out.println("正在执行task " + taskNum);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.unlock();
			System.out.println("task " + taskNum + " 执行完毕");
		}
	}
}
