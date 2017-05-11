package com.java2s.currentThread;
//http://www.java2s.com/Tutorials/Java/Java_Thread/0040__Java_Thread_Current.htm

class CatchAllThreadExceptionHandler
		implements Thread.UncaughtExceptionHandler {
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Caught  Exception from  Thread:" + t.getName());
	}
}

public class ThreadCurrentException {
	public static void main(String[] args) {
		CatchAllThreadExceptionHandler handler = new CatchAllThreadExceptionHandler();
		// Set an uncaught exception handler for main thread
		Thread.currentThread().setUncaughtExceptionHandler(handler);
		// Throw an exception
		throw new RuntimeException();
	}
}