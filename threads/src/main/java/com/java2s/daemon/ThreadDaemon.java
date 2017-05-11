package com.java2s.daemon;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0090__Java_Thread_Demon.htm
public class ThreadDaemon {
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable()  {
			@Override
			public void run() {
				print();
			}
		});
		t.setDaemon(true);
		t.start();
		System.out.println("Exiting main  method");
	}

	public static void print() {
		int counter = 1;
		while (true) {
			try {
				System.out.println("Counter:" + counter++);
				Thread.sleep(2000); // sleep for 2 seconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
