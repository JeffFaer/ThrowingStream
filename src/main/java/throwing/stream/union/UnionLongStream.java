package throwing.stream.union;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;

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
import throwing.stream.ThrowingLongStream;

public interface UnionLongStream<X extends UnionThrowable> extends
        UnionBaseStream<Long, X, UnionLongStream<X>, ThrowingLongStream<Throwable>>,
        ThrowingLongStream<Throwable> {
    @Override
    public UnionIterator.OfLong<X> iterator();

    @Override
    public UnionSpliterator.OfLong<X> spliterator();

    @Override
    public UnionLongStream<X> filter(ThrowingLongPredicate<? extends Throwable> predicate);

    @Override
    public UnionLongStream<X> map(ThrowingLongUnaryOperator<? extends Throwable> mapper);

    @Override
    public <U> UnionStream<U, X> mapToObj(
            ThrowingLongFunction<? extends U, ? extends Throwable> mapper);

    @Override
    public UnionIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends Throwable> mapper);

    @Override
    public UnionDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends Throwable> mapper);

    @Override
    public UnionLongStream<X> flatMap(
            ThrowingLongFunction<? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper);

    @Override
    public UnionLongStream<X> distinct();

    @Override
    public UnionLongStream<X> sorted();

    @Override
    public UnionLongStream<X> peek(ThrowingLongConsumer<? extends Throwable> action);

    @Override
    public UnionLongStream<X> limit(long maxSize);

    @Override
    public UnionLongStream<X> skip(long n);

    @Override
    public void forEach(ThrowingLongConsumer<? extends Throwable> action) throws X;

    @Override
    public void forEachOrdered(ThrowingLongConsumer<? extends Throwable> action) throws X;

    @Override
    public long[] toArray() throws X;

    @Override
    public long reduce(long identity, ThrowingLongBinaryOperator<? extends Throwable> op) throws X;

    @Override
    public OptionalLong reduce(ThrowingLongBinaryOperator<? extends Throwable> op) throws X;

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjLongConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

    @Override
    public long sum() throws X;

    @Override
    public OptionalLong min() throws X;

    @Override
    public OptionalLong max() throws X;

    @Override
    public long count() throws X;

    @Override
    public OptionalDouble average() throws X;

    @Override
    public LongSummaryStatistics summaryStatistics() throws X;

    @Override
    public boolean anyMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X;

    @Override
    public boolean allMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X;

    @Override
    public boolean noneMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X;

    @Override
    public OptionalLong findFirst() throws X;

    @Override
    public OptionalLong findAny() throws X;

    @Override
    public UnionDoubleStream<X> asDoubleStream();

    @Override
    public UnionStream<Long, X> boxed();
}
