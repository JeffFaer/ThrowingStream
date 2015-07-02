package throwing.stream.union.adapter;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.intermediate.adapter.ThrowingDoubleStreamIntermediateAdapter;
import throwing.stream.intermediate.adapter.ThrowingFunctionAdapter;
import throwing.stream.union.UnionBaseSpliterator;
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionDoubleStreamAdapter<X extends UnionThrowable> extends
    UnionBaseStreamAdapter<Double, X, ThrowingDoubleStream<X>, UnionDoubleStream<X>> implements
    ThrowingDoubleStreamIntermediateAdapter<Throwable, X, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>, UnionIntStream<X>, UnionLongStream<X>, UnionDoubleStream<X>>,
    UnionDoubleStream<X> {
  UnionDoubleStreamAdapter(ThrowingDoubleStream<X> delegate,
      ThrowingFunctionAdapter<X, Throwable> adapter) {
    super(delegate, adapter);
  }

  @Override
  public UnionDoubleStream<X> getSelf() {
    return this;
  }

  @Override
  public UnionDoubleStream<X> createNewAdapter(ThrowingDoubleStream<X> delegate) {
    return new UnionDoubleStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public UnionIntStream<X> newIntStream(ThrowingIntStream<X> delegate) {
    return new UnionIntStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public UnionLongStream<X> newLongStream(ThrowingLongStream<X> delegate) {
    return new UnionLongStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public UnionIterator.OfDouble<X> iterator() {
    return new UnionIteratorAdapter.OfDouble<>(getDelegate().iterator(), getFunctionAdapter());
  }

  @Override
  public UnionBaseSpliterator.OfDouble<X> spliterator() {
    return new UnionBaseSpliteratorAdapter.OfDouble<>(getDelegate().spliterator(),
        getFunctionAdapter());
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingDoubleFunction<? extends U, ? extends Throwable> mapper) {
    return new UnionStreamAdapter<>(getDelegate().mapToObj(getFunctionAdapter().convert(mapper)),
        getFunctionAdapter());
  }

  @Override
  public void forEach(ThrowingDoubleConsumer<? extends Throwable> action) throws X {
    getDelegate().forEach(getFunctionAdapter().convert(action));
  }

  @Override
  public void forEachOrdered(ThrowingDoubleConsumer<? extends Throwable> action) throws X {
    getDelegate().forEachOrdered(getFunctionAdapter().convert(action));
  }

  @Override
  public double[] toArray() throws X {
    return getDelegate().toArray();
  }

  @Override
  public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends Throwable> op)
      throws X {
    return getDelegate().reduce(identity, getFunctionAdapter().convert(op));
  }

  @Override
  public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends Throwable> op) throws X {
    return getDelegate().reduce(getFunctionAdapter().convert(op));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingObjDoubleConsumer<R, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
    return getDelegate().collect(getFunctionAdapter().convert(supplier),
        getFunctionAdapter().convert(accumulator), getFunctionAdapter().convert(combiner));
  }

  @Override
  public double sum() throws X {
    return getDelegate().sum();
  }

  @Override
  public OptionalDouble min() throws X {
    return getDelegate().min();
  }

  @Override
  public OptionalDouble max() throws X {
    return getDelegate().max();
  }

  @Override
  public long count() throws X {
    return getDelegate().count();
  }

  @Override
  public OptionalDouble average() throws X {
    return getDelegate().average();
  }

  @Override
  public DoubleSummaryStatistics summaryStatistics() throws X {
    return getDelegate().summaryStatistics();
  }

  @Override
  public boolean anyMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X {
    return getDelegate().anyMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean allMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X {
    return getDelegate().allMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean noneMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X {
    return getDelegate().noneMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public OptionalDouble findFirst() throws X {
    return getDelegate().findFirst();
  }

  @Override
  public OptionalDouble findAny() throws X {
    return getDelegate().findAny();
  }

  @Override
  public UnionStream<Double, X> boxed() {
    return new UnionStreamAdapter<>(getDelegate().boxed(), getFunctionAdapter());
  }
}
