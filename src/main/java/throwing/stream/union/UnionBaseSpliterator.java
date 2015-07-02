package throwing.stream.union;

import throwing.stream.terminal.ThrowingBaseStreamTerminal;
import throwing.stream.terminal.ThrowingBaseStreamTerminal.BaseSpliterator;

public interface UnionBaseSpliterator<T, X extends UnionThrowable, S extends UnionBaseSpliterator<T, X, S>> extends
    ThrowingBaseStreamTerminal.BaseSpliterator<T, Throwable, X, S> {
  public interface UnionSpliterator<T, X extends UnionThrowable> extends
      UnionBaseSpliterator<T, X, UnionSpliterator<T, X>> {}

  public interface OfInt<X extends UnionThrowable> extends
      UnionBaseSpliterator<Integer, X, OfInt<X>>,
      BaseSpliterator.OfInt<Throwable, X, OfInt<X>> {}

  public interface OfLong<X extends UnionThrowable> extends
      UnionBaseSpliterator<Long, X, OfLong<X>>,
      BaseSpliterator.OfLong<Throwable, X, OfLong<X>> {}

  public interface OfDouble<X extends UnionThrowable> extends
      UnionBaseSpliterator<Double, X, OfDouble<X>>,
      BaseSpliterator.OfDouble<Throwable, X, OfDouble<X>> {}
}
