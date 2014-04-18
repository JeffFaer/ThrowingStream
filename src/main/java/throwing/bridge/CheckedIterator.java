package throwing.bridge;

import java.util.Iterator;

import throwing.ThrowingIterator;

class CheckedIterator<E, X extends Throwable> extends CheckedBridge<Iterator<E>, X> implements
        ThrowingIterator<E, X> {
    CheckedIterator(Iterator<E> delegate, FunctionBridge<X> bridge) {
        super(delegate, bridge);
    }
    
    @Override
    public boolean hasNext() throws X {
        return filterBridgeException(getDelegate()::hasNext);
    }
    
    @Override
    public E next() throws X {
        return filterBridgeException(getDelegate()::next);
    }
}
