package com.tutorialspoint.synchronization;

//https://www.tutorialspoint.com/java/java_thread_synchronization.htm
class PrintDemo {
	public void printCount() {
		try {
			for (int i = 5; i > 0; i--) {
				System.out.println("Counter   ---   " + i);
			}
		} catch (Exception e) {
			System.out.println("Thread  interrupted.");
		}
	}
}

class ThreadSynchronizationDemo extends Thread {
	private Thread t;
	private String threadName;
	PrintDemo PD;

	ThreadSynchronizationDemo(String name, PrintDemo pd) {
		threadName = name;
		PD = pd;
	}

	public void run() {
		synchronized (PD) {
			PD.printCount();
		}
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

public class ThreadSynchronization {
	public static void main(String args[]) {
		PrintDemo PD = new PrintDemo();
		ThreadSynchronizationDemo T1 = new ThreadSynchronizationDemo(
				"Thread - 1 ", PD);
		ThreadSynchronizationDemo T2 = new ThreadSynchronizationDemo(
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
