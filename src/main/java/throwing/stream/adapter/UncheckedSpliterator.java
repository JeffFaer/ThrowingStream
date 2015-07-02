package throwing.stream.adapter;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import throwing.ThrowingSpliterator;

abstract class UncheckedSpliterator<T, X extends Throwable, D extends ThrowingSpliterator<T, X>, S extends Spliterator<T>> extends
    UncheckedAdapter<D, X> implements Spliterator<T>, ChainingAdapter<D, S> {
  static class Basic<T, X extends Throwable> extends
      UncheckedSpliterator<T, X, ThrowingSpliterator<T, X>, Spliterator<T>> {
    Basic(ThrowingSpliterator<T, X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator<T> trySplit() {
      return chain(ThrowingSpliterator::trySplit);
    }

    @Override
    public Spliterator<T> getSelf() {
      return this;
    }

    @Override
    public Spliterator<T> createNewAdapter(ThrowingSpliterator<T, X> newDelegate) {
      return new Basic<>(newDelegate, getExceptionClass());
    }
  }

  static class OfInt<X extends Throwable> extends
      UncheckedSpliterator<Integer, X, ThrowingSpliterator.OfInt<X>, Spliterator.OfInt> implements
      Spliterator.OfInt {
    OfInt(ThrowingSpliterator.OfInt<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator.OfInt trySplit() {
      return chain(ThrowingSpliterator.OfInt::trySplit);
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
    public Spliterator.OfInt createNewAdapter(ThrowingSpliterator.OfInt<X> newDelegate) {
      return new UncheckedSpliterator.OfInt<>(newDelegate, getExceptionClass());
    }
  }

  static class OfLong<X extends Throwable> extends
      UncheckedSpliterator<Long, X, ThrowingSpliterator.OfLong<X>, Spliterator.OfLong> implements
      Spliterator.OfLong {
    OfLong(ThrowingSpliterator.OfLong<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator.OfLong trySplit() {
      return chain(ThrowingSpliterator.OfLong::trySplit);
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
    public Spliterator.OfLong createNewAdapter(ThrowingSpliterator.OfLong<X> newDelegate) {
      return new UncheckedSpliterator.OfLong<>(newDelegate, getExceptionClass());
    }
  }

  static class OfDouble<X extends Throwable> extends
      UncheckedSpliterator<Double, X, ThrowingSpliterator.OfDouble<X>, Spliterator.OfDouble> implements
      Spliterator.OfDouble {
    OfDouble(ThrowingSpliterator.OfDouble<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public Spliterator.OfDouble trySplit() {
      return chain(ThrowingSpliterator.OfDouble::trySplit);
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
    public Spliterator.OfDouble createNewAdapter(ThrowingSpliterator.OfDouble<X> newDelegate) {
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
