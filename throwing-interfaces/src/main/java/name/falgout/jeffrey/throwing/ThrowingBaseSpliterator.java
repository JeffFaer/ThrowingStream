package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public interface ThrowingBaseSpliterator<E, X extends Throwable, S extends ThrowingBaseSpliterator<E, X, S>> {
  public static interface ThrowingSpliterator<E, X extends Throwable>
      extends ThrowingBaseSpliterator<E, X, ThrowingSpliterator<E, X>> {}

  public static interface OfPrimitive<E, E_CONS, X extends Throwable, S extends OfPrimitive<E, E_CONS, X, S>>
      extends ThrowingBaseSpliterator<E, X, S> {
    public boolean tryAdvance(E_CONS action) throws X;
  }

  public static interface OfInt<X extends Throwable>
      extends OfPrimitive<Integer, ThrowingIntConsumer<? extends X>, X, OfInt<X>> {
    default public boolean normalTryAdvance(IntConsumer action) throws X {
      return tryAdvance((ThrowingIntConsumer<? extends X>) action::accept);
    }
  }

  public static interface OfLong<X extends Throwable>
      extends OfPrimitive<Long, ThrowingLongConsumer<? extends X>, X, OfLong<X>> {
    default public boolean normalTryAdvance(LongConsumer action) throws X {
      return tryAdvance((ThrowingLongConsumer<? extends X>) action::accept);
    }
  }

  public static interface OfDouble<X extends Throwable>
      extends OfPrimitive<Double, ThrowingDoubleConsumer<? extends X>, X, OfDouble<X>> {
    default public boolean normalTryAdvance(DoubleConsumer action) throws X {
      return tryAdvance((ThrowingDoubleConsumer<? extends X>) action::accept);
    }
  }

  default public boolean normalTryAdvance(Consumer<? super E> action) throws X {
    return tryAdvance(action::accept);
  }

  public boolean tryAdvance(ThrowingConsumer<? super E, ? extends X> action) throws X;

  public S trySplit();

  public long estimateSize();

  public int characteristics();
}
