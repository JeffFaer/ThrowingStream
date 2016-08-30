package name.falgout.jeffrey.throwing;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingSupplier<R, X extends Throwable> {
  public R get() throws X;

  default public Supplier<R> fallbackTo(Supplier<? extends R> fallback) {
    return fallbackTo(fallback, null);
  }

  default public Supplier<R> fallbackTo(Supplier<? extends R> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingSupplier<R, Nothing> t = fallback::get;
    return orTry(t, thrown)::get;
  }

  default public <Y extends Throwable> ThrowingSupplier<R, Y>
      orTry(ThrowingSupplier<? extends R, ? extends Y> supplier) {
    return orTry(supplier, null);
  }

  default public <Y extends Throwable> ThrowingSupplier<R, Y> orTry(
      ThrowingSupplier<? extends R, ? extends Y> supplier,
      @Nullable Consumer<? super Throwable> thrown) {
    Objects.requireNonNull(supplier, "supplier");
    return () -> {
      try {
        return get();
      } catch (Throwable x) {
        if (thrown != null) {
          thrown.accept(x);
        }

        try {
          return supplier.get();
        } catch (Throwable y) {
          y.addSuppressed(x);
          throw y;
        }
      }
    };
  }

  default public <Y extends Throwable> ThrowingSupplier<R, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    Function<Throwable, ? extends Y> rethrower = RethrowChain.start(x).rethrow(mapper).finish();
    return () -> {
      try {
        return get();
      } catch (Throwable t) {
        throw rethrower.apply(t);
      }
    };
  }
}
