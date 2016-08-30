package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingObjLongConsumer<T, X extends Throwable> {
  public void accept(T t, long value) throws X;

  default public ObjLongConsumer<T> fallbackTo(ObjLongConsumer<? super T> fallback) {
    return fallbackTo(fallback, null);
  }

  default public ObjLongConsumer<T> fallbackTo(ObjLongConsumer<? super T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingObjLongConsumer<T, Nothing> t = fallback::accept;
    return orTry(t, thrown)::accept;
  }

  default public <Y extends Throwable> ThrowingObjLongConsumer<T, Y>
      orTry(ThrowingObjLongConsumer<? super T, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingObjLongConsumer<T, Y> orTry(
      ThrowingObjLongConsumer<? super T, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return (t, v) -> {
      ThrowingRunnable<X> s = () -> accept(t, v);
      s.orTry(() -> f.accept(t, v), thrown).run();
    };
  }

  default public <Y extends Throwable> ThrowingObjLongConsumer<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (t, v) -> {
      ThrowingRunnable<X> s = () -> accept(t, v);
      s.rethrow(x, mapper).run();
    };
  }
}
