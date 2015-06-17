package throwing.stream.adapter;

class AbstractAdapter<D, X extends Throwable> implements Adapter<D> {
    private final D delegate;
    private final Class<X> x;

    AbstractAdapter(D delegate, Class<X> x) {
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
