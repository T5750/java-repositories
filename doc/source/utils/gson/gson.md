# Gson

Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson can work with arbitrary Java objects including pre-existing objects that you do not have source-code of.

There are a few open-source projects that can convert Java objects to JSON. However, most of them require that you place Java annotations in your classes; something that you can not do if you do not have access to the source-code. Most also do not fully support the use of Java Generics. Gson considers both of these as very important design goals.

## Goals
Provide simple `toJson()` and `fromJson()` methods to convert Java objects to JSON and vice-versa
Allow pre-existing unmodifiable objects to be converted to and from JSON
Extensive support of Java Generics
Allow custom representations for objects
Support arbitrarily complex objects (with deep inheritance hierarchies and extensive use of generic types)

## Download
Gradle:
```
dependencies {
  implementation 'com.google.code.gson:gson:2.8.6'
}
```
Maven:
```
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.8.6</version>
</dependency>
```

## Documentation
- [API Javadoc](https://www.javadoc.io/doc/com.google.code.gson/gson): Documentation for the current release
- [User guide](https://github.com/google/gson/blob/master/UserGuide.md): This guide contains examples on how to use Gson in your code.
- [Change log](https://github.com/google/gson/blob/master/CHANGELOG.md): Changes in the recent versions
- [Design document](https://github.com/google/gson/blob/master/GsonDesignDocument.md): This document discusses issues we faced while designing Gson. It also includes a comparison of Gson with other Java libraries that can be used for Json conversion

## References
- [Gson](https://github.com/google/gson)