package name.falgout.jeffrey.throwing;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingBinaryOperator<T, X extends Throwable>
    extends ThrowingBiFunction<T, T, T, X> {
  @Override
  default BiFunction<T, T, T> fallbackTo(BiFunction<? super T, ? super T, ? extends T> fallback) {
    return ThrowingBiFunction.super.fallbackTo(fallback)::apply;
  }

  @Override
  default BiFunction<T, T, T> fallbackTo(BiFunction<? super T, ? super T, ? extends T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    return ThrowingBiFunction.super.fallbackTo(fallback, thrown)::apply;
  }

  @Override
  default <Y extends Throwable> ThrowingBiFunction<T, T, T, Y> orTry(
      ThrowingBiFunction<? super T, ? super T, ? extends T, ? extends Y> f) {
    return ThrowingBiFunction.super.orTry(f)::apply;
  }

  @Override
  default <Y extends Throwable> ThrowingBiFunction<T, T, T, Y> orTry(
      ThrowingBiFunction<? super T, ? super T, ? extends T, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return ThrowingBiFunction.super.orTry(f, thrown)::apply;
  }

  @Override
  default public <Y extends Throwable> ThrowingBinaryOperator<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return ThrowingBiFunction.super.rethrow(x, mapper)::apply;
  }
}
