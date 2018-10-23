package evangel.keyword.volatiletest;

/**
 * 指令重排的的应用，一个经典的使用场景就是双重懒加载的单例模式
 */
public class Singleton {
	private static volatile Singleton singleton;

	private Singleton() {
	}

	public static Singleton getInstance() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					// 防止指令重排
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
}
