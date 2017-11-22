# jdk8

## Runtime Environment
 - [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Simple Tutorial

Lambda expressions (Project Lambda - [JSR 335](https://www.jcp.org/en/jsr/detail?id=335)) are a new and important feature of the upcoming Java SE 8 platform ([JSR 337](https://www.jcp.org/en/jsr/detail?id=337)). They can be used to represent one method interface (also known as functional interface) in a clear and concise way using an expression in the form of:
_**(argument list) -> body**_

### Classic Code

Before Java 8, we create and start a thread by creating an anonymous class that implements the Runnable interface, as shown in the following code:
```
Runnable task1 = new Runnable(){
	@Override
	public void run(){
		System.out.println("Task #1 is running");
	}
};
Thread thread1 = new Thread(task1);
thread1.start();
```

Or pass the anonymous class into the Thread’s constructor:
```
Thread thread1 = new Thread(new Runnable() {
	@Override
	public void run(){
		System.out.println("Task #1 is running");
	}
});
thread1.start();
```

### Runnable Lambda Code

With Lambda expressions come with Java 8, the above code can be re-written more concisely. For example:
```
// Lambda Runnable
Runnable task2 = () -> { System.out.println("Task #2 is running"); };
// start the thread
new Thread(task2).start();
```

## Links
- [Java 8 Lambda - Runnable Example](http://www.codejava.net/java-core/the-java-language/java-8-lambda-runnable-example)
- [Java SE 8: Lambda Quick Start](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html)
