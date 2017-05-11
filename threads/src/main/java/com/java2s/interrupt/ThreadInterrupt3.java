package com.java2s.interrupt;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0100__Java_Thread_Interrupt.htm
public class ThreadInterrupt3 {
	public static void main(String[] args) {
		System.out.println("#1:" + Thread.interrupted());
		Thread mainThread = Thread.currentThread();
		mainThread.interrupt();
		System.out.println("#2:" + mainThread.isInterrupted());
		System.out.println("#3:" + mainThread.isInterrupted());
		System.out.println("#4:" + Thread.interrupted());
		System.out.println("#5:" + mainThread.isInterrupted());
	}
}
