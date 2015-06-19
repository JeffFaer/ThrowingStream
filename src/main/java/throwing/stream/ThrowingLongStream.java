package throwing.stream;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.Function;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingLongBinaryOperator;
import throwing.function.ThrowingLongConsumer;
import throwing.function.ThrowingLongFunction;
import throwing.function.ThrowingLongPredicate;
import throwing.function.ThrowingLongToDoubleFunction;
import throwing.function.ThrowingLongToIntFunction;
import throwing.function.ThrowingLongUnaryOperator;
import throwing.function.ThrowingObjLongConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingLongStream<X extends Throwable> extends
        ThrowingBaseStream<Long, X, ThrowingLongStream<X>> {
    @Override
    public ThrowingIterator.OfLong<X> iterator();

    @Override
    public ThrowingSpliterator.OfLong<X> spliterator();

    public ThrowingLongStream<X> filter(ThrowingLongPredicate<? extends X> predicate);

    public ThrowingLongStream<X> map(ThrowingLongUnaryOperator<? extends X> mapper);

    public <U> ThrowingStream<U, X> mapToObj(ThrowingLongFunction<? extends U, ? extends X> mapper);

    public ThrowingIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends X> mapper);

    public ThrowingDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends X> mapper);

    public ThrowingLongStream<X> flatMap(
            ThrowingLongFunction<? extends ThrowingLongStream<? extends X>, ? extends X> mapper);

    public ThrowingLongStream<X> distinct();

    public ThrowingLongStream<X> sorted();

    public ThrowingLongStream<X> peek(ThrowingLongConsumer<? extends X> action);

    public ThrowingLongStream<X> limit(long maxSize);

    public ThrowingLongStream<X> skip(long n);

    public void forEach(ThrowingLongConsumer<? extends X> action) throws X;

    public void forEachOrdered(ThrowingLongConsumer<? extends X> action) throws X;

    public long[] toArray() throws X;

    public long reduce(long identity, ThrowingLongBinaryOperator<? extends X> op) throws X;

    public OptionalLong reduce(ThrowingLongBinaryOperator<? extends X> op) throws X;

    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingObjLongConsumer<R, ? extends X> accumulator,
            ThrowingBiConsumer<R, R, ? extends X> combiner) throws X;

    public long sum() throws X;

    public OptionalLong min() throws X;

    public OptionalLong max() throws X;

    public long count() throws X;

    public OptionalDouble average() throws X;

    public LongSummaryStatistics summaryStatistics() throws X;

    public boolean anyMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

    public boolean allMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

    public boolean noneMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

    public OptionalLong findFirst() throws X;

    public OptionalLong findAny() throws X;

    public ThrowingDoubleStream<X> asDoubleStream();

    public ThrowingStream<Long, X> boxed();

    /**
     * Returns a stream which will only throw Y and will rethrow any X as Y as specified by the
     * mapper.
     * 
     * This is an intermediate operation.
     * 
     * @param y
     *            The new exception class
     * @param mapper
     *            A way to convert X exceptions to Ys
     * @return the new stream
     */
    public <Y extends Throwable> ThrowingLongStream<Y> rethrow(Class<Y> y,
            Function<? super X, ? extends Y> mapper);
}
