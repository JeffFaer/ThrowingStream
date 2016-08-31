package name.falgout.jeffrey.throwing;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ThrowingComparator<T, X extends Throwable> {
  public int compare(T o1, T o2) throws X;

  default public Comparator<T> fallbackTo(Comparator<T> fallback) {
    return fallbackTo(fallback, null);
  }

  default public Comparator<T> fallbackTo(Comparator<T> fallback,
      @Nullable Consumer<? super Throwable> thrown) {
    ThrowingComparator<T, Nothing> t = fallback::compare;
    return orTry(t, thrown)::compare;
  }

  default public <Y extends Throwable> ThrowingComparator<T, Y> orTry(
      ThrowingComparator<? super T, ? extends Y> f) {
    return orTry(f, null);
  }

  default public <Y extends Throwable> ThrowingComparator<T, Y> orTry(
      ThrowingComparator<? super T, ? extends Y> f,
      @Nullable Consumer<? super Throwable> thrown) {
    return (o1, o2) -> {
      ThrowingSupplier<Integer, X> s = () -> compare(o1, o2);
      return s.orTry(() -> f.compare(o1, o2), thrown).get();
    };
  }

  default public <Y extends Throwable> ThrowingComparator<T, Y> rethrow(Class<X> x,
      Function<? super X, ? extends Y> mapper) {
    return (o1, o2) -> {
      ThrowingSupplier<Integer, X> s = () -> compare(o1, o2);
      return s.rethrow(x, mapper).get();
    };
  }
}
