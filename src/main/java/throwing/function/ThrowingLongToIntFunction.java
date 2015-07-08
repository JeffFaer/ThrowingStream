package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongToIntFunction;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingLongToIntFunction<X extends Throwable> {
  public int applyAsInt(long value) throws X;

  default public LongToIntFunction fallbackTo(LongToIntFunction fallback) {
    return fallbackTo(fallback, null);
  }

  default public LongToIntFunction fallbackTo(LongToIntFunction fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingLongToIntFunction<Nothing> t = fallback::applyAsInt;
    return orTry(t, thrown)::applyAsInt;
  }

  default public <Y extends Throwable> ThrowingLongToIntFunction<Y> orTry(
      ThrowingLongToIntFunction<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingLongToIntFunction<Y> orTry(
      ThrowingLongToIntFunction<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
      return s.orTry(() -> f.applyAsInt(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingLongToIntFunction<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Integer, X> s = () -> applyAsInt(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
