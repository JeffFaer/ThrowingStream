package name.falgout.jeffrey.throwing;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingRunnable<X extends Throwable> {
  public void run() throws X;

  default public Runnable fallbackTo(Runnable fallback) {
    ThrowingRunnable<Nothing> t = fallback::run;
    return orTry(t)::run;
  }

  default public <Y extends Throwable> ThrowingRunnable<Y> orTry(ThrowingRunnable<? extends Y> r) {
    return orTry(r, null);
  }

  default public <Y extends Throwable> ThrowingRunnable<Y> orTry(ThrowingRunnable<? extends Y> r,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingSupplier<Void, X> s1 = () -> {
      run();
      return null;
    };
    return s1.orTry(() -> {
      r.run();
      return null;
    }, thrown)::get;
  }

  default public <Y extends Throwable> ThrowingRunnable<Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    ThrowingSupplier<Void, X> s1 = () -> {
      run();
      return null;
    };
    return s1.rethrow(x, mapper)::get;
  }
}
