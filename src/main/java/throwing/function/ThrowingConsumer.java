package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingConsumer<T, X extends Throwable> {
  public void accept(T t) throws X;

  default public Consumer<T> fallbackTo(Consumer<? super T> fallback) {
    return fallbackTo(fallback, null);
  }

  default public Consumer<T> fallbackTo(Consumer<? super T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingConsumer<T, Nothing> t = fallback::accept;
    return orTry(t, thrown)::accept;
  }

  default public <Y extends Throwable> ThrowingConsumer<T, Y> orTry(
      ThrowingConsumer<? super T, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingConsumer<T, Y> orTry(
      ThrowingConsumer<? super T, ? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingRunnable<X> s = () -> accept(t);
      s.orTry(() -> f.accept(t), thrown).run();
    };
  }

  default public <Y extends Throwable> ThrowingConsumer<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingRunnable<X> s = () -> accept(t);
      s.rethrow(x, mapper).run();
    };
  }
}
