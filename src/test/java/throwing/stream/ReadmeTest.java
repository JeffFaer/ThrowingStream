package throwing.stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class ReadmeTest {
    @SuppressWarnings("unchecked")
    @Test
    public void readme() throws ClassNotFoundException {
        Stream<String> names = Stream.of("java.lang.Object", "java.util.stream.Stream");
        ThrowingStream<String, ClassNotFoundException> s = ThrowingStream.of(names, ClassNotFoundException.class);
        List<Class<?>> l = s.map(ClassLoader.getSystemClassLoader()::loadClass).collect(toList());
        assertThat(l, contains(Object.class, Stream.class));
    }
}
