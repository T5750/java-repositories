package com.java2s.currentThread;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0040__Java_Thread_Current.htm
public class ThreadCurrent extends Thread {
	public ThreadCurrent(String name) {
		super(name);
	}

	@Override
	public void run() {
		Thread t = Thread.currentThread();
		String threadName = t.getName();
		System.out.println("Inside run() method:  " + threadName);
	}

	public static void main(String[] args) {
		ThreadCurrent ct1 = new ThreadCurrent("First Thread");
		ThreadCurrent ct2 = new ThreadCurrent("Second Thread");
		ct1.start();
		ct2.start();
		Thread t = Thread.currentThread();
		String threadName = t.getName();
		System.out.println("Inside  ThreadCurrent() method:  " + threadName);
	}
}