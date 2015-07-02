package throwing.stream.union;

import java.util.function.LongFunction;

import throwing.function.ThrowingLongFunction;
import throwing.stream.intermediate.ThrowingLongStreamIntermediate;
import throwing.stream.terminal.ThrowingLongStreamTerminal;

public interface UnionLongStream<X extends UnionThrowable> extends
    UnionBaseStream<Long, X, UnionLongStream<X>>,
    ThrowingLongStreamIntermediate<Throwable, UnionIntStream<X>, UnionLongStream<X>, UnionDoubleStream<X>>,
    ThrowingLongStreamTerminal<Throwable, X> {
  @Override
  public UnionIterator.OfLong<X> iterator();

  @Override
  public UnionBaseSpliterator.OfLong<X> spliterator();

  @Override
  default public <U> UnionStream<U, X> normalMapToObj(LongFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingLongFunction<? extends U, ? extends Throwable> mapper);

  @Override
  public UnionStream<Long, X> boxed();
}
