package throwing.function;

import java.util.function.IntUnaryOperator;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntUnaryOperator<X extends Throwable> {
    public int applyAsInt(int operand) throws X;

    default public IntUnaryOperator fallbackTo(IntUnaryOperator fallback) {
        ThrowingIntUnaryOperator<Nothing> t = fallback::applyAsInt;
        return orTry(t)::applyAsInt;
    }

    default public <Y extends Throwable> ThrowingIntUnaryOperator<Y> orTry(ThrowingIntUnaryOperator<? extends Y> f) {
        return t -> {
            ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
            return s.orTry(() -> f.applyAsInt(t)).get();
        };
    }
}
