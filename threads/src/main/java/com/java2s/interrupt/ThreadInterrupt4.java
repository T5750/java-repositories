package com.java2s.interrupt;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0100__Java_Thread_Interrupt.htm
public class ThreadInterrupt4 {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				print();
			}
		});
		t.start();
		Thread.sleep(5000);
		t.interrupt();
	}

	public static void print() {
		int counter = 1;
		while (true) {
			try {
				Thread.sleep(1000);
				System.out.println("Counter:" + counter++);
			} catch (InterruptedException e) {
				System.out.println("I got  interrupted!");
			}
		}
	}
}
