package throwing.stream.union.adapter;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.Function;

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
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionDoubleStreamAdapter<X extends UnionThrowable>
        extends
        UnionBaseStreamAdapter<Double, ThrowingDoubleStream<X>, UnionDoubleStream<X>, ThrowingDoubleStream<Throwable>, X>
        implements UnionDoubleStream<X> {
    UnionDoubleStreamAdapter(ThrowingDoubleStream<X> delegate, UnionFunctionAdapter<X> adapter) {
        super(delegate, adapter);
    }

    @Override
    public UnionDoubleStream<X> getSelf() {
        return this;
    }

    @Override
    public UnionDoubleStream<X> createNewStream(ThrowingDoubleStream<X> delegate) {
        return new UnionDoubleStreamAdapter<>(delegate, getAdapter());
    }

    @Override
    public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> y,
            Function<? super Throwable, ? extends Y> mapper) {
        return getDelegate().rethrow(y, mapper.compose(X::getCause));
    }

    @Override
    public UnionIterator.OfDouble<X> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public UnionSpliterator.OfDouble<X> spliterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public UnionDoubleStream<X> filter(ThrowingDoublePredicate<? extends Throwable> predicate) {
        return chain(getDelegate().filter(getAdapter().convert(predicate)));
    }

    @Override
    public UnionDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends Throwable> mapper) {
        return chain(getDelegate().map(getAdapter().convert(mapper)));
    }

    @Override
    public <U> UnionStream<U, X> mapToObj(
            ThrowingDoubleFunction<? extends U, ? extends Throwable> mapper) {
        return new UnionStreamAdapter<>(getDelegate().mapToObj(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends Throwable> mapper) {
        return new UnionIntStreamAdapter<>(getDelegate().mapToInt(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends Throwable> mapper) {
        return new UnionLongStreamAdapter<>(getDelegate().mapToLong(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionDoubleStream<X> flatMap(
            ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper) {
        Function<ThrowingDoubleStream<? extends Throwable>, ThrowingDoubleStream<X>> f = getAdapter()::convert;
        return chain(getDelegate().flatMap(getAdapter().convert(mapper).andThen(f)));
    }

    @Override
    public UnionDoubleStream<X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public UnionDoubleStream<X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public UnionDoubleStream<X> peek(ThrowingDoubleConsumer<? extends Throwable> action) {
        return chain(getDelegate().peek(getAdapter().convert(action)));
    }

    @Override
    public UnionDoubleStream<X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public UnionDoubleStream<X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingDoubleConsumer<? extends Throwable> action) throws X {
        getDelegate().forEach(getAdapter().convert(action));
    }

    @Override
    public void forEachOrdered(ThrowingDoubleConsumer<? extends Throwable> action) throws X {
        getDelegate().forEachOrdered(getAdapter().convert(action));
    }

    @Override
    public double[] toArray() throws X {
        return getDelegate().toArray();
    }

    @Override
    public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends Throwable> op)
            throws X {
        return getDelegate().reduce(identity, getAdapter().convert(op));
    }

    @Override
    public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends Throwable> op) throws X {
        return getDelegate().reduce(getAdapter().convert(op));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingObjDoubleConsumer<R, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
        return getDelegate().collect(getAdapter().convert(supplier),
                getAdapter().convert(accumulator), getAdapter().convert(combiner));
    }

    @Override
    public double sum() throws X {
        return getDelegate().sum();
    }

    @Override
    public OptionalDouble min() throws X {
        return getDelegate().min();
    }

    @Override
    public OptionalDouble max() throws X {
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
    public DoubleSummaryStatistics summaryStatistics() throws X {
        return getDelegate().summaryStatistics();
    }

    @Override
    public boolean anyMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X {
        return getDelegate().anyMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean allMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X {
        return getDelegate().allMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean noneMatch(ThrowingDoublePredicate<? extends Throwable> predicate) throws X {
        return getDelegate().noneMatch(getAdapter().convert(predicate));
    }

    @Override
    public OptionalDouble findFirst() throws X {
        return getDelegate().findFirst();
    }

    @Override
    public OptionalDouble findAny() throws X {
        return getDelegate().findAny();
    }

    @Override
    public UnionStream<Double, X> boxed() {
        return new UnionStreamAdapter<>(getDelegate().boxed(), getAdapter());
    }
}
