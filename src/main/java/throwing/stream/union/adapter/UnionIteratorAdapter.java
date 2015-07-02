package throwing.stream.union.adapter;

import throwing.ThrowingIterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionThrowable;

class UnionIteratorAdapter<T, X extends UnionThrowable, D extends ThrowingIterator<T, X>> extends
    UnionAdapter<D, X> implements UnionIterator<T, X> {
  static class OfInt<X extends UnionThrowable> extends
      UnionIteratorAdapter<Integer, X, ThrowingIterator.OfInt<X>> implements UnionIterator.OfInt<X> {
    OfInt(ThrowingIterator.OfInt<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public int nextInt() throws X {
      return getDelegate().nextInt();
    }

    @Override
    public void forEachRemaining(ThrowingIntConsumer<? extends Throwable> action) throws X {
      getDelegate().forEachRemaining(getFunctionAdapter().convert(action));
    }
  }

  static class OfLong<X extends UnionThrowable> extends
      UnionIteratorAdapter<Long, X, ThrowingIterator.OfLong<X>> implements UnionIterator.OfLong<X> {
    OfLong(ThrowingIterator.OfLong<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public long nextLong() throws X {
      return getDelegate().nextLong();
    }

    @Override
    public void forEachRemaining(ThrowingLongConsumer<? extends Throwable> action) throws X {
      getDelegate().forEachRemaining(getFunctionAdapter().convert(action));
    }
  }

  static class OfDouble<X extends UnionThrowable> extends
      UnionIteratorAdapter<Double, X, ThrowingIterator.OfDouble<X>> implements
      UnionIterator.OfDouble<X> {
    OfDouble(ThrowingIterator.OfDouble<X> delegate, UnionFunctionAdapter<X> adapter) {
      super(delegate, adapter);
    }

    @Override
    public double nextDouble() throws X {
      return getDelegate().nextDouble();
    }

    @Override
    public void forEachRemaining(ThrowingDoubleConsumer<? extends Throwable> action) throws X {
      getDelegate().forEachRemaining(getFunctionAdapter().convert(action));
    }
  }

  UnionIteratorAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public boolean hasNext() throws X {
    return getDelegate().hasNext();
  }

  @Override
  public T next() throws X {
    return getDelegate().next();
  }

  @Override
  public void forEachRemaining(ThrowingConsumer<? super T, ? extends Throwable> action) throws X {
    getDelegate().forEachRemaining(getFunctionAdapter().convert(action));
  }
}
