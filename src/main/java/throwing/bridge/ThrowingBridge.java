package throwing.bridge;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import throwing.Nothing;
import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingStream;

public final class ThrowingBridge {
    private ThrowingBridge() {}
    
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
    
    public static <T> Stream<T> of(ThrowingStream<T, Nothing> stream) {
        return of(stream, Nothing.class);
    }
    
    public static <T, X extends Throwable> Stream<T> of(ThrowingStream<T, X> stream, Class<X> x) {
        Objects.requireNonNull(stream, "stream");
        Objects.requireNonNull(x, "x");
        return new UncheckedStream<>(stream, x);
    }
    
    public static IntStream of(ThrowingIntStream<Nothing> stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> IntStream of(ThrowingIntStream<X> stream, Class<X> x) {
        Objects.requireNonNull(stream, "stream");
        Objects.requireNonNull(x, "x");
        return new UncheckedIntStream<>(stream, x);
    }
    
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
