package name.falgout.jeffrey.throwing.stream;

import java.util.function.Function;
import java.util.function.LongFunction;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.ThrowingLongFunction;
import name.falgout.jeffrey.throwing.stream.intermediate.ThrowingLongStreamIntermediate;
import name.falgout.jeffrey.throwing.stream.terminal.ThrowingLongStreamTerminal;

public interface ThrowingLongStream<X extends Throwable>
    extends ThrowingBaseStream<Long, X, ThrowingLongStream<X>>,
    ThrowingLongStreamIntermediate<X, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>>,
    ThrowingLongStreamTerminal<X> {
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
