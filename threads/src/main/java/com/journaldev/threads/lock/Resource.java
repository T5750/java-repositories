package com.journaldev.threads.lock;

public class Resource {
	public void doSomething() {
		// do some operation, DB read, write etc
		System.out.println("1. doSomething by "
				+ Thread.currentThread().getName());
	}

	public void doLogging() {
		// logging, no need for thread safety
		System.out.println("2. doLogging by "
				+ Thread.currentThread().getName());
	}
}