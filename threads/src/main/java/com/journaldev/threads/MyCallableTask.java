package com.journaldev.threads;

import java.util.concurrent.Callable;

public class MyCallableTask implements Callable<String> {
	private long waitTime;

	public MyCallableTask(int timeInMillis) {
		this.waitTime = timeInMillis;
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(waitTime);
		// return the thread name executing this callable task
		return Thread.currentThread().getName();
	}
}