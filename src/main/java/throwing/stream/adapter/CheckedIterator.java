package throwing.stream.adapter;

import java.util.Iterator;

import throwing.ThrowingIterator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

class CheckedIterator<E, X extends Throwable, D extends Iterator<E>> extends CheckedAdapter<D, X> implements
    ThrowingIterator<E, X> {
  static class OfInt<X extends Throwable> extends
      CheckedIterator<Integer, X, java.util.PrimitiveIterator.OfInt> implements
      ThrowingIterator.OfInt<X> {
    OfInt(java.util.PrimitiveIterator.OfInt delegate, FunctionAdapter<X> functionAdapter) {
      super(delegate, functionAdapter);
    }

    @Override
    public void forEachRemaining(ThrowingIntConsumer<? extends X> action) throws X {
      unmaskException(() -> getDelegate().forEachRemaining(getFunctionAdapter().convert(action)));
    }

    @Override
    public int nextInt() throws X {
      return unmaskException(getDelegate()::nextInt);
    }
  }

  static class OfLong<X extends Throwable> extends
      CheckedIterator<Long, X, java.util.PrimitiveIterator.OfLong> implements
      ThrowingIterator.OfLong<X> {
    OfLong(java.util.PrimitiveIterator.OfLong delegate, FunctionAdapter<X> functionAdapter) {
      super(delegate, functionAdapter);
    }

    @Override
    public void forEachRemaining(ThrowingLongConsumer<? extends X> action) throws X {
      unmaskException(() -> getDelegate().forEachRemaining(getFunctionAdapter().convert(action)));
    }

    @Override
    public long nextLong() throws X {
      return unmaskException(getDelegate()::nextLong);
    }
  }

  static class OfDouble<X extends Throwable> extends
      CheckedIterator<Double, X, java.util.PrimitiveIterator.OfDouble> implements
      ThrowingIterator.OfDouble<X> {
    OfDouble(java.util.PrimitiveIterator.OfDouble delegate, FunctionAdapter<X> functionAdapter) {
      super(delegate, functionAdapter);
    }

    @Override
    public void forEachRemaining(ThrowingDoubleConsumer<? extends X> action) throws X {
      unmaskException(() -> getDelegate().forEachRemaining(getFunctionAdapter().convert(action)));
    }

    @Override
    public double nextDouble() throws X {
      return unmaskException(getDelegate()::nextDouble);
    }
  }

  CheckedIterator(D delegate, FunctionAdapter<X> functionAdapter) {
    super(delegate, functionAdapter);
  }

  @Override
  public boolean hasNext() throws X {
    return unmaskException(getDelegate()::hasNext);
  }

  @Override
  public E next() throws X {
    return unmaskException(getDelegate()::next);
  }
}
