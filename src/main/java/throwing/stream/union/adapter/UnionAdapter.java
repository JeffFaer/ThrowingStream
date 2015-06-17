package throwing.stream.union.adapter;

import throwing.stream.ThrowingStream;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

abstract class UnionAdapter<D, S, X extends UnionThrowable> {
    private final D delegate;
    private final UnionFunctionAdapter<X> adapter;

    protected UnionAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
        this.delegate = delegate;
        this.adapter = adapter;
    }

    public D getDelegate() {
        return delegate;
    }

    public UnionFunctionAdapter<X> getAdapter() {
        return adapter;
    }

    public S chain(D newDelegate) {
        if (newDelegate == getDelegate()) {
            return getSelf();
        } else {
            return createNewStream(newDelegate);
        }
    }

    public abstract S getSelf();

    public abstract S createNewStream(D delegate);

    static <T, X extends UnionThrowable> UnionStream<T, X> of(ThrowingStream<T, X> stream,
            UnionFunctionAdapter<X> adapter) {
        return new UnionStreamAdapter<>(stream, adapter);
    }
}
