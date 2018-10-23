package evangel.keyword.volatiletest;

import java.util.Scanner;

/**
 * 需要在两个线程间依据主内存通信时，通信的那个变量就必须的用 volatile 来修饰
 */
public class VolatileTest implements Runnable {
	private static volatile boolean flag = true;

	@Override
	public void run() {
		while (flag) {
		}
		System.out.println(Thread.currentThread().getName() + "执行完毕");
	}

	public static void main(String[] args) throws InterruptedException {
		final VolatileTest aVolatile = new VolatileTest();
		new Thread(aVolatile, "thread A").start();
		System.out.println("main 线程正在运行");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String value = sc.next();
			if (value.equals("1")) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						aVolatile.stopThread();
					}
				}).start();
				break;
			}
		}
		System.out.println("主线程退出了！");
	}

	private void stopThread() {
		flag = false;
	}
}