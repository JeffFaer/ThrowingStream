package throwing.stream.bridge;

class AbstractBridge<D, X extends Throwable> implements Bridge<D> {
    private final D delegate;
    private final Class<X> x;

    AbstractBridge(D delegate, Class<X> x) {
        this.delegate = delegate;
        this.x = x;
    }

    @Override
    public D getDelegate() {
        return delegate;
    }

    public Class<X> getExceptionClass() {
        return x;
    }
}
