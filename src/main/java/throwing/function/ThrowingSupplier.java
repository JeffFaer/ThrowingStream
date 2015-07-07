package throwing.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import throwing.Nothing;
import throwing.RethrowChain;

@FunctionalInterface
public interface ThrowingSupplier<R, X extends Throwable> {
    public R get() throws X;

    default public Supplier<R> fallbackTo(Supplier<? extends R> supplier) {
        ThrowingSupplier<R, Nothing> t = supplier::get;
        return orTry(t)::get;
    }

    default public <Y extends Throwable> ThrowingSupplier<R, Y> orTry(
            ThrowingSupplier<? extends R, ? extends Y> supplier) {
        return orTry(supplier, null);
    }

    default public <Y extends Throwable> ThrowingSupplier<R, Y> orTry(
            ThrowingSupplier<? extends R, ? extends Y> supplier,
            @Nullable Consumer<? super Throwable> thrown) {
        Objects.requireNonNull(supplier, "supplier");
        return () -> {
            try {
                return get();
            } catch (Throwable x) {
                try {
                    R ret = supplier.get();

                    if (thrown != null) {
                        thrown.accept(x);
                    }

                    return ret;
                } catch (Throwable x2) {
                    x2.addSuppressed(x);
                    throw x2;
                }
            }
        };
    }

    default public <Y extends Throwable> ThrowingSupplier<R, Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        Function<Throwable, ? extends Y> rethrower = RethrowChain.rethrowAs(x)
                .rethrow(mapper)
                .finish();
        return () -> {
            try {
                return get();
            } catch (Throwable t) {
                throw rethrower.apply(t);
            }
        };
    }
}
