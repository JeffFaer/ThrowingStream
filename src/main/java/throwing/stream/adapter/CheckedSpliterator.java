package throwing.stream.adapter;

import java.util.Spliterator;

import throwing.ThrowingSpliterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

abstract class CheckedSpliterator<T, X extends Throwable, S extends ThrowingSpliterator<T, X>, D extends Spliterator<T>> extends
    CheckedAdapter<D, X> implements ThrowingSpliterator<T, X>, ChainingAdapter<S, D> {
  static class Basic<T, X extends Throwable> extends
      CheckedSpliterator<T, X, ThrowingSpliterator<T, X>, Spliterator<T>> {
    Basic(Spliterator<T> delegate, FunctionAdapter<X> functionAdapter) {
      super(delegate, functionAdapter);
    }

    @Override
    public ThrowingSpliterator<T, X> trySplit() {
      return chain(Spliterator<T>::trySplit);
    }

    @Override
    public ThrowingSpliterator<T, X> getSelf() {
      return this;
    }

    @Override
    public ThrowingSpliterator<T, X> createNewAdapter(Spliterator<T> newDelegate) {
      return new Basic<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfInt<X extends Throwable> extends
      CheckedSpliterator<Integer, X, ThrowingSpliterator.OfInt<X>, Spliterator.OfInt> implements
      ThrowingSpliterator.OfInt<X> {
    OfInt(Spliterator.OfInt delegate, FunctionAdapter<X> functionAdapter) {
      super(delegate, functionAdapter);
    }

    @Override
    public ThrowingSpliterator.OfInt<X> trySplit() {
      return chain(Spliterator.OfInt::trySplit);
    }

    @Override
    public boolean tryAdvance(ThrowingIntConsumer<? extends X> action) throws X {
      return unmaskException(() -> getDelegate().tryAdvance(getFunctionAdapter().convert(action)));
    }

    @Override
    public ThrowingSpliterator.OfInt<X> getSelf() {
      return this;
    }

    @Override
    public ThrowingSpliterator.OfInt<X> createNewAdapter(Spliterator.OfInt newDelegate) {
      return new CheckedSpliterator.OfInt<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfLong<X extends Throwable> extends
      CheckedSpliterator<Long, X, ThrowingSpliterator.OfLong<X>, Spliterator.OfLong> implements
      ThrowingSpliterator.OfLong<X> {
    OfLong(Spliterator.OfLong delegate, FunctionAdapter<X> functionAdapter) {
      super(delegate, functionAdapter);
    }

    @Override
    public ThrowingSpliterator.OfLong<X> trySplit() {
      return chain(Spliterator.OfLong::trySplit);
    }

    @Override
    public boolean tryAdvance(ThrowingLongConsumer<? extends X> action) throws X {
      return unmaskException(() -> getDelegate().tryAdvance(getFunctionAdapter().convert(action)));
    }

    @Override
    public ThrowingSpliterator.OfLong<X> getSelf() {
      return this;
    }

    @Override
    public ThrowingSpliterator.OfLong<X> createNewAdapter(Spliterator.OfLong newDelegate) {
      return new CheckedSpliterator.OfLong<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfDouble<X extends Throwable> extends
      CheckedSpliterator<Double, X, ThrowingSpliterator.OfDouble<X>, Spliterator.OfDouble> implements
      ThrowingSpliterator.OfDouble<X> {
    OfDouble(Spliterator.OfDouble delegate, FunctionAdapter<X> functionAdapter) {
      super(delegate, functionAdapter);
    }

    @Override
    public ThrowingSpliterator.OfDouble<X> trySplit() {
      return chain(Spliterator.OfDouble::trySplit);
    }

    @Override
    public boolean tryAdvance(ThrowingDoubleConsumer<? extends X> action) throws X {
      return unmaskException(() -> getDelegate().tryAdvance(getFunctionAdapter().convert(action)));
    }

    @Override
    public ThrowingSpliterator.OfDouble<X> getSelf() {
      return this;
    }

    @Override
    public ThrowingSpliterator.OfDouble<X> createNewAdapter(Spliterator.OfDouble newDelegate) {
      return new CheckedSpliterator.OfDouble<>(newDelegate, getFunctionAdapter());
    }
  }

  CheckedSpliterator(D delegate, FunctionAdapter<X> functionAdapter) {
    super(delegate, functionAdapter);
  }

  @Override
  public boolean tryAdvance(ThrowingConsumer<? super T, ? extends X> action) throws X {
    return unmaskException(() -> getDelegate().tryAdvance(getFunctionAdapter().convert(action)));
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
