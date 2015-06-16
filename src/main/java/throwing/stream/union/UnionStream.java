package throwing.stream.union;

import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Collector;

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
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

public interface UnionStream<T> extends ThrowingStream<T, Throwable> {
    @Override
    public UnionIterator<T> iterator();
    
    @Override
    public UnionSpliterator<T> spliterator();

    @Override
    public UnionStream<T> filter(ThrowingPredicate<? super T, ? extends Throwable> predicate);
    
    @Override
    public <R> UnionStream<R> map(ThrowingFunction<? super T, ? extends R, ? extends Throwable> mapper);
    
    @Override
    public UnionIntStream mapToInt(ThrowingToIntFunction<? super T, ? extends Throwable> mapper);
    
    @Override
    public UnionLongStream mapToLong(ThrowingToLongFunction<? super T, ? extends Throwable> mapper);
    
    @Override
    public UnionDoubleStream mapToDouble(ThrowingToDoubleFunction<? super T, ? extends Throwable> mapper);
    
    @Override
    public <R> UnionStream<R> flatMap(
            ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable> mapper);
    
    @Override
    public UnionIntStream flatMapToInt(
            ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper);
    
    @Override
    public UnionLongStream flatMapToLong(
            ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper);
    
    @Override
    public UnionDoubleStream flatMapToDouble(
            ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper);
    
    @Override
    public UnionStream<T> distinct();
    
    @Override
    public UnionStream<T> sorted();
    
    @Override
    public UnionStream<T> sorted(ThrowingComparator<? super T, ? extends Throwable> comparator);
    
    @Override
    public UnionStream<T> peek(ThrowingConsumer<? super T, ? extends Throwable> action);
    
    @Override
    public UnionStream<T> limit(long maxSize);
    
    @Override
    public UnionStream<T> skip(long n);

    @Override
    public void forEach(ThrowingConsumer<? super T, ? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public void forEachOrdered(ThrowingConsumer<? super T, ? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public Object[] toArray() throws UnionThrowable;
    
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) throws UnionThrowable;
    
    @Override
    public T reduce(T identity, ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws UnionThrowable;
    
    @Override
    public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws UnionThrowable;
    
    @Override
    public <U> U reduce(U identity, ThrowingBiFunction<U, ? super T, U, ? extends Throwable> accumulator,
            ThrowingBinaryOperator<U, ? extends Throwable> combiner) throws UnionThrowable;
    
    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingBiConsumer<R, ? super T, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws UnionThrowable;
    
    @Override
    public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends Throwable> collector) throws UnionThrowable;
    
    @Override
    default public <R, A> R collect(Collector<? super T, A, R> collector) throws UnionThrowable {
        return collect(ThrowingBridge.of(collector));
    }
    
    @Override
    public Optional<T> min(ThrowingComparator<? super T, ? extends Throwable> comparator) throws UnionThrowable;
    
    @Override
    public Optional<T> max(ThrowingComparator<? super T, ? extends Throwable> comparator) throws UnionThrowable;
    
    @Override
    public long count() throws UnionThrowable;
    
    @Override
    public boolean anyMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean allMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean noneMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws UnionThrowable;

    @Override
    public Optional<T> findFirst() throws UnionThrowable;
    
    @Override
    public Optional<T> findAny() throws UnionThrowable;
}
