package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

class UncheckedStream<T, X extends Throwable>
    extends UncheckedBaseStream<T, X, Stream<T>, ThrowingStream<T, X>> implements Stream<T> {
  UncheckedStream(ThrowingStream<T, X> delegate, Class<X> x) {
    super(delegate, x);
  }

  @Override
  public Stream<T> getSelf() {
    return this;
  }

  @Override
  public Stream<T> createNewAdapter(ThrowingStream<T, X> delegate) {
    return newStream(delegate);
  }

  private <R> Stream<R> newStream(ThrowingStream<R, X> delegate) {
    return new UncheckedStream<>(delegate, getExceptionClass());
  }

  @Override
  public Iterator<T> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionClass());
  }

  @Override
  public Spliterator<T> spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionClass());
  }

  @Override
  public Stream<T> filter(Predicate<? super T> predicate) {
    return chain(ThrowingStream::normalFilter, predicate);
  }

  @Override
  public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
    ThrowingFunction<? super T, ? extends R, ? extends X> f = mapper::apply;
    return newStream(getDelegate().map(f));
  }

  @Override
  public IntStream mapToInt(ToIntFunction<? super T> mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToInt(mapper), getExceptionClass());
  }

  @Override
  public LongStream mapToLong(ToLongFunction<? super T> mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToLong(mapper), getExceptionClass());
  }

  @Override
  public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToDouble(mapper), getExceptionClass());
  }

  @Override
  public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
    Function<? super T, ? extends ThrowingStream<? extends R, ? extends X>> f =
        mapper.andThen(s -> ThrowingBridge.of((Stream<? extends R>) s, getExceptionClass()));
    return newStream(getDelegate().normalFlatMap(f));
  }

  @Override
  public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
    Function<? super T, ? extends ThrowingIntStream<? extends X>> f =
        mapper.andThen(s -> ThrowingBridge.of(s, getExceptionClass()));
    return ThrowingBridge.of(getDelegate().normalFlatMapToInt(f), getExceptionClass());
  }

  @Override
  public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
    Function<? super T, ? extends ThrowingLongStream<? extends X>> f =
        mapper.andThen(s -> ThrowingBridge.of(s, getExceptionClass()));
    return ThrowingBridge.of(getDelegate().normalFlatMapToLong(f), getExceptionClass());
  }

  @Override
  public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
    Function<? super T, ? extends ThrowingDoubleStream<? extends X>> f =
        mapper.andThen(s -> ThrowingBridge.of(s, getExceptionClass()));
    return ThrowingBridge.of(getDelegate().normalFlatMapToDouble(f), getExceptionClass());
  }

  @Override
  public Stream<T> distinct() {
    return chain(ThrowingStream::distinct);
  }

  @Override
  public Stream<T> sorted() {
    return chain(ThrowingStream::sorted);
  }

  @Override
  public Stream<T> sorted(Comparator<? super T> comparator) {
    return chain(ThrowingStream::normalSorted, comparator);
  }

  @Override
  public Stream<T> peek(Consumer<? super T> action) {
    return chain(ThrowingStream::normalPeek, action);
  }

  @Override
  public Stream<T> limit(long maxSize) {
    return chain(ThrowingStream::limit, maxSize);
  }

  @Override
  public Stream<T> skip(long n) {
    return chain(ThrowingStream::skip, n);
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    getExceptionMasker().maskException(() -> getDelegate().normalForEach(action));
  }

  @Override
  public void forEachOrdered(Consumer<? super T> action) {
    getExceptionMasker().maskException(() -> getDelegate().normalForEachOrdered(action));
  }

  @Override
  public Object[] toArray() {
    return getExceptionMasker()
        .maskException((ThrowingSupplier<Object[], X>) getDelegate()::toArray);
  }

  @Override
  public <A> A[] toArray(IntFunction<A[]> generator) {
    return getExceptionMasker().maskException(() -> getDelegate().toArray(generator));
  }

  @Override
  public T reduce(T identity, BinaryOperator<T> accumulator) {
    return getExceptionMasker()
        .maskException(() -> getDelegate().normalReduce(identity, accumulator));
  }

  @Override
  public Optional<T> reduce(BinaryOperator<T> accumulator) {
    return getExceptionMasker().maskException(() -> getDelegate().normalReduce(accumulator));
  }

  @Override
  public <U> U reduce(U identity,
      BiFunction<U, ? super T, U> accumulator,
      BinaryOperator<U> combiner) {
    return getExceptionMasker()
        .maskException(() -> getDelegate().normalReduce(identity, accumulator, combiner));
  }

  @Override
  public <R> R collect(Supplier<R> supplier,
      BiConsumer<R, ? super T> accumulator,
      BiConsumer<R, R> combiner) {
    return getExceptionMasker()
        .maskException(() -> getDelegate().normalCollect(supplier, accumulator, combiner));
  }

  @Override
  public <R, A> R collect(Collector<? super T, A, R> collector) {
    return getExceptionMasker()
        .maskException(() -> getDelegate().collect(ThrowingBridge.of(collector)));
  }

  @Override
  public Optional<T> min(Comparator<? super T> comparator) {
    return getExceptionMasker().maskException(() -> getDelegate().normalMin(comparator));
  }

  @Override
  public Optional<T> max(Comparator<? super T> comparator) {
    return getExceptionMasker().maskException(() -> getDelegate().normalMax(comparator));
  }

  @Override
  public long count() {
    return getExceptionMasker().maskException(getDelegate()::count);
  }

  @Override
  public boolean anyMatch(Predicate<? super T> predicate) {
    return getExceptionMasker().maskException(() -> getDelegate().normalAnyMatch(predicate));
  }

  @Override
  public boolean allMatch(Predicate<? super T> predicate) {
    return getExceptionMasker().maskException(() -> getDelegate().normalAllMatch(predicate));
  }

  @Override
  public boolean noneMatch(Predicate<? super T> predicate) {
    return getExceptionMasker().maskException(() -> getDelegate().normalNoneMatch(predicate));
  }

  @Override
  public Optional<T> findFirst() {
    return getExceptionMasker().maskException(getDelegate()::findFirst);
  }

  @Override
  public Optional<T> findAny() {
    return getExceptionMasker().maskException(getDelegate()::findAny);
  }
}
