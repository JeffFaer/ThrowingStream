package throwing.bridge;

class AbstractBridge<D> implements Bridge<D> {
    private final D delegate;
    
    AbstractBridge(D delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public D getDelegate() {
        return delegate;
    }
}
