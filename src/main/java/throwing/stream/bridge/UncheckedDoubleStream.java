package throwing.stream.bridge;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator.OfDouble;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.function.ThrowingDoubleFunction;
import throwing.stream.ThrowingDoubleStream;

public class UncheckedDoubleStream<X extends Throwable> extends
        UncheckedBaseStream<Double, X, DoubleStream, ThrowingDoubleStream<X>> implements DoubleStream {
    UncheckedDoubleStream(ThrowingDoubleStream<X> delegate, Class<X> x) {
        super(delegate, x);
    }
    
    @Override
    public DoubleStream getSelf() {
        return this;
    }
    
    @Override
    public DoubleStream createNewStream(ThrowingDoubleStream<X> delegate) {
        return new UncheckedDoubleStream<>(delegate, getExceptionClass());
    }
    
    @Override
    public OfDouble iterator() {
        return ThrowingBridge.of(getDelegate().iterator(), getExceptionClass());
    }
    
    @Override
    public Spliterator.OfDouble spliterator() {
        return ThrowingBridge.of(getDelegate().spliterator(), getExceptionClass());
    }
    
    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        return chain(getDelegate().filter(predicate::test));
    }
    
    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        return chain(getDelegate().map(mapper::applyAsDouble));
    }
    
    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        ThrowingDoubleFunction<? extends U, ? extends X> f = mapper::apply;
        return ThrowingBridge.of(getDelegate().mapToObj(f), getExceptionClass());
    }
    
    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        return ThrowingBridge.of(getDelegate().mapToInt(mapper::applyAsInt), getExceptionClass());
    }
    
    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        return ThrowingBridge.of(getDelegate().mapToLong(mapper::applyAsLong), getExceptionClass());
    }
    
    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        DoubleFunction<? extends ThrowingDoubleStream<? extends X>> f = i -> ThrowingBridge.of(mapper.apply(i),
                getExceptionClass());
        return chain(getDelegate().flatMap(f::apply));
    }
    
    @Override
    public DoubleStream distinct() {
        return chain(getDelegate().distinct());
    }
    
    @Override
    public DoubleStream sorted() {
        return chain(getDelegate().sorted());
    }
    
    @Override
    public DoubleStream peek(DoubleConsumer action) {
        return chain(getDelegate().peek(action::accept));
    }
    
    @Override
    public DoubleStream limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }
    
    @Override
    public DoubleStream skip(long n) {
        return chain(getDelegate().skip(n));
    }
    
    @Override
    public void forEach(DoubleConsumer action) {
        maskException(() -> getDelegate().forEach(action::accept));
    }
    
    @Override
    public void forEachOrdered(DoubleConsumer action) {
        maskException(() -> getDelegate().forEachOrdered(action::accept));
    }
    
    @Override
    public double[] toArray() {
        return maskException(getDelegate()::toArray);
    }
    
    @Override
    public double reduce(double identity, DoubleBinaryOperator op) {
        return maskException(() -> getDelegate().reduce(identity, op::applyAsDouble));
    }
    
    @Override
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        return maskException(() -> getDelegate().reduce(op::applyAsDouble));
    }
    
    @Override
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return maskException(() -> getDelegate().collect(supplier::get, accumulator::accept, combiner::accept));
    }
    
    @Override
    public double sum() {
        return maskException(getDelegate()::sum);
    }
    
    @Override
    public OptionalDouble min() {
        return maskException(getDelegate()::min);
    }
    
    @Override
    public OptionalDouble max() {
        return maskException(getDelegate()::max);
    }
    
    @Override
    public long count() {
        return maskException(getDelegate()::count);
    }
    
    @Override
    public OptionalDouble average() {
        return maskException(getDelegate()::average);
    }
    
    @Override
    public DoubleSummaryStatistics summaryStatistics() {
        return maskException(getDelegate()::summaryStatistics);
    }
    
    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return maskException(() -> getDelegate().anyMatch(predicate::test));
    }
    
    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return maskException(() -> getDelegate().allMatch(predicate::test));
    }
    
    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        return maskException(() -> getDelegate().noneMatch(predicate::test));
    }
    
    @Override
    public OptionalDouble findFirst() {
        return maskException(getDelegate()::findFirst);
    }
    
    @Override
    public OptionalDouble findAny() {
        return maskException(getDelegate()::findAny);
    }
    
    @Override
    public Stream<Double> boxed() {
        return ThrowingBridge.of(getDelegate().boxed(), getExceptionClass());
    }
}
