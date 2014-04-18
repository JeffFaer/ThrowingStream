package throwing.bridge;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

import throwing.stream.ThrowingBaseStream;

abstract class UncheckedBaseStream<T, S extends BaseStream<T, S>, D extends ThrowingBaseStream<T, ?, D>> extends
        UncheckedBridge<D, Throwable> implements BaseStream<T, S>, BaseStreamBridge<S, D> {
    UncheckedBaseStream(D delegate) {
        super(delegate, Throwable.class);
    }
    
    @Override
    public Iterator<T> iterator() {
        return ThrowingBridge.unchecked(getDelegate().iterator());
    }
    
    @Override
    public Spliterator<T> spliterator() {
        return ThrowingBridge.unchecked(getDelegate().spliterator());
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
