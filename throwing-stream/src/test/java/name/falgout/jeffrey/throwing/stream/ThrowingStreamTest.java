package name.falgout.jeffrey.throwing.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.junit.Test;

import name.falgout.jeffrey.throwing.Nothing;
import name.falgout.jeffrey.throwing.ThrowingIntPredicate;
import name.falgout.jeffrey.throwing.stream.adapter.ThrowingBridge;

public class ThrowingStreamTest {
  public IntStream numbers = IntStream.range(0, 20);

  private <X extends Throwable> void exceptionTest(Class<X> x, Supplier<X> exceptionSupplier)
    throws X {
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

  @Test
  public void canRethrowExceptions() {
    ThrowingIntStream<IllegalArgumentException> s =
        ThrowingStream.of(numbers, IllegalArgumentException.class);
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
      fail("threw wrong exception");
    }
  }
}
