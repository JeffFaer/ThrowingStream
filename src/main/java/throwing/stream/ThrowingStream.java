package throwing.stream;

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
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.ThrowingComparator;
import throwing.ThrowingSpliterator;
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
import throwing.stream.adapter.ThrowingBridge;

/**
 * <p>
 * A standard {@link Stream} does not allow you to throw any checked exceptions. This class is a
 * mirror of {@code Stream} except this class allows for checked exceptions. Each method in the
 * exception-free API has its mirror here using interfaces which support exceptions. There are also
 * helper methods such as {@link #rethrow(Class, Function)}.
 *
 * <p>
 * Each terminal operation may throw an {@code X}
 *
 * @author jeffrey
 *
 * @param <T>
 *          the type of the stream elements
 * @param <X>
 *          the type of the exception that might be thrown
 */
public interface ThrowingStream<T, X extends Throwable> extends
    ThrowingBaseStream<T, X, ThrowingStream<T, X>> {
  default ThrowingStream<T, X> normalFilter(Predicate<? super T> predicate) {
    return filter(predicate::test);
  }

  public ThrowingStream<T, X> filter(ThrowingPredicate<? super T, ? extends X> predicate);

  default <R> ThrowingStream<R, X> normalMap(Function<? super T, ? extends R> mapper) {
    return map(mapper::apply);
  }

  public <R> ThrowingStream<R, X> map(ThrowingFunction<? super T, ? extends R, ? extends X> mapper);

  default public ThrowingIntStream<X> normalMapToInt(ToIntFunction<? super T> mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  public ThrowingIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends X> mapper);

  default public ThrowingLongStream<X> normalMapToLong(ToLongFunction<? super T> mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  public ThrowingLongStream<X> mapToLong(ThrowingToLongFunction<? super T, ? extends X> mapper);

  default public ThrowingDoubleStream<X> normalMapToDouble(ToDoubleFunction<? super T> mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  public ThrowingDoubleStream<X> mapToDouble(ThrowingToDoubleFunction<? super T, ? extends X> mapper);

  default public <R> ThrowingStream<R, X> normalFlatMap(
      Function<? super T, ? extends ThrowingStream<? extends R, ? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  public <R> ThrowingStream<R, X> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends X>, ? extends X> mapper);

  default public ThrowingIntStream<X> normalFlatMapToInt(
      Function<? super T, ? extends ThrowingIntStream<? extends X>> mapper) {
    return flatMapToInt((ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X>) mapper::apply);
  }

  public ThrowingIntStream<X> flatMapToInt(
      ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X> mapper);

  default public ThrowingLongStream<X> normalFlatMapToLong(
      Function<? super T, ? extends ThrowingLongStream<? extends X>> mapper) {
    return flatMapToLong(mapper::apply);
  }

  public ThrowingLongStream<X> flatMapToLong(
      ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends X>, ? extends X> mapper);

  default public ThrowingDoubleStream<X> normalFlatMapToDouble(
      Function<? super T, ? extends ThrowingDoubleStream<? extends X>> mapper) {
    return flatMapToDouble((ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X>) mapper::apply);
  }

  public ThrowingDoubleStream<X> flatMapToDouble(
      ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper);

  public ThrowingStream<T, X> distinct();

  public ThrowingStream<T, X> sorted();

  default public ThrowingStream<T, X> normalSorted(Comparator<? super T> comparator) {
    return sorted(comparator::compare);
  }

  public ThrowingStream<T, X> sorted(ThrowingComparator<? super T, ? extends X> comparator);

  default public ThrowingStream<T, X> normalPeek(Consumer<? super T> action) {
    return peek(action::accept);
  }

  public ThrowingStream<T, X> peek(ThrowingConsumer<? super T, ? extends X> action);

  public ThrowingStream<T, X> limit(long maxSize);

  public ThrowingStream<T, X> skip(long n);

  default public void normalForEach(Consumer<? super T> action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingConsumer<? super T, ? extends X> action) throws X;

  default public void normalForEachOrdered(Consumer<? super T> action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingConsumer<? super T, ? extends X> action) throws X;

  public Object[] toArray() throws X;

  public <A> A[] toArray(IntFunction<A[]> generator) throws X;

  default public T normalReduce(T identity, BinaryOperator<T> accumulator) throws X {
    return reduce(identity, accumulator::apply);
  }

  public T reduce(T identity, ThrowingBinaryOperator<T, ? extends X> accumulator) throws X;

  default public Optional<T> normalReduce(BinaryOperator<T> accumulator) throws X {
    return reduce(accumulator::apply);
  }

  public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends X> accumulator) throws X;

  default public <U> U normalReduce(U identity, BiFunction<U, ? super T, U> accumulator,
      BinaryOperator<U> combiner) throws X {
    return reduce(identity, accumulator::apply, combiner::apply);
  }

  public <U> U reduce(U identity, ThrowingBiFunction<U, ? super T, U, ? extends X> accumulator,
      ThrowingBinaryOperator<U, ? extends X> combiner) throws X;

  default public <R> R normalCollect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator,
      BiConsumer<R, R> combiner) throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingBiConsumer<R, ? super T, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X;

  default public <R, A> R collect(Collector<? super T, A, R> collector) throws X {
    return collect(ThrowingBridge.of(collector));
  }

  public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends X> collector) throws X;

  default public Optional<T> normalMin(Comparator<? super T> comparator) throws X {
    return min(comparator::compare);
  }

  public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws X;

  default public Optional<T> normalMax(Comparator<? super T> comparator) throws X {
    return max(comparator::compare);
  }

  public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws X;

  public long count() throws X;

  default public boolean normalAnyMatch(Predicate<? super T> predicate) throws X {
    return anyMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

  default public boolean normalAllMatch(Predicate<? super T> predicate) throws X {
    return allMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

  default public boolean normalNoneMatch(Predicate<? super T> predicate) throws X {
    return noneMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

  public Optional<T> findFirst() throws X;

  public Optional<T> findAny() throws X;

  /**
   * Returns a stream which will only throw Y and will rethrow any X as Y as specified by the
   * mapper.
   *
   * This is an intermediate operation.
   *
   * @param y
   *          The new exception class
   * @param mapper
   *          A way to convert X exceptions to Ys
   * @return the new stream
   */
  public <Y extends Throwable> ThrowingStream<T, Y> rethrow(Class<Y> y,
      Function<? super X, ? extends Y> mapper);

  public static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, Class<X> x) {
    return ThrowingBridge.of(stream, x);
  }

  public static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, Class<X> x) {
    return ThrowingBridge.of(stream, x);
  }

  public static <X extends Throwable> ThrowingLongStream<X> of(LongStream stream, Class<X> x) {
    return ThrowingBridge.of(stream, x);
  }

  public static <X extends Throwable> ThrowingDoubleStream<X> of(DoubleStream stream, Class<X> x) {
    return ThrowingBridge.of(stream, x);
  }

  public static <T, X extends Throwable> ThrowingStream<T, X> stream(
      ThrowingSpliterator<T, X> spliterator, boolean parallel, Class<X> x) {
    return ThrowingBridge.stream(spliterator, parallel, x);
  }

  public static <T, X extends Throwable> ThrowingStream<T, X> stream(
      Supplier<ThrowingSpliterator<T, X>> spliterator, int characteristics, boolean parallel,
      Class<X> x) {
    return ThrowingBridge.stream(spliterator, characteristics, parallel, x);
  }
}
