package throwing.function;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingBiFunction<T, U, R, X extends Throwable> {
    public R apply(T t, U u) throws X;

    default public BiFunction<T, U, R> fallbackTo(BiFunction<? super T, ? super U, ? extends R> fallback) {
        ThrowingBiFunction<T, U, R, Nothing> t = fallback::apply;
        return orTry(t)::apply;
    }

    default public <Y extends Throwable> ThrowingBiFunction<T, U, R, Y> orTry(
            ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingBiFunction<T, U, R, Y> orTry(
            ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends Y> f, Consumer<? super Throwable> thrown) {
        return (t, u) -> {
            ThrowingSupplier<R, X> s = () -> apply(t, u);
            return s.orTry(() -> f.apply(t, u), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingBiFunction<T, U, R, Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return (t, u) -> {
            ThrowingSupplier<R, X> s = () -> apply(t, u);
            return s.rethrow(x, mapper).get();
        };
    }
}
