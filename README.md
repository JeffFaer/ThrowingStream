ThrowingStream
==============
[![Maven Central][mvn-img]][mvn-link]

This project is an alternative API to `java.util.stream` and its various supporting interfaces that allows for checked exceptions to be thrown.

Example usage:

````
Stream<String> names = Stream.of("java.lang.Object", "java.util.stream.Stream");
ThrowingStream<String, ClassNotFoundException> s = ThrowingStream.of(names, 
    ClassNotFoundException.class);
s.map(ClassLoader.getSystemClassLoader()::loadClass).forEach(System.out::println);
````

Output:

````
class java.lang.Object
interface java.util.stream.Stream
````

###How can I use this library in my project?
 - [Clone the repository](http://git-scm.com/book/en/Git-Basics-Getting-a-Git-Repository#Cloning-an-Existing-Repository) and use maven to generate a .jar file:
````
git clone https://github.com/JeffreyFalgout/ThrowingStream
cd ThrowingStream/
mvn package
````
 - Download a .jar from the [releases](https://github.com/JeffreyFalgout/ThrowingStream/releases) page
 - [Maven central][mvn-link]:
````
<dependency>
    <groupId>name.falgout.jeffrey</groupId>
    <artifactId>throwing-streams</artifactId>
    <version>X.Y.Z</version>
</dependency>
````

### How does it work?
Check out the [wiki](https://github.com/JeffreyFalgout/ThrowingStream/wiki/How-it-works).

[mvn-img]: https://maven-badges.herokuapp.com/maven-central/name.falgout.jeffrey/throwing-streams/badge.svg
[mvn-link]: https://maven-badges.herokuapp.com/maven-central/name.falgout.jeffrey/throwing-streams
