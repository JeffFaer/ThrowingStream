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

public interface UnionLongStream extends ThrowingLongStream<Throwable> {
    @Override
    public UnionIterator.OfLong iterator();
    
    @Override
    public UnionSpliterator.OfLong spliterator();
    
    @Override
    public UnionLongStream filter(ThrowingLongPredicate<? extends Throwable> predicate);
    
    @Override
    public UnionLongStream map(ThrowingLongUnaryOperator<? extends Throwable> mapper);
    
    @Override
    public <U> UnionStream<U> mapToObj(ThrowingLongFunction<? extends U, ? extends Throwable> mapper);
    
    @Override
    public UnionIntStream mapToInt(ThrowingLongToIntFunction<? extends Throwable> mapper);
    
    @Override
    public UnionDoubleStream mapToDouble(ThrowingLongToDoubleFunction<? extends Throwable> mapper);
    
    @Override
    public UnionLongStream flatMap(
            ThrowingLongFunction<? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper);
    
    @Override
    public UnionLongStream distinct();
    
    @Override
    public UnionLongStream sorted();
    
    @Override
    public UnionLongStream peek(ThrowingLongConsumer<? extends Throwable> action);
    
    @Override
    public UnionLongStream limit(long maxSize);
    
    @Override
    public UnionLongStream skip(long n);
    
    @Override
    public void forEach(ThrowingLongConsumer<? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public void forEachOrdered(ThrowingLongConsumer<? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public long[] toArray() throws UnionThrowable;
    
    @Override
    public long reduce(long identity, ThrowingLongBinaryOperator<? extends Throwable> op) throws UnionThrowable;
    
    @Override
    public OptionalLong reduce(ThrowingLongBinaryOperator<? extends Throwable> op) throws UnionThrowable;
    
    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjLongConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws UnionThrowable;
    
    @Override
    public long sum() throws UnionThrowable;
    
    @Override
    public OptionalLong min() throws UnionThrowable;
    
    @Override
    public OptionalLong max() throws UnionThrowable;
    
    @Override
    public long count() throws UnionThrowable;
    
    @Override
    public OptionalDouble average() throws UnionThrowable;
    
    @Override
    public LongSummaryStatistics summaryStatistics() throws UnionThrowable;
    
    @Override
    public boolean anyMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean allMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean noneMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public OptionalLong findFirst() throws UnionThrowable;
    
    @Override
    public OptionalLong findAny() throws UnionThrowable;
    
    @Override
    public UnionDoubleStream asDoubleStream();
    
    @Override
    public UnionStream<Long> boxed();
}
