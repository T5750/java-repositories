package evangel.util.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier还提供一个更高级的构造函数CyclicBarrier(int parties, Runnable
 * barrierAction)，用于在线程到达屏障时，优先执行barrierAction，方便处理更复杂的业务场景。
 * <p>
 * 当四个线程都到达barrier状态后，会从四个线程中选择一个线程去执行Runnable。
 * </p>
 */
public class CyclicBarrierRunnableTest {
	public static void main(String[] args) {
		int N = 4;
		CyclicBarrier barrier = new CyclicBarrier(N, new Runnable() {
			@Override
			public void run() {
				System.out.println("当前线程" + Thread.currentThread().getName());
			}
		});
		for (int i = 0; i < N; i++) {
			new Writer(barrier).start();
		}
	}
}
