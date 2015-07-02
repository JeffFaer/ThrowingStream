package throwing.stream.union.adapter;

import throwing.ThrowingSpliterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;
import throwing.stream.adapter.ChainingAdapter;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionThrowable;

abstract class UnionSpliteratorAdapter<T, X extends UnionThrowable, D extends ThrowingSpliterator<T, X>, S extends UnionSpliterator<T, X>> extends
    UnionAdapter<D, X> implements UnionSpliterator<T, X>, ChainingAdapter<D, S> {
  static class Basic<T, X extends UnionThrowable> extends
      UnionSpliteratorAdapter<T, X, ThrowingSpliterator<T, X>, UnionSpliterator<T, X>> {
    Basic(ThrowingSpliterator<T, X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionSpliterator<T, X> trySplit() {
      return chain(ThrowingSpliterator::trySplit);
    }

    @Override
    public UnionSpliterator<T, X> getSelf() {
      return this;
    }

    @Override
    public UnionSpliterator<T, X> createNewAdapter(ThrowingSpliterator<T, X> newDelegate) {
      return new Basic<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfInt<X extends UnionThrowable> extends
      UnionSpliteratorAdapter<Integer, X, ThrowingSpliterator.OfInt<X>, UnionSpliterator.OfInt<X>> implements
      UnionSpliterator.OfInt<X> {
    OfInt(ThrowingSpliterator.OfInt<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionSpliterator.OfInt<X> trySplit() {
      return chain(getDelegate().trySplit());
    }

    @Override
    public boolean tryAdvance(ThrowingIntConsumer<? extends Throwable> action) throws X {
      return getDelegate().tryAdvance(getFunctionAdapter().convert(action));
    }

    @Override
    public UnionSpliterator.OfInt<X> getSelf() {
      return this;
    }

    @Override
    public UnionSpliterator.OfInt<X> createNewAdapter(ThrowingSpliterator.OfInt<X> newDelegate) {
      return new UnionSpliteratorAdapter.OfInt<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfLong<X extends UnionThrowable> extends
      UnionSpliteratorAdapter<Long, X, ThrowingSpliterator.OfLong<X>, UnionSpliterator.OfLong<X>> implements
      UnionSpliterator.OfLong<X> {
    OfLong(ThrowingSpliterator.OfLong<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionSpliterator.OfLong<X> trySplit() {
      return chain(getDelegate().trySplit());
    }

    @Override
    public boolean tryAdvance(ThrowingLongConsumer<? extends Throwable> action) throws X {
      return getDelegate().tryAdvance(getFunctionAdapter().convert(action));
    }

    @Override
    public UnionSpliterator.OfLong<X> getSelf() {
      return this;
    }

    @Override
    public UnionSpliterator.OfLong<X> createNewAdapter(ThrowingSpliterator.OfLong<X> newDelegate) {
      return new UnionSpliteratorAdapter.OfLong<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfDouble<X extends UnionThrowable> extends
      UnionSpliteratorAdapter<Double, X, ThrowingSpliterator.OfDouble<X>, UnionSpliterator.OfDouble<X>> implements
      UnionSpliterator.OfDouble<X> {
    OfDouble(ThrowingSpliterator.OfDouble<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionSpliterator.OfDouble<X> trySplit() {
      return chain(getDelegate().trySplit());
    }

    @Override
    public boolean tryAdvance(ThrowingDoubleConsumer<? extends Throwable> action) throws X {
      return getDelegate().tryAdvance(getFunctionAdapter().convert(action));
    }

    @Override
    public UnionSpliterator.OfDouble<X> getSelf() {
      return this;
    }

    @Override
    public UnionSpliterator.OfDouble<X> createNewAdapter(ThrowingSpliterator.OfDouble<X> newDelegate) {
      return new UnionSpliteratorAdapter.OfDouble<>(newDelegate, getFunctionAdapter());
    }
  }

  UnionSpliteratorAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public long estimateSize() {
    return getDelegate().estimateSize();
  }

  @Override
  public int characteristics() {
    return getDelegate().characteristics();
  }

  @Override
  public boolean tryAdvance(ThrowingConsumer<? super T, ? extends Throwable> action) throws X {
    return getDelegate().tryAdvance(getFunctionAdapter().convert(action));
  }
}
