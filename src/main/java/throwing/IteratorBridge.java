package throwing;

import java.util.Iterator;

class IteratorBridge<E, X extends Throwable> extends CheckedExceptionBridge<Iterator<E>, X> implements
        ThrowingIterator<E, X> {
    IteratorBridge(Iterator<E> delegate, FunctionBridge<X> bridge) {
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
