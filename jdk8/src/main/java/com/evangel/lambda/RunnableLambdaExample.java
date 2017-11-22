package com.evangel.lambda;

/**
 * This simple program demonstrates how to use Lambda expressions to create and
 * run threads.
 *
 * @author www.codejava.net
 */
public class RunnableLambdaExample {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + ": RunnableTest");
		// Anonymous Runnable
		Runnable task1 = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()
						+ " is running");
			}
		};
		// Passing a Runnable when creating a new thread
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()
						+ " is running");
			}
		});
		// Lambda Runnable
		Runnable task3 = () -> {
			System.out
					.println(Thread.currentThread().getName() + " is running");
		};
		Thread thread1 = new Thread(task1);
		thread1.start();
		thread2.start();
		new Thread(task3).start();
	}
}