package name.falgout.jeffrey.throwing.stream;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.InOrder;

import name.falgout.jeffrey.throwing.Nothing;
import name.falgout.jeffrey.throwing.ThrowingSupplier;

public class ThrowingLambdasTest {
  private <X extends Throwable> ThrowingSupplier<Object, X> failure(Runnable callback, X x) {
    return () -> {
      if (callback != null) {
        callback.run();
      }
      throw x;
    };
  }

  private <X extends Throwable> ThrowingSupplier<Object, X> success() {
    Object o = new Object();
    return () -> o;
  }

  @Test
  public void fallbackFailureTest() {
    IOException e = new IOException("foo");
    Runnable mock = mock(Runnable.class);
    ThrowingSupplier<Object, IOException> s = failure(mock, e);

    Supplier<Object> fallback = this.<Nothing> success()::get;
    Object o = fallback.get();

    assertSame("fallback was called", o, s.fallbackTo(fallback).get());
    verify(mock).run();
  }

  @Test
  public void fallbackSuccessTest() throws IOException {
    ThrowingSupplier<Object, IOException> s = success();
    Object o = s.get();

    Runnable mock = mock(Runnable.class);
    Supplier<Object> s2 = failure(mock, (Nothing) null)::get;

    assertSame("fallback was not called", o, s.fallbackTo(s2).get());
    verify(mock, never()).run();
  }

  @Test
  public void orTrySingleFailureTest() throws IOException {
    IOException e = new IOException();
    Runnable mock = mock(Runnable.class);
    ThrowingSupplier<Object, IOException> s = failure(mock, e);

    ThrowingSupplier<Object, IOException> s2 = success();
    Object o = s2.get();

    assertSame("s2 was called", o, s.orTry(s2).get());
    verify(mock).run();
  }

  @Test
  public void orTryDoubleFailureTest() {
    IOException e = new IOException();
    IOException e2 = new IOException();
    Runnable m = mock(Runnable.class);
    Runnable m2 = mock(Runnable.class);

    ThrowingSupplier<Object, IOException> s = failure(m, e);
    ThrowingSupplier<Object, IOException> s2 = failure(m2, e2);

    try {
      s.orTry(s2).get();
      fail("exception was not thrown");
    } catch (IOException x) {
      assertSame(x, e2);
      assertSame(e, e2.getSuppressed()[0]);
    }

    InOrder i = inOrder(m, m2);
    i.verify(m).run();
    i.verify(m2).run();
  }

  @Test
  public void orTrySuccessTest() throws IOException {
    ThrowingSupplier<Object, IOException> s = success();
    Object o = s.get();

    Runnable mock = mock(Runnable.class);
    ThrowingSupplier<Object, Nothing> s2 = failure(mock, (Nothing) null);

    assertSame("fallback was not called", o, s.orTry(s2).get());
    verify(mock, never()).run();
  }
}
