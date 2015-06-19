package throwing.stream.adapter;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.function.ThrowingFunction;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

class UncheckedStream<T, X extends Throwable> extends
        UncheckedBaseStream<T, X, Stream<T>, ThrowingStream<T, X>> implements Stream<T> {
    UncheckedStream(ThrowingStream<T, X> delegate, Class<X> x) {
        super(delegate, x);
    }

    @Override
    public Stream<T> getSelf() {
        return this;
    }

    @Override
    public Stream<T> createNewStream(ThrowingStream<T, X> delegate) {
        return newStream(delegate);
    }

    private <R> Stream<R> newStream(ThrowingStream<R, X> delegate) {
        return new UncheckedStream<>(delegate, getExceptionClass());
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return chain(getDelegate().filter(predicate::test));
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        ThrowingFunction<? super T, ? extends R, ? extends X> f = mapper::apply;
        return newStream(getDelegate().map(f));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return ThrowingBridge.of(getDelegate().mapToInt(mapper::applyAsInt), getExceptionClass());
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return ThrowingBridge.of(getDelegate().mapToLong(mapper::applyAsLong), getExceptionClass());
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return ThrowingBridge.of(getDelegate().mapToDouble(mapper::applyAsDouble),
                getExceptionClass());
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        Function<? super T, ? extends ThrowingStream<? extends R, ? extends X>> f = mapper.andThen(s -> ThrowingBridge.of(
                (Stream<? extends R>) s, getExceptionClass()));
        return newStream(getDelegate().flatMap(f::apply));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        Function<? super T, ? extends ThrowingIntStream<? extends X>> f = mapper.andThen(s -> ThrowingBridge.of(
                s, getExceptionClass()));
        return ThrowingBridge.of(getDelegate().flatMapToInt(f::apply), getExceptionClass());
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        Function<? super T, ? extends ThrowingLongStream<? extends X>> f = mapper.andThen(s -> ThrowingBridge.of(
                s, getExceptionClass()));
        return ThrowingBridge.of(getDelegate().flatMapToLong(f::apply), getExceptionClass());
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        Function<? super T, ? extends ThrowingDoubleStream<? extends X>> f = mapper.andThen(s -> ThrowingBridge.of(
                s, getExceptionClass()));
        return ThrowingBridge.of(getDelegate().flatMapToDouble(f::apply), getExceptionClass());
    }

    @Override
    public Stream<T> distinct() {
        return chain(getDelegate().distinct());
    }

    @Override
    public Stream<T> sorted() {
        return chain(getDelegate().sorted());
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return chain(getDelegate().sorted(comparator::compare));
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return chain(getDelegate().peek(action::accept));
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return chain(getDelegate().limit(maxSize));
    }

    @Override
    public Stream<T> skip(long n) {
        return chain(getDelegate().limit(n));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        maskException(() -> getDelegate().forEach(action::accept));
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        maskException(() -> getDelegate().forEachOrdered(action::accept));
    }

    @Override
    public Object[] toArray() {
        return maskException((ThrowingSupplier<Object[], X>) getDelegate()::toArray);
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return maskException(() -> getDelegate().toArray(generator));
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return maskException(() -> getDelegate().reduce(identity, accumulator::apply));
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return maskException(() -> getDelegate().reduce(accumulator::apply));
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator,
            BinaryOperator<U> combiner) {
        return maskException(() -> getDelegate().reduce(identity, accumulator::apply,
                combiner::apply));
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator,
            BiConsumer<R, R> combiner) {
        return maskException(() -> getDelegate().collect(supplier::get, accumulator::accept,
                combiner::accept));
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return maskException(() -> getDelegate().collect(ThrowingBridge.of(collector)));
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return maskException(() -> getDelegate().min(comparator::compare));
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return maskException(() -> getDelegate().max(comparator::compare));
    }

    @Override
    public long count() {
        return maskException(getDelegate()::count);
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return maskException(() -> getDelegate().anyMatch(predicate::test));
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return maskException(() -> getDelegate().allMatch(predicate::test));
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return maskException(() -> getDelegate().noneMatch(predicate::test));
    }

    @Override
    public Optional<T> findFirst() {
        return maskException(getDelegate()::findFirst);
    }

    @Override
    public Optional<T> findAny() {
        return maskException(getDelegate()::findAny);
    }
}
