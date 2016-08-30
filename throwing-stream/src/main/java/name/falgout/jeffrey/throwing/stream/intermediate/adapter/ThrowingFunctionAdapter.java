package name.falgout.jeffrey.throwing.stream.intermediate.adapter;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector.Characteristics;

import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingBiFunction;
import name.falgout.jeffrey.throwing.ThrowingBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingComparator;
import name.falgout.jeffrey.throwing.ThrowingConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingDoublePredicate;
import name.falgout.jeffrey.throwing.ThrowingDoubleToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingIntBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntFunction;
import name.falgout.jeffrey.throwing.ThrowingIntPredicate;
import name.falgout.jeffrey.throwing.ThrowingIntToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingIntToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingIntUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingLongFunction;
import name.falgout.jeffrey.throwing.ThrowingLongPredicate;
import name.falgout.jeffrey.throwing.ThrowingLongToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingLongToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingLongUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingObjDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingObjIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingObjLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingPredicate;
import name.falgout.jeffrey.throwing.ThrowingRunnable;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.ThrowingToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingToLongFunction;
import name.falgout.jeffrey.throwing.stream.ThrowingCollector;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

public interface ThrowingFunctionAdapter<X extends Throwable, Y extends Throwable> {
  public static <X extends Throwable, Y extends Throwable> ThrowingFunctionAdapter<X, Y> rethrow(
      Class<X> x, Class<Y> y, Function<? super Y, X> ctor) {
    return new RethrowingFunctionAdapter<>(x, y, ctor);
  }

  public ThrowingRunnable<X> convert(ThrowingRunnable<? extends Y> runnable);

  public <T> ThrowingConsumer<T, X> convert(ThrowingConsumer<? super T, ? extends Y> consumer);

  public <T> ThrowingPredicate<T, X> convert(ThrowingPredicate<? super T, ? extends Y> predicate);

  public <T> ThrowingBinaryOperator<T, X> convert(ThrowingBinaryOperator<T, ? extends Y> operator);

  public <T, U, R> ThrowingBiFunction<T, U, R, X> convert(
      ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends Y> function);

  public <R> ThrowingSupplier<R, X> convert(ThrowingSupplier<? extends R, ? extends Y> supplier);

  public <T, U> ThrowingBiConsumer<T, U, X> convert(
      ThrowingBiConsumer<? super T, ? super U, ? extends Y> consumer);

  public <T> ThrowingComparator<T, X> convert(ThrowingComparator<? super T, ? extends Y> comparator);

  public <T, R> ThrowingFunction<T, R, X> convert(
      ThrowingFunction<? super T, ? extends R, ? extends Y> function);

  public <T> ThrowingStream<? extends T, X> convert(ThrowingStream<? extends T, ? extends Y> stream);

  // int

  public ThrowingIntConsumer<X> convert(ThrowingIntConsumer<? extends Y> consumer);

  public ThrowingIntPredicate<X> convert(ThrowingIntPredicate<? extends Y> predicate);

  public ThrowingIntBinaryOperator<X> convert(ThrowingIntBinaryOperator<? extends Y> operator);

  public <T> ThrowingObjIntConsumer<T, X> convert(ThrowingObjIntConsumer<T, ? extends Y> consumer);

  public ThrowingIntUnaryOperator<X> convert(ThrowingIntUnaryOperator<? extends Y> operator);

  public <R> ThrowingIntFunction<R, X> convert(ThrowingIntFunction<R, ? extends Y> function);

  public <T> ThrowingToIntFunction<T, X> convert(ThrowingToIntFunction<T, ? extends Y> function);

  public ThrowingIntToLongFunction<X> convert(ThrowingIntToLongFunction<? extends Y> function);

  public ThrowingIntToDoubleFunction<X> convert(ThrowingIntToDoubleFunction<? extends Y> function);

  public <T> ThrowingIntStream<X> convert(ThrowingIntStream<? extends Y> stream);

  // long

  public ThrowingLongConsumer<X> convert(ThrowingLongConsumer<? extends Y> consumer);

  public ThrowingLongPredicate<X> convert(ThrowingLongPredicate<? extends Y> predicate);

  public ThrowingLongBinaryOperator<X> convert(ThrowingLongBinaryOperator<? extends Y> operator);

  public <T> ThrowingObjLongConsumer<T, X> convert(ThrowingObjLongConsumer<T, ? extends Y> consumer);

  public ThrowingLongUnaryOperator<X> convert(ThrowingLongUnaryOperator<? extends Y> operator);

  public <R> ThrowingLongFunction<R, X> convert(ThrowingLongFunction<R, ? extends Y> function);

  public <T> ThrowingToLongFunction<T, X> convert(ThrowingToLongFunction<T, ? extends Y> function);

  public ThrowingLongToIntFunction<X> convert(ThrowingLongToIntFunction<? extends Y> function);

  public ThrowingLongToDoubleFunction<X> convert(ThrowingLongToDoubleFunction<? extends Y> function);

  public <T> ThrowingLongStream<X> convert(ThrowingLongStream<? extends Y> stream);

  // Double

  public ThrowingDoubleConsumer<X> convert(ThrowingDoubleConsumer<? extends Y> consumer);

  public ThrowingDoublePredicate<X> convert(ThrowingDoublePredicate<? extends Y> predicate);

  public ThrowingDoubleBinaryOperator<X> convert(ThrowingDoubleBinaryOperator<? extends Y> operator);

  public <T> ThrowingObjDoubleConsumer<T, X> convert(
      ThrowingObjDoubleConsumer<T, ? extends Y> consumer);

  public ThrowingDoubleUnaryOperator<X> convert(ThrowingDoubleUnaryOperator<? extends Y> operator);

  public <R> ThrowingDoubleFunction<R, X> convert(ThrowingDoubleFunction<R, ? extends Y> function);

  public <T> ThrowingToDoubleFunction<T, X> convert(
      ThrowingToDoubleFunction<T, ? extends Y> function);

  public ThrowingDoubleToIntFunction<X> convert(ThrowingDoubleToIntFunction<? extends Y> function);

  public ThrowingDoubleToLongFunction<X> convert(ThrowingDoubleToLongFunction<? extends Y> function);

  public <T> ThrowingDoubleStream<X> convert(ThrowingDoubleStream<? extends Y> stream);

  default public <T, A, R> ThrowingCollector<T, A, R, X> convert(
      ThrowingCollector<T, A, R, ? extends Y> collector) {
    return new ThrowingCollector<T, A, R, X>() {
      @Override
      public ThrowingSupplier<A, X> supplier() {
        return convert(collector.supplier());
      }

      @Override
      public ThrowingBiConsumer<A, T, X> accumulator() {
        return convert(collector.accumulator());
      }

      @Override
      public ThrowingBinaryOperator<A, X> combiner() {
        return convert(collector.combiner());
      }

      @Override
      public ThrowingFunction<A, R, X> finisher() {
        return convert(collector.finisher());
      }

      @Override
      public Set<Characteristics> characteristics() {
        return collector.characteristics();
      }
    };
  }
}
