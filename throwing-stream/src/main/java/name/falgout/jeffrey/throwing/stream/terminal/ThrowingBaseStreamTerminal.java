package name.falgout.jeffrey.throwing.stream.terminal;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingIterator;

public interface ThrowingBaseStreamTerminal<T, X extends Throwable> {
  public ThrowingIterator<T, X> iterator();

  public ThrowingBaseSpliterator<T, X, ?> spliterator();

  public void close();
}
