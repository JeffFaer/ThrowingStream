package throwing.stream.adapter;

import java.util.stream.BaseStream;

import throwing.RethrowChain;
import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.stream.ThrowingBaseStream;

abstract class CheckedBaseStream<T, X extends Throwable, S extends ThrowingBaseStream<T, X, S>, D extends BaseStream<T, D>>
        extends CheckedAdapter<D, X> implements ThrowingBaseStream<T, X, S>,
        BaseStreamAdapter<S, D> {
    CheckedBaseStream(D delegate, FunctionAdapter<X> functionAdapter) {
        super(delegate, functionAdapter);
    }

    CheckedBaseStream(D delegate, FunctionAdapter<X> functionAdapter,
            RethrowChain<AdapterException, X> chain) {
        super(delegate, functionAdapter, chain);
    }

    @Override
    public ThrowingIterator<T, X> iterator() {
        return ThrowingAdapter.of(getDelegate().iterator(), getFunctionAdapter());
    }

    @Override
    public ThrowingSpliterator<T, X> spliterator() {
        return ThrowingAdapter.of(getDelegate().spliterator(), getFunctionAdapter());
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
