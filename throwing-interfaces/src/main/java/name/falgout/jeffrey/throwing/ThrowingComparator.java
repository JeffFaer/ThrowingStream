package name.falgout.jeffrey.throwing;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingComparator<T, X extends Throwable> {
  public int compare(T o1, T o2) throws X;

  default public <Y extends Throwable> ThrowingComparator<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (o1, o2) -> {
      ThrowingSupplier<Integer, X> s = () -> compare(o1, o2);
      return s.rethrow(x, mapper).get();
    };
  }
}
