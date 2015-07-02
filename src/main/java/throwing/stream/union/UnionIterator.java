package throwing.stream.union;

import throwing.stream.terminal.ThrowingBaseStreamTerminal;
import throwing.stream.terminal.ThrowingBaseStreamTerminal.Iterator;

public interface UnionIterator<E, X extends UnionThrowable> extends
    ThrowingBaseStreamTerminal.Iterator<E, Throwable, X> {
  public interface OfInt<X extends UnionThrowable> extends
      UnionIterator<Integer, X>,
      Iterator.OfInt<Throwable, X> {}

  public interface OfLong<X extends UnionThrowable> extends
      UnionIterator<Long, X>,
      Iterator.OfLong<Throwable, X> {}

  public interface OfDouble<X extends UnionThrowable> extends
      UnionIterator<Double, X>,
      Iterator.OfDouble<Throwable, X> {}
}
