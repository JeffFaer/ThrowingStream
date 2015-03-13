package throwing.stream;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.ThrowingComparator;
import throwing.bridge.ThrowingBridge;
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

/**
 * A standard {@link Stream} does not allow you to throw any checked exceptions.
 * This class is a mirror of {@code Stream} except this class allows for checked
 * exceptions. Each method in the exception-free API has its mirror here using
 * interfaces which support exceptions. There are also helper methods such as
 * {@link #rethrow(Class, Function)}.
 * 
 * <br><br>
 * 
 * Each terminal operation may throw an {@code X}
 * 
 * @author jeffrey
 *
 * @param <T>
 *            The type of the stream elements
 * @param <X>
 *            The type of the exception that might be thrown
 */
public interface ThrowingStream<T, X extends Throwable> extends ThrowingBaseStream<T, X, ThrowingStream<T, X>> {
    public ThrowingStream<T, X> filter(ThrowingPredicate<? super T, ? extends X> predicate);

    public <R> ThrowingStream<R, X> map(ThrowingFunction<? super T, ? extends R, ? extends X> mapper);

    public ThrowingIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends X> mapper);

    public ThrowingLongStream<X> mapToLong(ThrowingToLongFunction<? super T, ? extends X> mapper);

    public ThrowingDoubleStream<X> mapToDouble(ThrowingToDoubleFunction<? super T, ? extends X> mapper);

    public <R> ThrowingStream<R, X> flatMap(
            ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends X>, ? extends X> mapper);

    public ThrowingIntStream<X> flatMapToInt(
            ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X> mapper);

    public ThrowingLongStream<X> flatMapToLong(
            ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends X>, ? extends X> mapper);

    public ThrowingDoubleStream<X> flatMapToDouble(
            ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper);

    public ThrowingStream<T, X> distinct();

    public ThrowingStream<T, X> sorted();

    public ThrowingStream<T, X> sorted(ThrowingComparator<? super T, ? extends X> comparator);

    public ThrowingStream<T, X> peek(ThrowingConsumer<? super T, ? extends X> action);

    public ThrowingStream<T, X> limit(long maxSize);

    public ThrowingStream<T, X> skip(long n);

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

    default public <R, A> R collect(Collector<? super T, A, R> collector) throws X {
        return collect(ThrowingBridge.of(collector));
    }

    public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws X;

    public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws X;

    public long count() throws X;

    public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

    public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

    public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

    public Optional<T> findFirst() throws X;

    public Optional<T> findAny() throws X;

    /**
     * Returns a stream which will only throw Y and will rethrow any X as Y as
     * specified by the mapper.
     * 
     * This is an intermediate operation.
     * 
     * @param y
     *            The new exception class
     * @param mapper
     *            A way to convert X exceptions to Ys
     * @return the new stream
     */
    public <Y extends Throwable> ThrowingStream<T, Y> rethrow(Class<Y> y, Function<? super X, ? extends Y> mapper);

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
