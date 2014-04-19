package throwing.bridge;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator.OfLong;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.stream.ThrowingLongStream;

class UncheckedLongStream<X extends Throwable> extends
        UncheckedBaseStream<Long, X, LongStream, ThrowingLongStream<X>> implements LongStream {
    UncheckedLongStream(ThrowingLongStream<X> delegate, Class<X> x) {
        super(delegate, x);
    }
    
    @Override
    public LongStream getSelf() {
        return this;
    }
    
    @Override
    public LongStream createNewStream(ThrowingLongStream<X> delegate) {
        return new UncheckedLongStream<>(delegate, getExceptionClass());
    }
    
    @Override
    public OfLong iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Spliterator.OfLong spliterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public LongStream filter(LongPredicate predicate) {
        return chain(getDelegate().filter(predicate::test));
    }
    
    @Override
    public LongStream map(LongUnaryOperator mapper) {
        return chain(getDelegate().map(mapper::applyAsLong));
    }
    
    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        return ThrowingBridge.of(getDelegate().mapToObj(mapper::apply), getExceptionClass());
    }
    
    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        return ThrowingBridge.of(getDelegate().mapToInt(mapper::applyAsInt), getExceptionClass());
    }
    
    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        LongFunction<? extends ThrowingLongStream<? extends X>> f = i -> ThrowingBridge.of(mapper.apply(i),
                getExceptionClass());
        return chain(getDelegate().flatMap(f::apply));
    }
    
    @Override
    public LongStream distinct() {
        return chain(getDelegate().distinct());
    }
    
    @Override
    public LongStream sorted() {
        return chain(getDelegate().sorted());
    }
    
    @Override
    public LongStream peek(LongConsumer action) {
        return chain(getDelegate().peek(action::accept));
    }
    
    @Override
    public LongStream limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }
    
    @Override
    public LongStream skip(long n) {
        return chain(getDelegate().skip(n));
    }
    
    @Override
    public void forEach(LongConsumer action) {
        launder(() -> getDelegate().forEach(action::accept));
    }
    
    @Override
    public void forEachOrdered(LongConsumer action) {
        launder(() -> getDelegate().forEachOrdered(action::accept));
    }
    
    @Override
    public long[] toArray() {
        return launder(getDelegate()::toArray);
    }
    
    @Override
    public long reduce(long identity, LongBinaryOperator op) {
        return launder(() -> getDelegate().reduce(identity, op::applyAsLong));
    }
    
    @Override
    public OptionalLong reduce(LongBinaryOperator op) {
        return launder(() -> getDelegate().reduce(op::applyAsLong));
    }
    
    @Override
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return launder(() -> getDelegate().collect(supplier::get, accumulator::accept, combiner::accept));
    }
    
    @Override
    public long sum() {
        return launder(getDelegate()::sum);
    }
    
    @Override
    public OptionalLong min() {
        return launder(getDelegate()::min);
    }
    
    @Override
    public OptionalLong max() {
        return launder(getDelegate()::max);
    }
    
    @Override
    public long count() {
        return launder(getDelegate()::count);
    }
    
    @Override
    public OptionalDouble average() {
        return launder(getDelegate()::average);
    }
    
    @Override
    public LongSummaryStatistics summaryStatistics() {
        return launder(getDelegate()::summaryStatistics);
    }
    
    @Override
    public boolean anyMatch(LongPredicate predicate) {
        return launder(() -> getDelegate().anyMatch(predicate::test));
    }
    
    @Override
    public boolean allMatch(LongPredicate predicate) {
        return launder(() -> getDelegate().allMatch(predicate::test));
    }
    
    @Override
    public boolean noneMatch(LongPredicate predicate) {
        return launder(() -> getDelegate().noneMatch(predicate::test));
    }
    
    @Override
    public OptionalLong findFirst() {
        return launder(getDelegate()::findFirst);
    }
    
    @Override
    public OptionalLong findAny() {
        return launder(getDelegate()::findAny);
    }
    
    @Override
    public DoubleStream asDoubleStream() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Stream<Long> boxed() {
        return ThrowingBridge.of(getDelegate().boxed(), getExceptionClass());
    }
}
