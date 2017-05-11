package com.java2s.state;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0130__Java_Thread_State.htm
public class ThreadState extends Thread {
	private volatile boolean keepRunning = true;
	private boolean suspended = false;

	public synchronized void stopThread() {
		this.keepRunning = false;
		this.notify();
	}

	public synchronized void suspendThread() {
		this.suspended = true;
	}

	public synchronized void resumeThread() {
		this.suspended = false;
		this.notify();
	}

	public void run() {
		System.out.println("Thread started...");
		while (keepRunning) {
			try {
				System.out.println("Going to sleep...");
				Thread.sleep(1000);
				synchronized (this) {
					while (suspended) {
						System.out.println("Suspended...");
						this.wait();
						System.out.println("Resumed...");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ThreadState t = new ThreadState();
		t.start();
		Thread.sleep(2000);
		t.suspendThread();
		Thread.sleep(2000);
		t.resumeThread();
		Thread.sleep(2000);
		t.stopThread();
	}
}