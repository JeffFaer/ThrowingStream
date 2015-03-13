package throwing.function;

import java.util.function.Supplier;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingSupplier<R, X extends Throwable> {
    public R get() throws X;

    default public Supplier<R> fallbackTo(Supplier<? extends R> supplier) {
        ThrowingSupplier<R, Nothing> t = supplier::get;
        return orTry(t)::get;
    }

    default public <Y extends Throwable> ThrowingSupplier<R, Y> orTry(
            ThrowingSupplier<? extends R, ? extends Y> supplier) {
        return () -> {
            try {
                return get();
            } catch (Throwable x) {
                try {
                    return supplier.get();
                } catch (Throwable x2) {
                    x2.addSuppressed(x);
                    throw x2;
                }
            }
        };
    }
}
