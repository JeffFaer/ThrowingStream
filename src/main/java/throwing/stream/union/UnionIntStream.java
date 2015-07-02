package throwing.stream.union;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

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
import throwing.stream.ThrowingIntStream;

public interface UnionIntStream<X extends UnionThrowable> extends
    UnionBaseStream<Integer, X, UnionIntStream<X>, ThrowingIntStream<Throwable>>,
    ThrowingIntStream<Throwable> {
  @Override
  public UnionIterator.OfInt<X> iterator();

  @Override
  public UnionSpliterator.OfInt<X> spliterator();

  @Override
  default public UnionIntStream<X> normalFilter(IntPredicate predicate) {
    return filter(predicate::test);
  }

  @Override
  public UnionIntStream<X> filter(ThrowingIntPredicate<? extends Throwable> predicate);

  @Override
  default public UnionIntStream<X> normalMap(IntUnaryOperator mapper) {
    return map(mapper::applyAsInt);
  }

  @Override
  public UnionIntStream<X> map(ThrowingIntUnaryOperator<? extends Throwable> mapper);

  @Override
  default public <U> UnionStream<U, X> normalMapToObj(IntFunction<? extends U> mapper) {
    return mapToObj(mapper::apply);
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingIntFunction<? extends U, ? extends Throwable> mapper);

  @Override
  default public UnionLongStream<X> normalMapToLong(IntToLongFunction mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  @Override
  public UnionLongStream<X> mapToLong(ThrowingIntToLongFunction<? extends Throwable> mapper);

  @Override
  default public UnionDoubleStream<X> normalMapToDouble(IntToDoubleFunction mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  @Override
  public UnionDoubleStream<X> mapToDouble(ThrowingIntToDoubleFunction<? extends Throwable> mapper);

  @Override
  default public UnionIntStream<X> normalFlatMap(
      IntFunction<? extends ThrowingIntStream<? extends Throwable>> mapper) {
    return flatMap(mapper::apply);
  }

  @Override
  public UnionIntStream<X> flatMap(
      ThrowingIntFunction<? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper);

  @Override
  public UnionIntStream<X> distinct();

  @Override
  public UnionIntStream<X> sorted();

  @Override
  default public UnionIntStream<X> normalPeek(IntConsumer action) {
    return peek(action::accept);
  }

  @Override
  public UnionIntStream<X> peek(ThrowingIntConsumer<? extends Throwable> action);

  @Override
  public UnionIntStream<X> limit(long maxSize);

  @Override
  public UnionIntStream<X> skip(long n);

  @Override
  default public void normalForEach(IntConsumer action) throws X {
    forEach(action::accept);
  }

  @Override
  public void forEach(ThrowingIntConsumer<? extends Throwable> action) throws X;

  @Override
  default public void normalForEachOrdered(IntConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  @Override
  public void forEachOrdered(ThrowingIntConsumer<? extends Throwable> action) throws X;

  @Override
  public int[] toArray() throws X;

  @Override
  default public int normalReduce(int identity, IntBinaryOperator op) throws X {
    return reduce(identity, op::applyAsInt);
  }

  @Override
  public int reduce(int identity, ThrowingIntBinaryOperator<? extends Throwable> op) throws X;

  @Override
  default public OptionalInt normalReduce(IntBinaryOperator op) throws X {
    return reduce(op::applyAsInt);
  }

  @Override
  public OptionalInt reduce(ThrowingIntBinaryOperator<? extends Throwable> op) throws X;

  @Override
  default public <R> R normalCollect(Supplier<R> supplier, ObjIntConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingObjIntConsumer<R, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

  @Override
  public int sum() throws X;

  @Override
  public OptionalInt min() throws X;

  @Override
  public OptionalInt max() throws X;

  @Override
  public long count() throws X;

  @Override
  public OptionalDouble average() throws X;

  @Override
  public IntSummaryStatistics summaryStatistics() throws X;

  @Override
  default public boolean normalAnyMatch(IntPredicate predicate) throws X {
    return anyMatch((ThrowingIntPredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean anyMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalAllMatch(IntPredicate predicate) throws X {
    return allMatch((ThrowingIntPredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean allMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalNoneMatch(IntPredicate predicate) throws X {
    return noneMatch((ThrowingIntPredicate<? extends Throwable>) predicate::test);
  }

  @Override
  public boolean noneMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X;

  @Override
  public OptionalInt findFirst() throws X;

  @Override
  public OptionalInt findAny() throws X;

  @Override
  public UnionLongStream<X> asLongStream();

  @Override
  public UnionDoubleStream<X> asDoubleStream();

  @Override
  public UnionStream<Integer, X> boxed();
}
