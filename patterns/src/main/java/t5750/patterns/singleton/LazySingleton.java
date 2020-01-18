package t5750.patterns.singleton;

/**
 * 懒汉模式
 */
public final class LazySingleton {
	private static LazySingleton singObj = null;

	private LazySingleton() {
	}

	public static LazySingleton getSingleInstance() {
		if (null == singObj) {
			singObj = new LazySingleton();
		}
		return singObj;
	}
}