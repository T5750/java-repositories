package com.tutorialspoint.synchronization;

//https://www.tutorialspoint.com/java/java_thread_synchronization.htm
class ThreadWithoutSynchronizationDemo extends Thread {
	private Thread t;
	private String threadName;
	PrintDemo PD;

	ThreadWithoutSynchronizationDemo(String name, PrintDemo pd) {
		threadName = name;
		PD = pd;
	}

	public void run() {
		PD.printCount();
		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}

public class ThreadWithoutSynchronization {
	public static void main(String args[]) {
		PrintDemo PD = new PrintDemo();
		ThreadWithoutSynchronizationDemo T1 = new ThreadWithoutSynchronizationDemo(
				"Thread - 1 ", PD);
		ThreadWithoutSynchronizationDemo T2 = new ThreadWithoutSynchronizationDemo(
				"Thread - 2 ", PD);
		T1.start();
		T2.start();
		// wait for threads to end
		try {
			T1.join();
			T2.join();
		} catch (Exception e) {
			System.out.println("Interrupted");
		}
	}
}
