package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;

abstract class UncheckedSpliterator<T, X extends Throwable, D extends ThrowingBaseSpliterator<T, X, D>, S extends Spliterator<T>> extends
    UncheckedAdapter<D, X> implements Spliterator<T>, ChainingAdapter<D, S> {
  static class Basic<T, X extends Throwable> extends
      UncheckedSpliterator<T, X, ThrowingBaseSpliterator.ThrowingSpliterator<T, X>, Spliterator<T>> {
    Basic(ThrowingBaseSpliterator.ThrowingSpliterator<T, X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator<T> trySplit() {
      return chain(ThrowingBaseSpliterator::trySplit);
    }

    @Override
    public Spliterator<T> getSelf() {
      return this;
    }

    @Override
    public Spliterator<T> createNewAdapter(ThrowingBaseSpliterator.ThrowingSpliterator<T, X> newDelegate) {
      return new Basic<>(newDelegate, getExceptionClass());
    }
  }

  static class OfInt<X extends Throwable> extends
      UncheckedSpliterator<Integer, X, ThrowingBaseSpliterator.OfInt<X>, Spliterator.OfInt> implements
      Spliterator.OfInt {
    OfInt(ThrowingBaseSpliterator.OfInt<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator.OfInt trySplit() {
      return chain(ThrowingBaseSpliterator.OfInt::trySplit);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
      return maskException(() -> getDelegate().normalTryAdvance(action));
    }

    @Override
    public Spliterator.OfInt getSelf() {
      return this;
    }

    @Override
    public Spliterator.OfInt createNewAdapter(ThrowingBaseSpliterator.OfInt<X> newDelegate) {
      return new UncheckedSpliterator.OfInt<>(newDelegate, getExceptionClass());
    }
  }

  static class OfLong<X extends Throwable> extends
      UncheckedSpliterator<Long, X, ThrowingBaseSpliterator.OfLong<X>, Spliterator.OfLong> implements
      Spliterator.OfLong {
    OfLong(ThrowingBaseSpliterator.OfLong<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator.OfLong trySplit() {
      return chain(ThrowingBaseSpliterator.OfLong::trySplit);
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
      return maskException(() -> getDelegate().normalTryAdvance(action));
    }

    @Override
    public Spliterator.OfLong getSelf() {
      return this;
    }

    @Override
    public Spliterator.OfLong createNewAdapter(ThrowingBaseSpliterator.OfLong<X> newDelegate) {
      return new UncheckedSpliterator.OfLong<>(newDelegate, getExceptionClass());
    }
  }

  static class OfDouble<X extends Throwable> extends
      UncheckedSpliterator<Double, X, ThrowingBaseSpliterator.OfDouble<X>, Spliterator.OfDouble> implements
      Spliterator.OfDouble {
    OfDouble(ThrowingBaseSpliterator.OfDouble<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator.OfDouble trySplit() {
      return chain(ThrowingBaseSpliterator.OfDouble::trySplit);
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
      return maskException(() -> getDelegate().normalTryAdvance(action));
    }

    @Override
    public Spliterator.OfDouble getSelf() {
      return this;
    }

    @Override
    public Spliterator.OfDouble createNewAdapter(ThrowingBaseSpliterator.OfDouble<X> newDelegate) {
      return new UncheckedSpliterator.OfDouble<>(newDelegate, getExceptionClass());
    }
  }

  UncheckedSpliterator(D delegate, Class<X> x) {
    super(delegate, x);
  }

  @Override
  public boolean tryAdvance(Consumer<? super T> action) {
    return maskException(() -> getDelegate().tryAdvance(action::accept));
  }

  @Override
  public long estimateSize() {
    return getDelegate().estimateSize();
  }

  @Override
  public int characteristics() {
    return getDelegate().characteristics();
  }
}
