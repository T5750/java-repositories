package evangel.util.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier是可以重用的。
 * <p>
 * 在初次的4个线程越过barrier状态后，又可以用来进行新一轮的使用。 而CountDownLatch无法进行重复使用。
 * </p>
 */
public class CyclicBarrierReusingTest {
	public static void main(String[] args) {
		int N = 4;
		CyclicBarrier barrier = new CyclicBarrier(N);
		for (int i = 0; i < N; i++) {
			new Writer(barrier).start();
		}
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("CyclicBarrier重用");
		for (int i = 0; i < N; i++) {
			new Writer(barrier).start();
		}
	}
}
