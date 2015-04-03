package throwing.function;

import java.util.function.Consumer;
import java.util.function.IntToLongFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntToLongFunction<X extends Throwable> {
    public long applyAsLong(int value) throws X;

    default public IntToLongFunction fallbackTo(IntToLongFunction fallback) {
        ThrowingIntToLongFunction<Nothing> t = fallback::applyAsLong;
        return orTry(t)::applyAsLong;
    }

    default public <Y extends Throwable> ThrowingIntToLongFunction<Y> orTry(ThrowingIntToLongFunction<? extends Y> f) {
        return orTry(f, null);
    }
    
    default public <Y extends Throwable> ThrowingIntToLongFunction<Y> orTry(ThrowingIntToLongFunction<? extends Y> f, Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
            return s.orTry(() -> f.applyAsLong(t), thrown).get();
        };
    }
}
