package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.Iterator;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import name.falgout.jeffrey.throwing.Nothing;
import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator.ThrowingSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;
import name.falgout.jeffrey.throwing.stream.ThrowingCollector;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

public final class ThrowingBridge {
  private ThrowingBridge() {}

  // checked

  // streams

  public static <T> ThrowingStream<? extends T, Nothing> of(Stream<? extends T> stream) {
    return of(stream, Nothing.class);
  }

  public static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(stream, new ExceptionMasker<>(x));
  }

  public static <T, X extends Throwable> ThrowingStream<T, X>
      stream(ThrowingSpliterator<T, X> spliterator, Class<X> x) {
    return of(StreamSupport.stream(of(spliterator, x), false), x);
  }

  static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, ExceptionMasker<X> x) {
    Objects.requireNonNull(stream, "stream");
    return new CheckedStream<>(stream, x);
  }

  // int stream

  public static ThrowingIntStream<Nothing> of(IntStream stream) {
    return of(stream, Nothing.class);
  }

  public static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(stream, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, ExceptionMasker<X> x) {
    Objects.requireNonNull(stream, "stream");
    return new CheckedIntStream<>(stream, x);
  }

  // long stream

  public static ThrowingLongStream<Nothing> of(LongStream stream) {
    return of(stream, Nothing.class);
  }

  public static <X extends Throwable> ThrowingLongStream<X> of(LongStream stream, Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(stream, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingLongStream<X> of(LongStream stream, ExceptionMasker<X> x) {
    Objects.requireNonNull(stream, "stream");
    return new CheckedLongStream<>(stream, x);
  }

  // double stream

  public static ThrowingDoubleStream<Nothing> of(DoubleStream stream) {
    return of(stream, Nothing.class);
  }

  public static <X extends Throwable> ThrowingDoubleStream<X> of(DoubleStream stream, Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(stream, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingDoubleStream<X> of(DoubleStream stream,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(stream, "stream");
    return new CheckedDoubleStream<>(stream, x);
  }

  // utilities

  // iterator

  public static <T> ThrowingIterator<T, Nothing> of(Iterator<T> itr) {
    return of(itr, Nothing.class);
  }

  public static <T, X extends Throwable> ThrowingIterator<T, X> of(Iterator<T> itr, Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <T, X extends Throwable> ThrowingIterator<T, X> of(Iterator<T> itr, ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedIterator<>(itr, x);
  }

  // int

  public static <T> ThrowingIterator.OfInt<Nothing> of(PrimitiveIterator.OfInt itr) {
    return of(itr, Nothing.class);
  }

  public static <X extends Throwable> ThrowingIterator.OfInt<X> of(PrimitiveIterator.OfInt itr,
      Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingIterator.OfInt<X> of(PrimitiveIterator.OfInt itr,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedIterator.OfInt<>(itr, x);
  }

  // long

  public static <T> ThrowingIterator.OfLong<Nothing> of(PrimitiveIterator.OfLong itr) {
    return of(itr, Nothing.class);
  }

  public static <X extends Throwable> ThrowingIterator.OfLong<X> of(PrimitiveIterator.OfLong itr,
      Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingIterator.OfLong<X> of(PrimitiveIterator.OfLong itr,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedIterator.OfLong<>(itr, x);
  }

  // double

  public static <T> ThrowingIterator.OfDouble<Nothing> of(PrimitiveIterator.OfDouble itr) {
    return of(itr, Nothing.class);
  }

  public static <X extends Throwable> ThrowingIterator.OfDouble<X>
      of(PrimitiveIterator.OfDouble itr, Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingIterator.OfDouble<X> of(PrimitiveIterator.OfDouble itr,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedIterator.OfDouble<>(itr, x);
  }

  // spliterator

  public static <T> ThrowingSpliterator<T, Nothing> of(Spliterator<T> itr) {
    return of(itr, Nothing.class);
  }

  public static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr,
      Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedSpliterator.Basic<>(itr, x);
  }

  // int

  public static ThrowingBaseSpliterator.OfInt<Nothing> of(Spliterator.OfInt itr) {
    return of(itr, Nothing.class);
  }

  public static <X extends Throwable> ThrowingBaseSpliterator.OfInt<X> of(Spliterator.OfInt itr,
      Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingBaseSpliterator.OfInt<X> of(Spliterator.OfInt itr,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedSpliterator.OfInt<>(itr, x);
  }

  // long

  public static ThrowingBaseSpliterator.OfLong<Nothing> of(Spliterator.OfLong itr) {
    return of(itr, Nothing.class);
  }

  public static <X extends Throwable> ThrowingBaseSpliterator.OfLong<X> of(Spliterator.OfLong itr,
      Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingBaseSpliterator.OfLong<X> of(Spliterator.OfLong itr,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedSpliterator.OfLong<>(itr, x);
  }

  // double

  public static ThrowingBaseSpliterator.OfDouble<Nothing> of(Spliterator.OfDouble itr) {
    return of(itr, Nothing.class);
  }

  public static <X extends Throwable> ThrowingBaseSpliterator.OfDouble<X>
      of(Spliterator.OfDouble itr, Class<X> x) {
    Objects.requireNonNull(x, "x");
    return of(itr, new ExceptionMasker<>(x));
  }

  static <X extends Throwable> ThrowingBaseSpliterator.OfDouble<X> of(Spliterator.OfDouble itr,
      ExceptionMasker<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new CheckedSpliterator.OfDouble<>(itr, x);
  }

  public static <T, A, R, X extends Throwable> ThrowingCollector<T, A, R, X>
      of(Collector<T, A, R> collector) {
    Objects.requireNonNull(collector);
    return new ThrowingCollector<T, A, R, X>() {
      @Override
      public ThrowingSupplier<A, X> supplier() {
        return collector.supplier()::get;
      }

      @Override
      public ThrowingBiConsumer<A, T, X> accumulator() {
        return collector.accumulator()::accept;
      }

      @Override
      public ThrowingBinaryOperator<A, X> combiner() {
        return collector.combiner()::apply;
      }

      @Override
      public ThrowingFunction<A, R, X> finisher() {
        return collector.finisher()::apply;
      }

      @Override
      public Set<Characteristics> characteristics() {
        return collector.characteristics();
      }
    };
  }

  // unchecked

  // streams

  public static <T> Stream<T> of(ThrowingStream<T, Nothing> stream) {
    return of(stream, Nothing.class);
  }

  static <T, X extends Throwable> Stream<T> of(ThrowingStream<T, X> stream, Class<X> x) {
    Objects.requireNonNull(stream, "stream");
    Objects.requireNonNull(x, "x");
    return stream instanceof CheckedStream ? ((CheckedStream<T, X>) stream).getDelegate()
        : new UncheckedStream<>(stream, x);
  }

  // int stream

  public static IntStream of(ThrowingIntStream<Nothing> stream) {
    return of(stream, Nothing.class);
  }

  static <X extends Throwable> IntStream of(ThrowingIntStream<X> stream, Class<X> x) {
    Objects.requireNonNull(stream, "stream");
    Objects.requireNonNull(x, "x");
    return new UncheckedIntStream<>(stream, x);
  }

  // long stream

  public static LongStream of(ThrowingLongStream<Nothing> stream) {
    return of(stream, Nothing.class);
  }

  static <X extends Throwable> LongStream of(ThrowingLongStream<X> stream, Class<X> x) {
    Objects.requireNonNull(stream, "stream");
    Objects.requireNonNull(x, "x");
    return new UncheckedLongStream<>(stream, x);
  }

  // double stream

  public static DoubleStream of(ThrowingDoubleStream<Nothing> stream) {
    return of(stream, Nothing.class);
  }

  static <X extends Throwable> DoubleStream of(ThrowingDoubleStream<X> stream, Class<X> x) {
    Objects.requireNonNull(stream, "stream");
    Objects.requireNonNull(x, "x");
    return new UncheckedDoubleStream<>(stream, x);
  }

  // utilities

  // spliterator

  public static <T> Spliterator<T> of(ThrowingSpliterator<T, Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <T, X extends Throwable> Spliterator<T> of(ThrowingSpliterator<T, X> itr, Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedSpliterator.Basic<>(itr, x);
  }

  // int

  public static <T> Spliterator.OfInt of(ThrowingBaseSpliterator.OfInt<Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <T, X extends Throwable> Spliterator.OfInt of(ThrowingBaseSpliterator.OfInt<X> itr,
      Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedSpliterator.OfInt<>(itr, x);
  }

  // long

  public static <T> Spliterator.OfLong of(ThrowingBaseSpliterator.OfLong<Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <T, X extends Throwable> Spliterator.OfLong of(ThrowingBaseSpliterator.OfLong<X> itr,
      Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedSpliterator.OfLong<>(itr, x);
  }

  // double

  public static <T> Spliterator.OfDouble of(ThrowingBaseSpliterator.OfDouble<Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <T, X extends Throwable> Spliterator.OfDouble of(ThrowingBaseSpliterator.OfDouble<X> itr,
      Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedSpliterator.OfDouble<>(itr, x);
  }

  // iterator

  public static <T> Iterator<T> of(ThrowingIterator<T, Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <T, X extends Throwable> Iterator<T> of(ThrowingIterator<T, X> itr, Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedIterator<>(itr, x);
  }

  // int

  public static PrimitiveIterator.OfInt of(ThrowingIterator.OfInt<Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <X extends Throwable> PrimitiveIterator.OfInt of(ThrowingIterator.OfInt<X> itr,
      Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedIterator.OfInt<>(itr, x);
  }

  // long

  public static PrimitiveIterator.OfLong of(ThrowingIterator.OfLong<Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <X extends Throwable> PrimitiveIterator.OfLong of(ThrowingIterator.OfLong<X> itr,
      Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedIterator.OfLong<>(itr, x);
  }

  // double

  public static PrimitiveIterator.OfDouble of(ThrowingIterator.OfDouble<Nothing> itr) {
    return of(itr, Nothing.class);
  }

  static <X extends Throwable> PrimitiveIterator.OfDouble of(ThrowingIterator.OfDouble<X> itr,
      Class<X> x) {
    Objects.requireNonNull(itr, "itr");
    return new UncheckedIterator.OfDouble<>(itr, x);
  }
}
