package throwing.stream.union.adapter;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;

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
import throwing.stream.ThrowingIntStream;
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionIntStreamAdapter<X extends UnionThrowable>
        extends
        UnionBaseStreamAdapter<Integer, ThrowingIntStream<X>, UnionIntStream<X>, ThrowingIntStream<Throwable>, X>
        implements UnionIntStream<X> {
    UnionIntStreamAdapter(ThrowingIntStream<X> delegate, UnionFunctionAdapter<X> adapter) {
        super(delegate, adapter);
    }

    @Override
    public UnionIntStream<X> getSelf() {
        return this;
    }

    @Override
    public UnionIntStream<X> createNewStream(ThrowingIntStream<X> delegate) {
        return new UnionIntStreamAdapter<>(delegate, getAdapter());
    }

    @Override
    public <Y extends Throwable> ThrowingIntStream<Y> rethrow(Class<Y> y,
            Function<? super Throwable, ? extends Y> mapper) {
        return getDelegate().rethrow(y, mapper.compose(X::getCause));
    }

    @Override
    public UnionIterator.OfInt<X> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public UnionSpliterator.OfInt<X> spliterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public UnionIntStream<X> filter(ThrowingIntPredicate<? extends Throwable> predicate) {
        return chain(getDelegate().filter(getAdapter().convert(predicate)));
    }

    @Override
    public UnionIntStream<X> map(ThrowingIntUnaryOperator<? extends Throwable> mapper) {
        return chain(getDelegate().map(getAdapter().convert(mapper)));
    }

    @Override
    public <U> UnionStream<U, X> mapToObj(
            ThrowingIntFunction<? extends U, ? extends Throwable> mapper) {
        return new UnionStreamAdapter<>(getDelegate().mapToObj(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionLongStream<X> mapToLong(ThrowingIntToLongFunction<? extends Throwable> mapper) {
        return new UnionLongStreamAdapter<>(getDelegate().mapToLong(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionDoubleStream<X> mapToDouble(ThrowingIntToDoubleFunction<? extends Throwable> mapper) {
        return new UnionDoubleStreamAdapter<>(getDelegate().mapToDouble(
                getAdapter().convert(mapper)), getAdapter());
    }

    @Override
    public UnionIntStream<X> flatMap(
            ThrowingIntFunction<? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper) {
        Function<ThrowingIntStream<? extends Throwable>, ThrowingIntStream<X>> f = getAdapter()::convert;
        return chain(getDelegate().flatMap(getAdapter().convert(mapper).andThen(f)));
    }

    @Override
    public UnionIntStream<X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public UnionIntStream<X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public UnionIntStream<X> peek(ThrowingIntConsumer<? extends Throwable> action) {
        return chain(getDelegate().peek(getAdapter().convert(action)));
    }

    @Override
    public UnionIntStream<X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public UnionIntStream<X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingIntConsumer<? extends Throwable> action) throws X {
        getDelegate().forEach(getAdapter().convert(action));
    }

    @Override
    public void forEachOrdered(ThrowingIntConsumer<? extends Throwable> action) throws X {
        getDelegate().forEachOrdered(getAdapter().convert(action));
    }

    @Override
    public int[] toArray() throws X {
        return getDelegate().toArray();
    }

    @Override
    public int reduce(int identity, ThrowingIntBinaryOperator<? extends Throwable> op) throws X {
        return getDelegate().reduce(identity, getAdapter().convert(op));
    }

    @Override
    public OptionalInt reduce(ThrowingIntBinaryOperator<? extends Throwable> op) throws X {
        return getDelegate().reduce(getAdapter().convert(op));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjIntConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
        return getDelegate().collect(getAdapter().convert(supplier),
                getAdapter().convert(accumulator), getAdapter().convert(combiner));
    }

    @Override
    public int sum() throws X {
        return getDelegate().sum();
    }

    @Override
    public OptionalInt min() throws X {
        return getDelegate().min();
    }

    @Override
    public OptionalInt max() throws X {
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
    public IntSummaryStatistics summaryStatistics() throws X {
        return getDelegate().summaryStatistics();
    }

    @Override
    public boolean anyMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X {
        return getDelegate().anyMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean allMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X {
        return getDelegate().allMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean noneMatch(ThrowingIntPredicate<? extends Throwable> predicate) throws X {
        return getDelegate().noneMatch(getAdapter().convert(predicate));
    }

    @Override
    public OptionalInt findFirst() throws X {
        return getDelegate().findFirst();
    }

    @Override
    public OptionalInt findAny() throws X {
        return getDelegate().findAny();
    }

    @Override
    public UnionLongStream<X> asLongStream() {
        return new UnionLongStreamAdapter<>(getDelegate().asLongStream(), getAdapter());
    }

    @Override
    public UnionDoubleStream<X> asDoubleStream() {
        return new UnionDoubleStreamAdapter<>(getDelegate().asDoubleStream(), getAdapter());
    }

    @Override
    public UnionStream<Integer, X> boxed() {
        return new UnionStreamAdapter<>(getDelegate().boxed(), getAdapter());
    }
}
