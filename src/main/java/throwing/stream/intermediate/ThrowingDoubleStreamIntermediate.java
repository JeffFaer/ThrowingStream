package throwing.stream.intermediate;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;

import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.stream.ThrowingDoubleStream;

public interface ThrowingDoubleStreamIntermediate<X extends Throwable, I extends ThrowingIntStreamIntermediate<X,  I, L, D>, L extends ThrowingLongStreamIntermediate<X,  I, L, D>, D extends ThrowingDoubleStreamIntermediate<X,  I, L, D>> extends
    ThrowingBaseStreamIntermediate<D> {
  default public D normalFilter(DoublePredicate predicate) {
    return filter(predicate::test);
  }

  public D filter(ThrowingDoublePredicate<? extends X> predicate);

  default public D normalMap(DoubleUnaryOperator mapper) {
    return map(mapper::applyAsDouble);
  }

  public D map(ThrowingDoubleUnaryOperator<? extends X> mapper);

  default public <U> ThrowingStreamIntermediate<U, X,  ?, I, L, D> normalMapToObj(
      DoubleFunction<? extends U> mapper) {
    return this.<U> mapToObj(mapper::apply);
  }

  public <U> ThrowingStreamIntermediate<U, X,  ?, I, L, D> mapToObj(
      ThrowingDoubleFunction<? extends U, ? extends X> mapper);

  default public I normalMapToInt(DoubleToIntFunction mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  public I mapToInt(ThrowingDoubleToIntFunction<? extends X> mapper);

  default public L normalMapToLong(DoubleToLongFunction mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  public L mapToLong(ThrowingDoubleToLongFunction<? extends X> mapper);

  default public D normalFlatMap(DoubleFunction<? extends ThrowingDoubleStream<? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  public D flatMap(
      ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper);

  public D distinct();

  public D sorted();

  default public D normalPeek(DoubleConsumer action) {
    return peek(action::accept);
  }

  public D peek(ThrowingDoubleConsumer<? extends X> action);

  public D limit(long maxSize);

  public D skip(long n);

  public ThrowingStreamIntermediate<Double, X,  ?, I, L, D> boxed();
}
