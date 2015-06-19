package throwing.stream;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.Function;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingDoubleStream<X extends Throwable> extends
        ThrowingBaseStream<Double, X, ThrowingDoubleStream<X>> {
    @Override
    public ThrowingIterator.OfDouble<X> iterator();

    @Override
    public ThrowingSpliterator.OfDouble<X> spliterator();

    public ThrowingDoubleStream<X> filter(ThrowingDoublePredicate<? extends X> predicate);

    public ThrowingDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends X> mapper);

    public <U> ThrowingStream<U, X> mapToObj(ThrowingDoubleFunction<? extends U, ? extends X> mapper);

    public ThrowingIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends X> mapper);

    public ThrowingLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends X> mapper);

    public ThrowingDoubleStream<X> flatMap(
            ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper);

    public ThrowingDoubleStream<X> distinct();

    public ThrowingDoubleStream<X> sorted();

    public ThrowingDoubleStream<X> peek(ThrowingDoubleConsumer<? extends X> action);

    public ThrowingDoubleStream<X> limit(long maxSize);

    public ThrowingDoubleStream<X> skip(long n);

    public void forEach(ThrowingDoubleConsumer<? extends X> action) throws X;

    public void forEachOrdered(ThrowingDoubleConsumer<? extends X> action) throws X;

    public double[] toArray() throws X;

    public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends X> op) throws X;

    public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends X> op) throws X;

    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingObjDoubleConsumer<R, ? extends X> accumulator,
            ThrowingBiConsumer<R, R, ? extends X> combiner) throws X;

    public double sum() throws X;

    public OptionalDouble min() throws X;

    public OptionalDouble max() throws X;

    public long count() throws X;

    public OptionalDouble average() throws X;

    public DoubleSummaryStatistics summaryStatistics() throws X;

    public boolean anyMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

    public boolean allMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

    public boolean noneMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

    public OptionalDouble findFirst() throws X;

    public OptionalDouble findAny() throws X;

    public ThrowingStream<Double, X> boxed();

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
    public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> y,
            Function<? super X, ? extends Y> mapper);

}
