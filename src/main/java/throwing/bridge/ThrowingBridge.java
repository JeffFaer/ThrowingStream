package throwing.bridge;

import java.util.Iterator;
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
        return of(stream, new FunctionBridge<>(x));
    }
    
    static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, FunctionBridge<X> x) {
        return new CheckedStream<>(stream, x);
    }
    
    public static ThrowingIntStream<Nothing> of(IntStream stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, Class<X> x) {
        return of(stream, new FunctionBridge<>(x));
    }
    
    public static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, FunctionBridge<X> x) {
        return new CheckedIntStream<>(stream, x);
    }
    
    public static <T> ThrowingIterator<T, Nothing> of(Iterator<T> itr) {
        return of(itr, Nothing.class);
    }
    
    public static <T, X extends Throwable> ThrowingIterator<T, X> of(Iterator<T> itr, Class<X> x) {
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <T, X extends Throwable> ThrowingIterator<T, X> of(Iterator<T> itr, FunctionBridge<X> x) {
        return new CheckedIterator<>(itr, x);
    }
    
    public static <T> ThrowingSpliterator<T, Nothing> of(Spliterator<T> itr) {
        return of(itr, Nothing.class);
    }
    
    public static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr, Class<X> x) {
        return of(itr, new FunctionBridge<>(x));
    }
    
    static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr, FunctionBridge<X> x) {
        return new CheckedSpliterator<>(itr, x);
    }
}
