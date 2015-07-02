package throwing;

import java.util.Optional;
import java.util.function.Function;

/**
 * A short-circuiting chain of responsibility.
 *
 * @param <T>
 *          the input type.
 * @param <U>
 *          the output type.
 */
@FunctionalInterface
public interface Chain<T, U> extends Function<T, Optional<U>> {
  public static Chain<Object, Object> START = t -> Optional.empty();

  @SuppressWarnings("unchecked")
  public static <T, U> Chain<T, U> start() {
    return (Chain<T, U>) START;
  }

  default public Chain<T, U> connect(Chain<T, U> link) {
    return this == START ? link : t -> {
      Optional<U> o = apply(t);
      return o.isPresent() ? o : link.apply(t);
    };
  }

  /**
   * Try to process the input. If the result is {@link Optional#empty() empty}, then the input will
   * be passed to the next link.
   */
  @Override
  public Optional<U> apply(T t);
}
