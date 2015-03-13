package throwing.stream;

import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import throwing.Nothing;
import throwing.bridge.ThrowingBridge;
import throwing.function.ThrowingIntPredicate;

public class ThrowingStreamTest {
    public IntStream numbers = IntStream.range(0, 20);

    private <X extends Throwable> void exceptionTest(Class<X> x, Supplier<X> exceptionSupplier) throws X {
        ThrowingIntStream<X> s = ThrowingBridge.of(numbers, x);

        List<Integer> collected = new ArrayList<>();
        AtomicReference<X> ref = new AtomicReference<>();
        ThrowingIntPredicate<X> p = i -> {
            if (i >= 10) {
                X e = exceptionSupplier.get();
                ref.set(e);
                throw e;
            } else {
                return true;
            }
        };
        try {
            s.filter(p).forEach(collected::add);
            fail("did not throw exception");
        } catch (Throwable e2) {
            assertSame(ref.get(), e2);
        }

        assertEquals(10, collected.size());
    }

    @Test
    public void worksCorrectlyWithExceptions() throws Exception {
        exceptionTest(Exception.class, Exception::new);
    }

    @Test
    public void worksCorrectlyWithUncheckedExceptions() {
        exceptionTest(RuntimeException.class, RuntimeException::new);
    }

    @Test
    public void worksCorrectlyWithoutExceptions() {
        ThrowingIntStream<Nothing> s = ThrowingBridge.of(numbers);
        long l = s.filter(i -> i < 10).count();
        assertEquals(10, l);
    }

    @Test
    public void flatMapExceptions() {
        ThrowingIntStream<Exception> s = ThrowingBridge.of(numbers, Exception.class);

        List<Integer> collected = new ArrayList<>();
        Exception e = new Exception();
        try {
            s.flatMap(i -> {
                if (i >= 10) {
                    e.fillInStackTrace();
                    throw e;
                }
                return ThrowingBridge.of(IntStream.of(i), Exception.class);
            }).forEach(collected::add);
            fail("did not throw exception");
        } catch (Exception e2) {
            assertSame(e, e2);
        }

        assertEquals(10, collected.size());
    }

    @Test
    public void flatMapExceptionsInStream() {
        ThrowingIntStream<Exception> s = ThrowingBridge.of(numbers, Exception.class);

        List<Integer> collected = new ArrayList<>();
        Exception e = new Exception();
        try {
            s.flatMap(i -> ThrowingBridge.of(IntStream.of(i), Exception.class).peek(i2 -> {
                if (i2 >= 10) {
                    e.fillInStackTrace();
                    throw e;
                }
            })).forEach(collected::add);
            fail("did not throw exception");
        } catch (Exception e2) {
            assertSame(e, e2);
        }

        assertEquals(10, collected.size());
    }

    private <X extends Throwable> void verboseTest(Class<X> x, Supplier<X> exceptionSupplier) {
        verboseTest(x, exceptionSupplier, ThrowingBridge::of);
    }

    private <X extends Throwable> void verboseTest(Class<X> x, Supplier<X> exceptionSupplier,
            BiFunction<IntStream, Class<X>, ThrowingIntStream<X>> streamSupplier) {
        ThrowingIntStream<X> s = streamSupplier.apply(numbers, x);
        s = s.peek(i -> {
            throw exceptionSupplier.get();
        });
        try {
            s.count();
            fail("did not throw exception");
        } catch (Throwable e) {
            assertEquals("correct exception thrown", x, e.getClass());
            assertThat("stack trace verbose",
                    Stream.of(e.getStackTrace()).map(StackTraceElement::getClassName).toArray(String[]::new),
                    not(hasItemInArray(startsWith("throwing.bridge"))));
        }
    }

    @Test
    public void exceptionsAreNotVerbose() {
        verboseTest(Exception.class, Exception::new);
    }

    @Test
    public void runtimeExceptionsAreNotVerbose() {
        verboseTest(RuntimeException.class, RuntimeException::new);
    }

    @Test
    public void canRethrowExceptions() {
        ThrowingIntStream<IllegalArgumentException> s = ThrowingStream.of(numbers, IllegalArgumentException.class);
        IllegalArgumentException e = new IllegalArgumentException("foo");
        s = s.peek(i -> {
            throw e;
        });
        ThrowingIntStream<IOException> s2 = s.rethrow(IOException.class, IOException::new);

        try {
            s2.count();
            fail("did not throw exception");
        } catch (IOException x) {
            assertSame(e, x.getCause());
        } catch (Throwable t) {
            t.printStackTrace();
            fail("threw wrong exception");
        }
    }

    @Test
    public void rethrownExceptionsAreNotVerbose() {
        verboseTest(IOException.class, IOException::new, (s, x) -> {
            return ThrowingStream.of(s, IllegalArgumentException.class).peek(i -> {
                throw new IllegalArgumentException("foo");
            }).rethrow(x, IOException::new);
        });
    }
}
