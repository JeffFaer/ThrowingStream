package throwing.stream.union.adapter;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.Function;

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
import throwing.stream.ThrowingLongStream;
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionLongStreamAdapter<X extends UnionThrowable>
        extends
        UnionBaseStreamAdapter<Long, ThrowingLongStream<X>, UnionLongStream<X>, ThrowingLongStream<Throwable>, X>
        implements UnionLongStream<X> {
    UnionLongStreamAdapter(ThrowingLongStream<X> delegate, UnionFunctionAdapter<X> adapter) {
        super(delegate, adapter);
    }

    @Override
    public UnionLongStream<X> getSelf() {
        return this;
    }

    @Override
    public UnionLongStream<X> createNewStream(ThrowingLongStream<X> delegate) {
        return new UnionLongStreamAdapter<>(delegate, getAdapter());
    }

    @Override
    public <Y extends Throwable> ThrowingLongStream<Y> rethrow(Class<Y> y,
            Function<? super Throwable, ? extends Y> mapper) {
        return getDelegate().rethrow(y, mapper.compose(X::getCause));
    }

    @Override
    public UnionIterator.OfLong<X> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public UnionSpliterator.OfLong<X> spliterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public UnionLongStream<X> filter(ThrowingLongPredicate<? extends Throwable> predicate) {
        return chain(getDelegate().filter(getAdapter().convert(predicate)));
    }

    @Override
    public UnionLongStream<X> map(ThrowingLongUnaryOperator<? extends Throwable> mapper) {
        return chain(getDelegate().map(getAdapter().convert(mapper)));
    }

    @Override
    public <U> UnionStream<U, X> mapToObj(
            ThrowingLongFunction<? extends U, ? extends Throwable> mapper) {
        return new UnionStreamAdapter<>(getDelegate().mapToObj(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends Throwable> mapper) {
        return new UnionIntStreamAdapter<>(getDelegate().mapToInt(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends Throwable> mapper) {
        return new UnionDoubleStreamAdapter<>(getDelegate().mapToDouble(
                getAdapter().convert(mapper)), getAdapter());
    }

    @Override
    public UnionLongStream<X> flatMap(
            ThrowingLongFunction<? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper) {
        Function<ThrowingLongStream<? extends Throwable>, ThrowingLongStream<X>> f = getAdapter()::convert;
        return chain(getDelegate().flatMap(getAdapter().convert(mapper).andThen(f)));
    }

    @Override
    public UnionLongStream<X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public UnionLongStream<X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public UnionLongStream<X> peek(ThrowingLongConsumer<? extends Throwable> action) {
        return chain(getDelegate().peek(getAdapter().convert(action)));
    }

    @Override
    public UnionLongStream<X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public UnionLongStream<X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingLongConsumer<? extends Throwable> action) throws X {
        getDelegate().forEach(getAdapter().convert(action));
    }

    @Override
    public void forEachOrdered(ThrowingLongConsumer<? extends Throwable> action) throws X {
        getDelegate().forEachOrdered(getAdapter().convert(action));
    }

    @Override
    public long[] toArray() throws X {
        return getDelegate().toArray();
    }

    @Override
    public long reduce(long identity, ThrowingLongBinaryOperator<? extends Throwable> op) throws X {
        return getDelegate().reduce(identity, getAdapter().convert(op));
    }

    @Override
    public OptionalLong reduce(ThrowingLongBinaryOperator<? extends Throwable> op) throws X {
        return getDelegate().reduce(getAdapter().convert(op));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjLongConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
        return getDelegate().collect(getAdapter().convert(supplier),
                getAdapter().convert(accumulator), getAdapter().convert(combiner));
    }

    @Override
    public long sum() throws X {
        return getDelegate().sum();
    }

    @Override
    public OptionalLong min() throws X {
        return getDelegate().min();
    }

    @Override
    public OptionalLong max() throws X {
        return getDelegate().max();
    }

    @Override
    public long count() throws X {
        return getDelegate().count();
    }

    @Override
    public OptionalDouble average() throws X {
        return getDelegate().average();
    }

    @Override
    public LongSummaryStatistics summaryStatistics() throws X {
        return getDelegate().summaryStatistics();
    }

    @Override
    public boolean anyMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X {
        return getDelegate().anyMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean allMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X {
        return getDelegate().allMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean noneMatch(ThrowingLongPredicate<? extends Throwable> predicate) throws X {
        return getDelegate().noneMatch(getAdapter().convert(predicate));
    }

    @Override
    public OptionalLong findFirst() throws X {
        return getDelegate().findFirst();
    }

    @Override
    public OptionalLong findAny() throws X {
        return getDelegate().findAny();
    }

    @Override
    public UnionDoubleStream<X> asDoubleStream() {
        return new UnionDoubleStreamAdapter<>(getDelegate().asDoubleStream(), getAdapter());
    }

    @Override
    public UnionStream<Long, X> boxed() {
        return new UnionStreamAdapter<>(getDelegate().boxed(), getAdapter());
    }
}
