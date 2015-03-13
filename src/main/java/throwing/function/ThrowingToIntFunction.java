package throwing.function;

import java.util.function.ToIntFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingToIntFunction<T, X extends Throwable> {
    public int applyAsInt(T value) throws X;

    default public ToIntFunction<T> fallbackTo(ToIntFunction<? super T> fallback) {
        ThrowingToIntFunction<T, Nothing> t = fallback::applyAsInt;
        return orTry(t)::applyAsInt;
    }

    default public <Y extends Throwable> ThrowingToIntFunction<T, Y> orTry(
            ThrowingToIntFunction<? super T, ? extends Y> f) {
        return t -> {
            ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
            return s.orTry(() -> f.applyAsInt(t)).get();
        };
    }
}
