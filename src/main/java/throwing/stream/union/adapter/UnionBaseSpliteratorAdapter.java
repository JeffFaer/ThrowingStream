package throwing.stream.union.adapter;

import throwing.ThrowingBaseSpliterator;
import throwing.ThrowingBaseSpliterator.ThrowingSpliterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;
import throwing.stream.adapter.ChainingAdapter;
import throwing.stream.union.UnionBaseSpliterator;
import throwing.stream.union.UnionThrowable;

abstract class UnionBaseSpliteratorAdapter<T, X extends UnionThrowable, D extends ThrowingBaseSpliterator<T, X, D>, S extends UnionBaseSpliterator<T, X, S>> extends
    UnionAdapter<D, X> implements UnionBaseSpliterator<T, X, S>, ChainingAdapter<D, S> {
  static class UnionSpliteratorAdapter<T, X extends UnionThrowable> extends
      UnionBaseSpliteratorAdapter<T, X, ThrowingSpliterator<T, X>, UnionSpliterator<T, X>> implements
      UnionSpliterator<T, X> {
    UnionSpliteratorAdapter(ThrowingSpliterator<T, X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionSpliterator<T, X> trySplit() {
      return chain(ThrowingBaseSpliterator::trySplit);
    }

    @Override
    public UnionSpliterator<T, X> getSelf() {
      return this;
    }

    @Override
    public UnionSpliterator<T, X> createNewAdapter(ThrowingSpliterator<T, X> newDelegate) {
      return new UnionSpliteratorAdapter<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfInt<X extends UnionThrowable> extends
      UnionBaseSpliteratorAdapter<Integer, X, ThrowingBaseSpliterator.OfInt<X>, UnionBaseSpliterator.OfInt<X>> implements
      UnionBaseSpliterator.OfInt<X> {
    OfInt(ThrowingBaseSpliterator.OfInt<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionBaseSpliterator.OfInt<X> trySplit() {
      return chain(getDelegate().trySplit());
    }

    @Override
    public boolean tryAdvance(ThrowingIntConsumer<? extends Throwable> action) throws X {
      return getDelegate().tryAdvance(getFunctionAdapter().convert(action));
    }

    @Override
    public UnionBaseSpliterator.OfInt<X> getSelf() {
      return this;
    }

    @Override
    public UnionBaseSpliterator.OfInt<X> createNewAdapter(
        ThrowingBaseSpliterator.OfInt<X> newDelegate) {
      return new UnionBaseSpliteratorAdapter.OfInt<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfLong<X extends UnionThrowable> extends
      UnionBaseSpliteratorAdapter<Long, X, ThrowingBaseSpliterator.OfLong<X>, UnionBaseSpliterator.OfLong<X>> implements
      UnionBaseSpliterator.OfLong<X> {
    OfLong(ThrowingBaseSpliterator.OfLong<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionBaseSpliterator.OfLong<X> trySplit() {
      return chain(getDelegate().trySplit());
    }

    @Override
    public boolean tryAdvance(ThrowingLongConsumer<? extends Throwable> action) throws X {
      return getDelegate().tryAdvance(getFunctionAdapter().convert(action));
    }

    @Override
    public UnionBaseSpliterator.OfLong<X> getSelf() {
      return this;
    }

    @Override
    public UnionBaseSpliterator.OfLong<X> createNewAdapter(
        ThrowingBaseSpliterator.OfLong<X> newDelegate) {
      return new UnionBaseSpliteratorAdapter.OfLong<>(newDelegate, getFunctionAdapter());
    }
  }

  static class OfDouble<X extends UnionThrowable> extends
      UnionBaseSpliteratorAdapter<Double, X, ThrowingBaseSpliterator.OfDouble<X>, UnionBaseSpliterator.OfDouble<X>> implements
      UnionBaseSpliterator.OfDouble<X> {
    OfDouble(ThrowingBaseSpliterator.OfDouble<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public UnionBaseSpliterator.OfDouble<X> trySplit() {
      return chain(getDelegate().trySplit());
    }

    @Override
    public boolean tryAdvance(ThrowingDoubleConsumer<? extends Throwable> action) throws X {
      return getDelegate().tryAdvance(getFunctionAdapter().convert(action));
    }

    @Override
    public UnionBaseSpliterator.OfDouble<X> getSelf() {
      return this;
    }

    @Override
    public UnionBaseSpliterator.OfDouble<X> createNewAdapter(
        ThrowingBaseSpliterator.OfDouble<X> newDelegate) {
      return new UnionBaseSpliteratorAdapter.OfDouble<>(newDelegate, getFunctionAdapter());
    }
  }

  UnionBaseSpliteratorAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
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
