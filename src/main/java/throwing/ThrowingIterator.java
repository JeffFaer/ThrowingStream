package throwing;

import java.util.Objects;

import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;
import throwing.stream.terminal.ThrowingBaseStreamTerminal;
import throwing.stream.terminal.ThrowingBaseStreamTerminal.Iterator;

public interface ThrowingIterator<E, X extends Throwable> extends
    ThrowingBaseStreamTerminal.Iterator<E, X, X> {
  public interface OfInt<X extends Throwable> extends
      ThrowingIterator<Integer, X>,
      Iterator.OfInt<X, X> {
    @Override
    default public void forEachRemaining(ThrowingIntConsumer<? extends X> action) throws X {
      Objects.requireNonNull(action);
      while (hasNext()) {
        action.accept(nextInt());
      }
    }
  }

  public interface OfLong<X extends Throwable> extends
      ThrowingIterator<Long, X>,
      Iterator.OfLong<X, X> {
    @Override
    default public void forEachRemaining(ThrowingLongConsumer<? extends X> action) throws X {
      Objects.requireNonNull(action);
      while (hasNext()) {
        action.accept(nextLong());
      }
    }
  }

  public interface OfDouble<X extends Throwable> extends
      ThrowingIterator<Double, X>,
      Iterator.OfDouble<X, X> {
    @Override
    default public void forEachRemaining(ThrowingDoubleConsumer<? extends X> action) throws X {
      Objects.requireNonNull(action);
      while (hasNext()) {
        action.accept(nextDouble());
      }
    }
  }

  @Override
  default public void forEachRemaining(ThrowingConsumer<? super E, ? extends X> action) throws X {
    Objects.requireNonNull(action);
    while (hasNext()) {
      action.accept(next());
    }
  }
}
