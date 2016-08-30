package name.falgout.jeffrey.throwing;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingBinaryOperator<T, X extends Throwable>
    extends ThrowingBiFunction<T, T, T, X> {
  @Override
  default public <Y extends Throwable> ThrowingBinaryOperator<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (t1, t2) -> {
      ThrowingSupplier<T, X> s = () -> apply(t1, t2);
      return s.rethrow(x, mapper).get();
    };
  }
}
