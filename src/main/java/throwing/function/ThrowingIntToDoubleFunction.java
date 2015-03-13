package throwing.function;

import java.util.function.IntToDoubleFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntToDoubleFunction<X extends Throwable> {
    public double applyAsDouble(int value) throws X;

    default public IntToDoubleFunction fallbackTo(IntToDoubleFunction fallback) {
        ThrowingIntToDoubleFunction<Nothing> t = fallback::applyAsDouble;
        return orTry(t)::applyAsDouble;
    }

    default public <Y extends Throwable> ThrowingIntToDoubleFunction<Y> orTry(ThrowingIntToDoubleFunction<? extends Y> f) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.orTry(() -> f.applyAsDouble(t)).get();
        };
    }
}
