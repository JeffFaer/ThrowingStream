package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoublePredicate;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoublePredicate<X extends Throwable> {
    public boolean test(double value) throws X;

    default public DoublePredicate fallbackTo(DoublePredicate fallback) {
        ThrowingDoublePredicate<Nothing> t = fallback::test;
        return orTry(t)::test;
    }

    default public <Y extends Throwable> ThrowingDoublePredicate<Y> orTry(ThrowingDoublePredicate<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingDoublePredicate<Y> orTry(ThrowingDoublePredicate<? extends Y> f,
            Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.orTry(() -> f.test(t), thrown).get();
        };
    }
}
