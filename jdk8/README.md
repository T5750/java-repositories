# jdk8

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Java 8 Lambda - Runnable Example

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

## Java 8 Lambda - Collections Comparator Example

### Sorting the list collection in the classic way

As a way mentioned in the article [Sorting List Collections Examples](http://www.codejava.net/java-core/collections/sorting-list-collections-examples), we can write a comparator to sort the list. For example, the following code creates a comparator which compares two books by their titles:
```
Comparator<Book> titleComparator = new Comparator<Book>() {
	public int compare(Book book1, Book book2) {
		return book1.getTitle().compareTo(book2.getTitle());
	}
};
```

### Sorting the list collection in the Lambda way

Since Java 8 with Lambda expressions support, we can write a comparator in a more concise way as follows:
```
Comparator<Book> descPriceComp = (Book b1, Book b2) -> (int) (b2.getPrice() - b1.getPrice());
```
This comparator compares two books by their prices which cause the list to be sorted in descending order of prices, using the Lambda expression:
```
(Book b1, Book b2) -> (int) (b2.getPrice() - b1.getPrice());
```

## Java 8 Lambda - Listener Example

### Classic Listener Code

Before Java 8, it’s very common that an anonymous class is used to handle click event of a `JButton`, as shown in the following code:
```
JButton button = new JButton("Click Me!");
button.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent evt) {
		System.out.println("Handled by anonymous class listener");
	}
});
```
Here, an anonymous class that implements the `ActionListener` interface is created and passed into the `addActionListener()` method. And as normal, the event handler code is placed inside the `actionPerformed()` method.

### Using Lambda Listener Code

Because the `ActionListener` interface defines only one method `actionPerformed()`, it is a functional interface which means there’s a place to use Lambda expressions to replace the boilerplate code. The above example can be re-written using Lambda expressions as follows:
```
button.addActionListener(e -> System.out.println("Handled by Lambda listener"));
```

## Links
- [Java 8 Lambda - Runnable Example](http://www.codejava.net/java-core/the-java-language/java-8-lambda-runnable-example)
- [Java 8 Lambda - Collections Comparator Example](http://www.codejava.net/java-core/the-java-language/java-8-lambda-collections-comparator-example)
- [Java 8 Lambda - Listener Example](http://www.codejava.net/java-core/the-java-language/java-8-lambda-listener-example)
- [Java SE 8: Lambda Quick Start](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html)
