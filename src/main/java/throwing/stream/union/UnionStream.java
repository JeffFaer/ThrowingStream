package throwing.stream.union;

import java.util.function.Function;
import java.util.stream.Stream;

import throwing.function.ThrowingFunction;
import throwing.stream.ThrowingStream;
import throwing.stream.intermediate.ThrowingStreamIntermediate;
import throwing.stream.terminal.ThrowingStreamTerminal;
import throwing.stream.union.UnionBaseSpliterator.UnionSpliterator;
import throwing.stream.union.adapter.UnionBridge;

public interface UnionStream<T, X extends UnionThrowable> extends
    UnionBaseStream<T, X, UnionStream<T, X>>,
    ThrowingStreamIntermediate<T, Throwable, UnionStream<T, X>, UnionIntStream<X>, UnionLongStream<X>, UnionDoubleStream<X>>,
    ThrowingStreamTerminal<T, Throwable, X> {
  @Override
  public UnionIterator<T, X> iterator();

  @Override
  public UnionSpliterator<T, X> spliterator();

  @Override
  default <R> UnionStream<R, X> normalMap(Function<? super T, ? extends R> mapper) {
    return map(mapper::apply);
  }

  @Override
  public <R> UnionStream<R, X> map(
      ThrowingFunction<? super T, ? extends R, ? extends Throwable> mapper);

  @Override
  default public <R> UnionStream<R, X> normalFlatMap(
      Function<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>> mapper) {
    return flatMap(mapper::apply);
  }

  @Override
  public <R> UnionStream<R, X> flatMap(
      ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable> mapper);

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
