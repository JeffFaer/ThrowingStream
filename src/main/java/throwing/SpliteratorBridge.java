package throwing;

import java.util.Spliterator;
import java.util.function.Consumer;

import throwing.function.ThrowingConsumer;

class SpliteratorBridge<T, X extends Throwable> extends CheckedExceptionBridge<X> implements ThrowingSpliterator<T, X> {
    private final Spliterator<T> delegate;
    
    SpliteratorBridge(Spliterator<T> delegate, FunctionBridge<X> bridge) {
        super(bridge);
        this.delegate = delegate;
    }
    
    @Override
    public boolean tryAdvance(ThrowingConsumer<? super T, ? extends X> action) throws X {
        return filterBridgeException(() -> delegate.tryAdvance(getBridge().convert(action)));
    }
    
    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return delegate.tryAdvance(action);
    }
    
    @Override
    public ThrowingSpliterator<T, X> trySplit() {
        return new SpliteratorBridge<>(delegate.trySplit(), getBridge());
    }
    
    @Override
    public long estimateSize() {
        return delegate.estimateSize();
    }
    
    @Override
    public int characteristics() {
        return delegate.characteristics();
    }
}
