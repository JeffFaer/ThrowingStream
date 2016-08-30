package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingToDoubleFunction<T, X extends Throwable> {
  public double applyAsDouble(T value) throws X;

  default public ToDoubleFunction<T> fallbackTo(ToDoubleFunction<? super T> fallback) {
    return fallbackTo(fallback, null);
  }

  default public ToDoubleFunction<T> fallbackTo(ToDoubleFunction<? super T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingToDoubleFunction<T, Nothing> t = fallback::applyAsDouble;
    return orTry(t, thrown)::applyAsDouble;
  }

  default public <Y extends Throwable> ThrowingToDoubleFunction<T, Y>
      orTry(ThrowingToDoubleFunction<? super T, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingToDoubleFunction<T, Y> orTry(
      ThrowingToDoubleFunction<? super T, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
      return s.orTry(() -> f.applyAsDouble(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingToDoubleFunction<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Double, X> s = () -> applyAsDouble(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
