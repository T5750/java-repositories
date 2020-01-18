package t5750.patterns.singleton;

/**
 * 线程安全
 */
public final class ThreadSafeSingleton {
	private static ThreadSafeSingleton singObj = null;

	private ThreadSafeSingleton() {
	}

	public static synchronized ThreadSafeSingleton getSingleInstance() {
		if (null == singObj) {
			singObj = new ThreadSafeSingleton();
		}
		return singObj;
	}
}