package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingLongConsumer<X extends Throwable> {
  void accept(long value) throws X;

  default public LongConsumer fallbackTo(LongConsumer fallback) {
    return fallbackTo(fallback, null);
  }

  default public LongConsumer fallbackTo(LongConsumer fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingLongConsumer<Nothing> t = fallback::accept;
    return orTry(t, thrown)::accept;
  }

  default public <Y extends Throwable> ThrowingLongConsumer<Y>
      orTry(ThrowingLongConsumer<? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingLongConsumer<Y>
      orTry(ThrowingLongConsumer<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
    return t -> {
      ThrowingRunnable<X> s = () -> accept(t);
      s.orTry(() -> f.accept(t), thrown).run();
    };
  }

  default public <Y extends Throwable> ThrowingLongConsumer<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return t -> {
      ThrowingRunnable<X> s = () -> accept(t);
      s.rethrow(x, mapper).run();
    };
  }
}
