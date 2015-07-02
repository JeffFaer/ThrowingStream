package throwing.stream.union.adapter;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

import throwing.ThrowingComparator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;
import throwing.function.ThrowingToDoubleFunction;
import throwing.function.ThrowingToIntFunction;
import throwing.function.ThrowingToLongFunction;
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionStreamAdapter<T, X extends UnionThrowable> extends
    UnionBaseStreamAdapter<T, X, ThrowingStream<T, X>, UnionStream<T, X>, ThrowingStream<T, Throwable>> implements
    UnionStream<T, X> {
  UnionStreamAdapter(ThrowingStream<T, X> delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public UnionStream<T, X> getSelf() {
    return this;
  }

  @Override
  public UnionStream<T, X> createNewAdapter(ThrowingStream<T, X> delegate) {
    return newStream(delegate);
  }

  private <R> UnionStream<R, X> newStream(ThrowingStream<R, X> delegate) {
    return new UnionStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public <Y extends Throwable> ThrowingStream<T, Y> rethrow(Class<Y> y,
      Function<? super Throwable, ? extends Y> mapper) {
    return getDelegate().rethrow(y, mapper.compose(X::getCause));
  }

  @Override
  public UnionStream<T, X> filter(ThrowingPredicate<? super T, ? extends Throwable> predicate) {
    return chain(ThrowingStream<T, X>::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  public <R> UnionStream<R, X> map(
      ThrowingFunction<? super T, ? extends R, ? extends Throwable> mapper) {
    return newStream(getDelegate().map(getFunctionAdapter().convert(mapper)));
  }

  @Override
  public UnionIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends Throwable> mapper) {
    return new UnionIntStreamAdapter<>(
        getDelegate().mapToInt(getFunctionAdapter().convert(mapper)), getFunctionAdapter());
  }

  @Override
  public UnionLongStream<X> mapToLong(ThrowingToLongFunction<? super T, ? extends Throwable> mapper) {
    return new UnionLongStreamAdapter<>(getDelegate().mapToLong(
        getFunctionAdapter().convert(mapper)), getFunctionAdapter());
  }

  @Override
  public UnionDoubleStream<X> mapToDouble(
      ThrowingToDoubleFunction<? super T, ? extends Throwable> mapper) {
    return new UnionDoubleStreamAdapter<>(getDelegate().mapToDouble(
        getFunctionAdapter().convert(mapper)), getFunctionAdapter());
  }

  @Override
  public <R> UnionStream<R, X> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable> mapper) {
    Function<ThrowingStream<? extends R, ? extends Throwable>, ThrowingStream<? extends R, X>> convertStream = getFunctionAdapter()::convert;
    return newStream(getDelegate().flatMap(
        getFunctionAdapter().convert(mapper).andThen(convertStream)));
  }

  @Override
  public UnionIntStream<X> flatMapToInt(
      ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper) {
    Function<ThrowingIntStream<? extends Throwable>, ThrowingIntStream<X>> convertStream = getFunctionAdapter()::convert;
    return new UnionIntStreamAdapter<>(getDelegate().flatMapToInt(
        getFunctionAdapter().convert(mapper).andThen(convertStream)), getFunctionAdapter());
  }

  @Override
  public UnionLongStream<X> flatMapToLong(
      ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper) {
    Function<ThrowingLongStream<? extends Throwable>, ThrowingLongStream<X>> convertStream = getFunctionAdapter()::convert;
    return new UnionLongStreamAdapter<>(getDelegate().flatMapToLong(
        getFunctionAdapter().convert(mapper).andThen(convertStream)), getFunctionAdapter());
  }

  @Override
  public UnionDoubleStream<X> flatMapToDouble(
      ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper) {
    Function<ThrowingDoubleStream<? extends Throwable>, ThrowingDoubleStream<X>> convertStream = getFunctionAdapter()::convert;
    return new UnionDoubleStreamAdapter<>(getDelegate().flatMapToDouble(
        getFunctionAdapter().convert(mapper).andThen(convertStream)), getFunctionAdapter());
  }

  @Override
  public UnionStream<T, X> distinct() {
    return chain(ThrowingStream::distinct);
  }

  @Override
  public UnionStream<T, X> sorted() {
    return chain(ThrowingStream::sorted);
  }

  @Override
  public UnionStream<T, X> sorted(ThrowingComparator<? super T, ? extends Throwable> comparator) {
    return this.<ThrowingComparator<? super T, ? extends X>> chain(ThrowingStream::sorted,
        getFunctionAdapter().convert(comparator));
  }

  @Override
  public UnionStream<T, X> peek(ThrowingConsumer<? super T, ? extends Throwable> action) {
    return chain(ThrowingStream<T, X>::peek, getFunctionAdapter().convert(action));
  }

  @Override
  public UnionStream<T, X> limit(long maxSize) {
    return chain(ThrowingStream::limit, maxSize);
  }

  @Override
  public UnionStream<T, X> skip(long n) {
    return chain(ThrowingStream::skip, n);
  }

  @Override
  public void forEach(ThrowingConsumer<? super T, ? extends Throwable> action) throws X {
    getDelegate().forEach(getFunctionAdapter().convert(action));
  }

  @Override
  public void forEachOrdered(ThrowingConsumer<? super T, ? extends Throwable> action) throws X {
    getDelegate().forEachOrdered(getFunctionAdapter().convert(action));
  }

  @Override
  public Object[] toArray() throws X {
    return getDelegate().toArray();
  }

  @Override
  public <A> A[] toArray(IntFunction<A[]> generator) throws X {
    return getDelegate().toArray(generator);
  }

  @Override
  public T reduce(T identity, ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws X {
    return getDelegate().reduce(identity, getFunctionAdapter().convert(accumulator));
  }

  @Override
  public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws X {
    return getDelegate().reduce(getFunctionAdapter().convert(accumulator));
  }

  @Override
  public <U> U reduce(U identity,
      ThrowingBiFunction<U, ? super T, U, ? extends Throwable> accumulator,
      ThrowingBinaryOperator<U, ? extends Throwable> combiner) throws X {
    return getDelegate().reduce(identity, getFunctionAdapter().convert(accumulator),
        getFunctionAdapter().convert(combiner));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingBiConsumer<R, ? super T, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
    return getDelegate().collect(getFunctionAdapter().convert(supplier),
        getFunctionAdapter().convert(accumulator), getFunctionAdapter().convert(combiner));
  }

  @Override
  public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends Throwable> collector)
      throws X {
    return getDelegate().collect(getFunctionAdapter().convert(collector));
  }

  @Override
  public Optional<T> min(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X {
    return getDelegate().min(getFunctionAdapter().convert(comparator));
  }

  @Override
  public Optional<T> max(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X {
    return getDelegate().max(getFunctionAdapter().convert(comparator));
  }

  @Override
  public long count() throws X {
    return getDelegate().count();
  }

  @Override
  public boolean anyMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X {
    return getDelegate().anyMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean allMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X {
    return getDelegate().allMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public boolean noneMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X {
    return getDelegate().noneMatch(getFunctionAdapter().convert(predicate));
  }

  @Override
  public Optional<T> findFirst() throws X {
    return getDelegate().findFirst();
  }

  @Override
  public Optional<T> findAny() throws X {
    return getDelegate().findAny();
  }
}
