package com.bjsxt.base.conn011;

public class Singletion {
	private static class InnerSingletion {
		private static Singletion single = new Singletion();
	}

	public static Singletion getInstance() {
		return InnerSingletion.single;
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Singletion.getInstance().hashCode());
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Singletion.getInstance().hashCode());
			}
		}, "t2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Singletion.getInstance().hashCode());
			}
		}, "t3");
		t1.start();
		t2.start();
		t3.start();
	}
}
