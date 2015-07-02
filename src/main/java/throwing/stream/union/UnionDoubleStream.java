package throwing.stream.union;

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
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

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
import throwing.stream.ThrowingDoubleStream;

public interface UnionDoubleStream<X extends UnionThrowable> extends
    UnionBaseStream<Double, X, UnionDoubleStream<X>, ThrowingDoubleStream<Throwable>>,
    ThrowingDoubleStream<Throwable> {
  @Override
  public UnionIterator.OfDouble<X> iterator();

  @Override
  public UnionSpliterator.OfDouble<X> spliterator();

  @Override
  default public UnionDoubleStream<X> normalFilter(DoublePredicate predicate) {
    return filter(predicate::test);
  }

  @Override
  public UnionDoubleStream<X> filter(ThrowingDoublePredicate<? extends Throwable> predicate);

  @Override
  default public UnionDoubleStream<X> normalMap(DoubleUnaryOperator mapper) {
    return map(mapper::applyAsDouble);
  }

  @Override
  public UnionDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends Throwable> mapper);

  @Override
  default public <U> UnionStream<U, X> normalMapToObj(DoubleFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingDoubleFunction<? extends U, ? extends Throwable> mapper);

  @Override
  default public UnionLongStream<X> normalMapToLong(DoubleToLongFunction mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  @Override
  public UnionLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends Throwable> mapper);

  @Override
  default public UnionIntStream<X> normalMapToInt(DoubleToIntFunction mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  @Override
  public UnionIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends Throwable> mapper);

  @Override
  default public UnionDoubleStream<X> normalFlatMap(
      DoubleFunction<? extends ThrowingDoubleStream<? extends Throwable>> mapper) {
    return flatMap(mapper::apply);
  }

  @Override
  public UnionDoubleStream<X> flatMap(
      ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper);

  @Override
  public UnionDoubleStream<X> distinct();

  @Override
  public UnionDoubleStream<X> sorted();

  @Override
  default public UnionDoubleStream<X> normalPeek(DoubleConsumer action) {
    return peek(action::accept);
  }

  @Override
  public UnionDoubleStream<X> peek(ThrowingDoubleConsumer<? extends Throwable> action);

  @Override
  public UnionDoubleStream<X> limit(long maxSize);

  @Override
  public UnionDoubleStream<X> skip(long n);

  @Override
  default public void normalForEach(DoubleConsumer action) throws X {
    forEach(action::accept);
  }

  @Override
  public void forEach(ThrowingDoubleConsumer<? extends Throwable> action) throws X;

  @Override
  default public void normalForEachOrdered(DoubleConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  @Override
  public void forEachOrdered(ThrowingDoubleConsumer<? extends Throwable> action) throws X;

  @Override
  public double[] toArray() throws X;

  @Override
  default public double normalReduce(double identity, DoubleBinaryOperator op) throws X {
    return reduce(identity, op::applyAsDouble);
  }

  @Override
  public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends Throwable> op)
      throws X;

  @Override
  default public OptionalDouble normalReduce(DoubleBinaryOperator op) throws X {
    return reduce(op::applyAsDouble);
  }

  @Override
  public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends Throwable> op) throws X;

  @Override
  default public <R> R normalCollect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingObjDoubleConsumer<R, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

  @Override
  public double sum() throws X;

  @Override
  public OptionalDouble min() throws X;

  @Override
  public OptionalDouble max() throws X;

  @Override
  public long count() throws X;

  @Override
  public OptionalDouble average() throws X;

  @Override
  public DoubleSummaryStatistics summaryStatistics() throws X;

  @Override
  default public boolean normalAnyMatch(DoublePredicate predicate) throws X {
    return anyMatch((ThrowingDoublePredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean anyMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalAllMatch(DoublePredicate predicate) throws X {
    return allMatch((ThrowingDoublePredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean allMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalNoneMatch(DoublePredicate predicate) throws X {
    return noneMatch((ThrowingDoublePredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean noneMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X;

  @Override
  public OptionalDouble findFirst() throws X;

  @Override
  public OptionalDouble findAny() throws X;

  @Override
  public UnionStream<Double, X> boxed();
}
