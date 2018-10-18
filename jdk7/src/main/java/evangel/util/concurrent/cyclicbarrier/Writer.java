package evangel.util.concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Writer extends Thread {
	private CyclicBarrier cyclicBarrier;
	private long timeout;

	public Writer(CyclicBarrier cyclicBarrier, long timeout) {
		this.cyclicBarrier = cyclicBarrier;
		this.timeout = timeout;
	}

	public Writer(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		System.out.println("线程" + Thread.currentThread().getName()
				+ "正在写入数据...");
		try {
			// 以睡眠来模拟写入数据操作
			Thread.sleep(5000);
			System.out.println("线程" + Thread.currentThread().getName()
					+ "写入数据完毕，等待其他线程写入完毕");
			if (timeout > 0) {
				cyclicBarrier.await(timeout, TimeUnit.MILLISECONDS);
			} else {
				cyclicBarrier.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()
				+ "所有线程写入完毕，继续处理其他任务...");
	}
}
