package throwing;

import java.util.Spliterator;
import java.util.function.Consumer;

import throwing.function.ThrowingConsumer;

class SpliteratorBridge<T, X extends Throwable> extends CheckedExceptionBridge<Spliterator<T>, X> implements
        ThrowingSpliterator<T, X> {
    SpliteratorBridge(Spliterator<T> delegate, FunctionBridge<X> bridge) {
        super(delegate, bridge);
    }
    
    @Override
    public boolean tryAdvance(ThrowingConsumer<? super T, ? extends X> action) throws X {
        return filterBridgeException(() -> getDelegate().tryAdvance(getBridge().convert(action)));
    }
    
    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return getDelegate().tryAdvance(action);
    }
    
    @Override
    public ThrowingSpliterator<T, X> trySplit() {
        return new SpliteratorBridge<>(getDelegate().trySplit(), getBridge());
    }
    
    @Override
    public long estimateSize() {
        return getDelegate().estimateSize();
    }
    
    @Override
    public int characteristics() {
        return getDelegate().characteristics();
    }
}
