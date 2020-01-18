package t5750.patterns.singleton;

/**
 * 饿汉模式
 */
public final class EagerSingleton {
	private static EagerSingleton singObj = new EagerSingleton();

	private EagerSingleton() {
	}

	public static EagerSingleton getSingleInstance() {
		return singObj;
	}
}