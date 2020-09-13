package t5750.patterns.singleton.javaworld.example1;

//http://www.javaworld.com/article/2073352/core-java/simply-singleton.html
public class ClassicSingleton {
	private static ClassicSingleton instance = null;

	protected ClassicSingleton() {
		// Exists only to defeat instantiation.
	}

	public static ClassicSingleton getInstance() {
		if (instance == null) {
			instance = new ClassicSingleton();
		}
		return instance;
	}
}
