package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingLongBinaryOperator<X extends Throwable> {
  public long applyAsLong(long left, long right) throws X;

  default public LongBinaryOperator fallbackTo(LongBinaryOperator fallback) {
    return fallbackTo(fallback, null);
  }

  default public LongBinaryOperator fallbackTo(LongBinaryOperator fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingLongBinaryOperator<Nothing> t = fallback::applyAsLong;
    return orTry(t, thrown)::applyAsLong;
  }

  default public <Y extends Throwable> ThrowingLongBinaryOperator<Y>
      orTry(ThrowingLongBinaryOperator<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingLongBinaryOperator<Y> orTry(
      ThrowingLongBinaryOperator<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return (t1, t2) -> {
      ThrowingSupplier<Long, X> s = () -> applyAsLong(t1, t2);
      return s.orTry(() -> f.applyAsLong(t1, t2), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingLongBinaryOperator<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (t1, t2) -> {
      ThrowingSupplier<Long, X> s = () -> applyAsLong(t1, t2);
      return s.rethrow(x, mapper).get();
    };
  }
}
