package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleUnaryOperator<X extends Throwable> {
    public double applyAsDouble(double operand) throws X;

    default public DoubleUnaryOperator fallbackTo(DoubleUnaryOperator fallback) {
        ThrowingDoubleUnaryOperator<Nothing> t = fallback::applyAsDouble;
        return orTry(t)::applyAsDouble;
    }

    default public <Y extends Throwable> ThrowingDoubleUnaryOperator<Y> orTry(ThrowingDoubleUnaryOperator<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingDoubleUnaryOperator<Y> orTry(
            ThrowingDoubleUnaryOperator<? extends Y> f, Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.orTry(() -> f.applyAsDouble(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingDoubleUnaryOperator<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
