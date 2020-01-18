package t5750.patterns.singleton;

/**
 * 双重检查锁（Double-Checked Lock）
 */
public final class DoubleCheckedSingleton {
	private static DoubleCheckedSingleton singObj = null;

	private DoubleCheckedSingleton() {
	}

	public static DoubleCheckedSingleton getSingleInstance() {
		if (null == singObj) {
			synchronized (DoubleCheckedSingleton.class) {
				if (null == singObj) {
					singObj = new DoubleCheckedSingleton();
				}
			}
		}
		return singObj;
	}
}