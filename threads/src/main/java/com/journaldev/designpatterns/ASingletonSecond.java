package com.journaldev.designpatterns;

public class ASingletonSecond {
	private static volatile ASingletonSecond instance;
	private static Object mutex = new Object();

	private ASingletonSecond() {
	}

	public static ASingletonSecond getInstance() {
		ASingletonSecond result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null) {
					instance = result = new ASingletonSecond();
				}
			}
		}
		return result;
	}
}