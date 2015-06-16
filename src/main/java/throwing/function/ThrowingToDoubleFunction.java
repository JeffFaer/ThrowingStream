package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingToDoubleFunction<T, X extends Throwable> {
    public double applyAsDouble(T value) throws X;

    default public ToDoubleFunction<T> fallbackTo(ToDoubleFunction<? super T> fallback) {
        ThrowingToDoubleFunction<T, Nothing> t = fallback::applyAsDouble;
        return orTry(t)::applyAsDouble;
    }

    default public <Y extends Throwable> ThrowingToDoubleFunction<T, Y> orTry(
            ThrowingToDoubleFunction<? super T, ? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingToDoubleFunction<T, Y> orTry(
            ThrowingToDoubleFunction<? super T, ? extends Y> f, Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.orTry(() -> f.applyAsDouble(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingToDoubleFunction<T, Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
