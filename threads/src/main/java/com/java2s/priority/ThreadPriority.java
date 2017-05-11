package com.java2s.priority;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0080__Java_Thread_Priority.htm
public class ThreadPriority {
	public static void main(String[] args) {
		Thread t = Thread.currentThread();
		System.out.println("main Thread  Priority:" + t.getPriority());
		Thread t1 = new Thread();
		System.out.println("Thread(t1) Priority:" + t1.getPriority());
		t.setPriority(Thread.MAX_PRIORITY);
		System.out.println("main Thread  Priority:" + t.getPriority());
		Thread t2 = new Thread();
		System.out.println("Thread(t2) Priority:" + t2.getPriority());
		// Change thread t2 priority to minimum
		t2.setPriority(Thread.MIN_PRIORITY);
		System.out.println("Thread(t2) Priority:" + t2.getPriority());
	}
}
