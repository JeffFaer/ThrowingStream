package throwing.bridge;

import throwing.ThrowingRunnable;
import throwing.function.ThrowingSupplier;

class UncheckedBridge<D, X extends Throwable> extends AbstractBridge<D> {
    private final Class<X> x;
    
    UncheckedBridge(D delegate, Class<X> x) {
        super(delegate);
        this.x = x;
    }
    
    public Class<X> getExceptionClass() {
        return x;
    }
    
    protected void launder(ThrowingRunnable<? extends X> r) {
        launder(() -> {
            r.run();
            return null;
        });
    }
    
    protected <R> R launder(ThrowingSupplier<R, ? extends X> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            X x = ThrowingBridge.launder(e, this.x);
            throw new BridgeException(x);
        }
    }
}
