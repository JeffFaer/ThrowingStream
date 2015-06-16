package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongPredicate;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingLongPredicate<X extends Throwable> {
    public boolean test(long value) throws X;

    default public LongPredicate fallbackTo(LongPredicate fallback) {
        ThrowingLongPredicate<Nothing> t = fallback::test;
        return orTry(t)::test;
    }

    default public <Y extends Throwable> ThrowingLongPredicate<Y> orTry(ThrowingLongPredicate<? extends Y> f) {
        return orTry(f, null);
    }
    
    default public <Y extends Throwable> ThrowingLongPredicate<Y> orTry(ThrowingLongPredicate<? extends Y> f, Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.orTry(() -> f.test(t), thrown).get();
        };
    }

    default public <Y extends Throwable> ThrowingLongPredicate<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingSupplier<Boolean, X> s = () -> test(t);
            return s.rethrow(x, mapper).get();
        };
    }
}
