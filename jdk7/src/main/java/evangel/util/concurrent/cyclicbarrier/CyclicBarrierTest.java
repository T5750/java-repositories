package evangel.util.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * 假若有若干个线程都要进行写数据操作，并且只有所有线程都完成写数据操作之后，这些线程才能继续做后面的事情，此时就可以利用CyclicBarrier了
 */
public class CyclicBarrierTest {
	public static void main(String[] args) {
		int N = 4;
		CyclicBarrier barrier = new CyclicBarrier(N);
		for (int i = 0; i < N; i++) {
			new Writer(barrier).start();
		}
	}
}
