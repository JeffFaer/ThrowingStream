package throwing.stream.adapter;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import throwing.RethrowChain;
import throwing.ThrowingIterator.OfInt;
import throwing.ThrowingSpliterator;
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
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

class CheckedIntStream<X extends Throwable> extends
        CheckedBaseStream<Integer, X, ThrowingIntStream<X>, IntStream> implements
        ThrowingIntStream<X> {
    CheckedIntStream(IntStream delegate, FunctionAdapter<X> functionAdapter) {
        super(delegate, functionAdapter);
    }

    CheckedIntStream(IntStream delegate, FunctionAdapter<X> functionAdapter,
            RethrowChain<AdapterException, X> chain) {
        super(delegate, functionAdapter, chain);
    }

    @Override
    public ThrowingIntStream<X> getSelf() {
        return this;
    }

    @Override
    public ThrowingIntStream<X> createNewStream(IntStream delegate) {
        return new CheckedIntStream<>(delegate, getFunctionAdapter(), getChain());
    }

    @Override
    public OfInt<X> iterator() {
        return ThrowingBridge.of(getDelegate().iterator(), getFunctionAdapter());
    }

    @Override
    public ThrowingSpliterator.OfInt<X> spliterator() {
        return ThrowingBridge.of(getDelegate().spliterator(), getFunctionAdapter());
    }

    @Override
    public ThrowingIntStream<X> filter(ThrowingIntPredicate<? extends X> predicate) {
        return chain(getDelegate().filter(getFunctionAdapter().convert(predicate)));
    }

    @Override
    public ThrowingIntStream<X> map(ThrowingIntUnaryOperator<? extends X> mapper) {
        return chain(getDelegate().map(getFunctionAdapter().convert(mapper)));
    }

    @Override
    public <U> ThrowingStream<U, X> mapToObj(ThrowingIntFunction<? extends U, ? extends X> mapper) {
        IntFunction<? extends U> f = getFunctionAdapter().convert(mapper);
        return ThrowingBridge.of(getDelegate().mapToObj(f), getFunctionAdapter());
    }

    @Override
    public ThrowingLongStream<X> mapToLong(ThrowingIntToLongFunction<? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToLong(getFunctionAdapter().convert(mapper)),
                getFunctionAdapter());
    }

    @Override
    public ThrowingDoubleStream<X> mapToDouble(ThrowingIntToDoubleFunction<? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToDouble(getFunctionAdapter().convert(mapper)),
                getFunctionAdapter());
    }

    @Override
    public ThrowingIntStream<X> flatMap(
            ThrowingIntFunction<? extends ThrowingIntStream<? extends X>, ? extends X> mapper) {
        @SuppressWarnings("unchecked") Function<? super ThrowingIntStream<? extends X>, ? extends IntStream> c = s -> ThrowingBridge.of(
                (ThrowingIntStream<X>) s, getExceptionClass());
        return chain(getDelegate().flatMap(getFunctionAdapter().convert(mapper.andThen(c))));
    }

    @Override
    public ThrowingIntStream<X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public ThrowingIntStream<X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public ThrowingIntStream<X> peek(ThrowingIntConsumer<? extends X> action) {
        return chain(getDelegate().peek(getFunctionAdapter().convert(action)));
    }

    @Override
    public ThrowingIntStream<X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public ThrowingIntStream<X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingIntConsumer<? extends X> action) throws X {
        unmaskException(() -> getDelegate().forEach(getFunctionAdapter().convert(action)));
    }

    @Override
    public void forEachOrdered(ThrowingIntConsumer<? extends X> action) throws X {
        unmaskException(() -> getDelegate().forEachOrdered(getFunctionAdapter().convert(action)));
    }

    @Override
    public int[] toArray() throws X {
        return unmaskException(getDelegate()::toArray);
    }

    @Override
    public int reduce(int identity, ThrowingIntBinaryOperator<? extends X> op) throws X {
        return unmaskException(() -> getDelegate().reduce(identity,
                getFunctionAdapter().convert(op)));
    }

    @Override
    public OptionalInt reduce(ThrowingIntBinaryOperator<? extends X> op) throws X {
        return unmaskException(() -> getDelegate().reduce(getFunctionAdapter().convert(op)));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingObjIntConsumer<R, ? extends X> accumulator,
            ThrowingBiConsumer<R, R, ? extends X> combiner) throws X {
        return unmaskException(() -> getDelegate().collect(getFunctionAdapter().convert(supplier),
                getFunctionAdapter().convert(accumulator), getFunctionAdapter().convert(combiner)));
    }

    @Override
    public int sum() throws X {
        return unmaskException(getDelegate()::sum);
    }

    @Override
    public OptionalInt min() throws X {
        return unmaskException(getDelegate()::min);
    }

    @Override
    public OptionalInt max() throws X {
        return unmaskException(getDelegate()::max);
    }

    @Override
    public long count() throws X {
        return unmaskException(getDelegate()::count);
    }

    @Override
    public OptionalDouble average() throws X {
        return unmaskException(getDelegate()::average);
    }

    @Override
    public IntSummaryStatistics summaryStatistics() throws X {
        return unmaskException(getDelegate()::summaryStatistics);
    }

    @Override
    public boolean anyMatch(ThrowingIntPredicate<? extends X> predicate) throws X {
        return unmaskException(() -> getDelegate().anyMatch(getFunctionAdapter().convert(predicate)));
    }

    @Override
    public boolean allMatch(ThrowingIntPredicate<? extends X> predicate) throws X {
        return unmaskException(() -> getDelegate().allMatch(getFunctionAdapter().convert(predicate)));
    }

    @Override
    public boolean noneMatch(ThrowingIntPredicate<? extends X> predicate) throws X {
        return unmaskException(() -> getDelegate().noneMatch(
                getFunctionAdapter().convert(predicate)));
    }

    @Override
    public OptionalInt findFirst() throws X {
        return unmaskException(getDelegate()::findFirst);
    }

    @Override
    public OptionalInt findAny() throws X {
        return unmaskException(getDelegate()::findAny);
    }

    @Override
    public ThrowingLongStream<X> asLongStream() {
        return ThrowingBridge.of(getDelegate().asLongStream(), getFunctionAdapter());
    }

    @Override
    public ThrowingDoubleStream<X> asDoubleStream() {
        return ThrowingBridge.of(getDelegate().asDoubleStream(), getFunctionAdapter());
    }

    @Override
    public ThrowingStream<Integer, X> boxed() {
        return ThrowingBridge.of(getDelegate().boxed(), getFunctionAdapter());
    }

    @Override
    public <Y extends Throwable> ThrowingIntStream<Y> rethrow(Class<Y> e,
            Function<? super X, ? extends Y> mapper) {
        RethrowChain<AdapterException, Y> c = getChain().rethrow(mapper);
        return new CheckedIntStream<>(getDelegate(), new FunctionAdapter<>(e), c);
    }
}
