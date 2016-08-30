package name.falgout.jeffrey.throwing.stream;

import java.util.function.DoubleFunction;
import java.util.function.Function;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.stream.intermediate.ThrowingDoubleStreamIntermediate;
import name.falgout.jeffrey.throwing.stream.terminal.ThrowingDoubleStreamTerminal;

public interface ThrowingDoubleStream<X extends Throwable>
    extends ThrowingBaseStream<Double, X, ThrowingDoubleStream<X>>,
    ThrowingDoubleStreamIntermediate<X, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>>,
    ThrowingDoubleStreamTerminal<X> {
  @Override
  public ThrowingIterator.OfDouble<X> iterator();

  @Override
  public ThrowingBaseSpliterator.OfDouble<X> spliterator();

  @Override
  default public <U> ThrowingStream<U, X> normalMapToObj(DoubleFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> ThrowingStream<U, X> mapToObj(ThrowingDoubleFunction<? extends U, ? extends X> mapper);

  @Override
  public ThrowingStream<Double, X> boxed();

  @Override
  public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> y,
      Function<? super X, ? extends Y> mapper);
}
