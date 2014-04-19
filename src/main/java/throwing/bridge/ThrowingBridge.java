package throwing.bridge;

import java.util.Iterator;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.Nothing;
import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

public final class ThrowingBridge {
    private ThrowingBridge() {}
    
    // checked
    
    // streams
    
    public static <T> ThrowingStream<T, Nothing> of(Stream<T> stream) {
        return of(stream, Nothing.class);
    }
    
    public static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionBridge<>(x));
    }
    
    static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, FunctionBridge<X> x) {
        Objects.requireNonNull(stream, "stream");
        return new CheckedStream<>(stream, x);
    }
    
    // int stream
    
    public static ThrowingIntStream<Nothing> of(IntStream stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, FunctionBridge<X> x) {
        Objects.requireNonNull(stream, "stream");
        return new CheckedIntStream<>(stream, x);
    }
    
    // long stream
    
    public static ThrowingLongStream<Nothing> of(LongStream stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingLongStream<X> of(LongStream stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingLongStream<X> of(LongStream stream, FunctionBridge<X> x) {
        Objects.requireNonNull(stream, "stream");
        return new CheckedLongStream<>(stream, x);
    }
    
    // double stream
    
    public static ThrowingDoubleStream<Nothing> of(DoubleStream stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingDoubleStream<X> of(DoubleStream stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingDoubleStream<X> of(DoubleStream stream, FunctionBridge<X> x) {
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
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <T, X extends Throwable> ThrowingIterator<T, X> of(Iterator<T> itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator<>(itr, x);
    }
    
    // int
    
    public static <T> ThrowingIterator.OfInt<Nothing> of(PrimitiveIterator.OfInt itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIterator.OfInt<X> of(PrimitiveIterator.OfInt itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingIterator.OfInt<X> of(PrimitiveIterator.OfInt itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator.OfInt<>(itr, x);
    }
    
    // long
    
    public static <T> ThrowingIterator.OfLong<Nothing> of(PrimitiveIterator.OfLong itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIterator.OfLong<X> of(PrimitiveIterator.OfLong itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingIterator.OfLong<X> of(PrimitiveIterator.OfLong itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator.OfLong<>(itr, x);
    }
    
    // double
    
    public static <T> ThrowingIterator.OfDouble<Nothing> of(PrimitiveIterator.OfDouble itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIterator.OfDouble<X> of(PrimitiveIterator.OfDouble itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingIterator.OfDouble<X> of(PrimitiveIterator.OfDouble itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator.OfDouble<>(itr, x);
    }
    
    // spliterator
    
    public static <T> ThrowingSpliterator<T, Nothing> of(Spliterator<T> itr) {
        return of(itr, Nothing.class);
    }
    
    public static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator<>(itr, x);
    }
    
    // int
    
    public static ThrowingSpliterator.OfInt<Nothing> of(Spliterator.OfInt itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingSpliterator.OfInt<X> of(Spliterator.OfInt itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingSpliterator.OfInt<X> of(Spliterator.OfInt itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator.OfInt<>(itr, x);
    }
    
    // long
    
    public static ThrowingSpliterator.OfLong<Nothing> of(Spliterator.OfLong itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingSpliterator.OfLong<X> of(Spliterator.OfLong itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingSpliterator.OfLong<X> of(Spliterator.OfLong itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator.OfLong<>(itr, x);
    }
    
    // double
    
    public static ThrowingSpliterator.OfDouble<Nothing> of(Spliterator.OfDouble itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingSpliterator.OfDouble<X> of(Spliterator.OfDouble itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <X extends Throwable> ThrowingSpliterator.OfDouble<X> of(Spliterator.OfDouble itr, FunctionBridge<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator.OfDouble<>(itr, x);
    }
    
    // unchecked
    
    // streams
    
    public static <T> Stream<T> of(ThrowingStream<T, Nothing> stream) {
        return of(stream, Nothing.class);
    }
    
    static <T, X extends Throwable> Stream<T> of(ThrowingStream<T, X> stream, Class<X> x) {
        Objects.requireNonNull(stream, "stream");
        Objects.requireNonNull(x, "x");
        return new UncheckedStream<>(stream, x);
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
    
    public static <T> Spliterator<T> of(ThrowingSpliterator<T, Nothing> itr) {
        return unchecked(itr);
    }
    
    static <T> Spliterator<T> unchecked(ThrowingSpliterator<T, ?> itr) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedSpliterator<>(itr);
    }
    
    public static <T> Iterator<T> of(ThrowingIterator<T, Nothing> itr) {
        return unchecked(itr);
    }
    
    static <T> Iterator<T> unchecked(ThrowingIterator<T, ?> itr) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedIterator<>(itr);
    }
}
