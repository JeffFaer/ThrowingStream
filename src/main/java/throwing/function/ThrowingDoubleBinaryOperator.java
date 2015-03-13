package throwing.function;

import java.util.function.DoubleBinaryOperator;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleBinaryOperator<X extends Throwable> {
    public double applyAsDouble(double left, double right) throws X;

    default public DoubleBinaryOperator fallbackTo(DoubleBinaryOperator fallback) {
        ThrowingDoubleBinaryOperator<Nothing> t = fallback::applyAsDouble;
        return orTry(t)::applyAsDouble;
    }

    default public <Y extends Throwable> ThrowingDoubleBinaryOperator<Y> orTry(
            ThrowingDoubleBinaryOperator<? extends Y> f) {
        return (t1, t2) -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t1, t2);
            return s.orTry(() -> f.applyAsDouble(t1, t2)).get();
        };
    }
}
