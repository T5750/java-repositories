package com.java2s.multiple;

//http://www.java2s.com/Tutorials/Java/Java_Thread/0020__Java_Thread_Multiple.htm
public class ThreadMultiple {
	private static int myValue = 1;

	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					updateBalance();
				}
			}
		});
		t.start();
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					monitorBalance();
				}
			}
		});
		t.start();
	}

	public static synchronized void updateBalance() {
		System.out.println("start:" + myValue);
		myValue = myValue + 1;
		myValue = myValue - 1;
		System.out.println("end:" + myValue);
	}

	public static synchronized void monitorBalance() {
		int b = myValue;
		if (b != 1) {
			System.out.println("Balance  changed: " + b);
			System.exit(1);
		}
	}
}
