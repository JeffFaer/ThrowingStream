package throwing.bridge;

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
        launder(() -> getDelegate().forEach(action::accept));
    }
    
    @Override
    public void forEachOrdered(DoubleConsumer action) {
        launder(() -> getDelegate().forEachOrdered(action::accept));
    }
    
    @Override
    public double[] toArray() {
        return launder(getDelegate()::toArray);
    }
    
    @Override
    public double reduce(double identity, DoubleBinaryOperator op) {
        return launder(() -> getDelegate().reduce(identity, op::applyAsDouble));
    }
    
    @Override
    public OptionalDouble reduce(DoubleBinaryOperator op) {
        return launder(() -> getDelegate().reduce(op::applyAsDouble));
    }
    
    @Override
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return launder(() -> getDelegate().collect(supplier::get, accumulator::accept, combiner::accept));
    }
    
    @Override
    public double sum() {
        return launder(getDelegate()::sum);
    }
    
    @Override
    public OptionalDouble min() {
        return launder(getDelegate()::min);
    }
    
    @Override
    public OptionalDouble max() {
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
    public DoubleSummaryStatistics summaryStatistics() {
        return launder(getDelegate()::summaryStatistics);
    }
    
    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return launder(() -> getDelegate().anyMatch(predicate::test));
    }
    
    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return launder(() -> getDelegate().allMatch(predicate::test));
    }
    
    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        return launder(() -> getDelegate().noneMatch(predicate::test));
    }
    
    @Override
    public OptionalDouble findFirst() {
        return launder(getDelegate()::findFirst);
    }
    
    @Override
    public OptionalDouble findAny() {
        return launder(getDelegate()::findAny);
    }
    
    @Override
    public Stream<Double> boxed() {
        return ThrowingBridge.of(getDelegate().boxed(), getExceptionClass());
    }
}
