package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;

import javax.annotation.Nullable;

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
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingDoubleToLongFunction<Y> orTry(
            ThrowingDoubleToLongFunction<? extends Y> f,
            @Nullable Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
            return s.orTry(() -> f.applyAsLong(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingDoubleToLongFunction<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
