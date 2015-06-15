package throwing.bridge;

import java.util.Spliterator;

import throwing.ThrowingSpliterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

class CheckedSpliterator<T, S extends Spliterator<T>, X extends Throwable> extends CheckedBridge<S, X> implements
        ThrowingSpliterator<T, X> {
    static class OfInt<X extends Throwable> extends CheckedSpliterator<Integer, Spliterator.OfInt, X> implements
            ThrowingSpliterator.OfInt<X> {
        OfInt(Spliterator.OfInt delegate, FunctionBridge<X> bridge) {
            super(delegate, bridge);
        }
        
        @Override
        public ThrowingSpliterator.OfInt<X> trySplit() {
            return new CheckedSpliterator.OfInt<>(getDelegate().trySplit(), getBridge());
        }
        
        @Override
        public boolean tryAdvance(ThrowingIntConsumer<X> action) throws X {
            return unmaskBridgeException(() -> getDelegate().tryAdvance(getBridge().convert(action)));
        }
    }
    
    static class OfLong<X extends Throwable> extends CheckedSpliterator<Long, Spliterator.OfLong, X> implements
            ThrowingSpliterator.OfLong<X> {
        OfLong(Spliterator.OfLong delegate, FunctionBridge<X> bridge) {
            super(delegate, bridge);
        }
        
        @Override
        public ThrowingSpliterator.OfLong<X> trySplit() {
            return new CheckedSpliterator.OfLong<>(getDelegate().trySplit(), getBridge());
        }
        
        @Override
        public boolean tryAdvance(ThrowingLongConsumer<X> action) throws X {
            return unmaskBridgeException(() -> getDelegate().tryAdvance(getBridge().convert(action)));
        }
    }
    
    static class OfDouble<X extends Throwable> extends CheckedSpliterator<Double, Spliterator.OfDouble, X> implements
            ThrowingSpliterator.OfDouble<X> {
        OfDouble(Spliterator.OfDouble delegate, FunctionBridge<X> bridge) {
            super(delegate, bridge);
        }
        
        @Override
        public ThrowingSpliterator.OfDouble<X> trySplit() {
            return new CheckedSpliterator.OfDouble<>(getDelegate().trySplit(), getBridge());
        }
        
        @Override
        public boolean tryAdvance(ThrowingDoubleConsumer<X> action) throws X {
            return unmaskBridgeException(() -> getDelegate().tryAdvance(getBridge().convert(action)));
        }
    }
    
    CheckedSpliterator(S delegate, FunctionBridge<X> bridge) {
        super(delegate, bridge);
    }
    
    @Override
    public boolean tryAdvance(ThrowingConsumer<? super T, ? extends X> action) throws X {
        return unmaskBridgeException(() -> getDelegate().tryAdvance(getBridge().convert(action)));
    }
    
    @Override
    public ThrowingSpliterator<T, X> trySplit() {
        return new CheckedSpliterator<>(getDelegate().trySplit(), getBridge());
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
