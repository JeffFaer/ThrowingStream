package throwing.stream.union;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;

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
import throwing.stream.ThrowingDoubleStream;

public interface UnionDoubleStream extends ThrowingDoubleStream<Throwable> {
    @Override
    public UnionIterator.OfDouble iterator();
    
    @Override
    public UnionSpliterator.OfDouble spliterator();
    
    @Override
    public UnionDoubleStream filter(ThrowingDoublePredicate<? extends Throwable> predicate);
    
    @Override
    public UnionDoubleStream map(ThrowingDoubleUnaryOperator<? extends Throwable> mapper);
    
    @Override
    public <U> UnionStream<U> mapToObj(ThrowingDoubleFunction<? extends U, ? extends Throwable> mapper);
    
    @Override
    public UnionIntStream mapToInt(ThrowingDoubleToIntFunction<? extends Throwable> mapper);
    
    @Override
    public UnionLongStream mapToLong(ThrowingDoubleToLongFunction<? extends Throwable> mapper);

    @Override
    public UnionDoubleStream flatMap(
            ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper);
    
    @Override
    public UnionDoubleStream distinct();
    
    @Override
    public UnionDoubleStream sorted();
    
    @Override
    public UnionDoubleStream peek(ThrowingDoubleConsumer<? extends Throwable> action);
    
    @Override
    public UnionDoubleStream limit(long maxSize);
    
    @Override
    public UnionDoubleStream skip(long n);
    
    @Override
    public void forEach(ThrowingDoubleConsumer<? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public void forEachOrdered(ThrowingDoubleConsumer<? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public double[] toArray() throws UnionThrowable;
    
    @Override
    public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends Throwable> op) throws UnionThrowable;
    
    @Override
    public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends Throwable> op) throws UnionThrowable;
    
    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjDoubleConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws UnionThrowable;
    
    @Override
    public double sum() throws UnionThrowable;
    
    @Override
    public OptionalDouble min() throws UnionThrowable;
    
    @Override
    public OptionalDouble max() throws UnionThrowable;
    
    @Override
    public long count() throws UnionThrowable;
    
    @Override
    public OptionalDouble average() throws UnionThrowable;
    
    @Override
    public DoubleSummaryStatistics summaryStatistics() throws UnionThrowable;
    
    @Override
    public boolean anyMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean allMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public boolean noneMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws UnionThrowable;
    
    @Override
    public OptionalDouble findFirst() throws UnionThrowable;
    
    @Override
    public OptionalDouble findAny() throws UnionThrowable;
    
    @Override
    public UnionStream<Double> boxed();
}
