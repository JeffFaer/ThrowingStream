package throwing.bridge;

import java.util.Spliterator;
import java.util.function.Consumer;

import throwing.ThrowingSpliterator;

class UncheckedSpliterator<T> extends UncheckedBridge<ThrowingSpliterator<T, ?>, Throwable> implements Spliterator<T> {
    UncheckedSpliterator(ThrowingSpliterator<T, ?> delegate) {
        super(delegate, Throwable.class);
    }
    
    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return launder(() -> getDelegate().tryAdvance(action::accept));
    }
    
    @Override
    public Spliterator<T> trySplit() {
        return new UncheckedSpliterator<>(getDelegate().trySplit());
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
