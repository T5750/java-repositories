package t5750.hzcourse.chapter08;

/**
 * 方法静态解析演示<br/>
 * javap -verbose StaticResolution.class
 */
public class StaticResolution {
	public static void sayHello() {
		System.out.println("hello world");
	}

	public static void main(String[] args) {
		StaticResolution.sayHello();
	}
}
