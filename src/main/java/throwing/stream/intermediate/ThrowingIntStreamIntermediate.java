package throwing.stream.intermediate;

import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingIntToDoubleFunction;
import throwing.function.ThrowingIntToLongFunction;
import throwing.function.ThrowingIntUnaryOperator;
import throwing.stream.ThrowingIntStream;

public interface ThrowingIntStreamIntermediate<X extends Throwable, I extends ThrowingIntStreamIntermediate<X, I, L, D>, L extends ThrowingLongStreamIntermediate<X, I, L, D>, D extends ThrowingDoubleStreamIntermediate<X, I, L, D>> extends
    ThrowingBaseStreamIntermediate<I> {
  default public I normalFilter(IntPredicate predicate) {
    return filter(predicate::test);
  }

  public I filter(ThrowingIntPredicate<? extends X> predicate);

  default public I normalMap(IntUnaryOperator mapper) {
    return map(mapper::applyAsInt);
  }

  public I map(ThrowingIntUnaryOperator<? extends X> mapper);

  default public <U> ThrowingStreamIntermediate<U, X, ?, I, L, D> normalMapToObj(
      IntFunction<? extends U> mapper) {
    return this.<U> mapToObj(mapper::apply);
  }

  public <U> ThrowingStreamIntermediate<U, X, ?, I, L, D> mapToObj(
      ThrowingIntFunction<? extends U, ? extends X> mapper);

  default public L normalMapToLong(IntToLongFunction mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  public L mapToLong(ThrowingIntToLongFunction<? extends X> mapper);

  default public D normalMapToDouble(IntToDoubleFunction mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  public D mapToDouble(ThrowingIntToDoubleFunction<? extends X> mapper);

  default public I normalFlatMap(IntFunction<? extends ThrowingIntStream<? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  public I flatMap(ThrowingIntFunction<? extends ThrowingIntStream<? extends X>, ? extends X> mapper);

  public I distinct();

  public I sorted();

  default public I normalPeek(IntConsumer action) {
    return peek(action::accept);
  }

  public I peek(ThrowingIntConsumer<? extends X> action);

  public I limit(long maxSize);

  public I skip(long n);

  public L asLongStream();

  public D asDoubleStream();

  public ThrowingStreamIntermediate<Integer, X, ?, I, L, D> boxed();
}
