package throwing.stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import throwing.stream.union.UnionThrowable;

public class ReadmeTest {
    @SuppressWarnings("unchecked")
    @Test
    public void throwingStream() throws ClassNotFoundException {
        Stream<String> names = Stream.of("java.lang.Object", "java.util.stream.Stream");
        ThrowingStream<String, ClassNotFoundException> s = ThrowingStream.of(names,
                ClassNotFoundException.class);
        List<Class<?>> l = s.map(ClassLoader.getSystemClassLoader()::loadClass).collect(toList());
        assertThat(l, contains(Object.class, Stream.class));
    }

    @Test
    public void unionStream() throws CancellationException, InterruptedException,
        ExecutionException {
        Stream<Future<String>> strings = Stream.of("foo", "bar").map(s -> {
            CompletableFuture<String> f = new CompletableFuture<>();
            f.complete(s);
            return f;
        });

        try {
            List<String> l = ThrowingStream.unionOf(strings).map(Future::get).collect(
                    Collectors.<String> toList());
            assertThat(l, contains("foo", "bar"));
        } catch (UnionThrowable e) {
            throw e.rethrow(ExecutionException.class).rethrow(InterruptedException.class).rethrow(
                    CancellationException.class).finish();
        }
    }

    @Test
    public void customUnionThrowable() throws CancellationException, InterruptedException, ExecutionException, Error {
        class FutureThrowable extends UnionThrowable {
            private static final long serialVersionUID = -6765785390295893395L;

            public FutureThrowable(Throwable cause) {
                super(cause);
            }

            public Error rethrow() throws CancellationException, InterruptedException,
                ExecutionException {
                return rethrow(ExecutionException.class).rethrow(InterruptedException.class).rethrow(
                        CancellationException.class).finish();
            }
        }

        Stream<Future<String>> strings = Stream.of("foo", "bar").map(s -> {
            CompletableFuture<String> f = new CompletableFuture<>();
            f.complete(s);
            return f;
        });

        try {
            List<String> l = ThrowingStream.unionOf(strings, FutureThrowable::new).map(Future::get).collect(
                    Collectors.<String> toList());
            assertThat(l, contains("foo", "bar"));
        } catch (FutureThrowable e) {
            throw e.rethrow();
        }
    }
}
