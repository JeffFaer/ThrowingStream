package throwing.stream.adapter;

public abstract class AbstractAdapter<D> implements Adapter<D> {
    private final D delegate;

    protected AbstractAdapter(D delegate) {
        this.delegate = delegate;
    }

    @Override
    public D getDelegate() {
        return delegate;
    }
}
