package throwing.stream;

import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.ThrowingBaseSpliterator.ThrowingSpliterator;
import throwing.ThrowingIterator;
import throwing.function.ThrowingFunction;
import throwing.stream.adapter.ThrowingBridge;
import throwing.stream.intermediate.ThrowingStreamIntermediate;
import throwing.stream.terminal.ThrowingStreamTerminal;

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
 * @param <T>
 *          the type of the stream elements
 * @param <X>
 *          the type of the exception that might be thrown
 */
public interface ThrowingStream<T, X extends Throwable> extends
    ThrowingBaseStream<T, X, ThrowingStream<T, X>>,
    ThrowingStreamIntermediate<T, X, ThrowingStream<T, X>, ThrowingIntStream<X>, ThrowingLongStream<X>, ThrowingDoubleStream<X>>,
    ThrowingStreamTerminal<T, X, X> {
  @Override
  public ThrowingIterator<T, X> iterator();

  @Override
  public ThrowingSpliterator<T, X> spliterator();

  @Override
  default public <R> ThrowingStream<R, X> normalMap(Function<? super T, ? extends R> mapper) {
    return map(mapper::apply);
  }

  @Override
  public <R> ThrowingStream<R, X> map(ThrowingFunction<? super T, ? extends R, ? extends X> mapper);

  @Override
  default public <R> ThrowingStream<R, X> normalFlatMap(
      Function<? super T, ? extends ThrowingStream<? extends R, ? extends X>> mapper) {
    return flatMap(mapper::apply);
  }

  @Override
  public <R> ThrowingStream<R, X> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends X>, ? extends X> mapper);

  @Override
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
}
