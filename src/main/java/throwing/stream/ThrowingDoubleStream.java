package throwing.stream;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingDoubleStream<X extends Throwable> extends
    ThrowingBaseStream<Double, X, ThrowingDoubleStream<X>> {
  @Override
  public ThrowingIterator.OfDouble<X> iterator();

  @Override
  public ThrowingSpliterator.OfDouble<X> spliterator();

  default public ThrowingDoubleStream<X> normalFilter(DoublePredicate predicate) {
    return filter(predicate::test);
  }

  public ThrowingDoubleStream<X> filter(ThrowingDoublePredicate<? extends X> predicate);

  default public ThrowingDoubleStream<X> normalMap(DoubleUnaryOperator mapper) {
    return map(mapper::applyAsDouble);
  }

  public ThrowingDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends X> mapper);

  default public <U> ThrowingStream<U, X> normalMapToObj(DoubleFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  public <U> ThrowingStream<U, X> mapToObj(ThrowingDoubleFunction<? extends U, ? extends X> mapper);

  default public ThrowingIntStream<X> normalMapToInt(DoubleToIntFunction mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  public ThrowingIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends X> mapper);

  default public ThrowingLongStream<X> normalMapToLong(DoubleToLongFunction mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  public ThrowingLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends X> mapper);

  default public ThrowingDoubleStream<X> normalFlatMap(
      DoubleFunction<? extends ThrowingDoubleStream<? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  public ThrowingDoubleStream<X> flatMap(
      ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper);

  public ThrowingDoubleStream<X> distinct();

  public ThrowingDoubleStream<X> sorted();

  default public ThrowingDoubleStream<X> normalPeek(DoubleConsumer action) {
    return peek(action::accept);
  }

  public ThrowingDoubleStream<X> peek(ThrowingDoubleConsumer<? extends X> action);

  public ThrowingDoubleStream<X> limit(long maxSize);

  public ThrowingDoubleStream<X> skip(long n);

  default public void normalForEach(DoubleConsumer action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingDoubleConsumer<? extends X> action) throws X;

  default public void normalForEachOrdered(DoubleConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingDoubleConsumer<? extends X> action) throws X;

  public double[] toArray() throws X;

  default public double normalReduce(double identity, DoubleBinaryOperator op) throws X {
    return reduce(identity, op::applyAsDouble);
  }

  public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends X> op) throws X;

  default public OptionalDouble normalReduce(DoubleBinaryOperator op) throws X {
    return reduce(op::applyAsDouble);
  }

  public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends X> op) throws X;

  default public <R> R normalCollect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjDoubleConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X;

  public double sum() throws X;

  public OptionalDouble min() throws X;

  public OptionalDouble max() throws X;

  public long count() throws X;

  public OptionalDouble average() throws X;

  public DoubleSummaryStatistics summaryStatistics() throws X;

  default public boolean normalAnyMatch(DoublePredicate predicate) throws X {
    return anyMatch((ThrowingDoublePredicate<? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

  default public boolean normalAllMatch(DoublePredicate predicate) throws X {
    return allMatch((ThrowingDoublePredicate<? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

  default public boolean normalNoneMatch(DoublePredicate predicate) throws X {
    return noneMatch((ThrowingDoublePredicate<? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

  public OptionalDouble findFirst() throws X;

  public OptionalDouble findAny() throws X;

  public ThrowingStream<Double, X> boxed();

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
  public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> y,
      Function<? super X, ? extends Y> mapper);
}
