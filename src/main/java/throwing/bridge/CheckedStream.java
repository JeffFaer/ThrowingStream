package throwing.bridge;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.RethrowChain;
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

class CheckedStream<T, X extends Throwable> extends CheckedBaseStream<T, X, ThrowingStream<T, X>, Stream<T>> implements
        ThrowingStream<T, X> {
    CheckedStream(Stream<T> delegate, FunctionBridge<X> bridge) {
        super(delegate, bridge);
    }

    CheckedStream(Stream<T> delegate, FunctionBridge<X> bridge, RethrowChain<BridgeException, X> chain) {
        super(delegate, bridge, chain);
    }

    @Override
    public ThrowingStream<T, X> getSelf() {
        return this;
    }

    @Override
    public ThrowingStream<T, X> createNewStream(Stream<T> delegate) {
        return newStream(delegate);
    }

    private <R> ThrowingStream<R, X> newStream(Stream<R> delegate) {
        return new CheckedStream<>(delegate, getBridge(), getChain());
    }

    @Override
    public ThrowingStream<T, X> filter(ThrowingPredicate<? super T, ? extends X> predicate) {
        return chain(getDelegate().filter(getBridge().convert(predicate)));
    }

    @Override
    public <R> ThrowingStream<R, X> map(ThrowingFunction<? super T, ? extends R, ? extends X> mapper) {
        return newStream(getDelegate().map(getBridge().convert(mapper)));
    }

    @Override
    public ThrowingIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToInt(getBridge().convert(mapper)), getBridge());
    }

    @Override
    public ThrowingLongStream<X> mapToLong(ThrowingToLongFunction<? super T, ? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToLong(getBridge().convert(mapper)), getBridge());
    }

    @Override
    public ThrowingDoubleStream<X> mapToDouble(ThrowingToDoubleFunction<? super T, ? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToDouble(getBridge().convert(mapper)), getBridge());
    }

    @Override
    public <R> ThrowingStream<R, X> flatMap(
            ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends X>, ? extends X> mapper) {
        @SuppressWarnings("unchecked") Function<? super ThrowingStream<? extends R, ? extends X>, ? extends Stream<? extends R>> c = s -> ThrowingBridge.of(
                (ThrowingStream<? extends R, X>) s, getExceptionClass());
        return newStream(getDelegate().flatMap(getBridge().convert(mapper.andThen(c))));
    }

    @Override
    public ThrowingIntStream<X> flatMapToInt(
            ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X> mapper) {
        @SuppressWarnings("unchecked") Function<? super ThrowingIntStream<? extends X>, ? extends IntStream> c = s -> ThrowingBridge.of(
                (ThrowingIntStream<X>) s, getExceptionClass());
        return ThrowingBridge.of(getDelegate().flatMapToInt(getBridge().convert(mapper.andThen(c))), getBridge());
    }

    @Override
    public ThrowingLongStream<X> flatMapToLong(
            ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends X>, ? extends X> mapper) {
        @SuppressWarnings("unchecked") Function<? super ThrowingLongStream<? extends X>, ? extends LongStream> c = s -> ThrowingBridge.of(
                (ThrowingLongStream<X>) s, getExceptionClass());
        return ThrowingBridge.of(getDelegate().flatMapToLong(getBridge().convert(mapper.andThen(c))), getBridge());
    }

    @Override
    public ThrowingDoubleStream<X> flatMapToDouble(
            ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper) {
        @SuppressWarnings("unchecked") Function<? super ThrowingDoubleStream<? extends X>, ? extends DoubleStream> c = s -> ThrowingBridge.of(
                (ThrowingDoubleStream<X>) s, getExceptionClass());
        return ThrowingBridge.of(getDelegate().flatMapToDouble(getBridge().convert(mapper.andThen(c))), getBridge());
    }

    @Override
    public ThrowingStream<T, X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public ThrowingStream<T, X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public ThrowingStream<T, X> sorted(ThrowingComparator<? super T, ? extends X> comparator) {
        return chain(getDelegate().sorted(getBridge().convert(comparator)));
    }

    @Override
    public ThrowingStream<T, X> peek(ThrowingConsumer<? super T, ? extends X> action) {
        return chain(getDelegate().peek(getBridge().convert(action)));
    }

    @Override
    public ThrowingStream<T, X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public ThrowingStream<T, X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingConsumer<? super T, ? extends X> action) throws X {
        unmaskBridgeException(() -> getDelegate().forEach(getBridge().convert(action)));
    }

    @Override
    public void forEachOrdered(ThrowingConsumer<? super T, ? extends X> action) throws X {
        unmaskBridgeException(() -> getDelegate().forEachOrdered(getBridge().convert(action)));
    }

    @Override
    public Object[] toArray() throws X {
        return unmaskBridgeException((Supplier<Object[]>) getDelegate()::toArray);
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) throws X {
        return unmaskBridgeException(() -> getDelegate().toArray(generator));
    }

    @Override
    public T reduce(T identity, ThrowingBinaryOperator<T, ? extends X> accumulator) throws X {
        return unmaskBridgeException(() -> getDelegate().reduce(identity, getBridge().convert(accumulator)));
    }

    @Override
    public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends X> accumulator) throws X {
        return unmaskBridgeException(() -> getDelegate().reduce(getBridge().convert(accumulator)));
    }

    @Override
    public <U> U reduce(U identity, ThrowingBiFunction<U, ? super T, U, ? extends X> accumulator,
            ThrowingBinaryOperator<U, ? extends X> combiner) throws X {
        return unmaskBridgeException(() -> getDelegate().reduce(identity, getBridge().convert(accumulator),
                getBridge().convert(combiner)));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingBiConsumer<R, ? super T, ? extends X> accumulator, ThrowingBiConsumer<R, R, ? extends X> combiner)
        throws X {
        return unmaskBridgeException(() -> getDelegate().collect(getBridge().convert(supplier),
                getBridge().convert(accumulator), getBridge().convert(combiner)));
    }

    @Override
    public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends X> collector) throws X {
        return unmaskBridgeException(() -> getDelegate().collect(ThrowingBridge.of(collector, getBridge())));
    }

    @Override
    public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws X {
        return unmaskBridgeException(() -> getDelegate().min(getBridge().convert(comparator)));
    }

    @Override
    public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws X {
        return unmaskBridgeException(() -> getDelegate().max(getBridge().convert(comparator)));
    }

    @Override
    public long count() throws X {
        return unmaskBridgeException(getDelegate()::count);
    }

    @Override
    public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
        return unmaskBridgeException(() -> getDelegate().anyMatch(getBridge().convert(predicate)));
    }

    @Override
    public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
        return unmaskBridgeException(() -> getDelegate().allMatch(getBridge().convert(predicate)));
    }

    @Override
    public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
        return unmaskBridgeException(() -> getDelegate().noneMatch(getBridge().convert(predicate)));
    }

    @Override
    public Optional<T> findFirst() throws X {
        return unmaskBridgeException(getDelegate()::findFirst);
    }

    @Override
    public Optional<T> findAny() throws X {
        return unmaskBridgeException(getDelegate()::findAny);
    }

    @Override
    public <Y extends Throwable> ThrowingStream<T, Y> rethrow(Class<Y> e, Function<? super X, ? extends Y> mapper) {
        RethrowChain<BridgeException, Y> c = getChain().rethrow(mapper);
        return new CheckedStream<>(getDelegate(), new FunctionBridge<>(e), c);
    }
}
