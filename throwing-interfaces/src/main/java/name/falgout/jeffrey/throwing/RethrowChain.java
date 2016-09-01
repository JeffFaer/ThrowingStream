package name.falgout.jeffrey.throwing;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface RethrowChain<X extends Throwable, Y extends Throwable>
    extends Function<X, Optional<Y>> {
  public static <Y extends Throwable> RethrowChain<Throwable, Y> castTo(Class<Y> clazz) {
    return e -> Optional.ofNullable(clazz.isInstance(e) ? clazz.cast(e) : null);
  }

  default public RethrowChain<X, Y> connect(RethrowChain<X, Y> link) {
    Objects.requireNonNull(link);
    return x -> {
      Optional<Y> y = apply(x);
      if (y.isPresent()) {
        return y;
      }

      return link.apply(x);
    };
  }

  default public <Z extends Throwable> RethrowChain<X, Z> rethrow(
      Function<? super Y, ? extends Z> mapper) {
    Objects.requireNonNull(mapper);
    return x -> apply(x).map(mapper);
  }

  default public Function<X, Y> finish() {
    return finish(x -> {
      throw new AssertionError(x);
    });
  }

  default public Function<X, Y> finish(Function<? super X, ? extends Y> unhandledException) {
    Objects.requireNonNull(unhandledException);
    return x -> {
      return apply(x).orElseGet(() -> {
        if (x instanceof RuntimeException) {
          throw (RuntimeException) x;
        } else if (x instanceof Error) {
          throw (Error) x;
        }

        return unhandledException.apply(x);
      });
    };
  }
}
