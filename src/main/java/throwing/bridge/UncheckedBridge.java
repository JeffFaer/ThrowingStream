package throwing.bridge;

import throwing.ThrowingRunnable;
import throwing.function.ThrowingSupplier;

class UncheckedBridge<D, X extends Throwable> extends AbstractBridge<D, X> {
    UncheckedBridge(D delegate, Class<X> x) {
        super(delegate, x);
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
        } catch (Throwable t) {
            throw launder(t, getExceptionClass()::isInstance, BridgeException::new);
        }
    }
}
