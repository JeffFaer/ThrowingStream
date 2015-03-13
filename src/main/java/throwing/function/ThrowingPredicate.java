package throwing.function;

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
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.orTry(() -> f.test(t)).get();
        };
    }
}
