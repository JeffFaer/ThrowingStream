package throwing.stream;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingIntBinaryOperator;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingIntToDoubleFunction;
import throwing.function.ThrowingIntToLongFunction;
import throwing.function.ThrowingIntUnaryOperator;
import throwing.function.ThrowingObjIntConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingIntStream<X extends Throwable> extends
    ThrowingBaseStream<Integer, X, ThrowingIntStream<X>> {
  @Override
  public ThrowingIterator.OfInt<X> iterator();

  @Override
  public ThrowingSpliterator.OfInt<X> spliterator();

  default public ThrowingIntStream<X> normalFilter(IntPredicate predicate) {
    return filter(predicate::test);
  }

  public ThrowingIntStream<X> filter(ThrowingIntPredicate<? extends X> predicate);

  default public ThrowingIntStream<X> normalMap(IntUnaryOperator mapper) {
    return map(mapper::applyAsInt);
  }

  public ThrowingIntStream<X> map(ThrowingIntUnaryOperator<? extends X> mapper);

  default public <U> ThrowingStream<U, X> normalMapToObj(IntFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  public <U> ThrowingStream<U, X> mapToObj(ThrowingIntFunction<? extends U, ? extends X> mapper);

  default public ThrowingLongStream<X> normalMapToLong(IntToLongFunction mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  public ThrowingLongStream<X> mapToLong(ThrowingIntToLongFunction<? extends X> mapper);

  default public ThrowingDoubleStream<X> normalMapToDouble(IntToDoubleFunction mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  public ThrowingDoubleStream<X> mapToDouble(ThrowingIntToDoubleFunction<? extends X> mapper);

  default public ThrowingIntStream<X> normalFlatMap(
      IntFunction<? extends ThrowingIntStream<? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  public ThrowingIntStream<X> flatMap(
      ThrowingIntFunction<? extends ThrowingIntStream<? extends X>, ? extends X> mapper);

  public ThrowingIntStream<X> distinct();

  public ThrowingIntStream<X> sorted();

  default public ThrowingIntStream<X> normalPeek(IntConsumer action) {
    return peek(action::accept);
  }

  public ThrowingIntStream<X> peek(ThrowingIntConsumer<? extends X> action);

  public ThrowingIntStream<X> limit(long maxSize);

  public ThrowingIntStream<X> skip(long n);

  default public void normalForEach(IntConsumer action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingIntConsumer<? extends X> action) throws X;

  default public void normalForEachOrdered(IntConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingIntConsumer<? extends X> action) throws X;

  public int[] toArray() throws X;

  default public int normalReduce(int identity, IntBinaryOperator op) throws X {
    return reduce(identity, op::applyAsInt);
  }

  public int reduce(int identity, ThrowingIntBinaryOperator<? extends X> op) throws X;

  default public OptionalInt normalReduce(IntBinaryOperator op) throws X {
    return reduce(op::applyAsInt);
  }

  public OptionalInt reduce(ThrowingIntBinaryOperator<? extends X> op) throws X;

  default public <R> R normalCollect(Supplier<R> supplier, ObjIntConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjIntConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X;

  public int sum() throws X;

  public OptionalInt min() throws X;

  public OptionalInt max() throws X;

  public long count() throws X;

  public OptionalDouble average() throws X;

  public IntSummaryStatistics summaryStatistics() throws X;

  default public boolean normalAnyMatch(IntPredicate predicate) throws X {
    return anyMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

  default public boolean normalAllMatch(IntPredicate predicate) throws X {
    return allMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

  default public boolean normalNoneMatch(IntPredicate predicate) throws X {
    return noneMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

  public OptionalInt findFirst() throws X;

  public OptionalInt findAny() throws X;

  public ThrowingLongStream<X> asLongStream();

  public ThrowingDoubleStream<X> asDoubleStream();

  public ThrowingStream<Integer, X> boxed();

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
  public <Y extends Throwable> ThrowingIntStream<Y> rethrow(Class<Y> y,
      Function<? super X, ? extends Y> mapper);
}
