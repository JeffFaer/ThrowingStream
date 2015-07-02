package throwing.stream.union.adapter;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingIntBinaryOperator;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingObjIntConsumer;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.intermediate.adapter.ThrowingIntStreamIntermediateAdapter;
import throwing.stream.union.UnionBaseSpliterator;
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionIntStreamAdapter<X extends UnionThrowable> extends
    UnionBaseStreamAdapter<Integer, X, ThrowingIntStream<X>, UnionIntStream<X>> implements
    ThrowingIntStreamIntermediateAdapter<Throwable, X, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>, UnionIntStream<X>, UnionLongStream<X>, UnionDoubleStream<X>>,
    UnionIntStream<X> {
  UnionIntStreamAdapter(ThrowingIntStream<X> delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public UnionIntStream<X> getSelf() {
    return this;
  }

  @Override
  public UnionIntStream<X> createNewAdapter(ThrowingIntStream<X> delegate) {
    return new UnionIntStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public UnionLongStream<X> newLongStream(ThrowingLongStream<X> delegate) {
    return new UnionLongStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public UnionDoubleStream<X> newDoubleStream(ThrowingDoubleStream<X> delegate) {
    return new UnionDoubleStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public UnionIterator.OfInt<X> iterator() {
    return new UnionIteratorAdapter.OfInt<>(getDelegate().iterator(), getFunctionAdapter());
  }

  @Override
  public UnionBaseSpliterator.OfInt<X> spliterator() {
    return new UnionBaseSpliteratorAdapter.OfInt<>(getDelegate().spliterator(),
        getFunctionAdapter());
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(ThrowingIntFunction<? extends U, ? extends Throwable> mapper) {
    return new UnionStreamAdapter<>(getDelegate().mapToObj(getFunctionAdapter().convert(mapper)),
        getFunctionAdapter());
  }

  @Override
  public void forEach(ThrowingIntConsumer<? extends Throwable> action) throws X {
    getDelegate().forEach(getFunctionAdapter().convert(action));
  }

  @Override
  public void forEachOrdered(ThrowingIntConsumer<? extends Throwable> action) throws X {
    getDelegate().forEachOrdered(getFunctionAdapter().convert(action));
  }

  @Override
  public int[] toArray() throws X {
    return getDelegate().toArray();
  }

  @Override
  public int reduce(int identity, ThrowingIntBinaryOperator<? extends Throwable> op) throws X {
    return getDelegate().reduce(identity, getFunctionAdapter().convert(op));
  }

  @Override
  public OptionalInt reduce(ThrowingIntBinaryOperator<? extends Throwable> op) throws X {
    return getDelegate().reduce(getFunctionAdapter().convert(op));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingObjIntConsumer<R, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
    return getDelegate().collect(getFunctionAdapter().convert(supplier),
        getFunctionAdapter().convert(accumulator), getFunctionAdapter().convert(combiner));
  }

  @Override
  public int sum() throws X {
    return getDelegate().sum();
  }

  @Override
  public OptionalInt min() throws X {
    return getDelegate().min();
  }

  @Override
  public OptionalInt max() throws X {
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
  public IntSummaryStatistics summaryStatistics() throws X {
    return getDelegate().summaryStatistics();
  }

  @Override
  public boolean anyMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X {
    return getDelegate().anyMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean allMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X {
    return getDelegate().allMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean noneMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X {
    return getDelegate().noneMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public OptionalInt findFirst() throws X {
    return getDelegate().findFirst();
  }

  @Override
  public OptionalInt findAny() throws X {
    return getDelegate().findAny();
  }

  @Override
  public UnionLongStream<X> asLongStream() {
    return new UnionLongStreamAdapter<>(getDelegate().asLongStream(), getFunctionAdapter());
  }

  @Override
  public UnionDoubleStream<X> asDoubleStream() {
    return new UnionDoubleStreamAdapter<>(getDelegate().asDoubleStream(), getFunctionAdapter());
  }

  @Override
  public UnionStream<Integer, X> boxed() {
    return new UnionStreamAdapter<>(getDelegate().boxed(), getFunctionAdapter());
  }
}
