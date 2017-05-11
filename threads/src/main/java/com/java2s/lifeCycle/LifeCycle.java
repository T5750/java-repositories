package com.java2s.lifeCycle;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0070__Java_Thread_Life_Cycle.htm
class ThreadState extends Thread {
	private boolean keepRunning = true;
	private boolean wait = false;
	private Object syncObject = null;

	public ThreadState(Object syncObject) {
		this.syncObject = syncObject;
	}

	public void run() {
		while (keepRunning) {
			synchronized (syncObject) {
				if (wait) {
					try {
						syncObject.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void setKeepRunning(boolean keepRunning) {
		this.keepRunning = keepRunning;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}
}

public class LifeCycle {
	public static void main(String[] args) throws InterruptedException {
		Object syncObject = new Object();
		ThreadState ts = new ThreadState(syncObject);
		System.out.println("Before start()-ts.isAlive():" + ts.isAlive());
		System.out.println("#1:" + ts.getState());
		ts.start();
		System.out.println("After start()-ts.isAlive():" + ts.isAlive());
		System.out.println("#2:" + ts.getState());
		ts.setWait(true);
		Thread.currentThread().sleep(100);
		synchronized (syncObject) {
			System.out.println("#3:" + ts.getState());
			ts.setWait(false);
			syncObject.notifyAll();
		}
		Thread.currentThread().sleep(2000);
		System.out.println("#4:" + ts.getState());
		ts.setKeepRunning(false);
		Thread.currentThread().sleep(2000);
		System.out.println("#5:" + ts.getState());
		System.out.println("At the   end. ts.isAlive():" + ts.isAlive());
	}
}
