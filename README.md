ThrowingStream
==============

A wrapper around `java.util.stream` and its various supporting interfaces that allows for checked exceptions to be thrown.

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
