# Java 8 Tutorial

## Overview
### New Features
- **Lambda expression** − Adds functional processing capability to Java.
- **Method references** − Referencing functions by their names instead of invoking them directly. Using functions as parameter.
- **Default method** − Interface to have default method implementation.
- **New tools** − New compiler tools and utilities are added like ‘jdeps’ to figure out dependencies.
- **Stream API** − New stream API to facilitate pipeline processing.
- **Date Time API** − Improved date time API.
- **Optional** − Emphasis on best practices to handle null values properly.
- **Nashorn, JavaScript Engine** − A Java-based engine to execute JavaScript code.

## Lambda Expressions
### Syntax
```
parameter -> expression body
```

### Lambda Expressions Example
```
MathOperation addition = (int a, int b) -> a + b;

GreetingService greetService1 = message -> System.out.println("Hello " + message);
```
- `LambdaSort`
- `LambdaMathService`

## Method References
```
List names = new ArrayList();
names.forEach(System.out::println);
```
- `MethodReferences`

## Functional Interfaces
- `FunctionalInterfaces`

## Default Methods
### Syntax
```
public interface vehicle {
   default void print() {
      System.out.println("I am a vehicle!");
   }
}
```

### Multiple Defaults
```
public interface fourWheeler {
   default void print() {
      System.out.println("I am a four wheeler!");
   }
}
```
```
public class car implements vehicle, fourWheeler {
   public void print() {
      System.out.println("I am a four wheeler car vehicle!");
   }
}
```

### Static Default Methods
```
public interface vehicle {
   default void print() {
      System.out.println("I am a vehicle!");
   }
	
   static void blowHorn() {
      System.out.println("Blowing horn!!!");
   }
}
```
- `DefaultMethods`

## Streams
### Generating Streams
```
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
```

### forEach
```
Random random = new Random();
random.ints().limit(10).forEach(System.out::println);
```

### map
```
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
//get list of unique squares
List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
```

### filter
```
List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
//get count of empty string
int count = strings.stream().filter(string -> string.isEmpty()).count();
```

### limit
```
Random random = new Random();
random.ints().limit(10).forEach(System.out::println);
```

### sorted
```
Random random = new Random();
random.ints().limit(10).sorted().forEach(System.out::println);
```

### Parallel Processing
```
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
//get count of empty string
long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
```

### Collectors
```
List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
System.out.println("Filtered List: " + filtered);
String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
System.out.println("Merged String: " + mergedString);
```

### Statistics
```
List numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
System.out.println("Highest number in List : " + stats.getMax());
System.out.println("Lowest number in List : " + stats.getMin());
System.out.println("Sum of all numbers : " + stats.getSum());
System.out.println("Average of all numbers : " + stats.getAverage());
```
- `Streams`

## Optional Class
### Class Declaration
```
public final class Optional<T> extends Object
```
- `OptionalClass`

## Nashorn JavaScript
- `NashornJavaScript`

## New Date/Time API
- `LocalDateTimeAPI`  
- `ZonedDateTimeAPI`
- `ChronoUnitsEnum`
- `PeriodAndDuration`
- `TemporalAdjusters`
- `BackwardCompatibility`

## Base64
- `Base64Example`

## References
- [Java 8 - Overview](https://www.tutorialspoint.com/java8/java8_overview.htm)