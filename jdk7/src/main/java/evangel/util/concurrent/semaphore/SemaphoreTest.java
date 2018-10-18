package evangel.util.concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 假若一个工厂有5台机器，但是有8个工人，一台机器同时只能被一个工人使用，只有使用完了，其他工人才能继续使用。
 */
public class SemaphoreTest {
	public static void main(String[] args) {
		// 工人数
		int N = 8;
		// 机器数目
		Semaphore semaphore = new Semaphore(5);
		for (int i = 0; i < N; i++) {
			new Worker(i, semaphore).start();
		}
	}

	static class Worker extends Thread {
		private int num;
		private Semaphore semaphore;

		public Worker(int num, Semaphore semaphore) {
			this.num = num;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println("工人" + this.num + "占用一个机器在生产...");
				Thread.sleep(2000);
				System.out.println("工人" + this.num + "释放出机器");
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
