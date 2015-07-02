package throwing.stream.union;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

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
import throwing.stream.ThrowingLongStream;

public interface UnionLongStream<X extends UnionThrowable> extends
    UnionBaseStream<Long, X, UnionLongStream<X>, ThrowingLongStream<Throwable>>,
    ThrowingLongStream<Throwable> {
  @Override
  public UnionIterator.OfLong<X> iterator();

  @Override
  public UnionSpliterator.OfLong<X> spliterator();

  @Override
  default public UnionLongStream<X> normalFilter(LongPredicate predicate) {
    return filter(predicate::test);
  }

  @Override
  public UnionLongStream<X> filter(ThrowingLongPredicate<? extends Throwable> predicate);

  @Override
  default public UnionLongStream<X> normalMap(LongUnaryOperator mapper) {
    return map(mapper::applyAsLong);
  }

  @Override
  public UnionLongStream<X> map(ThrowingLongUnaryOperator<? extends Throwable> mapper);

  @Override
  default public <U> UnionStream<U, X> normalMapToObj(LongFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingLongFunction<? extends U, ? extends Throwable> mapper);

  @Override
  default public UnionIntStream<X> normalMapToInt(LongToIntFunction mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  @Override
  public UnionIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends Throwable> mapper);

  @Override
  default public UnionDoubleStream<X> normalMapToDouble(LongToDoubleFunction mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  @Override
  public UnionDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends Throwable> mapper);

  @Override
  default public UnionLongStream<X> normalFlatMap(
      LongFunction<? extends ThrowingLongStream<? extends Throwable>> mapper) {
    return flatMap(mapper::apply);
  }

  @Override
  public UnionLongStream<X> flatMap(
      ThrowingLongFunction<? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper);

  @Override
  public UnionLongStream<X> distinct();

  @Override
  public UnionLongStream<X> sorted();

  @Override
  default public UnionLongStream<X> normalPeek(LongConsumer action) {
    return peek(action::accept);
  }

  @Override
  public UnionLongStream<X> peek(ThrowingLongConsumer<? extends Throwable> action);

  @Override
  public UnionLongStream<X> limit(long maxSize);

  @Override
  public UnionLongStream<X> skip(long n);

  @Override
  default public void normalForEach(LongConsumer action) throws X {
    forEach(action::accept);
  }

  @Override
  public void forEach(ThrowingLongConsumer<? extends Throwable> action) throws X;

  @Override
  default public void normalForEachOrdered(LongConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  @Override
  public void forEachOrdered(ThrowingLongConsumer<? extends Throwable> action) throws X;

  @Override
  public long[] toArray() throws X;

  @Override
  default public long normalReduce(long identity, LongBinaryOperator op) throws X {
    return reduce(identity, op::applyAsLong);
  }

  @Override
  public long reduce(long identity, ThrowingLongBinaryOperator<? extends Throwable> op) throws X;

  @Override
  default public OptionalLong normalReduce(LongBinaryOperator op) throws X {
    return reduce(op::applyAsLong);
  }

  @Override
  public OptionalLong reduce(ThrowingLongBinaryOperator<? extends Throwable> op) throws X;

  @Override
  default public <R> R normalCollect(Supplier<R> supplier, ObjLongConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingObjLongConsumer<R, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

  @Override
  public long sum() throws X;

  @Override
  public OptionalLong min() throws X;

  @Override
  public OptionalLong max() throws X;

  @Override
  public long count() throws X;

  @Override
  public OptionalDouble average() throws X;

  @Override
  public LongSummaryStatistics summaryStatistics() throws X;

  @Override
  default public boolean normalAnyMatch(LongPredicate predicate) throws X {
    return anyMatch((ThrowingLongPredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean anyMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalAllMatch(LongPredicate predicate) throws X {
    return allMatch((ThrowingLongPredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean allMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalNoneMatch(LongPredicate predicate) throws X {
    return noneMatch((ThrowingLongPredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean noneMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X;

  @Override
  public OptionalLong findFirst() throws X;

  @Override
  public OptionalLong findAny() throws X;

  @Override
  public UnionDoubleStream<X> asDoubleStream();

  @Override
  public UnionStream<Long, X> boxed();
}
