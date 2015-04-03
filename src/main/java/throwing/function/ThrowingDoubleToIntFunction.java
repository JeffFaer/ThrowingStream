package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleToIntFunction<X extends Throwable> {
    public int applyAsInt(double value) throws X;

    default public DoubleToIntFunction fallbackTo(DoubleToIntFunction fallback) {
        ThrowingDoubleToIntFunction<Nothing> t = fallback::applyAsInt;
        return orTry(t)::applyAsInt;
    }

    default public <Y extends Throwable> ThrowingDoubleToIntFunction<Y> orTry(ThrowingDoubleToIntFunction<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingDoubleToIntFunction<Y> orTry(
            ThrowingDoubleToIntFunction<? extends Y> f, Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
            return s.orTry(() -> f.applyAsInt(t), thrown).get();
        };
    }
}
