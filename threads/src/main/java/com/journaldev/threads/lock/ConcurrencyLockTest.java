package com.journaldev.threads.lock;

public class ConcurrencyLockTest {
	public static void main(String[] args) throws InterruptedException {
		ConcurrencyLockExample concurrencyLockExample = new ConcurrencyLockExample(
				new Resource());
		Thread t1 = new Thread(concurrencyLockExample, "t1");
		t1.start();
		Thread t2 = new Thread(concurrencyLockExample, "t2");
		t2.start();
		t1.join();
		t2.join();
	}
}
