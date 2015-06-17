package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleToIntFunction<X extends Throwable> {
    public int applyAsInt(double value) throws X;

    default public DoubleToIntFunction fallbackTo(DoubleToIntFunction fallback) {
        ThrowingDoubleToIntFunction<Nothing> t = fallback::applyAsInt;
        return orTry(t)::applyAsInt;
    }

    default public <Y extends Throwable> ThrowingDoubleToIntFunction<Y> orTry(
            ThrowingDoubleToIntFunction<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingDoubleToIntFunction<Y> orTry(
            ThrowingDoubleToIntFunction<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
            return s.orTry(() -> f.applyAsInt(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingDoubleToIntFunction<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
