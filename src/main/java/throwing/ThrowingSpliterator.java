package throwing;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface ThrowingSpliterator<T, X extends Throwable> {
  public interface OfPrimitive<T, T_CONS, T_SPLITR extends OfPrimitive<T, T_CONS, T_SPLITR, X>, X extends Throwable> extends
      ThrowingSpliterator<T, X> {
    @Override
    public T_SPLITR trySplit();

    public boolean tryAdvance(T_CONS action) throws X;
  }

  public interface OfInt<X extends Throwable> extends
      OfPrimitive<Integer, ThrowingIntConsumer<? extends X>, OfInt<X>, X> {
    default public boolean normalTryAdvance(IntConsumer action) throws X {
      return tryAdvance((ThrowingIntConsumer<? extends X>) action::accept);
    }
  }

  public interface OfLong<X extends Throwable> extends
      OfPrimitive<Long, ThrowingLongConsumer<? extends X>, OfLong<X>, X> {
    default public boolean normalTryAdvance(LongConsumer action) throws X {
      return tryAdvance((ThrowingLongConsumer<? extends X>) action::accept);
    }
  }

  public interface OfDouble<X extends Throwable> extends
      OfPrimitive<Double, ThrowingDoubleConsumer<? extends X>, OfDouble<X>, X> {
    default public boolean normalTryAdvance(DoubleConsumer action) throws X {
      return tryAdvance((ThrowingDoubleConsumer<? extends X>) action::accept);
    }
  }

  default public boolean normalTryAdvance(Consumer<? super T> action) throws X {
    return tryAdvance(action::accept);
  }

  public boolean tryAdvance(ThrowingConsumer<? super T, ? extends X> action) throws X;

  public ThrowingSpliterator<T, X> trySplit();

  public long estimateSize();

  public int characteristics();
}
