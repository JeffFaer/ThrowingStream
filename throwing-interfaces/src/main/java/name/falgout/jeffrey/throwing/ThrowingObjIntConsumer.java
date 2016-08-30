package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingObjIntConsumer<T, X extends Throwable> {
  public void accept(T t, int value) throws X;

  default public ObjIntConsumer<T> fallbackTo(ObjIntConsumer<? super T> fallback) {
    return fallbackTo(fallback, null);
  }

  default public ObjIntConsumer<T> fallbackTo(ObjIntConsumer<? super T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingObjIntConsumer<T, Nothing> t = fallback::accept;
    return orTry(t, thrown)::accept;
  }

  default public <Y extends Throwable> ThrowingObjIntConsumer<T, Y>
      orTry(ThrowingObjIntConsumer<? super T, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingObjIntConsumer<T, Y> orTry(
      ThrowingObjIntConsumer<? super T, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return (t, v) -> {
      ThrowingRunnable<X> s = () -> accept(t, v);
      s.orTry(() -> f.accept(t, v)).run();
    };
  }

  default public <Y extends Throwable> ThrowingObjIntConsumer<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (t, v) -> {
      ThrowingRunnable<X> s = () -> accept(t, v);
      s.rethrow(x, mapper).run();
    };
  }
}
