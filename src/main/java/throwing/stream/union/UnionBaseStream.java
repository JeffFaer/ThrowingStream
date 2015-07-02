package throwing.stream.union;

import throwing.stream.intermediate.ThrowingBaseStreamIntermediate;
import throwing.stream.terminal.ThrowingBaseStreamTerminal;

public interface UnionBaseStream<T, X extends UnionThrowable, S extends UnionBaseStream<T, X, S>> extends
    ThrowingBaseStreamIntermediate<S>,
    ThrowingBaseStreamTerminal<T, Throwable, X> {
  @Override
  public UnionIterator<T, X> iterator();

  @Override
  public UnionBaseSpliterator<T, X, ?> spliterator();
}
