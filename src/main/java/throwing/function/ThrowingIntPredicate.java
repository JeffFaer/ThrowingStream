package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntPredicate<X extends Throwable> {
    public boolean test(int value) throws X;

    default public IntPredicate fallbackTo(IntPredicate fallback) {
        ThrowingIntPredicate<Nothing> t = fallback::test;
        return orTry(t)::test;
    }

    default public <Y extends Throwable> ThrowingIntPredicate<Y> orTry(ThrowingIntPredicate<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingIntPredicate<Y> orTry(ThrowingIntPredicate<? extends Y> f,
            Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.orTry(() -> f.test(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingIntPredicate<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
