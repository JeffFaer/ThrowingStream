package throwing.stream.union.adapter;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

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
import throwing.stream.union.UnionDoubleStream;
import throwing.stream.union.UnionIntStream;
import throwing.stream.union.UnionLongStream;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

class UnionStreamAdapter<T, X extends UnionThrowable>
        extends
        UnionBaseStreamAdapter<T, ThrowingStream<T, X>, UnionStream<T, X>, ThrowingStream<T, Throwable>, X>
        implements UnionStream<T, X> {
    UnionStreamAdapter(ThrowingStream<T, X> delegate, UnionFunctionAdapter<X> adapter) {
        super(delegate, adapter);
    }

    @Override
    public UnionStream<T, X> getSelf() {
        return this;
    }

    @Override
    public UnionStream<T, X> createNewStream(ThrowingStream<T, X> delegate) {
        return newStream(delegate);
    }

    private <R> UnionStream<R, X> newStream(ThrowingStream<R, X> delegate) {
        return new UnionStreamAdapter<>(delegate, getAdapter());
    }

    @Override
    public <Y extends Throwable> ThrowingStream<T, Y> rethrow(Class<Y> y,
            Function<? super Throwable, ? extends Y> mapper) {
        return getDelegate().rethrow(y, mapper.compose(X::getCause));
    }

    @Override
    public boolean isParallel() {
        return getDelegate().isParallel();
    }

    @Override
    public void close() {
        getDelegate().close();
    }

    @Override
    public UnionStream<T, X> onClose(Runnable closeHandler) {
        return chain(getDelegate().onClose(closeHandler));
    }

    @Override
    public UnionStream<T, X> parallel() {
        return chain(getDelegate().parallel());
    }

    @Override
    public UnionStream<T, X> sequential() {
        return chain(getDelegate().sequential());
    }

    @Override
    public UnionStream<T, X> unordered() {
        return chain(getDelegate().unordered());
    }

    @Override
    public UnionStream<T, X> filter(ThrowingPredicate<? super T, ? extends Throwable> predicate) {
        return chain(getDelegate().filter(getAdapter().convert(predicate)));
    }

    @Override
    public <R> UnionStream<R, X> map(
            ThrowingFunction<? super T, ? extends R, ? extends Throwable> mapper) {
        return newStream(getDelegate().map(getAdapter().convert(mapper)));
    }

    @Override
    public UnionIntStream<X> mapToInt(ThrowingToIntFunction<? super T, ? extends Throwable> mapper) {
        return new UnionIntStreamAdapter<>(getDelegate().mapToInt(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionLongStream<X> mapToLong(
            ThrowingToLongFunction<? super T, ? extends Throwable> mapper) {
        return new UnionLongStreamAdapter<>(getDelegate().mapToLong(getAdapter().convert(mapper)),
                getAdapter());
    }

    @Override
    public UnionDoubleStream<X> mapToDouble(
            ThrowingToDoubleFunction<? super T, ? extends Throwable> mapper) {
        return new UnionDoubleStreamAdapter<>(getDelegate().mapToDouble(
                getAdapter().convert(mapper)), getAdapter());
    }

    @Override
    public <R> UnionStream<R, X> flatMap(
            ThrowingFunction<? super T, ? extends ThrowingStream<? extends R, ? extends Throwable>, ? extends Throwable> mapper) {
        Function<ThrowingStream<? extends R, ? extends Throwable>, ThrowingStream<? extends R, X>> convertStream = getAdapter()::convert;
        return newStream(getDelegate().flatMap(getAdapter().convert(mapper).andThen(convertStream)));
    }

    @Override
    public UnionIntStream<X> flatMapToInt(
            ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends Throwable>, ? extends Throwable> mapper) {
        Function<ThrowingIntStream<? extends Throwable>, ThrowingIntStream<X>> convertStream = getAdapter()::convert;
        return new UnionIntStreamAdapter<>(getDelegate().flatMapToInt(
                getAdapter().convert(mapper).andThen(convertStream)), getAdapter());
    }

    @Override
    public UnionLongStream<X> flatMapToLong(
            ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends Throwable>, ? extends Throwable> mapper) {
        Function<ThrowingLongStream<? extends Throwable>, ThrowingLongStream<X>> convertStream = getAdapter()::convert;
        return new UnionLongStreamAdapter<>(getDelegate().flatMapToLong(
                getAdapter().convert(mapper).andThen(convertStream)), getAdapter());
    }

    @Override
    public UnionDoubleStream<X> flatMapToDouble(
            ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends Throwable>, ? extends Throwable> mapper) {
        Function<ThrowingDoubleStream<? extends Throwable>, ThrowingDoubleStream<X>> convertStream = getAdapter()::convert;
        return new UnionDoubleStreamAdapter<>(getDelegate().flatMapToDouble(
                getAdapter().convert(mapper).andThen(convertStream)), getAdapter());
    }

    @Override
    public UnionStream<T, X> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public UnionStream<T, X> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public UnionStream<T, X> sorted(ThrowingComparator<? super T, ? extends Throwable> comparator) {
        return chain(getDelegate().sorted(getAdapter().convert(comparator)));
    }

    @Override
    public UnionStream<T, X> peek(ThrowingConsumer<? super T, ? extends Throwable> action) {
        return chain(getDelegate().peek(getAdapter().convert(action)));
    }

    @Override
    public UnionStream<T, X> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public UnionStream<T, X> skip(long n) {
        return chain(getDelegate().skip(n));
    }

    @Override
    public void forEach(ThrowingConsumer<? super T, ? extends Throwable> action) throws X {
        getDelegate().forEach(getAdapter().convert(action));
    }

    @Override
    public void forEachOrdered(ThrowingConsumer<? super T, ? extends Throwable> action) throws X {
        getDelegate().forEachOrdered(getAdapter().convert(action));
    }

    @Override
    public Object[] toArray() throws X {
        return getDelegate().toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) throws X {
        return getDelegate().toArray(generator);
    }

    @Override
    public T reduce(T identity, ThrowingBinaryOperator<T, ? extends Throwable> accumulator)
            throws X {
        return getDelegate().reduce(identity, getAdapter().convert(accumulator));
    }

    @Override
    public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends Throwable> accumulator) throws X {
        return getDelegate().reduce(getAdapter().convert(accumulator));
    }

    @Override
    public <U> U reduce(U identity,
            ThrowingBiFunction<U, ? super T, U, ? extends Throwable> accumulator,
            ThrowingBinaryOperator<U, ? extends Throwable> combiner) throws X {
        return getDelegate().reduce(identity, getAdapter().convert(accumulator),
                getAdapter().convert(combiner));
    }

    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends Throwable> supplier,
            ThrowingBiConsumer<R, ? super T, ? extends Throwable> accumulator,
            ThrowingBiConsumer<R, R, ? extends Throwable> combiner) throws X {
        return getDelegate().collect(getAdapter().convert(supplier),
                getAdapter().convert(accumulator), getAdapter().convert(combiner));
    }

    @Override
    public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends Throwable> collector)
            throws X {
        return getDelegate().collect(getAdapter().convert(collector));
    }

    @Override
    public Optional<T> min(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X {
        return getDelegate().min(getAdapter().convert(comparator));
    }

    @Override
    public Optional<T> max(ThrowingComparator<? super T, ? extends Throwable> comparator) throws X {
        return getDelegate().max(getAdapter().convert(comparator));
    }

    @Override
    public long count() throws X {
        return getDelegate().count();
    }

    @Override
    public boolean anyMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X {
        return getDelegate().anyMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean allMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X {
        return getDelegate().allMatch(getAdapter().convert(predicate));
    }

    @Override
    public boolean noneMatch(ThrowingPredicate<? super T, ? extends Throwable> predicate) throws X {
        return getDelegate().noneMatch(getAdapter().convert(predicate));
    }

    @Override
    public Optional<T> findFirst() throws X {
        return getDelegate().findFirst();
    }

    @Override
    public Optional<T> findAny() throws X {
        return getDelegate().findAny();
    }
}
