package t5750.jmx.service;

/**
 * 该类名称必须与实现的接口的前缀保持一致（即MBean前面的名称<br/>
 * JmxHello, JmxHelloMBean同包
 */
public class JmxHello implements JmxHelloMBean {
	private String name;
	private String age;

	public JmxHello() {
	}

	public JmxHello(String name) {
		this.name = name;
	}

	@Override
	public void getTelephone() {
		System.out.println("get Telephone");
	}

	@Override
	public void helloWorld() {
		System.out.println("Hello World!");
	}

	@Override
	public void helloWorld(String str) {
		System.out.println("helloWorld: " + str);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAge() {
		System.out.println("get age 123");
		return age;
	}

	@Override
	public void setAge(String age) {
		System.out.println("set age 123");
		this.age = age;
	}

	@Override
	public void printHello() {
		System.out.println("Hi, I'm " + name);
	}

	@Override
	public void printHello(String whoName) {
		System.out.println("Hello, I'm " + whoName);
	}
}