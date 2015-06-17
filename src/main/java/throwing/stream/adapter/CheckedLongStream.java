package throwing.stream.adapter;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

import throwing.RethrowChain;
import throwing.ThrowingIterator.OfLong;
import throwing.ThrowingSpliterator;
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
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

class CheckedLongStream<X extends Throwable> extends
        CheckedBaseStream<Long, X, ThrowingLongStream<X>, LongStream> implements
        ThrowingLongStream<X> {
    CheckedLongStream(LongStream delegate, FunctionAdapter<X> functionAdapter) {
        super(delegate, functionAdapter);
    }

    CheckedLongStream(LongStream delegate, FunctionAdapter<X> functionAdapter,
            RethrowChain<AdapterException, X> chain) {
        super(delegate, functionAdapter, chain);
    }

    @Override
    public ThrowingLongStream<X> getSelf() {
        return this;
    }

    @Override
    public ThrowingLongStream<X> createNewStream(LongStream delegate) {
        return new CheckedLongStream<>(delegate, getFunctionAdapter(), getChain());
    }

    @Override
    public OfLong<X> iterator() {
        return ThrowingBridge.of(getDelegate().iterator(), getFunctionAdapter());
    }

    @Override
    public ThrowingSpliterator.OfLong<X> spliterator() {
        return ThrowingBridge.of(getDelegate().spliterator(), getFunctionAdapter());
    }

    @Override
    public ThrowingLongStream<X> filter(ThrowingLongPredicate<? extends X> predicate) {
        return chain(getDelegate().filter(getFunctionAdapter().convert(predicate)));
    }

    @Override
    public ThrowingLongStream<X> map(ThrowingLongUnaryOperator<? extends X> mapper) {
        return chain(getDelegate().map(getFunctionAdapter().convert(mapper)));
    }

    @Override
    public <U> ThrowingStream<U, X> mapToObj(ThrowingLongFunction<? extends U, ? extends X> mapper) {
        LongFunction<? extends U> f = getFunctionAdapter().convert(mapper);
        return ThrowingBridge.of(getDelegate().mapToObj(f), getFunctionAdapter());
    }

    @Override
    public ThrowingIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToInt(getFunctionAdapter().convert(mapper)),
                getFunctionAdapter());
    }

    @Override
    public ThrowingDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends X> mapper) {
        return ThrowingBridge.of(getDelegate().mapToDouble(getFunctionAdapter().convert(mapper)),
                getFunctionAdapter());
    }

    @Override
    public ThrowingLongStream<X> flatMap(
            ThrowingLongFunction<? extends ThrowingLongStream<? extends X>, ? extends X> mapper) {
        @SuppressWarnings("unchecked") Function<? super ThrowingLongStream<? extends X>, ? extends LongStream> c = s -> ThrowingBridge.of(
                (ThrowingLongStream<X>) s, getExceptionClass());
        return chain(getDelegate().flatMap(getFunctionAdapter().convert(mapper.andThen(c))));
    }

    @Override
    public ThrowingLongStream<X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public ThrowingLongStream<X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public ThrowingLongStream<X> peek(ThrowingLongConsumer<? extends X> action) {
        return chain(getDelegate().peek(getFunctionAdapter().convert(action)));
    }

    @Override
    public ThrowingLongStream<X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public ThrowingLongStream<X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingLongConsumer<? extends X> action) throws X {
        unmaskException(() -> getDelegate().forEach(getFunctionAdapter().convert(action)));
    }

    @Override
    public void forEachOrdered(ThrowingLongConsumer<? extends X> action) throws X {
        unmaskException(() -> getDelegate().forEachOrdered(getFunctionAdapter().convert(action)));
    }

    @Override
    public long[] toArray() throws X {
        return unmaskException(getDelegate()::toArray);
    }

    @Override
    public long reduce(long identity, ThrowingLongBinaryOperator<? extends X> op) throws X {
        return unmaskException(() -> getDelegate().reduce(identity,
                getFunctionAdapter().convert(op)));
    }

    @Override
    public OptionalLong reduce(ThrowingLongBinaryOperator<? extends X> op) throws X {
        return unmaskException(() -> getDelegate().reduce(getFunctionAdapter().convert(op)));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingObjLongConsumer<R, ? extends X> accumulator,
            ThrowingBiConsumer<R, R, ? extends X> combiner) throws X {
        return unmaskException(() -> getDelegate().collect(getFunctionAdapter().convert(supplier),
                getFunctionAdapter().convert(accumulator), getFunctionAdapter().convert(combiner)));
    }

    @Override
    public long sum() throws X {
        return unmaskException(getDelegate()::sum);
    }

    @Override
    public OptionalLong min() throws X {
        return unmaskException(getDelegate()::min);
    }

    @Override
    public OptionalLong max() throws X {
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
    public LongSummaryStatistics summaryStatistics() throws X {
        return unmaskException(getDelegate()::summaryStatistics);
    }

    @Override
    public boolean anyMatch(ThrowingLongPredicate<? extends X> predicate) throws X {
        return unmaskException(() -> getDelegate().anyMatch(getFunctionAdapter().convert(predicate)));
    }

    @Override
    public boolean allMatch(ThrowingLongPredicate<? extends X> predicate) throws X {
        return unmaskException(() -> getDelegate().allMatch(getFunctionAdapter().convert(predicate)));
    }

    @Override
    public boolean noneMatch(ThrowingLongPredicate<? extends X> predicate) throws X {
        return unmaskException(() -> getDelegate().noneMatch(
                getFunctionAdapter().convert(predicate)));
    }

    @Override
    public OptionalLong findFirst() throws X {
        return unmaskException(getDelegate()::findFirst);
    }

    @Override
    public OptionalLong findAny() throws X {
        return unmaskException(getDelegate()::findAny);
    }

    @Override
    public ThrowingDoubleStream<X> asDoubleStream() {
        return ThrowingBridge.of(getDelegate().asDoubleStream(), getFunctionAdapter());
    }

    @Override
    public ThrowingStream<Long, X> boxed() {
        return ThrowingBridge.of(getDelegate().boxed(), getFunctionAdapter());
    }

    @Override
    public <Y extends Throwable> ThrowingLongStream<Y> rethrow(Class<Y> e,
            Function<? super X, ? extends Y> mapper) {
        RethrowChain<AdapterException, Y> c = getChain().rethrow(mapper);
        return new CheckedLongStream<>(getDelegate(), new FunctionAdapter<>(e), c);
    }
}
