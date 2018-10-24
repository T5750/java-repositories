package evangel.lang.thread.communication;

import java.util.concurrent.TimeUnit;

/**
 * 因为 Java 是采用共享内存的方式进行线程通信的，所以可以采用以下方式用主线程关闭 A 线程
 */
public class VolatileTest implements Runnable {
	private static volatile boolean flag = true;

	@Override
	public void run() {
		while (flag) {
			System.out.println(Thread.currentThread().getName() + "正在运行。。。");
		}
		System.out.println(Thread.currentThread().getName() + "执行完毕");
	}

	public static void main(String[] args) throws InterruptedException {
		VolatileTest aVolatile = new VolatileTest();
		new Thread(aVolatile, "thread A").start();
		System.out.println("main 线程正在运行");
		TimeUnit.MILLISECONDS.sleep(100);
		aVolatile.stopThread();
	}

	private void stopThread() {
		flag = false;
	}
}