package throwing.stream.union.adapter;

import java.util.Optional;
import java.util.function.BiFunction;
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
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;
import throwing.stream.intermediate.adapter.ThrowingStreamIntermediateAdapter;
import throwing.stream.union.UnionBaseSpliterator.UnionSpliterator;
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;
import throwing.stream.union.adapter.UnionBaseSpliteratorAdapter.UnionSpliteratorAdapter;

class UnionStreamAdapter<T, X extends UnionThrowable> extends
    UnionBaseStreamAdapter<T, X, ThrowingStream<T, X>, UnionStream<T, X>> implements
    ThrowingStreamIntermediateAdapter<T, Throwable, X, ThrowingStream<T, X>, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>, UnionStream<T, X>, UnionIntStream<X>, UnionLongStream<X>, UnionDoubleStream<X>>,
    UnionStream<T, X> {
  UnionStreamAdapter(ThrowingStream<T, X> delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public UnionStream<T, X> getSelf() {
    return this;
  }

  @Override
  public UnionStream<T, X> createNewAdapter(ThrowingStream<T, X> newDelegate) {
    return newStream(newDelegate);
  }

  private <U, R> UnionStream<R, X> newStream(
      BiFunction<? super ThrowingStream<T, X>, U, ThrowingStream<R, X>> function, U secondArgument) {
    return newStream(function.apply(getDelegate(), secondArgument));
  }

  private <R> UnionStream<R, X> newStream(ThrowingStream<R, X> newDelegate) {
    return new UnionStreamAdapter<>(newDelegate, getFunctionAdapter());
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
  public UnionDoubleStream<X> newDoubleStream(ThrowingDoubleStream<X> delegate) {
    return new UnionDoubleStreamAdapter<>(delegate, getFunctionAdapter());
  }

  @Override
  public UnionIterator<T, X> iterator() {
    return new UnionIteratorAdapter<>(getDelegate().iterator(), getFunctionAdapter());
  }

  @Override
  public UnionSpliterator<T, X> spliterator() {
    return new UnionSpliteratorAdapter<>(getDelegate().spliterator(), getFunctionAdapter());
  }

  @Override
  public <R> UnionStream<R, X> map(
      ThrowingFunction<? super T, ? extends R, ? extends Throwable> mapper) {
    return newStream(ThrowingStream<T, X>::<R> map, getFunctionAdapter().convert(mapper));
  }

  @Override
  public <R> UnionStream<R, X> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable> mapper) {
    Function<ThrowingStream<? extends R, ? extends Throwable>, ThrowingStream<? extends R, X>> streamMapper = getFunctionAdapter()::convert;
    ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, X>, X> f = getFunctionAdapter().convert(
        mapper.andThen(streamMapper));
    return newStream(ThrowingStream::flatMap, f);
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
