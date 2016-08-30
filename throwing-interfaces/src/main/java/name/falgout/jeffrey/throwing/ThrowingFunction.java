package name.falgout.jeffrey.throwing;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingFunction<T, R, X extends Throwable> {
  public R apply(T t) throws X;

  default public Function<T, R> fallbackTo(Function<? super T, ? extends R> fallback) {
    return fallbackTo(fallback, null);
  }

  default public Function<T, R> fallbackTo(Function<? super T, ? extends R> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingFunction<T, R, Nothing> t = fallback::apply;
    return orTry(t, thrown)::apply;
  }

  default public <Y extends Throwable> ThrowingFunction<T, R, Y>
      orTry(ThrowingFunction<? super T, ? extends R, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingFunction<T, R, Y> orTry(
      ThrowingFunction<? super T, ? extends R, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingSupplier<R, X> s = () -> apply(t);
      return s.orTry(() -> f.apply(t), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingFunction<T, R, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingSupplier<R, X> s = () -> apply(t);
      return s.rethrow(x, mapper).get();
    };
  }

  default public <RR> ThrowingFunction<T, RR, X> andThen(Function<? super R, ? extends RR> after) {
    return andThen((ThrowingFunction<? super R, ? extends RR, ? extends X>) after::apply);
  }

  default public <RR> ThrowingFunction<T, RR, X>
      andThen(ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
    Objects.requireNonNull(after);
    return t -> after.apply(apply(t));
  }
}
