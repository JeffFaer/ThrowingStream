package name.falgout.jeffrey.throwing.stream.intermediate;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import name.falgout.jeffrey.throwing.ThrowingComparator;
import name.falgout.jeffrey.throwing.ThrowingConsumer;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingPredicate;
import name.falgout.jeffrey.throwing.ThrowingToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingToLongFunction;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

/**
 * Contains all of the intermediate stream operations for a {@link ThrowingStream}.
 *
 * @param <T>
 *          the type of the stream elements
 * @param <X>
 *          the type of exception this stream accepts
 * @param <S>
 *          the stream type to chain with
 * @param <I>
 *          the IntStream type to chain with
 * @param <L>
 *          the LongStream type to chain with
 * @param <D>
 *          the DoubleStreamType to chain with
 */
public interface ThrowingStreamIntermediate<T, X extends Throwable, S extends ThrowingStreamIntermediate<T, X, S, I, L, D>, I extends ThrowingIntStreamIntermediate<X, I, L, D>, L extends ThrowingLongStreamIntermediate<X, I, L, D>, D extends ThrowingDoubleStreamIntermediate<X, I, L, D>> extends
    ThrowingBaseStreamIntermediate<S> {
  default public S normalFilter(Predicate<? super T> predicate) {
    return filter(predicate::test);
  }

  public S filter(ThrowingPredicate<? super T, ? extends X> predicate);

  default public <R> ThrowingStreamIntermediate<R, X, ?, I, L, D> normalMap(
      Function<? super T, ? extends R> mapper) {
    return this.<R> map(mapper::apply);
  }

  public <R> ThrowingStreamIntermediate<R, X, ?, I, L, D> map(
      ThrowingFunction<? super T, ? extends R, ? extends X> mapper);

  default public I normalMapToInt(ToIntFunction<? super T> mapper) {
    return mapToInt(mapper::applyAsInt);
  }

  public I mapToInt(ThrowingToIntFunction<? super T, ? extends X> mapper);

  default public L normalMapToLong(ToLongFunction<? super T> mapper) {
    return mapToLong(mapper::applyAsLong);
  }

  public L mapToLong(ThrowingToLongFunction<? super T, ? extends X> mapper);

  default public D normalMapToDouble(ToDoubleFunction<? super T> mapper) {
    return mapToDouble(mapper::applyAsDouble);
  }

  public D mapToDouble(ThrowingToDoubleFunction<? super T, ? extends X> mapper);

  default public <R> ThrowingStreamIntermediate<R, X, ?, I, L, D> normalFlatMap(
      Function<? super T, ? extends ThrowingStream<? extends R, ? extends X>> mapper) {
    return this.<R> flatMap(mapper::apply);
  }

  public <R> ThrowingStreamIntermediate<R, X, ?, I, L, D> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends X>, ? extends X> mapper);

  default public I normalFlatMapToInt(
      Function<? super T, ? extends ThrowingIntStream<? extends X>> mapper) {
    return flatMapToInt((ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X>) mapper::apply);
  }

  public I flatMapToInt(
      ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X> mapper);

  default public L normalFlatMapToLong(
      Function<? super T, ? extends ThrowingLongStream<? extends X>> mapper) {
    return flatMapToLong(mapper::apply);
  }

  public L flatMapToLong(
      ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends X>, ? extends X> mapper);

  default public D normalFlatMapToDouble(
      Function<? super T, ? extends ThrowingDoubleStream<? extends X>> mapper) {
    return flatMapToDouble((ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X>) mapper::apply);
  }

  public D flatMapToDouble(
      ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper);

  public S distinct();

  public S sorted();

  default public S normalSorted(Comparator<? super T> comparator) {
    return sorted(comparator::compare);
  }

  public S sorted(ThrowingComparator<? super T, ? extends X> comparator);

  default public S normalPeek(Consumer<? super T> action) {
    return peek(action::accept);
  }

  public S peek(ThrowingConsumer<? super T, ? extends X> action);

  public S limit(long maxSize);

  public S skip(long n);
}
