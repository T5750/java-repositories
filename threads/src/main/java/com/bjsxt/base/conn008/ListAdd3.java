package com.bjsxt.base.conn008;

import java.util.ArrayList;
import java.util.List;

/**
 * wait notfiy 方法，wait释放锁，notfiy不释放锁
 * 
 * @author alienware
 *
 */
public class ListAdd3 {
	private volatile static List list = new ArrayList();

	public void add() {
		list.add("bjsxt");
	}

	public int size() {
		return list.size();
	}

	public static void main(String[] args) {
		final ListAdd3 list2 = new ListAdd3();
		// 1 实例化出来一个 lock
		// 当使用wait 和 notify 的时候 ， 一定要配合着synchronized关键字去使用
		final Object lock = new Object();
		// final CountDownLatch countDownLatch = new CountDownLatch(1);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 10; i++) {
						// synchronized 需在for循环里边
						synchronized (lock) {
							list2.add();
							System.out.println(
									"当前线程：" + Thread.currentThread().getName()
											+ "添加了一个元素..");
							Thread.sleep(500);
							if (list2.size() == 5) {
								System.out.println("已经发出通知..");
								// countDownLatch.countDown();
								lock.notify();
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					if (list2.size() != 5) {
						try {
							System.out.println("t2进入...");
							lock.wait();
							// countDownLatch.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("当前线程："
							+ Thread.currentThread().getName() + "收到通知线程停止..");
					throw new RuntimeException();
				}
			}
		}, "t2");
		t2.start();
		t1.start();
	}
}
