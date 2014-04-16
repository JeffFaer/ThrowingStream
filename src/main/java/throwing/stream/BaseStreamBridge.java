package throwing.stream;

import java.util.stream.BaseStream;

import throwing.CheckedExceptionBridge;
import throwing.FunctionBridge;
import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;

abstract class BaseStreamBridge<T, X extends Throwable, S extends ThrowingBaseStream<T, X, S>, D extends BaseStream<T, D>>
        extends CheckedExceptionBridge<D, X> implements ThrowingBaseStream<T, X, S> {
    BaseStreamBridge(D delegate, FunctionBridge<X> bridge) {
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
        return ThrowingIterator.of(getDelegate().iterator(), getBridge());
    }
    
    @Override
    public ThrowingSpliterator<T, X> spliterator() {
        return ThrowingSpliterator.of(getDelegate().spliterator(), getBridge());
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
