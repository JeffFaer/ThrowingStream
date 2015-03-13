package throwing.function;

import java.util.function.ToLongFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingToLongFunction<T, X extends Throwable> {
    public long applyAsLong(T value) throws X;

    default public ToLongFunction<T> fallbackTo(ToLongFunction<? super T> fallback) {
        ThrowingToLongFunction<T, Nothing> t = fallback::applyAsLong;
        return orTry(t)::applyAsLong;
    }

    default public <Y extends Throwable> ThrowingToLongFunction<T, Y> orTry(
            ThrowingToLongFunction<? super T, ? extends Y> f) {
        return t -> {
            ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
            return s.orTry(() -> f.applyAsLong(t)).get();
        };
    }
}
