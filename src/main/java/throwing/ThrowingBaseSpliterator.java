package throwing;

import throwing.stream.terminal.ThrowingBaseStreamTerminal;
import throwing.stream.terminal.ThrowingBaseStreamTerminal.BaseSpliterator;

public interface ThrowingBaseSpliterator<T, X extends Throwable, S extends ThrowingBaseSpliterator<T, X, S>> extends
    ThrowingBaseStreamTerminal.BaseSpliterator<T, X, X, S> {
  public interface ThrowingSpliterator<T, X extends Throwable> extends
      ThrowingBaseSpliterator<T, X, ThrowingSpliterator<T, X>> {}

  public interface OfInt<X extends Throwable> extends
      ThrowingBaseSpliterator<Integer, X, OfInt<X>>,
      BaseSpliterator.OfInt<X, X, OfInt<X>> {}

  public interface OfLong<X extends Throwable> extends
      ThrowingBaseSpliterator<Long, X, OfLong<X>>,
      BaseSpliterator.OfLong<X, X, OfLong<X>> {}

  public interface OfDouble<X extends Throwable> extends
      ThrowingBaseSpliterator<Double, X, OfDouble<X>>,
      BaseSpliterator.OfDouble<X, X, OfDouble<X>> {}
}
