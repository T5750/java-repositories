package com.java2s.interrupt;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0100__Java_Thread_Interrupt.htm
public class ThreadInterrupt {
	public static void main(String[] args) {
		System.out.println("#1:" + Thread.interrupted());
		// Now interrupt the main thread
		Thread.currentThread().interrupt();
		// Check if it has been interrupted
		System.out.println("#2:" + Thread.interrupted());
		// Check again if it has been interrupted
		System.out.println("#3:" + Thread.interrupted());
	}
}
