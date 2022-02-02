package t5750.jmx.service;

public interface JmxHelloMBean {
	String getName();

	void setName(String name);

	String getAge();

	void setAge(String age);

	void helloWorld();

	void helloWorld(String str);

	void getTelephone();

	void printHello();

	void printHello(String whoName);
}