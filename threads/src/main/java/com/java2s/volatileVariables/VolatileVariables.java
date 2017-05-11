package com.java2s.volatileVariables;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0120__Java_Volatile_Variables.htm
public class VolatileVariables extends Thread {
	private volatile boolean keepRunning = true;

	public void run() {
		System.out.println("Thread started");
		while (keepRunning) {
			try {
				System.out.println("Going to sleep");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Thread stopped");
	}

	public void stopThread() {
		this.keepRunning = false;
	}

	public static void main(String[] args) throws Exception {
		VolatileVariables v = new VolatileVariables();
		v.start();
		Thread.sleep(3000);
		System.out.println("Going to set the stop flag to true");
		v.stopThread();
	}
}