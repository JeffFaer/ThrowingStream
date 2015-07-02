package throwing.stream.union.adapter;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.Function;

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
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionDoubleStreamAdapter<X extends UnionThrowable> extends
    UnionBaseStreamAdapter<Double, X, ThrowingDoubleStream<X>, UnionDoubleStream<X>, ThrowingDoubleStream<Throwable>> implements
    UnionDoubleStream<X> {
  UnionDoubleStreamAdapter(ThrowingDoubleStream<X> delegate, UnionFunctionAdapter<X> adapter) {
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
  public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> y,
      Function<? super Throwable, ? extends Y> mapper) {
    return getDelegate().rethrow(y, mapper.compose(X::getCause));
  }

  @Override
  public UnionIterator.OfDouble<X> iterator() {
    return new UnionIteratorAdapter.OfDouble<>(getDelegate().iterator(), getFunctionAdapter());
  }

  @Override
  public UnionSpliterator.OfDouble<X> spliterator() {
    return new UnionSpliteratorAdapter.OfDouble<>(getDelegate().spliterator(), getFunctionAdapter());
  }

  @Override
  public UnionDoubleStream<X> filter(ThrowingDoublePredicate<? extends Throwable> predicate) {
    return chain(ThrowingDoubleStream::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  public UnionDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends Throwable> mapper) {
    return chain(ThrowingDoubleStream::map, getFunctionAdapter().convert(mapper));
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingDoubleFunction<? extends U, ? extends Throwable> mapper) {
    return new UnionStreamAdapter<>(getDelegate().mapToObj(getFunctionAdapter().convert(mapper)),
        getFunctionAdapter());
  }

  @Override
  public UnionIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends Throwable> mapper) {
    return new UnionIntStreamAdapter<>(
        getDelegate().mapToInt(getFunctionAdapter().convert(mapper)), getFunctionAdapter());
  }

  @Override
  public UnionLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends Throwable> mapper) {
    return new UnionLongStreamAdapter<>(getDelegate().mapToLong(
        getFunctionAdapter().convert(mapper)), getFunctionAdapter());
  }

  @Override
  public UnionDoubleStream<X> flatMap(
      ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper) {
    Function<ThrowingDoubleStream<? extends Throwable>, ThrowingDoubleStream<X>> f = getFunctionAdapter()::convert;
    return chain(ThrowingDoubleStream<X>::flatMap, getFunctionAdapter().convert(mapper).andThen(f));
  }

  @Override
  public UnionDoubleStream<X> distinct() {
    return chain(ThrowingDoubleStream::distinct);
  }

  @Override
  public UnionDoubleStream<X> sorted() {
    return chain(ThrowingDoubleStream::sorted);
  }

  @Override
  public UnionDoubleStream<X> peek(ThrowingDoubleConsumer<? extends Throwable> action) {
    return chain(ThrowingDoubleStream::peek, getFunctionAdapter().convert(action));
  }

  @Override
  public UnionDoubleStream<X> limit(long maxSize) {
    return chain(ThrowingDoubleStream::limit, maxSize);
  }

  @Override
  public UnionDoubleStream<X> skip(long n) {
    return chain(ThrowingDoubleStream::skip, n);
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
