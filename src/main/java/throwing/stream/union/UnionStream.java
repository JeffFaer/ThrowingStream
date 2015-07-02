package throwing.stream.union;

import java.util.Comparator;
import java.util.Optional;
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
import java.util.stream.Stream;

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
import throwing.stream.adapter.ThrowingBridge;
import throwing.stream.union.adapter.UnionBridge;

public interface UnionStream<T, X extends UnionThrowable> extends
    UnionBaseStream<T, X, UnionStream<T, X>, ThrowingStream<T, Throwable>>,
    ThrowingStream<T, Throwable> {
  @Override
  default UnionStream<T, X> normalFilter(Predicate<? super T> predicate) {
    return filter((ThrowingPredicate<? super T, ? extends Throwable>) predicate::test);
  }

  @Override
  public UnionStream<T, X> filter(ThrowingPredicate<? super T, ? extends Throwable> predicate);

  @Override
  default <R> UnionStream<R, X> normalMap(Function<? super T, ? extends R> mapper) {
    return map((ThrowingFunction<? super T, ? extends R, ? extends Throwable>) mapper::apply);
  }

  @Override
  public <R> UnionStream<R, X> map(
      ThrowingFunction<? super T, ? extends R, ? extends Throwable> mapper);

  @Override
  default public UnionIntStream<X> normalMapToInt(ToIntFunction<? super T> mapper) {
    return mapToInt((ThrowingToIntFunction<? super T, ? extends Throwable>) mapper::applyAsInt);
  }

  @Override
  public UnionIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends Throwable> mapper);

  @Override
  default public UnionLongStream<X> normalMapToLong(ToLongFunction<? super T> mapper) {
    return mapToLong((ThrowingToLongFunction<? super T, ? extends Throwable>) mapper::applyAsLong);
  }

  @Override
  public UnionLongStream<X> mapToLong(ThrowingToLongFunction<? super T, ? extends Throwable> mapper);

  @Override
  default public UnionDoubleStream<X> normalMapToDouble(ToDoubleFunction<? super T> mapper) {
    return mapToDouble((ThrowingToDoubleFunction<? super T, ? extends Throwable>) mapper::applyAsDouble);
  }

  @Override
  public UnionDoubleStream<X> mapToDouble(
      ThrowingToDoubleFunction<? super T, ? extends Throwable> mapper);

  @Override
  default public <R> UnionStream<R, X> normalFlatMap(
      Function<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>> mapper) {
    return flatMap((ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable>) mapper::apply);
  }

  @Override
  public <R> UnionStream<R, X> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable> mapper);

  @Override
  default public UnionIntStream<X> normalFlatMapToInt(
      Function<? super T, ? extends ThrowingIntStream<? extends Throwable>> mapper) {
    return flatMapToInt((ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable>) mapper::apply);
  }

  @Override
  public UnionIntStream<X> flatMapToInt(
      ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper);

  @Override
  default public UnionLongStream<X> normalFlatMapToLong(
      Function<? super T, ? extends ThrowingLongStream<? extends Throwable>> mapper) {
    return flatMapToLong((ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable>) mapper::apply);
  }

  @Override
  public UnionLongStream<X> flatMapToLong(
      ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper);

  @Override
  default public UnionDoubleStream<X> normalFlatMapToDouble(
      Function<? super T, ? extends ThrowingDoubleStream<? extends Throwable>> mapper) {
    return flatMapToDouble((ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable>) mapper::apply);
  }

  @Override
  public UnionDoubleStream<X> flatMapToDouble(
      ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper);

  @Override
  public UnionStream<T, X> distinct();

  @Override
  public UnionStream<T, X> sorted();

  @Override
  default public UnionStream<T, X> normalSorted(Comparator<? super T> comparator) {
    return sorted((ThrowingComparator<? super T, ? extends Throwable>) comparator::compare);
  }

  @Override
  public UnionStream<T, X> sorted(ThrowingComparator<? super T, ? extends Throwable> comparator);

  @Override
  default public UnionStream<T, X> normalPeek(Consumer<? super T> action) {
    return peek((ThrowingConsumer<? super T, ? extends Throwable>) action::accept);
  }

  @Override
  public UnionStream<T, X> peek(ThrowingConsumer<? super T, ? extends Throwable> action);

  @Override
  public UnionStream<T, X> limit(long maxSize);

  @Override
  public UnionStream<T, X> skip(long n);

  @Override
  default public void normalForEach(Consumer<? super T> action) throws X {
    forEach((ThrowingConsumer<? super T, ? extends Throwable>) action::accept);
  }

  @Override
  public void forEach(ThrowingConsumer<? super T, ? extends Throwable> action) throws X;

  @Override
  default public void normalForEachOrdered(Consumer<? super T> action) throws X {
    forEachOrdered((ThrowingConsumer<? super T, ? extends Throwable>) action::accept);
  }

  @Override
  public void forEachOrdered(ThrowingConsumer<? super T, ? extends Throwable> action) throws X;

  @Override
  public Object[] toArray() throws X;

  @Override
  public <A> A[] toArray(IntFunction<A[]> generator) throws X;

  @Override
  default public T normalReduce(T identity, BinaryOperator<T> accumulator) throws X {
    return reduce(identity, (ThrowingBinaryOperator<T, ? extends Throwable>) accumulator::apply);
  }

  @Override
  public T reduce(T identity, ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws X;

  @Override
  default public Optional<T> normalReduce(BinaryOperator<T> accumulator) throws X {
    return reduce((ThrowingBinaryOperator<T, ? extends Throwable>) accumulator::apply);
  }

  @Override
  public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws X;

  @Override
  default public <U> U normalReduce(U identity, BiFunction<U, ? super T, U> accumulator,
      BinaryOperator<U> combiner) throws X {
    return reduce(identity,
        (ThrowingBiFunction<U, ? super T, U, ? extends Throwable>) accumulator::apply,
        (ThrowingBinaryOperator<U, ? extends Throwable>) combiner::apply);
  }

  @Override
  public <U> U reduce(U identity,
      ThrowingBiFunction<U, ? super T, U, ? extends Throwable> accumulator,
      ThrowingBinaryOperator<U, ? extends Throwable> combiner) throws X;

  @Override
  default public <R> R normalCollect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect((ThrowingSupplier<R, ? extends Throwable>) supplier::get,
        (ThrowingBiConsumer<R, ? super T, ? extends Throwable>) accumulator::accept,
        (ThrowingBiConsumer<R, R, ? extends Throwable>) combiner::accept);
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
      ThrowingBiConsumer<R, ? super T, ? extends Throwable> accumulator,
      ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

  @Override
  default public <R, A> R collect(Collector<? super T, A, R> collector) throws X {
    return collect(ThrowingBridge.of(collector));
  }

  @Override
  public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends Throwable> collector)
      throws X;

  @Override
  default public Optional<T> normalMin(Comparator<? super T> comparator) throws X {
    return min((ThrowingComparator<? super T, ? extends Throwable>) comparator::compare);
  }

  @Override
  public Optional<T> min(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X;

  @Override
  default public Optional<T> normalMax(Comparator<? super T> comparator) throws X {
    return max((ThrowingComparator<? super T, ? extends Throwable>) comparator::compare);
  }

  @Override
  public Optional<T> max(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X;

  @Override
  public long count() throws X;

  @Override
  default public boolean normalAnyMatch(Predicate<? super T> predicate) throws X {
    return anyMatch((ThrowingPredicate<? super T, ? extends Throwable>) predicate::test);
  }

  @Override
  public boolean anyMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalAllMatch(Predicate<? super T> predicate) throws X {
    return allMatch((ThrowingPredicate<? super T, ? extends Throwable>) predicate::test);
  }

  @Override
  public boolean allMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X;

  @Override
  default public boolean normalNoneMatch(Predicate<? super T> predicate) throws X {
    return noneMatch((ThrowingPredicate<? super T, ? extends Throwable>) predicate::test);
  }

  @Override
  public boolean noneMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X;

  @Override
  public Optional<T> findFirst() throws X;

  @Override
  public Optional<T> findAny() throws X;

  public static <T> UnionStream<T, UnionThrowable> of(Stream<T> stream) {
    return UnionBridge.of(ThrowingStream.of(stream, UnionThrowable.class));
  }

  /**
   * This method will try to guess {@code Class<X>} using the constructor.
   *
   * @param stream
   *          the stream to delegate to
   * @param ctor
   *          the constructor for {@code X}
   * @return a new {@code UnionStream}.
   */
  public static <T, X extends UnionThrowable> UnionStream<T, X> of(Stream<T> stream,
      Function<? super Throwable, X> ctor) {
    @SuppressWarnings("unchecked") Class<X> x = (Class<X>) ctor.apply(new Throwable()).getClass();
    return of(stream, x, ctor);
  }

  public static <T, X extends UnionThrowable> UnionStream<T, X> of(Stream<T> stream, Class<X> x,
      Function<? super Throwable, X> ctor) {
    return UnionBridge.of(ThrowingStream.of(stream, x), x, ctor);
  }
}
