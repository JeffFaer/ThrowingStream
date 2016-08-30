package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntToLongFunction;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingIntToLongFunction<X extends Throwable> {
  public long applyAsLong(int value) throws X;

  default public IntToLongFunction fallbackTo(IntToLongFunction fallback) {
    return fallbackTo(fallback, null);
  }

  default public IntToLongFunction fallbackTo(IntToLongFunction fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingIntToLongFunction<Nothing> t = fallback::applyAsLong;
    return orTry(t, thrown)::applyAsLong;
  }

  default public <Y extends Throwable> ThrowingIntToLongFunction<Y>
      orTry(ThrowingIntToLongFunction<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingIntToLongFunction<Y> orTry(
      ThrowingIntToLongFunction<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
      return s.orTry(() -> f.applyAsLong(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingIntToLongFunction<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Long, X> s = () -> applyAsLong(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
