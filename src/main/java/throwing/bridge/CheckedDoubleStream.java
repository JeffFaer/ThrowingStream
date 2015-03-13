package throwing.bridge;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;

import throwing.ThrowingIterator.OfDouble;
import throwing.ThrowingSpliterator;
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
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

class CheckedDoubleStream<X extends Throwable> extends
        CheckedBaseStream<Double, X, ThrowingDoubleStream<X>, DoubleStream> implements ThrowingDoubleStream<X> {
    CheckedDoubleStream(DoubleStream delegate, FunctionBridge<X> bridge) {
        super(delegate, bridge);
    }

    CheckedDoubleStream(DoubleStream delegate, FunctionBridge<X> bridge, RethrowChain<BridgeException, X> chain) {
        super(delegate, bridge, chain);
    }

    @Override
    public ThrowingDoubleStream<X> getSelf() {
        return this;
    }

    @Override
    public ThrowingDoubleStream<X> createNewStream(DoubleStream delegate) {
        return new CheckedDoubleStream<>(delegate, getBridge(), getChain());
    }

    @Override
    public OfDouble<X> iterator() {
        return ThrowingBridge.of(getDelegate().iterator(), getBridge());
    }

    @Override
    public ThrowingSpliterator.OfDouble<X> spliterator() {
        return ThrowingBridge.of(getDelegate().spliterator(), getBridge());
    }

    @Override
    public ThrowingDoubleStream<X> filter(ThrowingDoublePredicate<? extends X> predicate) {
        return chain(getDelegate().filter(getBridge().convert(predicate)));
    }

    @Override
    public ThrowingDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends X> mapper) {
        return chain(getDelegate().map(getBridge().convert(mapper)));
    }

    @Override
    public <U> ThrowingStream<U, X> mapToObj(ThrowingDoubleFunction<? extends U, ? extends X> mapper) {
        DoubleFunction<? extends U> f = getBridge().convert(mapper);
        return ThrowingBridge.of(getDelegate().mapToObj(f), getBridge());
    }

    @Override
    public ThrowingIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToInt(getBridge().convert(mapper)), getBridge());
    }

    @Override
    public ThrowingLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToLong(getBridge().convert(mapper)), getBridge());
    }

    @Override
    public ThrowingDoubleStream<X> flatMap(
            ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper) {
        @SuppressWarnings("unchecked") Function<? super ThrowingDoubleStream<? extends X>, ? extends DoubleStream> c = s -> ThrowingBridge.of(
                (ThrowingDoubleStream<X>) s, getExceptionClass());
        return chain(getDelegate().flatMap(getBridge().convert(mapper.andThen(c))));
    }

    @Override
    public ThrowingDoubleStream<X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public ThrowingDoubleStream<X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public ThrowingDoubleStream<X> peek(ThrowingDoubleConsumer<? extends X> action) {
        return chain(getDelegate().peek(getBridge().convert(action)));
    }

    @Override
    public ThrowingDoubleStream<X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public ThrowingDoubleStream<X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingDoubleConsumer<? extends X> action) throws X {
        filterBridgeException(() -> getDelegate().forEach(getBridge().convert(action)));
    }

    @Override
    public void forEachOrdered(ThrowingDoubleConsumer<? extends X> action) throws X {
        filterBridgeException(() -> getDelegate().forEachOrdered(getBridge().convert(action)));
    }

    @Override
    public double[] toArray() throws X {
        return filterBridgeException(getDelegate()::toArray);
    }

    @Override
    public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends X> op) throws X {
        return filterBridgeException(() -> getDelegate().reduce(identity, getBridge().convert(op)));
    }

    @Override
    public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends X> op) throws X {
        return filterBridgeException(() -> getDelegate().reduce(getBridge().convert(op)));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingObjDoubleConsumer<R, ? extends X> accumulator, ThrowingBiConsumer<R, R, ? extends X> combiner)
        throws X {
        return filterBridgeException(() -> getDelegate().collect(getBridge().convert(supplier),
                getBridge().convert(accumulator), getBridge().convert(combiner)));
    }

    @Override
    public double sum() throws X {
        return filterBridgeException(getDelegate()::sum);
    }

    @Override
    public OptionalDouble min() throws X {
        return filterBridgeException(getDelegate()::min);
    }

    @Override
    public OptionalDouble max() throws X {
        return filterBridgeException(getDelegate()::max);
    }

    @Override
    public long count() throws X {
        return filterBridgeException(getDelegate()::count);
    }

    @Override
    public OptionalDouble average() throws X {
        return filterBridgeException(getDelegate()::average);
    }

    @Override
    public DoubleSummaryStatistics summaryStatistics() throws X {
        return filterBridgeException(getDelegate()::summaryStatistics);
    }

    @Override
    public boolean anyMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
        return filterBridgeException(() -> getDelegate().anyMatch(getBridge().convert(predicate)));
    }

    @Override
    public boolean allMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
        return filterBridgeException(() -> getDelegate().allMatch(getBridge().convert(predicate)));
    }

    @Override
    public boolean noneMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
        return filterBridgeException(() -> getDelegate().noneMatch(getBridge().convert(predicate)));
    }

    @Override
    public OptionalDouble findFirst() throws X {
        return filterBridgeException(getDelegate()::findFirst);
    }

    @Override
    public OptionalDouble findAny() throws X {
        return filterBridgeException(getDelegate()::findAny);
    }

    @Override
    public ThrowingStream<Double, X> boxed() {
        return ThrowingBridge.of(getDelegate().boxed(), getBridge());
    }

    @Override
    public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> e, Function<? super X, ? extends Y> mapper) {
        RethrowChain<BridgeException, Y> c = getChain().rethrow(mapper);
        return new CheckedDoubleStream<>(getDelegate(), new FunctionBridge<>(e), c);
    }
}
