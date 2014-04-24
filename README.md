ThrowingStream
==============

This project is an alternative API to `java.util.stream` and its various supporting interfaces that allows for checked exceptions to be thrown.

Example usage:

````
Stream<String> names = Stream.of("java.lang.Object", "java.util.stream.Stream");
ThrowingStream<String, ClassNotFoundException> s = ThrowingBridge.of(names, ClassNotFoundException.class);
s.map(ClassLoader.getSystemClassLoader()::loadClass).forEach(System.out::println);
````

Output:

````
class java.lang.Object
interface java.util.stream.Stream
````

###How can I use this library in my project?
 1. [Clone the repository](http://git-scm.com/book/en/Git-Basics-Getting-a-Git-Repository#Cloning-an-Existing-Repository) and execute `mvn package` to create a .jar file
 2. Download a .jar from the [releases](https://github.com/JeffreyFalgout/ThrowingStream/releases) page
 3. Maven central
