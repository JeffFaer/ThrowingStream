package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.Optional;
import java.util.function.Function;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.ThrowingRunnable;
import name.falgout.jeffrey.throwing.ThrowingSupplier;

class UncheckedAdapter<D, X extends Throwable> extends ThrowingAbstractAdapter<D, X> {
    private final Function<Throwable, AdapterException> masker;

    UncheckedAdapter(D delegate, Class<X> x) {
        super(delegate, x);
        RethrowChain<Throwable, AdapterException> c = t -> Optional.ofNullable(getExceptionClass().isInstance(
                t) ? new AdapterException(t) : null);
        masker = c.finish();
    }

    protected void maskException(ThrowingRunnable<? extends X> r) {
        maskException(() -> {
            r.run();
            return null;
        });
    }

    protected <R> R maskException(ThrowingSupplier<R, ? extends X> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            throw masker.apply(t);
        }
    }
}
