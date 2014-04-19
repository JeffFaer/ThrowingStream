package throwing.stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import throwing.bridge.ThrowingBridge;

public class ReadmeTest {
    @Test
    public void readme() throws ClassNotFoundException {
        Stream<String> names = Stream.of("java.lang.Object", "java.util.stream.Stream");
        ThrowingStream<String, ClassNotFoundException> s = ThrowingBridge.of(names, ClassNotFoundException.class);
        List<Class<?>> l = s.map(ClassLoader.getSystemClassLoader()::loadClass).collect(toList());
        assertEquals(2, l.size());
    }
}
