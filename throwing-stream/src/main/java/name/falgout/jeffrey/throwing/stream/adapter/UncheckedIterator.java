package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import name.falgout.jeffrey.throwing.ThrowingDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.ThrowingLongConsumer;

class UncheckedIterator<T, I extends ThrowingIterator<T, X>, X extends Throwable> extends
    UncheckedAdapter<I, X> implements Iterator<T> {
  static class OfInt<X extends Throwable> extends
      UncheckedIterator<Integer, ThrowingIterator.OfInt<X>, X> implements PrimitiveIterator.OfInt {
    OfInt(name.falgout.jeffrey.throwing.ThrowingIterator.OfInt<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
      maskException(() -> getDelegate().forEachRemaining(
          (ThrowingIntConsumer<? extends X>) action::accept));
    }

    @Override
    public int nextInt() {
      return maskException(getDelegate()::nextInt);
    }
  }

  static class OfLong<X extends Throwable> extends
      UncheckedIterator<Long, ThrowingIterator.OfLong<X>, X> implements PrimitiveIterator.OfLong {
    OfLong(name.falgout.jeffrey.throwing.ThrowingIterator.OfLong<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public void forEachRemaining(LongConsumer action) {
      maskException(() -> getDelegate().forEachRemaining(
          (ThrowingLongConsumer<? extends X>) action::accept));
    }

    @Override
    public long nextLong() {
      return maskException(getDelegate()::nextLong);
    }
  }

  static class OfDouble<X extends Throwable> extends
      UncheckedIterator<Double, ThrowingIterator.OfDouble<X>, X> implements
      PrimitiveIterator.OfDouble {
    OfDouble(name.falgout.jeffrey.throwing.ThrowingIterator.OfDouble<X> delegate, Class<X> x) {
      super(delegate, x);
    }

    @Override
    public void forEachRemaining(DoubleConsumer action) {
      maskException(() -> getDelegate().forEachRemaining(
          (ThrowingDoubleConsumer<? extends X>) action::accept));
    }

    @Override
    public double nextDouble() {
      return maskException(getDelegate()::nextDouble);
    }
  }

  UncheckedIterator(I delegate, Class<X> x) {
    super(delegate, x);
  }

  @Override
  public boolean hasNext() {
    return maskException(getDelegate()::hasNext);
  }

  @Override
  public T next() {
    return maskException(getDelegate()::next);
  }
}
