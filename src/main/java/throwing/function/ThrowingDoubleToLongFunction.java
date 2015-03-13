package throwing.function;

import java.util.function.DoubleToLongFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleToLongFunction<X extends Throwable> {
    public long applyAsLong(double value) throws X;

    default public DoubleToLongFunction fallbackTo(DoubleToLongFunction fallback) {
        ThrowingDoubleToLongFunction<Nothing> t = fallback::applyAsLong;
        return orTry(t)::applyAsLong;
    }

    default public <Y extends Throwable> ThrowingDoubleToLongFunction<Y> orTry(
            ThrowingDoubleToLongFunction<? extends Y> f) {
        return t -> {
            ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
            return s.orTry(() -> f.applyAsLong(t)).get();
        };
    }
}
