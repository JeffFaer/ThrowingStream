package throwing.function;

import java.util.function.DoubleUnaryOperator;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleUnaryOperator<X extends Throwable> {
    public double applyAsDouble(double operand) throws X;

    default public DoubleUnaryOperator fallbackTo(DoubleUnaryOperator fallback) {
        ThrowingDoubleUnaryOperator<Nothing> t = fallback::applyAsDouble;
        return orTry(t)::applyAsDouble;
    }

    default public <Y extends Throwable> ThrowingDoubleUnaryOperator<Y> orTry(ThrowingDoubleUnaryOperator<? extends Y> f) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.orTry(() -> f.applyAsDouble(t)).get();
        };
    }
}
