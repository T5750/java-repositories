package evangel.util.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * 在main方法的for循环中，故意让最后一个线程启动延迟，因为在前面三个线程都达到barrier之后，
 * 等待了指定的时间发现第四个线程还没有达到barrier，就抛出异常并继续执行后面的任务。
 */
public class CyclicBarrierAwaitTest {
	public static void main(String[] args) {
		long timeout = 2000;
		int N = 4;
		CyclicBarrier barrier = new CyclicBarrier(N);
		for (int i = 0; i < N; i++) {
			if (i < N - 1) {
				new Writer(barrier, timeout).start();
			} else {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new Writer(barrier, timeout).start();
			}
		}
	}
}
