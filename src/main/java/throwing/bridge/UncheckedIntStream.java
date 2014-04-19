package throwing.bridge;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator.OfInt;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.function.ThrowingIntFunction;
import throwing.stream.ThrowingIntStream;

class UncheckedIntStream<X extends Throwable> extends UncheckedBaseStream<Integer, X, IntStream, ThrowingIntStream<X>>
        implements IntStream {
    UncheckedIntStream(ThrowingIntStream<X> delegate, Class<X> x) {
        super(delegate, x);
    }
    
    @Override
    public IntStream getSelf() {
        return this;
    }
    
    @Override
    public IntStream createNewStream(ThrowingIntStream<X> delegate) {
        return new UncheckedIntStream<>(delegate, getExceptionClass());
    }
    
    @Override
    public OfInt iterator() {
        return ThrowingBridge.of(getDelegate().iterator(), getExceptionClass());
    }
    
    @Override
    public Spliterator.OfInt spliterator() {
        return ThrowingBridge.of(getDelegate().spliterator(), getExceptionClass());
    }
    
    @Override
    public IntStream filter(IntPredicate predicate) {
        return chain(getDelegate().filter(predicate::test));
    }
    
    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return chain(getDelegate().map(mapper::applyAsInt));
    }
    
    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        ThrowingIntFunction<? extends U, ? extends X> f = mapper::apply;
        return ThrowingBridge.of(getDelegate().mapToObj(f), getExceptionClass());
    }
    
    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        return ThrowingBridge.of(getDelegate().mapToLong(mapper::applyAsLong), getExceptionClass());
    }
    
    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        return ThrowingBridge.of(getDelegate().mapToDouble(mapper::applyAsDouble), getExceptionClass());
    }
    
    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        IntFunction<? extends ThrowingIntStream<? extends X>> f = i -> ThrowingBridge.of(mapper.apply(i),
                getExceptionClass());
        return chain(getDelegate().flatMap(f::apply));
    }
    
    @Override
    public IntStream distinct() {
        return chain(getDelegate().distinct());
    }
    
    @Override
    public IntStream sorted() {
        return chain(getDelegate().sorted());
    }
    
    @Override
    public IntStream peek(IntConsumer action) {
        return chain(getDelegate().peek(action::accept));
    }
    
    @Override
    public IntStream limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }
    
    @Override
    public IntStream skip(long n) {
        return chain(getDelegate().skip(n));
    }
    
    @Override
    public void forEach(IntConsumer action) {
        launder(() -> getDelegate().forEach(action::accept));
    }
    
    @Override
    public void forEachOrdered(IntConsumer action) {
        launder(() -> getDelegate().forEachOrdered(action::accept));
    }
    
    @Override
    public int[] toArray() {
        return launder(getDelegate()::toArray);
    }
    
    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        return launder(() -> getDelegate().reduce(identity, op::applyAsInt));
    }
    
    @Override
    public OptionalInt reduce(IntBinaryOperator op) {
        return launder(() -> getDelegate().reduce(op::applyAsInt));
    }
    
    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
        return launder(() -> getDelegate().collect(supplier::get, accumulator::accept, combiner::accept));
    }
    
    @Override
    public int sum() {
        return launder(getDelegate()::sum);
    }
    
    @Override
    public OptionalInt min() {
        return launder(getDelegate()::min);
    }
    
    @Override
    public OptionalInt max() {
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
    public IntSummaryStatistics summaryStatistics() {
        return launder(getDelegate()::summaryStatistics);
    }
    
    @Override
    public boolean anyMatch(IntPredicate predicate) {
        return launder(() -> getDelegate().anyMatch(predicate::test));
    }
    
    @Override
    public boolean allMatch(IntPredicate predicate) {
        return launder(() -> getDelegate().allMatch(predicate::test));
    }
    
    @Override
    public boolean noneMatch(IntPredicate predicate) {
        return launder(() -> getDelegate().noneMatch(predicate::test));
    }
    
    @Override
    public OptionalInt findFirst() {
        return launder(getDelegate()::findFirst);
    }
    
    @Override
    public OptionalInt findAny() {
        return launder(getDelegate()::findAny);
    }
    
    @Override
    public LongStream asLongStream() {
        return ThrowingBridge.of(getDelegate().asLongStream(), getExceptionClass());
    }
    
    @Override
    public DoubleStream asDoubleStream() {
        return ThrowingBridge.of(getDelegate().asDoubleStream(), getExceptionClass());
    }
    
    @Override
    public Stream<Integer> boxed() {
        return ThrowingBridge.of(getDelegate().boxed(), getExceptionClass());
    }
}
