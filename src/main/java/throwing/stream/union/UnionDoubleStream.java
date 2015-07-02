package throwing.stream.union;

import java.util.function.DoubleFunction;

import throwing.function.ThrowingDoubleFunction;
import throwing.stream.intermediate.ThrowingDoubleStreamIntermediate;
import throwing.stream.terminal.ThrowingDoubleStreamTerminal;

public interface UnionDoubleStream<X extends UnionThrowable> extends
    UnionBaseStream<Double, X, UnionDoubleStream<X>>,
    ThrowingDoubleStreamIntermediate<Throwable, UnionIntStream<X>, UnionLongStream<X>, UnionDoubleStream<X>>,
    ThrowingDoubleStreamTerminal<Throwable, X> {
  @Override
  public UnionIterator.OfDouble<X> iterator();

  @Override
  public UnionBaseSpliterator.OfDouble<X> spliterator();

  @Override
  default public <U> UnionStream<U, X> normalMapToObj(DoubleFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingDoubleFunction<? extends U, ? extends Throwable> mapper);

  @Override
  public UnionStream<Double, X> boxed();
}
