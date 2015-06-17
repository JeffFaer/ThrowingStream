package throwing.stream.union;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;

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
import throwing.stream.ThrowingIntStream;

public interface UnionIntStream<X extends UnionThrowable> extends ThrowingIntStream<Throwable> {
    @Override
    public UnionIterator.OfInt<X> iterator();

    @Override
    public UnionSpliterator.OfInt<X> spliterator();

    @Override
    public UnionIntStream<X> onClose(Runnable closeHandler);

    @Override
    public UnionIntStream<X> parallel();

    @Override
    public UnionIntStream<X> sequential();

    @Override
    public UnionIntStream<X> unordered();

    @Override
    public UnionIntStream<X> filter(ThrowingIntPredicate<? extends Throwable> predicate);

    @Override
    public UnionIntStream<X> map(ThrowingIntUnaryOperator<? extends Throwable> mapper);

    @Override
    public <U> UnionStream<U, X> mapToObj(
            ThrowingIntFunction<? extends U, ? extends Throwable> mapper);

    @Override
    public UnionLongStream<X> mapToLong(ThrowingIntToLongFunction<? extends Throwable> mapper);

    @Override
    public UnionDoubleStream<X> mapToDouble(ThrowingIntToDoubleFunction<? extends Throwable> mapper);

    @Override
    public UnionIntStream<X> flatMap(
            ThrowingIntFunction<? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper);

    @Override
    public UnionIntStream<X> distinct();

    @Override
    public UnionIntStream<X> sorted();

    @Override
    public UnionIntStream<X> peek(ThrowingIntConsumer<? extends Throwable> action);

    @Override
    public UnionIntStream<X> limit(long maxSize);

    @Override
    public UnionIntStream<X> skip(long n);

    @Override
    public void forEach(ThrowingIntConsumer<? extends Throwable> action) throws X;

    @Override
    public void forEachOrdered(ThrowingIntConsumer<? extends Throwable> action) throws X;

    @Override
    public int[] toArray() throws X;

    @Override
    public int reduce(int identity, ThrowingIntBinaryOperator<? extends Throwable> op) throws X;

    @Override
    public OptionalInt reduce(ThrowingIntBinaryOperator<? extends Throwable> op) throws X;

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjIntConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

    @Override
    public int sum() throws X;

    @Override
    public OptionalInt min() throws X;

    @Override
    public OptionalInt max() throws X;

    @Override
    public long count() throws X;

    @Override
    public OptionalDouble average() throws X;

    @Override
    public IntSummaryStatistics summaryStatistics() throws X;

    @Override
    public boolean anyMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X;

    @Override
    public boolean allMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X;

    @Override
    public boolean noneMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X;

    @Override
    public OptionalInt findFirst() throws X;

    @Override
    public OptionalInt findAny() throws X;

    @Override
    public UnionDoubleStream<X> asDoubleStream();

    @Override
    public UnionStream<Integer, X> boxed();
}
