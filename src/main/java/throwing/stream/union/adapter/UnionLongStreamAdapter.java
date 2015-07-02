package throwing.stream.union.adapter;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.Function;

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
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionLongStreamAdapter<X extends UnionThrowable> extends
    UnionBaseStreamAdapter<Long, X, ThrowingLongStream<X>, UnionLongStream<X>, ThrowingLongStream<Throwable>> implements
    UnionLongStream<X> {
  UnionLongStreamAdapter(ThrowingLongStream<X> delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public UnionLongStream<X> getSelf() {
    return this;
  }

  @Override
  public UnionLongStream<X> createNewAdapter(ThrowingLongStream<X> delegate) {
    return new UnionLongStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public <Y extends Throwable> ThrowingLongStream<Y> rethrow(Class<Y> y,
      Function<? super Throwable, ? extends Y> mapper) {
    return getDelegate().rethrow(y, mapper.compose(X::getCause));
  }

  @Override
  public UnionIterator.OfLong<X> iterator() {
    return new UnionIteratorAdapter.OfLong<>(getDelegate().iterator(), getFunctionAdapter());
  }

  @Override
  public UnionSpliterator.OfLong<X> spliterator() {
    return new UnionSpliteratorAdapter.OfLong<>(getDelegate().spliterator(), getFunctionAdapter());
  }

  @Override
  public UnionLongStream<X> filter(ThrowingLongPredicate<? extends Throwable> predicate) {
    return chain(ThrowingLongStream::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  public UnionLongStream<X> map(ThrowingLongUnaryOperator<? extends Throwable> mapper) {
    return chain(ThrowingLongStream::map, getFunctionAdapter().convert(mapper));
  }

  @Override
  public <U> UnionStream<U, X> mapToObj(
      ThrowingLongFunction<? extends U, ? extends Throwable> mapper) {
    return new UnionStreamAdapter<>(getDelegate().mapToObj(getFunctionAdapter().convert(mapper)),
        getFunctionAdapter());
  }

  @Override
  public UnionDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends Throwable> mapper) {
    return new UnionDoubleStreamAdapter<>(getDelegate().mapToDouble(
        getFunctionAdapter().convert(mapper)), getFunctionAdapter());
  }

  @Override
  public UnionIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends Throwable> mapper) {
    return new UnionIntStreamAdapter<>(
        getDelegate().mapToInt(getFunctionAdapter().convert(mapper)), getFunctionAdapter());
  }

  @Override
  public UnionLongStream<X> flatMap(
      ThrowingLongFunction<? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper) {
    Function<ThrowingLongStream<? extends Throwable>, ThrowingLongStream<X>> f = getFunctionAdapter()::convert;
    return chain(ThrowingLongStream<X>::flatMap, getFunctionAdapter().convert(mapper).andThen(f));
  }

  @Override
  public UnionLongStream<X> distinct() {
    return chain(ThrowingLongStream::distinct);
  }

  @Override
  public UnionLongStream<X> sorted() {
    return chain(ThrowingLongStream::sorted);
  }

  @Override
  public UnionLongStream<X> peek(ThrowingLongConsumer<? extends Throwable> action) {
    return chain(ThrowingLongStream::peek, getFunctionAdapter().convert(action));
  }

  @Override
  public UnionLongStream<X> limit(long maxSize) {
    return chain(ThrowingLongStream::limit, maxSize);
  }

  @Override
  public UnionLongStream<X> skip(long n) {
    return chain(ThrowingLongStream::skip, n);
  }

  @Override
  public void forEach(ThrowingLongConsumer<? extends Throwable> action) throws X {
    getDelegate().forEach(getFunctionAdapter().convert(action));
  }

  @Override
  public void forEachOrdered(ThrowingLongConsumer<? extends Throwable> action) throws X {
    getDelegate().forEachOrdered(getFunctionAdapter().convert(action));
  }

  @Override
  public long[] toArray() throws X {
    return getDelegate().toArray();
  }

  @Override
  public long reduce(long identity, ThrowingLongBinaryOperator<? extends Throwable> op) throws X {
    return getDelegate().reduce(identity, getFunctionAdapter().convert(op));
  }

  @Override
  public OptionalLong reduce(ThrowingLongBinaryOperator<? extends Throwable> op) throws X {
    return getDelegate().reduce(getFunctionAdapter().convert(op));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingObjLongConsumer<R, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
    return getDelegate().collect(getFunctionAdapter().convert(supplier),
        getFunctionAdapter().convert(accumulator), getFunctionAdapter().convert(combiner));
  }

  @Override
  public long sum() throws X {
    return getDelegate().sum();
  }

  @Override
  public OptionalLong min() throws X {
    return getDelegate().min();
  }

  @Override
  public OptionalLong max() throws X {
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
  public LongSummaryStatistics summaryStatistics() throws X {
    return getDelegate().summaryStatistics();
  }

  @Override
  public boolean anyMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X {
    return getDelegate().anyMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean allMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X {
    return getDelegate().allMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean noneMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X {
    return getDelegate().noneMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public OptionalLong findFirst() throws X {
    return getDelegate().findFirst();
  }

  @Override
  public OptionalLong findAny() throws X {
    return getDelegate().findAny();
  }

  @Override
  public UnionDoubleStream<X> asDoubleStream() {
    return new UnionDoubleStreamAdapter<>(getDelegate().asDoubleStream(), getFunctionAdapter());
  }

  @Override
  public UnionStream<Long, X> boxed() {
    return new UnionStreamAdapter<>(getDelegate().boxed(), getFunctionAdapter());
  }

}
