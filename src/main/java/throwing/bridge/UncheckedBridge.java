package throwing.bridge;

import throwing.ThrowingRunnable;
import throwing.function.ThrowingSupplier;

class UncheckedBridge<D, X extends Throwable> extends Bridge<D> {
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
            if (x.isInstance(e)) {
                throw new BridgeException(e);
            } else if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else if (e instanceof Error) {
                throw (Error) e;
            } else {
                throw new AssertionError(e);
            }
        }
    }
}
