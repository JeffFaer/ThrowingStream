package throwing.bridge;

import java.util.Optional;
import java.util.function.Function;

import throwing.ThrowingRunnable;
import throwing.function.ThrowingSupplier;

class UncheckedBridge<D, X extends Throwable> extends AbstractBridge<D, X> {
    private final Function<Throwable, BridgeException> launder;

    UncheckedBridge(D delegate, Class<X> x) {
        super(delegate, x);
        RethrowChain<Throwable, BridgeException> c = t -> Optional.ofNullable(getExceptionClass().isInstance(t) ? new BridgeException(
                t) : null);
        launder = c.finish();
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
            throw launder.apply(t);
        }
    }
}
