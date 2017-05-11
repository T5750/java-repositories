package singleton.ibm.example2;

class SingletonException extends RuntimeException {
	public SingletonException(String s) {
		super(s);
	}
}

class Singleton {
	static boolean instance_flag = false; // true if 1 instance

	public Singleton() {
		if (instance_flag)
			throw new SingletonException("Only one instance allowed");
		else
			instance_flag = true; // set flag for 1 instance
	}
}

// 测试类
public class SingletonTest {
	static public void main(String argv[]) {
		Singleton s1, s2;
		// create one incetance--this should always work
		System.out.println("Creating one instance");
		try {
			s1 = new Singleton();
		} catch (SingletonException e) {
			System.out.println(e.getMessage());
		}
		// try to create another spooler --should fail
		System.out.println("Creating two instance");
		try {
			s2 = new Singleton();
		} catch (SingletonException e) {
			System.out.println(e.getMessage());
		}
	}
}
