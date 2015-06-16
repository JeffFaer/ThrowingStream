package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingPredicate<T, X extends Throwable> {
    public boolean test(T t) throws X;

    default public Predicate<T> fallbackTo(Predicate<? super T> fallback) {
        ThrowingPredicate<T, Nothing> t = fallback::test;
        return orTry(t)::test;
    }

    default public <Y extends Throwable> ThrowingPredicate<T, Y> orTry(ThrowingPredicate<? super T, ? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingPredicate<T, Y> orTry(ThrowingPredicate<? super T, ? extends Y> f,
            Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.orTry(() -> f.test(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingPredicate<T, Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
