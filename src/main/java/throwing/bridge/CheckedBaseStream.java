package throwing.bridge;

import java.util.stream.BaseStream;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.stream.ThrowingBaseStream;

abstract class CheckedBaseStream<T, X extends Throwable, S extends ThrowingBaseStream<T, X, S>, D extends BaseStream<T, D>>
        extends CheckedBridge<D, X> implements ThrowingBaseStream<T, X, S> {
    CheckedBaseStream(D delegate, FunctionBridge<X> bridge) {
        super(delegate, bridge);
    }
    
    protected S chain(D newDelegate) {
        if (newDelegate == getDelegate()) {
            return getStream();
        } else {
            return updateStream(newDelegate);
        }
    }
    
    protected abstract S getStream();
    
    protected abstract S updateStream(D delegate);
    
    @Override
    public ThrowingIterator<T, X> iterator() {
        return ThrowingBridge.of(getDelegate().iterator(), getBridge());
    }
    
    @Override
    public ThrowingSpliterator<T, X> spliterator() {
        return ThrowingBridge.of(getDelegate().spliterator(), getBridge());
    }
    
    @Override
    public boolean isParallel() {
        return getDelegate().isParallel();
    }
    
    @Override
    public S sequential() {
        return chain(getDelegate().sequential());
    }
    
    @Override
    public S parallel() {
        return chain(getDelegate().parallel());
    }
    
    @Override
    public S unordered() {
        return chain(getDelegate().unordered());
    }
    
    @Override
    public S onClose(Runnable closeHandler) {
        return chain(getDelegate().onClose(closeHandler));
    }
    
    @Override
    public void close() {
        getDelegate().close();
    }
}
