package throwing;

import java.util.function.Supplier;

import throwing.FunctionBridge.BridgeException;

public abstract class CheckedExceptionBridge<X extends Throwable> {
    protected final FunctionBridge<X> bridge;
    protected final Class<X> x;
    
    protected CheckedExceptionBridge(Class<X> x) {
        this.bridge = new FunctionBridge<>(x);
        this.x = x;
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
                throw x.cast(t.getCause());
            } else if (x.isInstance(t)) {
                throw x.cast(t);
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
