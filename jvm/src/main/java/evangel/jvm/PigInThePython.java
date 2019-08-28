package evangel.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://ifeve.com/garbage-collection-increasing-the-throughput/">
 * GC对吞吐量的影响</a>
 * 
 * -XX:+PrintGCDetails -Xms4g -Xmx4g -XX:+UseConcMarkSweepGC -Xmn512m
 * -XX:+PrintGCDetails -Xms2g -Xmx2g -XX:+UseParallelGC -Xmn1536m
 */
public class PigInThePython {
	static volatile List pigs = new ArrayList();
	static volatile int pigsEaten = 0;
	static final int ENOUGH_PIGS = 2000;

	public static void main(String[] args) throws InterruptedException {
		new PigEater().start();
		new PigDigester().start();
	}

	/**
	 * 模仿巨蟒不停吞食大肥猪的过程。每次吞食完后会睡眠100ms
	 */
	static class PigEater extends Thread {
		@Override
		public void run() {
			while (true) {
				// 32MB per pig
				pigs.add(new byte[32 * 1024 * 1024]);
				if (pigsEaten > ENOUGH_PIGS) {
					return;
				}
				takeANap(100);
			}
		}
	}

	/**
	 * 模拟异步消化的过程。由于这是个很累的过程，因此每次清除完引用后这个线程都会睡眠2000ms
	 */
	static class PigDigester extends Thread {
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			while (true) {
				takeANap(2000);
				pigsEaten += pigs.size();
				pigs = new ArrayList();
				if (pigsEaten > ENOUGH_PIGS) {
					System.out.format("Digested %d pigs in %d ms.%n",
							pigsEaten, System.currentTimeMillis() - start);
					return;
				}
			}
		}
	}

	static void takeANap(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}