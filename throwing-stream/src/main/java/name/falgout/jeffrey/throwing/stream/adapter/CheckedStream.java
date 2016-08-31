package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingBiFunction;
import name.falgout.jeffrey.throwing.ThrowingBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingComparator;
import name.falgout.jeffrey.throwing.ThrowingConsumer;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingPredicate;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.ThrowingToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingToLongFunction;
import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;
import name.falgout.jeffrey.throwing.stream.ThrowingCollector;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

class CheckedStream<T, X extends Throwable> extends
    CheckedBaseStream<T, X, Stream<T>, ThrowingStream<T, X>> implements ThrowingStream<T, X> {
  CheckedStream(Stream<T> delegate, ExceptionMasker<X> ExceptionMasker) {
    super(delegate, ExceptionMasker);
  }

  CheckedStream(Stream<T> delegate, ExceptionMasker<X> ExceptionMasker,
      RethrowChain<Throwable, X> chain) {
    super(delegate, ExceptionMasker, chain);
  }

  @Override
  public ThrowingStream<T, X> getSelf() {
    return this;
  }

  @Override
  public ThrowingStream<T, X> createNewAdapter(Stream<T> delegate) {
    return newStream(delegate);
  }

  private <R> ThrowingStream<R, X> newStream(Stream<R> delegate) {
    return new CheckedStream<>(delegate, getExceptionMasker(), getChain());
  }

  @Override
  public ThrowingBaseSpliterator.ThrowingSpliterator<T, X> spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionMasker());
  }

  @Override
  public ThrowingStream<T, X> filter(ThrowingPredicate<? super T, ? extends X> predicate) {
    return chain(Stream<T>::filter, getExceptionMasker().mask(predicate));
  }

  @Override
  public <R> ThrowingStream<R, X> map(
      ThrowingFunction<? super T, ? extends R, ? extends X> mapper) {
    return newStream(getDelegate().map(getExceptionMasker().mask(mapper)));
  }

  @Override
  public ThrowingIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToInt(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingLongStream<X> mapToLong(ThrowingToLongFunction<? super T, ? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToLong(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingDoubleStream<X> mapToDouble(
      ThrowingToDoubleFunction<? super T, ? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToDouble(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public <R> ThrowingStream<R, X> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked")
    Function<? super ThrowingStream<? extends R, ? extends X>, ? extends Stream<? extends R>> c =
        s -> ThrowingBridge.of((ThrowingStream<? extends R, X>) s, getExceptionClass());
    return newStream(getDelegate().flatMap(getExceptionMasker().mask(mapper.andThen(c::apply))));
  }

  @Override
  public ThrowingIntStream<X> flatMapToInt(
      ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked")
    Function<? super ThrowingIntStream<? extends X>, ? extends IntStream> c =
        s -> ThrowingBridge.of((ThrowingIntStream<X>) s, getExceptionClass());
    return ThrowingBridge.of(
        getDelegate().flatMapToInt(getExceptionMasker().mask(mapper.andThen(c::apply))),
        getExceptionMasker());
  }

  @Override
  public ThrowingLongStream<X> flatMapToLong(
      ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked")
    Function<? super ThrowingLongStream<? extends X>, ? extends LongStream> c =
        s -> ThrowingBridge.of((ThrowingLongStream<X>) s, getExceptionClass());
    return ThrowingBridge.of(
        getDelegate().flatMapToLong(getExceptionMasker().mask(mapper.andThen(c::apply))),
        getExceptionMasker());
  }

  @Override
  public ThrowingDoubleStream<X> flatMapToDouble(
      ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked")
    Function<? super ThrowingDoubleStream<? extends X>, ? extends DoubleStream> c =
        s -> ThrowingBridge.of((ThrowingDoubleStream<X>) s, getExceptionClass());
    return ThrowingBridge.of(
        getDelegate().flatMapToDouble(getExceptionMasker().mask(mapper.andThen(c::apply))),
        getExceptionMasker());
  }

  @Override
  public ThrowingStream<T, X> distinct() {
    return chain(Stream::distinct);
  }

  @Override
  public ThrowingStream<T, X> sorted() {
    return chain(Stream::sorted);
  }

  @Override
  public ThrowingStream<T, X> sorted(ThrowingComparator<? super T, ? extends X> comparator) {
    return this.<Comparator<? super T>>chain(Stream::sorted, getExceptionMasker().mask(comparator));
  }

  @Override
  public ThrowingStream<T, X> peek(ThrowingConsumer<? super T, ? extends X> action) {
    return chain(Stream<T>::peek, getExceptionMasker().mask(action));
  }

  @Override
  public ThrowingStream<T, X> limit(long maxSize) {
    return chain(Stream::limit, maxSize);
  }

  @Override
  public ThrowingStream<T, X> skip(long n) {
    return chain(Stream::skip, n);
  }

  @Override
  public void forEach(ThrowingConsumer<? super T, ? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEach(getExceptionMasker().mask(action)));
  }

  @Override
  public void forEachOrdered(ThrowingConsumer<? super T, ? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEachOrdered(getExceptionMasker().mask(action)));
  }

  @Override
  public Object[] toArray() throws X {
    return unmaskException((Supplier<Object[]>) getDelegate()::toArray);
  }

  @Override
  public <A> A[] toArray(IntFunction<A[]> generator) throws X {
    return unmaskException(() -> getDelegate().toArray(generator));
  }

  @Override
  public T reduce(T identity, ThrowingBinaryOperator<T, ? extends X> accumulator) throws X {
    return unmaskException(
        () -> getDelegate().reduce(identity, getExceptionMasker().mask(accumulator)));
  }

  @Override
  public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends X> accumulator) throws X {
    return unmaskException(() -> getDelegate().reduce(getExceptionMasker().mask(accumulator)));
  }

  @Override
  public <U> U reduce(U identity,
      ThrowingBiFunction<U, ? super T, U, ? extends X> accumulator,
      ThrowingBinaryOperator<U, ? extends X> combiner) throws X {
    return unmaskException(() -> getDelegate().reduce(identity,
        getExceptionMasker().mask(accumulator), getExceptionMasker().mask(combiner)));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingBiConsumer<R, ? super T, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X {
    return unmaskException(() -> getDelegate().collect(getExceptionMasker().mask(supplier),
        getExceptionMasker().mask(accumulator), getExceptionMasker().mask(combiner)));
  }

  @Override
  public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends X> collector) throws X {
    return unmaskException(() -> getDelegate().collect(new Collector<T, A, R>() {
      @Override
      public Supplier<A> supplier() {
        return getExceptionMasker().mask(collector.supplier());
      }

      @Override
      public BiConsumer<A, T> accumulator() {
        return getExceptionMasker().mask(collector.accumulator());
      }

      @Override
      public BinaryOperator<A> combiner() {
        return getExceptionMasker().mask(collector.combiner());
      }

      @Override
      public Function<A, R> finisher() {
        return getExceptionMasker().mask(collector.finisher());
      }

      @Override
      public Set<Collector.Characteristics> characteristics() {
        return collector.characteristics();
      }
    }));
  }

  @Override
  public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws X {
    return unmaskException(() -> getDelegate().min(getExceptionMasker().mask(comparator)));
  }

  @Override
  public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws X {
    return unmaskException(() -> getDelegate().max(getExceptionMasker().mask(comparator)));
  }

  @Override
  public long count() throws X {
    return unmaskException(getDelegate()::count);
  }

  @Override
  public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().anyMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().allMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().noneMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public Optional<T> findFirst() throws X {
    return unmaskException(getDelegate()::findFirst);
  }

  @Override
  public Optional<T> findAny() throws X {
    return unmaskException(getDelegate()::findAny);
  }

  @Override
  public <Y extends Throwable> ThrowingStream<T, Y> rethrow(Class<Y> e,
      Function<? super X, ? extends Y> mapper) {
    RethrowChain<Throwable, Y> c = getChain().rethrow(mapper);
    return new CheckedStream<>(getDelegate(), new ExceptionMasker<>(e), c);
  }
}
