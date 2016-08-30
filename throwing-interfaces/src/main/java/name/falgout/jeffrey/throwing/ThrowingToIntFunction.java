package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingToIntFunction<T, X extends Throwable> {
  public int applyAsInt(T value) throws X;

  default public ToIntFunction<T> fallbackTo(ToIntFunction<? super T> fallback) {
    return fallbackTo(fallback, null);
  }

  default public ToIntFunction<T> fallbackTo(ToIntFunction<? super T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingToIntFunction<T, Nothing> t = fallback::applyAsInt;
    return orTry(t, thrown)::applyAsInt;
  }

  default public <Y extends Throwable> ThrowingToIntFunction<T, Y>
      orTry(ThrowingToIntFunction<? super T, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingToIntFunction<T, Y> orTry(
      ThrowingToIntFunction<? super T, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
      return s.orTry(() -> f.applyAsInt(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingToIntFunction<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
