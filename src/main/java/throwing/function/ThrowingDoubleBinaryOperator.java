package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleBinaryOperator<X extends Throwable> {
  public double applyAsDouble(double left, double right) throws X;

  default public DoubleBinaryOperator fallbackTo(DoubleBinaryOperator fallback) {
    return fallbackTo(fallback, null);
  }

  default public DoubleBinaryOperator fallbackTo(DoubleBinaryOperator fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingDoubleBinaryOperator<Nothing> t = fallback::applyAsDouble;
    return orTry(t, thrown)::applyAsDouble;
  }

  default public <Y extends Throwable> ThrowingDoubleBinaryOperator<Y> orTry(
      ThrowingDoubleBinaryOperator<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingDoubleBinaryOperator<Y> orTry(
      ThrowingDoubleBinaryOperator<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return (t1, t2) -> {
      ThrowingSupplier<Double, X> s = () -> applyAsDouble(t1, t2);
      return s.orTry(() -> f.applyAsDouble(t1, t2), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingDoubleBinaryOperator<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (t1, t2) -> {
      ThrowingSupplier<Double, X> s = () -> applyAsDouble(t1, t2);
      return s.rethrow(x, mapper).get();
    };
  }
}
