package throwing.function;

import java.util.function.LongUnaryOperator;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingLongUnaryOperator<X extends Throwable> {
    public long applyAsLong(long operand) throws X;

    default public LongUnaryOperator fallbackTo(LongUnaryOperator fallback) {
        ThrowingLongUnaryOperator<Nothing> t = fallback::applyAsLong;
        return orTry(t)::applyAsLong;
    }

    default public <Y extends Throwable> ThrowingLongUnaryOperator<Y> orTry(ThrowingLongUnaryOperator<? extends Y> f) {
        return t -> {
            ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
            return s.orTry(() -> f.applyAsLong(t)).get();
        };
    }
}
