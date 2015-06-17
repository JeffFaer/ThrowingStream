package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntToDoubleFunction;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntToDoubleFunction<X extends Throwable> {
    public double applyAsDouble(int value) throws X;

    default public IntToDoubleFunction fallbackTo(IntToDoubleFunction fallback) {
        ThrowingIntToDoubleFunction<Nothing> t = fallback::applyAsDouble;
        return orTry(t)::applyAsDouble;
    }

    default public <Y extends Throwable> ThrowingIntToDoubleFunction<Y> orTry(
            ThrowingIntToDoubleFunction<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingIntToDoubleFunction<Y> orTry(
            ThrowingIntToDoubleFunction<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.orTry(() -> f.applyAsDouble(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingIntToDoubleFunction<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
