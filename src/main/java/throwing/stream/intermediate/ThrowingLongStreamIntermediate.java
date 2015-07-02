package throwing.stream.intermediate;

import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;

import throwing.function.ThrowingLongConsumer;
import throwing.function.ThrowingLongFunction;
import throwing.function.ThrowingLongPredicate;
import throwing.function.ThrowingLongToDoubleFunction;
import throwing.function.ThrowingLongToIntFunction;
import throwing.function.ThrowingLongUnaryOperator;
import throwing.stream.ThrowingLongStream;

public interface ThrowingLongStreamIntermediate<X extends Throwable, I extends ThrowingIntStreamIntermediate<X, I, L, D>, L extends ThrowingLongStreamIntermediate<X, I, L, D>, D extends ThrowingDoubleStreamIntermediate<X, I, L, D>> extends
    ThrowingBaseStreamIntermediate<L> {
  default public L normalFilter(LongPredicate predicate) {
    return filter(predicate::test);
  }

  public L filter(ThrowingLongPredicate<? extends X> predicate);

  default public L normalMap(LongUnaryOperator mapper) {
    return map(mapper::applyAsLong);
  }

  public L map(ThrowingLongUnaryOperator<? extends X> mapper);

  default public <U> ThrowingStreamIntermediate<U, X, ?, I, L, D> normalMapToObj(
      LongFunction<? extends U> mapper) {
    return this.<U> mapToObj(mapper::apply);
  }

  public <U> ThrowingStreamIntermediate<U, X, ?, I, L, D> mapToObj(
      ThrowingLongFunction<? extends U, ? extends X> mapper);

  default public I normalMapToInt(LongToIntFunction mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  public I mapToInt(ThrowingLongToIntFunction<? extends X> mapper);

  default public D normalMapToDouble(LongToDoubleFunction mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  public D mapToDouble(ThrowingLongToDoubleFunction<? extends X> mapper);

  default public L normalFlatMap(LongFunction<? extends ThrowingLongStream<? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  public L flatMap(
      ThrowingLongFunction<? extends ThrowingLongStream<? extends X>, ? extends X> mapper);

  public L distinct();

  public L sorted();

  default public L normalPeek(LongConsumer action) {
    return peek(action::accept);
  }

  public L peek(ThrowingLongConsumer<? extends X> action);

  public L limit(long maxSize);

  public L skip(long n);

  public D asDoubleStream();

  public ThrowingStreamIntermediate<Long, X, ?, I, L, D> boxed();
}
