package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntUnaryOperator<X extends Throwable> {
  public int applyAsInt(int operand) throws X;

  default public IntUnaryOperator fallbackTo(IntUnaryOperator fallback) {
    return fallbackTo(fallback, null);
  }

  default public IntUnaryOperator fallbackTo(IntUnaryOperator fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingIntUnaryOperator<Nothing> t = fallback::applyAsInt;
    return orTry(t, thrown)::applyAsInt;
  }

  default public <Y extends Throwable> ThrowingIntUnaryOperator<Y> orTry(
      ThrowingIntUnaryOperator<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingIntUnaryOperator<Y> orTry(
      ThrowingIntUnaryOperator<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
      return s.orTry(() -> f.applyAsInt(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingIntUnaryOperator<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
