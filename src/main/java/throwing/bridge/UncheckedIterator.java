package throwing.bridge;

import java.util.Iterator;

import throwing.ThrowingIterator;

class UncheckedIterator<T> extends UncheckedBridge<ThrowingIterator<T, ?>, Throwable> implements Iterator<T> {
    UncheckedIterator(ThrowingIterator<T, ?> delegate) {
        super(delegate, Throwable.class);
    }
    
    @Override
    public boolean hasNext() {
        return launder(getDelegate()::hasNext);
    }
    
    @Override
    public T next() {
        return launder(getDelegate()::next);
    }
}
