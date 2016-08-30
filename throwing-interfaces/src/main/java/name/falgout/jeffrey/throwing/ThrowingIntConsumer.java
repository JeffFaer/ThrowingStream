package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingIntConsumer<X extends Throwable> {
  void accept(int value) throws X;

  default public IntConsumer fallbackTo(IntConsumer fallback) {
    return fallbackTo(fallback, null);
  }

  default public IntConsumer fallbackTo(IntConsumer fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingIntConsumer<Nothing> t = fallback::accept;
    return orTry(t, thrown)::accept;
  }

  default public <Y extends Throwable> ThrowingIntConsumer<Y>
      orTry(ThrowingIntConsumer<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingIntConsumer<Y>
      orTry(ThrowingIntConsumer<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingRunnable<X> s = () -> accept(t);
      s.orTry(() -> f.accept(t), thrown).run();
    };
  }

  default public <Y extends Throwable> ThrowingIntConsumer<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingRunnable<X> s = () -> accept(t);
      s.rethrow(x, mapper).run();
    };
  }
}
