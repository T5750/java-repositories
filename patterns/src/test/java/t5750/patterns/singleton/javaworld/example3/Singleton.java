package t5750.patterns.singleton.javaworld.example3;

public class Singleton implements java.io.Serializable {
	public static Singleton INSTANCE = new Singleton();

	protected Singleton() {
		// Exists only to thwart instantiation.
	}

	private Object readResolve() {
		return INSTANCE;
	}
}