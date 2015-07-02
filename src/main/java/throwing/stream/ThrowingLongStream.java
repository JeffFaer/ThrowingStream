package throwing.stream;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingLongBinaryOperator;
import throwing.function.ThrowingLongConsumer;
import throwing.function.ThrowingLongFunction;
import throwing.function.ThrowingLongPredicate;
import throwing.function.ThrowingLongToDoubleFunction;
import throwing.function.ThrowingLongToIntFunction;
import throwing.function.ThrowingLongUnaryOperator;
import throwing.function.ThrowingObjLongConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingLongStream<X extends Throwable> extends
    ThrowingBaseStream<Long, X, ThrowingLongStream<X>> {
  @Override
  public ThrowingIterator.OfLong<X> iterator();

  @Override
  public ThrowingSpliterator.OfLong<X> spliterator();

  default public ThrowingLongStream<X> normalFilter(LongPredicate predicate) {
    return filter(predicate::test);
  }

  public ThrowingLongStream<X> filter(ThrowingLongPredicate<? extends X> predicate);

  default public ThrowingLongStream<X> normalMap(LongUnaryOperator mapper) {
    return map(mapper::applyAsLong);
  }

  public ThrowingLongStream<X> map(ThrowingLongUnaryOperator<? extends X> mapper);

  default public <U> ThrowingStream<U, X> normalMapToObj(LongFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  public <U> ThrowingStream<U, X> mapToObj(ThrowingLongFunction<? extends U, ? extends X> mapper);

  default public ThrowingIntStream<X> normalMapToInt(LongToIntFunction mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  public ThrowingIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends X> mapper);

  default public ThrowingDoubleStream<X> normalMapToDouble(LongToDoubleFunction mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  public ThrowingDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends X> mapper);

  default public ThrowingLongStream<X> normalFlatMap(
      LongFunction<? extends ThrowingLongStream<? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  public ThrowingLongStream<X> flatMap(
      ThrowingLongFunction<? extends ThrowingLongStream<? extends X>, ? extends X> mapper);

  public ThrowingLongStream<X> distinct();

  public ThrowingLongStream<X> sorted();

  default public ThrowingLongStream<X> normalPeek(LongConsumer action) {
    return peek(action::accept);
  }

  public ThrowingLongStream<X> peek(ThrowingLongConsumer<? extends X> action);

  public ThrowingLongStream<X> limit(long maxSize);

  public ThrowingLongStream<X> skip(long n);

  default public void normalForEach(LongConsumer action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingLongConsumer<? extends X> action) throws X;

  default public void normalForEachOrdered(LongConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingLongConsumer<? extends X> action) throws X;

  public long[] toArray() throws X;

  default public long normalReduce(long identity, LongBinaryOperator op) throws X {
    return reduce(identity, op::applyAsLong);
  }

  public long reduce(long identity, ThrowingLongBinaryOperator<? extends X> op) throws X;

  default public OptionalLong normalReduce(LongBinaryOperator op) throws X {
    return reduce(op::applyAsLong);
  }

  public OptionalLong reduce(ThrowingLongBinaryOperator<? extends X> op) throws X;

  default public <R> R normalCollect(Supplier<R> supplier, ObjLongConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjLongConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X;

  public long sum() throws X;

  public OptionalLong min() throws X;

  public OptionalLong max() throws X;

  public long count() throws X;

  public OptionalDouble average() throws X;

  public LongSummaryStatistics summaryStatistics() throws X;

  default public boolean normalAnyMatch(LongPredicate predicate) throws X {
    return anyMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

  default public boolean normalAllMatch(LongPredicate predicate) throws X {
    return allMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

  default public boolean normalNoneMatch(LongPredicate predicate) throws X {
    return noneMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

  public OptionalLong findFirst() throws X;

  public OptionalLong findAny() throws X;

  public ThrowingDoubleStream<X> asDoubleStream();

  public ThrowingStream<Long, X> boxed();

  /**
   * Returns a stream which will only throw Y and will rethrow any X as Y as specified by the
   * mapper.
   *
   * This is an intermediate operation.
   *
   * @param y
   *          The new exception class
   * @param mapper
   *          A way to convert X exceptions to Ys
   * @return the new stream
   */
  public <Y extends Throwable> ThrowingLongStream<Y> rethrow(Class<Y> y,
      Function<? super X, ? extends Y> mapper);
}
