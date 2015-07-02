package throwing.stream;

import java.util.function.Function;
import java.util.function.LongFunction;

import throwing.ThrowingBaseSpliterator;
import throwing.ThrowingIterator;
import throwing.function.ThrowingLongFunction;
import throwing.stream.intermediate.ThrowingLongStreamIntermediate;
import throwing.stream.terminal.ThrowingLongStreamTerminal;

public interface ThrowingLongStream<X extends Throwable> extends
    ThrowingBaseStream<Long, X, ThrowingLongStream<X>>,
    ThrowingLongStreamIntermediate<X, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>>,
    ThrowingLongStreamTerminal<X, X> {
  @Override
  public ThrowingIterator.OfLong<X> iterator();

  @Override
  public ThrowingBaseSpliterator.OfLong<X> spliterator();

  @Override
  default public <U> ThrowingStream<U, X> normalMapToObj(LongFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> ThrowingStream<U, X> mapToObj(ThrowingLongFunction<? extends U, ? extends X> mapper);

  @Override
  public ThrowingStream<Long, X> boxed();

  @Override
  public <Y extends Throwable> ThrowingLongStream<Y> rethrow(Class<Y> y,
      Function<? super X, ? extends Y> mapper);
}
