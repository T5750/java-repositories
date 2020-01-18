package t5750.patterns.singleton.ibm.example1;

//https://www.ibm.com/developerworks/cn/java/designpattern/singleton/index.html
public class Singleton {
	private static Singleton s;

	private Singleton() {
	}

	/**
	 * Class method to access the singleton instance of the class.
	 */
	public static Singleton getInstance() {
		if (s == null)
			s = new Singleton();
		return s;
	}
}

// 测试类
class singletonTest {
	public static void main(String[] args) {
		Singleton s1 = Singleton.getInstance();
		Singleton s2 = Singleton.getInstance();
		if (s1 == s2)
			System.out.println("s1 is the same instance with s2");
		else
			System.out.println("s1 is not the same instance with s2");
	}
}