package com.journaldev.threads;

import java.util.Arrays;

public class SyncronizedMethod {
	public static void main(String[] args) throws InterruptedException {
		String[] arr = { "1", "2", "3", "4", "5", "6" };
		HashMapProcessor hmp = new HashMapProcessor(arr);
		Thread t1 = new Thread(hmp, "t1");
		Thread t2 = new Thread(hmp, "t2");
		Thread t3 = new Thread(hmp, "t3");
		long start = System.currentTimeMillis();
		// start all the threads
		t1.start();
		t2.start();
		t3.start();
		// wait for threads to finish
		t1.join();
		t2.join();
		t3.join();
		System.out.println("Time taken= "
				+ (System.currentTimeMillis() - start));
		// check the shared variable value now
		System.out.println(Arrays.asList(hmp.getMap()));
	}
}

class HashMapProcessor implements Runnable {
	private String[] strArr = null;
	private Object lock = new Object();

	public HashMapProcessor(String[] m) {
		this.strArr = m;
	}

	public String[] getMap() {
		return strArr;
	}

	@Override
	public void run() {
		processArr(Thread.currentThread().getName());
	}

	private void processArr(String name) {
		for (int i = 0; i < strArr.length; i++) {
			// process data and append thread name
			processSomething(i);
			addThreadName(i, name);
		}
	}

	private void addThreadName(int i, String name) {
		synchronized (lock) {
			strArr[i] = strArr[i] + ":" + name;
		}
	}

	private void processSomething(int index) {
		// processing some job
		try {
			Thread.sleep(index * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}