package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingToLongFunction<T, X extends Throwable> {
  public long applyAsLong(T value) throws X;

  default public ToLongFunction<T> fallbackTo(ToLongFunction<? super T> fallback) {
    return fallbackTo(fallback, null);
  }

  default public ToLongFunction<T> fallbackTo(ToLongFunction<? super T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingToLongFunction<T, Nothing> t = fallback::applyAsLong;
    return orTry(t, thrown)::applyAsLong;
  }

  default public <Y extends Throwable> ThrowingToLongFunction<T, Y>
      orTry(ThrowingToLongFunction<? super T, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingToLongFunction<T, Y> orTry(
      ThrowingToLongFunction<? super T, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
      return s.orTry(() -> f.applyAsLong(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingToLongFunction<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
