package com.java2s.join;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0060__Java_Thread_Join.htm
public class ThreadWithoutJoin {
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				print();
			}
		});
		t1.start();
		System.out.println("Done.");
	}

	public static void print() {
		for (int i = 1; i <= 5; i++) {
			try {
				System.out.println("Counter: " + i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
