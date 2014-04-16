package throwing.stream;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.ThrowingComparator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;

/**
 * A mirror of {@link Stream}. A {@link Stream} normally allows for lazy
 * functions to be applied to is. A {@link ThrowingStream} allows for these
 * functions to throw checked exceptions. Any stateful intermediate operation or
 * terminal operation has the possibility of applying one of these lazy
 * functions, so each stateful intermediate operation and terminal operation
 * declares a thrown exception.
 * 
 * @author jeffrey
 *
 * @param <T> The type of the stream elements
 * @param <X> The type of the exception that might be thrown
 */
public interface ThrowingStream<T, X extends Throwable> extends ThrowingBaseStream<T, X, ThrowingStream<T, X>> {
    public ThrowingStream<T, X> filter(ThrowingPredicate<? super T, ? extends X> predicate);
    
    public <R> ThrowingStream<R, X> map(ThrowingFunction<? super T, ? extends R, ? extends X> mapper);
    
    public IntStream mapToInt(ToIntFunction<? super T> mapper);
    
    public LongStream mapToLong(ToLongFunction<? super T> mapper);
    
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
    
    public <R> ThrowingStream<R, X> flatMap(
            ThrowingFunction<? super T, ? extends Stream<? extends R>, ? extends X> mapper);
    
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
    
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
    
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
    
    public ThrowingStream<T, X> distinct() throws X;
    
    public ThrowingStream<T, X> sorted() throws X;
    
    public ThrowingStream<T, X> sorted(ThrowingComparator<? super T, ? extends X> comparator) throws X;
    
    public ThrowingStream<T, X> peek(ThrowingConsumer<? super T, ? extends X> action);
    
    public ThrowingStream<T, X> limit(long maxSize) throws X;
    
    public ThrowingStream<T, X> skip(long n) throws X;
    
    public void forEach(ThrowingConsumer<? super T, ? extends X> action) throws X;
    
    public void forEachOrdered(ThrowingConsumer<? super T, ? extends X> action) throws X;
    
    public Object[] toArray() throws X;
    
    public <A> A[] toArray(IntFunction<A[]> generator) throws X;
    
    public T reduce(T identity, ThrowingBinaryOperator<T, ? extends X> accumulator) throws X;
    
    public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends X> accumulator) throws X;
    
    public <U> U reduce(U identity, ThrowingBiFunction<U, ? super T, U, ? extends X> accumulator,
            ThrowingBinaryOperator<U, ? extends X> combiner) throws X;
    
    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingBiConsumer<R, ? super T, ? extends X> accumulator, ThrowingBiConsumer<R, R, ? extends X> combiner)
        throws X;
    
    public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends X> collector) throws X;
    
    public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws X;
    
    public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws X;
    
    public long count() throws X;
    
    public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;
    
    public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;
    
    public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;
    
    public Optional<T> findFirst() throws X;
    
    public Optional<T> findAny() throws X;
    
    public static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> bridged, Class<X> x) {
        return new StreamBridge<>(bridged, x);
    }
}
