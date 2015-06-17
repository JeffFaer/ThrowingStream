package throwing.stream.union.adapter;

import throwing.stream.ThrowingBaseStream;
import throwing.stream.union.UnionBaseStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionThrowable;

abstract class UnionBaseStreamAdapter<T, D extends ThrowingBaseStream<T, X, D>, S extends S2, S2 extends ThrowingBaseStream<T, Throwable, S2>, X extends UnionThrowable>
        extends UnionAdapter<D, S, X> implements UnionBaseStream<T, X, S, S2> {
    protected UnionBaseStreamAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
        super(delegate, adapter);
    }

    @Override
    public boolean isParallel() {
        return getDelegate().isParallel();
    }

    @Override
    public void close() {
        getDelegate().close();
    }

    @Override
    public UnionIterator<T, X> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public UnionSpliterator<T, X> spliterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public S onClose(Runnable closeHandler) {
        return chain(getDelegate().onClose(closeHandler));
    }

    @Override
    public S parallel() {
        return chain(getDelegate().parallel());
    }

    @Override
    public S sequential() {
        return chain(getDelegate().sequential());
    }

    @Override
    public S unordered() {
        return chain(getDelegate().unordered());
    }
}
