package throwing.stream;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingIntBinaryOperator;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingIntToDoubleFunction;
import throwing.function.ThrowingIntToLongFunction;
import throwing.function.ThrowingIntUnaryOperator;
import throwing.function.ThrowingObjIntConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingIntStream<X extends Throwable> extends ThrowingBaseStream<Integer, X, ThrowingIntStream<X>> {
    @Override
    public ThrowingIterator.OfInt<X> iterator();

    @Override
    public ThrowingSpliterator.OfInt<X> spliterator();

    public ThrowingIntStream<X> filter(ThrowingIntPredicate<? extends X> predicate);

    public ThrowingIntStream<X> map(ThrowingIntUnaryOperator<? extends X> mapper);

    public <U> ThrowingStream<U, X> mapToObj(ThrowingIntFunction<? extends U, ? extends X> mapper);

    public ThrowingLongStream<X> mapToLong(ThrowingIntToLongFunction<? extends X> mapper);

    public ThrowingDoubleStream<X> mapToDouble(ThrowingIntToDoubleFunction<? extends X> mapper);

    public ThrowingIntStream<X> flatMap(
            ThrowingIntFunction<? extends ThrowingIntStream<? extends X>, ? extends X> mapper);

    public ThrowingIntStream<X> distinct();

    public ThrowingIntStream<X> sorted();

    public ThrowingIntStream<X> peek(ThrowingIntConsumer<? extends X> action);

    public ThrowingIntStream<X> limit(long maxSize);

    public ThrowingIntStream<X> skip(long n);

    public void forEach(ThrowingIntConsumer<? extends X> action) throws X;

    public void forEachOrdered(ThrowingIntConsumer<? extends X> action) throws X;

    public int[] toArray() throws X;

    public int reduce(int identity, ThrowingIntBinaryOperator<? extends X> op) throws X;

    public OptionalInt reduce(ThrowingIntBinaryOperator<? extends X> op) throws X;

    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier, ThrowingObjIntConsumer<R, ? extends X> accumulator,
            ThrowingBiConsumer<R, R, ? extends X> combiner) throws X;

    public int sum() throws X;

    public OptionalInt min() throws X;

    public OptionalInt max() throws X;

    public long count() throws X;

    public OptionalDouble average() throws X;

    public IntSummaryStatistics summaryStatistics() throws X;

    public boolean anyMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

    public boolean allMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

    public boolean noneMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

    public OptionalInt findFirst() throws X;

    public OptionalInt findAny() throws X;

    public ThrowingLongStream<X> asLongStream();

    public ThrowingDoubleStream<X> asDoubleStream();

    public ThrowingStream<Integer, X> boxed();

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
    public <Y extends Throwable> ThrowingIntStream<Y> rethrow(Class<Y> y, Function<? super X, ? extends Y> mapper);
}
