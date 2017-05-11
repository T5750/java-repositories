package com.java2s.interrupt;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0100__Java_Thread_Interrupt.htm
public class ThreadInterrupt2 {
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				print();
			}
		});
		t.start();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t.interrupt();
	}

	public static void print() {
		int counter = 0;
		while (!Thread.interrupted()) {
			counter++;
		}
		System.out.println("Counter:" + counter);
	}
}
