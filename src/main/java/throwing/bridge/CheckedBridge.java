package throwing.bridge;

import java.util.function.Supplier;

abstract class CheckedBridge<D, X extends Throwable> extends Bridge<D> {
    private final FunctionBridge<X> bridge;
    
    CheckedBridge(D delegate, FunctionBridge<X> bridge) {
        super(delegate);
        this.bridge = bridge;
    }
    
    protected FunctionBridge<X> getBridge() {
        return bridge;
    }
    
    protected void filterBridgeException(Runnable runnable) throws X {
        filterBridgeException(() -> {
            runnable.run();
            return null;
        });
    }
    
    protected <R> R filterBridgeException(Supplier<R> supplier) throws X {
        try {
            return supplier.get();
        } catch (Throwable t) {
            if (t instanceof BridgeException) {
                Throwable cause = t.getCause();
                if (getBridge().getExceptionClass().isInstance(cause)) {
                    throw getBridge().getExceptionClass().cast(cause);
                } else {
                    throw t;
                }
            } else if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else if (t instanceof Error) {
                throw (Error) t;
            } else {
                throw new AssertionError(t);
            }
        }
    }
}
