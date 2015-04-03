package throwing.function;

import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingLongBinaryOperator<X extends Throwable> {
    public long applyAsLong(long left, long right) throws X;

    default public LongBinaryOperator fallbackTo(LongBinaryOperator fallback) {
        ThrowingLongBinaryOperator<Nothing> t = fallback::applyAsLong;
        return orTry(t)::applyAsLong;
    }

    default public <Y extends Throwable> ThrowingLongBinaryOperator<Y> orTry(ThrowingLongBinaryOperator<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingLongBinaryOperator<Y> orTry(ThrowingLongBinaryOperator<? extends Y> f,
            Consumer<? super Throwable> thrown) {
        return (t1, t2) -> {
            ThrowingSupplier<Long, X> s = () -> applyAsLong(t1, t2);
            return s.orTry(() -> f.applyAsLong(t1, t2), thrown).get();
        };
    }
}
