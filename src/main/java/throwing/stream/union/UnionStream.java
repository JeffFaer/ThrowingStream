package throwing.stream.union;

import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Collector;

import throwing.ThrowingComparator;
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
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;
import throwing.stream.bridge.ThrowingBridge;

public interface UnionStream<T, X extends UnionThrowable> extends ThrowingStream<T, Throwable> {
    @Override
    public UnionIterator<T, X> iterator();

    @Override
    public UnionSpliterator<T, X> spliterator();

    @Override
    public UnionStream<T, X> onClose(Runnable closeHandler);

    @Override
    public UnionStream<T, X> parallel();

    @Override
    public UnionStream<T, X> sequential();

    @Override
    public UnionStream<T, X> unordered();

    @Override
    public UnionStream<T, X> filter(ThrowingPredicate<? super T, ? extends Throwable> predicate);

    @Override
    public <R> UnionStream<R, X> map(
            ThrowingFunction<? super T, ? extends R, ? extends Throwable> mapper);

    @Override
    public UnionIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends Throwable> mapper);

    @Override
    public UnionLongStream<X> mapToLong(ThrowingToLongFunction<? super T, ? extends Throwable> mapper);

    @Override
    public UnionDoubleStream<X> mapToDouble(
            ThrowingToDoubleFunction<? super T, ? extends Throwable> mapper);

    @Override
    public <R> UnionStream<R, X> flatMap(
            ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable> mapper);

    @Override
    public UnionIntStream<X> flatMapToInt(
            ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper);

    @Override
    public UnionLongStream<X> flatMapToLong(
            ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper);

    @Override
    public UnionDoubleStream<X> flatMapToDouble(
            ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper);

    @Override
    public UnionStream<T, X> distinct();

    @Override
    public UnionStream<T, X> sorted();

    @Override
    public UnionStream<T, X> sorted(ThrowingComparator<? super T, ? extends Throwable> comparator);

    @Override
    public UnionStream<T, X> peek(ThrowingConsumer<? super T, ? extends Throwable> action);

    @Override
    public UnionStream<T, X> limit(long maxSize);

    @Override
    public UnionStream<T, X> skip(long n);

    @Override
    public void forEach(ThrowingConsumer<? super T, ? extends Throwable> action) throws X;

    @Override
    public void forEachOrdered(ThrowingConsumer<? super T, ? extends Throwable> action) throws X;

    @Override
    public Object[] toArray() throws X;

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) throws X;

    @Override
    public T reduce(T identity, ThrowingBinaryOperator<T, ? extends Throwable> accumulator)
        throws X;

    @Override
    public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws X;

    @Override
    public <U> U reduce(U identity,
            ThrowingBiFunction<U, ? super T, U, ? extends Throwable> accumulator,
            ThrowingBinaryOperator<U, ? extends Throwable> combiner) throws X;

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingBiConsumer<R, ? super T, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X;

    @Override
    public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends Throwable> collector)
        throws X;

    @Override
    default public <R, A> R collect(Collector<? super T, A, R> collector) throws X {
        return collect(ThrowingBridge.of(collector));
    }

    @Override
    public Optional<T> min(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X;

    @Override
    public Optional<T> max(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X;

    @Override
    public long count() throws X;

    @Override
    public boolean anyMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X;

    @Override
    public boolean allMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X;

    @Override
    public boolean noneMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X;

    @Override
    public Optional<T> findFirst() throws X;

    @Override
    public Optional<T> findAny() throws X;
}
