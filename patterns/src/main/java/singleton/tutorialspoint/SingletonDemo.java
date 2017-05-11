package singleton.tutorialspoint;

//https://www.tutorialspoint.com/java/java_using_singleton.htm
// File Name: SingletonDemo.java
public class SingletonDemo {
	public static void main(String[] args) {
		// Example 1
		Singleton tmp = Singleton.getInstance();
		tmp.demoMethod();
		// Example 2
		ClassicSingleton classSignature = ClassicSingleton.getInstance();
		classSignature.demoMethod();
	}
}