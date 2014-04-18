package throwing.bridge;

abstract class Bridge<D> {
    private final D delegate;
    
    Bridge(D delegate) {
        this.delegate = delegate;
    }
    
    protected D getDelegate() {
        return delegate;
    }
}
