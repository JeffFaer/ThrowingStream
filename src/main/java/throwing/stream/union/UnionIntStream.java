package throwing.stream.union;

import java.util.function.IntFunction;

import throwing.function.ThrowingIntFunction;
import throwing.stream.intermediate.ThrowingIntStreamIntermediate;
import throwing.stream.terminal.ThrowingIntStreamTerminal;

public interface UnionIntStream<X extends UnionThrowable> extends
    UnionBaseStream<Integer, X, UnionIntStream<X>>,
    ThrowingIntStreamIntermediate<Throwable, UnionIntStream<X>, UnionLongStream<X>, UnionDoubleStream<X>>,
    ThrowingIntStreamTerminal<Throwable, X> {
  @Override
  public UnionIterator.OfInt<X> iterator();

  @Override
  public UnionBaseSpliterator.OfInt<X> spliterator();

  @Override
  default public <U> UnionStream<U, X> normalMapToObj(IntFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(ThrowingIntFunction<? extends U, ? extends Throwable> mapper);

  @Override
  public UnionStream<Integer, X> boxed();
}
