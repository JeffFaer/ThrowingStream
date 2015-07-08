package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Function;

import javax.annotation.Nullable;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoublePredicate<X extends Throwable> {
  public boolean test(double value) throws X;

  default public DoublePredicate fallbackTo(DoublePredicate fallback) {
    return fallbackTo(fallback, null);
  }

  default public DoublePredicate fallbackTo(DoublePredicate fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingDoublePredicate<Nothing> t = fallback::test;
    return orTry(t, thrown)::test;
  }

  default public <Y extends Throwable> ThrowingDoublePredicate<Y> orTry(
      ThrowingDoublePredicate<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingDoublePredicate<Y> orTry(
      ThrowingDoublePredicate<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<Boolean, X> s = () -> test(t);
      return s.orTry(() -> f.test(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingDoublePredicate<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<Boolean, X> s = () -> test(t);
      return s.rethrow(x, mapper).get();
    };
  }
}
