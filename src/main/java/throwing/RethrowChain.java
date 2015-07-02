package throwing;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface RethrowChain<X extends Throwable, Y extends Throwable> extends Chain<X, Y> {
  public static final RethrowChain<Throwable, Throwable> END = t -> {
    if (t instanceof Error) {
      throw (Error) t;
    } else if (t instanceof RuntimeException) {
      throw (RuntimeException) t;
    } else {
      throw new AssertionError(t);
    }
  };
  public static final Function<Throwable, Error> END_FUNCTION = RethrowChain.<Throwable, Error> start()
      .finish();

  public static <X extends Throwable, Y extends Throwable> RethrowChain<X, Y> start() {
    return Chain.<X, Y> start()::apply;
  }

  @SuppressWarnings("unchecked")
  public static <X extends Throwable, Y extends Throwable> RethrowChain<X, Y> end() {
    return (RethrowChain<X, Y>) END;
  }

  public static <Y extends Throwable> RethrowChain<Throwable, Y> rethrowAs(Class<Y> clazz) {
    return e -> clazz.isInstance(e) ? Optional.of(clazz.cast(e)) : Optional.empty();
  }

  @Override
  default public RethrowChain<X, Y> connect(Chain<X, Y> link) {
    return Chain.super.connect(link)::apply;
  }

  default public <Z extends Throwable> RethrowChain<X, Z> rethrow(
      Function<? super Y, ? extends Z> mapper) {
    return t -> apply(t).map(mapper);
  }

  default public Function<X, Y> finish() {
    RethrowChain<X, Y> c = this.connect(end());
    return t -> c.apply(t).get();
  }
}
