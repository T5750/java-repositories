package com.journaldev.threads.lock;

public class SynchronizedLockTest {
	public static void main(String[] args) throws InterruptedException {
		SynchronizedLockExample synchronizedLockExample = new SynchronizedLockExample(
				new Resource());
		Thread t1 = new Thread(synchronizedLockExample, "t1");
		t1.start();
		Thread t2 = new Thread(synchronizedLockExample, "t2");
		t2.start();
		t1.join();
		t2.join();
	}
}
