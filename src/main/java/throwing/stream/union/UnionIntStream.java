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

public interface UnionIntStream extends ThrowingIntStream<Throwable> {
    @Override
    public UnionIterator.OfInt iterator();
    
    @Override
    public UnionSpliterator.OfInt spliterator();
    
    @Override
    public UnionIntStream filter(ThrowingIntPredicate<? extends Throwable> predicate);
    
    @Override
    public UnionIntStream map(ThrowingIntUnaryOperator<? extends Throwable> mapper);
    
    @Override
    public <U> UnionStream<U> mapToObj(ThrowingIntFunction<? extends U, ? extends Throwable> mapper);
    
    @Override
    public UnionLongStream mapToLong(ThrowingIntToLongFunction<? extends Throwable> mapper);

    @Override
    public UnionDoubleStream mapToDouble(ThrowingIntToDoubleFunction<? extends Throwable> mapper);
    
    @Override
    public UnionIntStream flatMap(
            ThrowingIntFunction<? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper);
    
    @Override
    public UnionIntStream distinct();
    
    @Override
    public UnionIntStream sorted();
    
    @Override
    public UnionIntStream peek(ThrowingIntConsumer<? extends Throwable> action);
    
    @Override
    public UnionIntStream limit(long maxSize);
    
    @Override
    public UnionIntStream skip(long n);
    
    @Override
    public void forEach(ThrowingIntConsumer<? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public void forEachOrdered(ThrowingIntConsumer<? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public int[] toArray() throws UnionThrowable;
    
    @Override
    public int reduce(int identity, ThrowingIntBinaryOperator<? extends Throwable> op) throws UnionThrowable;
    
    @Override
    public OptionalInt reduce(ThrowingIntBinaryOperator<? extends Throwable> op) throws UnionThrowable;
    
    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjIntConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws UnionThrowable;
    
    @Override
    public int sum() throws UnionThrowable;
    
    @Override
    public OptionalInt min() throws UnionThrowable;
    
    @Override
    public OptionalInt max() throws UnionThrowable;
    
    @Override
    public long count() throws UnionThrowable;
    
    @Override
    public OptionalDouble average() throws UnionThrowable;
    
    @Override
    public IntSummaryStatistics summaryStatistics() throws UnionThrowable;
    
    @Override
    public boolean anyMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean allMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean noneMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public OptionalInt findFirst() throws UnionThrowable;
    
    @Override
    public OptionalInt findAny() throws UnionThrowable;
    
    @Override
    public UnionDoubleStream asDoubleStream();
    
    @Override
    public UnionStream<Integer> boxed();
}
