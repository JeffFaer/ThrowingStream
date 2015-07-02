package throwing.stream.terminal;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface ThrowingBaseStreamTerminal<T, X extends Throwable, Y extends Throwable> {
  public static interface Iterator<E, X extends Throwable, Y extends Throwable> {
    public static interface OfPrimitive<E, E_CONS, X extends Throwable, Y extends Throwable> extends
        Iterator<E, X, Y> {
      public void forEachRemaining(E_CONS action) throws Y;
    }

    public static interface OfInt<X extends Throwable, Y extends Throwable> extends
        OfPrimitive<Integer, ThrowingIntConsumer<? extends X>, X, Y> {
      public int nextInt() throws Y;

      @Override
      default public Integer next() throws Y {
        return nextInt();
      }
    }

    public static interface OfLong<X extends Throwable, Y extends Throwable> extends
        OfPrimitive<Long, ThrowingLongConsumer<? extends X>, X, Y> {
      public long nextLong() throws Y;

      @Override
      default public Long next() throws Y {
        return nextLong();
      }
    }

    public static interface OfDouble<X extends Throwable, Y extends Throwable> extends
        OfPrimitive<Double, ThrowingDoubleConsumer<? extends X>, X, Y> {
      public double nextDouble() throws Y;

      @Override
      default public Double next() throws Y {
        return nextDouble();
      }
    }

    public E next() throws Y;

    public boolean hasNext() throws Y;

    default public void remove() throws Y {
      throw new UnsupportedOperationException();
    }

    public void forEachRemaining(ThrowingConsumer<? super E, ? extends X> action) throws Y;
  }

  public static interface BaseSpliterator<E, X extends Throwable, Y extends Throwable, S extends BaseSpliterator<E, X, Y, S>> {
    public static interface Spliterator<E, X extends Throwable, Y extends Throwable> extends
        BaseSpliterator<E, X, Y, Spliterator<E, X, Y>> {}

    public static interface OfPrimitive<E, E_CONS, X extends Throwable, Y extends Throwable, S extends OfPrimitive<E, E_CONS, X, Y, S>> extends
        BaseSpliterator<E, X, Y, S> {
      public boolean tryAdvance(E_CONS action) throws Y;
    }

    public static interface OfInt<X extends Throwable, Y extends Throwable, S extends OfInt<X, Y, S>> extends
        OfPrimitive<Integer, ThrowingIntConsumer<? extends X>, X, Y, S> {
      default public boolean normalTryAdvance(IntConsumer action) throws Y {
        return tryAdvance((ThrowingIntConsumer<? extends X>) action::accept);
      }
    }

    public static interface OfLong<X extends Throwable, Y extends Throwable, S extends OfLong<X, Y, S>> extends
        OfPrimitive<Long, ThrowingLongConsumer<? extends X>, X, Y, S> {
      default public boolean normalTryAdvance(LongConsumer action) throws Y {
        return tryAdvance((ThrowingLongConsumer<? extends X>) action::accept);
      }
    }

    public static interface OfDouble<X extends Throwable, Y extends Throwable, S extends OfDouble<X, Y, S>> extends
        OfPrimitive<Double, ThrowingDoubleConsumer<? extends X>, X, Y, S> {
      default public boolean normalTryAdvance(DoubleConsumer action) throws Y {
        return tryAdvance((ThrowingDoubleConsumer<? extends X>) action::accept);
      }
    }

    default public boolean normalTryAdvance(Consumer<? super E> action) throws Y {
      return tryAdvance(action::accept);
    }

    public boolean tryAdvance(ThrowingConsumer<? super E, ? extends X> action) throws Y;

    public S trySplit();

    public long estimateSize();

    public int characteristics();
  }

  public Iterator<T, X, Y> iterator();

  public BaseSpliterator<T, X, Y, ?> spliterator();

  public void close();
}
