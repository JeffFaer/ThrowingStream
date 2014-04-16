package throwing;

import java.util.function.Supplier;

import throwing.FunctionBridge.BridgeException;

public abstract class CheckedExceptionBridge<X extends Throwable> {
    private final FunctionBridge<X> bridge;
    
    protected CheckedExceptionBridge(FunctionBridge<X> bridge) {
        this.bridge = bridge;
    }
    
    public FunctionBridge<X> getBridge() {
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
                throw bridge.getExceptionClass().cast(t.getCause());
            } else if (bridge.getExceptionClass().isInstance(t)) {
                throw bridge.getExceptionClass().cast(t);
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
