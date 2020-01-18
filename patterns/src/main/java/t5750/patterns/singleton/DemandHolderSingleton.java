package t5750.patterns.singleton;

/**
 * Initialization on Demand Holder
 */
public class DemandHolderSingleton {
	private static class SingletonHolder {
		public final static DemandHolderSingleton instance = new DemandHolderSingleton();
	}

	public static DemandHolderSingleton getInstance() {
		return SingletonHolder.instance;
	}
}