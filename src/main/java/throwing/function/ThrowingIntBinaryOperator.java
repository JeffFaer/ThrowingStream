package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntBinaryOperator<X extends Throwable> {
  public int applyAsInt(int left, int right) throws X;

  default public IntBinaryOperator fallbackTo(IntBinaryOperator fallback) {
    return fallbackTo(fallback, null);
  }

  default public IntBinaryOperator fallbackTo(IntBinaryOperator fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingIntBinaryOperator<Nothing> t = fallback::applyAsInt;
    return orTry(t, thrown)::applyAsInt;
  }

  default public <Y extends Throwable> ThrowingIntBinaryOperator<Y> orTry(
      ThrowingIntBinaryOperator<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingIntBinaryOperator<Y> orTry(
      ThrowingIntBinaryOperator<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return (t1, t2) -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t1, t2);
      return s.orTry(() -> f.applyAsInt(t1, t2), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingIntBinaryOperator<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (t1, t2) -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t1, t2);
      return s.rethrow(x, mapper).get();
    };
  }
}
