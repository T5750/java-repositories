package com.bjsxt.height.concurrent017;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Temp extends Thread {
	@Override
	public void run() {
		System.out.println("run");
	}
}

public class ScheduledJob {
	public static void main(String args[]) throws Exception {
		Temp command = new Temp();
		// ScheduledExecutorService scheduler =
		// Executors.newScheduledThreadPool(1);
		ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
		ScheduledFuture<?> scheduleTask = scheduler.scheduleWithFixedDelay(
				command, 5, 1, TimeUnit.SECONDS);
	}
}