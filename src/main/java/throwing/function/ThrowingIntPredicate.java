package throwing.function;

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
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.orTry(() -> f.test(t)).get();
        };
    }
}
