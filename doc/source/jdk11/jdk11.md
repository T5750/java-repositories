# Java 11 Tutorial

## Overview
### New Features
Following are the major new features which are introduced in Java 11.
- **JEP 321** − HTTP Client API standardized.
- **JEP 330** − Launch Single-File Source-Code Programs without compilation
- **JEP 323** − Local-Variable Syntax for Lambda Parameters
- **JEP 181** − Nest-Based Access Control
- **JEP 331** − Low-Overhead Heap Profiling
- **JEP 318** − Epsilon, A No-Op Garbage Collector
- **JEP 333** − ZGC A Scalable Low-Latency Garbage Collector
- **Collection API Updates** − New Collection.toArray(IntFunction) Default Method.
- **String API Updates** − New methods added like `repeat()`, `isBlank()`, `strip()` and `lines()`.
- **Files API Updates** − New methods added like `readString()`, and `writeString()`.
- **Optional Updates** − New method added, `isEmpty()`.

## Standard HttpClient
Following are the steps to use an HttpClient.
- Create HttpClient instance using `HttpClient.newBuilder()` instance
- Create HttpRequest instance using `HttpRequest.newBuilder()` instance
- Make a request using `httpClient.send()` and get a response object.

## String API
- `String.repeat(int)` − Repeats a string given number of times. Returns the concatenated string.
- `String.isBlank()` − Checks if a string is empty or have white spaces only.
- `String.strip()` − Removes the leading and trailing whitespaces.
- `String.stripLeading()` − Removes the leading whitespaces.
- `String.stripTrailing()` − Removes the trailing whitespaces.
- `String.lines()` − Return the stream of lines of multi-line string.

## Collections to Array
### Old Way
```
nameArray = nameList.toArray(new String[nameList.size()]);
```

### New Way
```
nameArray = nameList.toArray(String[]::new);
```

## Optional Class
Java 11 introduced new method to Optional class as `isEmpty()` to check if value is present. `isEmpty()` returns false if value is present otherwise true.

It can be used as an alternative of `isPresent()` method which often needs to negate to check if value is not present.

## Not Predicate
Java 11 introduced new method to Predicate interface as `not()` to negate an existing predicate similar to negate method.

## Nested Based Access
Java 11 introduced a concept of nested class where we can declare a class within a class. This nesting of classes allows to logically group the classes to be used in one place, making them more readable and maintainable. Nested class can be of four types −
- Static nested classes
- Non-static nested classes
- Local classes
- Anonymous classes

## Examples
- `StandardHttpClient`
- `StringAPI`
- `Collections2Array`
- `OptionalClass`
- `NotPredicate`
- `NestedBasedAccess`

## References
- [Java 11 Tutorial](https://www.tutorialspoint.com/java11/)