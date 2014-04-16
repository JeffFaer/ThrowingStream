package throwing.stream;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.CheckedExceptionBridge;
import throwing.ThrowingComparator;
import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;

class StreamBridge<T, X extends Throwable> extends CheckedExceptionBridge<X> implements ThrowingStream<T, X> {
    private final Stream<T> delegate;
    
    StreamBridge(Stream<T> delegate, Class<X> x) {
        super(x);
        this.delegate = delegate;
    }
    
    @SuppressWarnings("unchecked")
    private <R> StreamBridge<R, X> chain(Stream<R> newStream) {
        return newStream == delegate ? (StreamBridge<R, X>) this : new StreamBridge<>(newStream,
                getBridge().getExceptionClass());
    }
    
    @Override
    public ThrowingIterator<T, X> iterator() {
        return ThrowingIterator.of(delegate.iterator(), getBridge().getExceptionClass());
    }
    
    @Override
    public ThrowingSpliterator<T, X> spliterator() {
        return ThrowingSpliterator.of(delegate.spliterator(), getBridge().getExceptionClass());
    }
    
    @Override
    public boolean isParallel() {
        return delegate.isParallel();
    }
    
    @Override
    public ThrowingStream<T, X> sequential() {
        return chain(delegate.sequential());
    }
    
    @Override
    public ThrowingStream<T, X> parallel() {
        return chain(delegate.parallel());
    }
    
    @Override
    public ThrowingStream<T, X> unordered() {
        return chain(delegate.unordered());
    }
    
    @Override
    public ThrowingStream<T, X> onClose(Runnable closeHandler) {
        return chain(delegate.onClose(closeHandler));
    }
    
    @Override
    public void close() {
        delegate.close();
    }
    
    @Override
    public ThrowingStream<T, X> filter(ThrowingPredicate<? super T, ? extends X> predicate) {
        return chain(delegate.filter(getBridge().convert(predicate)));
    }
    
    @Override
    public <R> ThrowingStream<R, X> map(ThrowingFunction<? super T, ? extends R, ? extends X> mapper) {
        return chain(delegate.map(getBridge().convert(mapper)));
    }
    
    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return delegate.mapToInt(mapper);
    }
    
    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return delegate.mapToLong(mapper);
    }
    
    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return delegate.mapToDouble(mapper);
    }
    
    @Override
    public <R> ThrowingStream<R, X> flatMap(
            ThrowingFunction<? super T, ? extends Stream<? extends R>, ? extends X> mapper) {
        return chain(delegate.flatMap(getBridge().convert(mapper)));
    }
    
    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return delegate.flatMapToInt(mapper);
    }
    
    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return delegate.flatMapToLong(mapper);
    }
    
    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return delegate.flatMapToDouble(mapper);
    }
    
    @Override
    public ThrowingStream<T, X> distinct() throws X {
        return chain(filterBridgeException(delegate::distinct));
    }
    
    @Override
    public ThrowingStream<T, X> sorted() throws X {
        return chain(filterBridgeException((Supplier<Stream<T>>) delegate::sorted));
    }
    
    @Override
    public ThrowingStream<T, X> sorted(ThrowingComparator<? super T, ? extends X> comparator) throws X {
        return chain(filterBridgeException(() -> delegate.sorted(getBridge().convert(comparator))));
    }
    
    @Override
    public ThrowingStream<T, X> peek(ThrowingConsumer<? super T, ? extends X> action) {
        return chain(delegate.peek(getBridge().convert(action)));
    }
    
    @Override
    public ThrowingStream<T, X> limit(long maxSize) throws X {
        return chain(filterBridgeException(() -> delegate.limit(maxSize)));
    }
    
    @Override
    public ThrowingStream<T, X> skip(long n) throws X {
        return chain(filterBridgeException(() -> delegate.skip(n)));
    }
    
    @Override
    public void forEach(ThrowingConsumer<? super T, ? extends X> action) throws X {
        filterBridgeException(() -> delegate.forEach(getBridge().convert(action)));
    }
    
    @Override
    public void forEachOrdered(ThrowingConsumer<? super T, ? extends X> action) throws X {
        filterBridgeException(() -> delegate.forEachOrdered(getBridge().convert(action)));
    }
    
    @Override
    public Object[] toArray() throws X {
        return filterBridgeException((Supplier<Object[]>) delegate::toArray);
    }
    
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) throws X {
        return filterBridgeException(() -> delegate.toArray(generator));
    }
    
    @Override
    public T reduce(T identity, ThrowingBinaryOperator<T, ? extends X> accumulator) throws X {
        return filterBridgeException(() -> delegate.reduce(identity, getBridge().convert(accumulator)));
    }
    
    @Override
    public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends X> accumulator) throws X {
        return filterBridgeException(() -> delegate.reduce(getBridge().convert(accumulator)));
    }
    
    @Override
    public <U> U reduce(U identity, ThrowingBiFunction<U, ? super T, U, ? extends X> accumulator,
            ThrowingBinaryOperator<U, ? extends X> combiner) throws X {
        return filterBridgeException(() -> delegate.reduce(identity, getBridge().convert(accumulator),
                getBridge().convert(combiner)));
    }
    
    @Override
    public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
            ThrowingBiConsumer<R, ? super T, ? extends X> accumulator, ThrowingBiConsumer<R, R, ? extends X> combiner)
        throws X {
        return filterBridgeException(() -> delegate.collect(getBridge().convert(supplier),
                getBridge().convert(accumulator), getBridge().convert(combiner)));
    }
    
    @Override
    public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends X> collector) throws X {
        return filterBridgeException(() -> delegate.collect(getBridge().convert(collector)));
    }
    
    @Override
    public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws X {
        return filterBridgeException(() -> delegate.min(getBridge().convert(comparator)));
    }
    
    @Override
    public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws X {
        return filterBridgeException(() -> delegate.max(getBridge().convert(comparator)));
    }
    
    @Override
    public long count() throws X {
        return filterBridgeException(delegate::count);
    }
    
    @Override
    public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
        return filterBridgeException(() -> delegate.anyMatch(getBridge().convert(predicate)));
    }
    
    @Override
    public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
        return filterBridgeException(() -> delegate.allMatch(getBridge().convert(predicate)));
    }
    
    @Override
    public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X {
        return filterBridgeException(() -> delegate.noneMatch(getBridge().convert(predicate)));
    }
    
    @Override
    public Optional<T> findFirst() throws X {
        return filterBridgeException(delegate::findFirst);
    }
    
    @Override
    public Optional<T> findAny() throws X {
        return filterBridgeException(delegate::findAny);
    }
}
