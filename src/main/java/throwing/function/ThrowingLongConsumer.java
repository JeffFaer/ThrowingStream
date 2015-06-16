package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingLongConsumer<X extends Throwable> {
    void accept(long value) throws X;

    default public LongConsumer fallbackTo(LongConsumer fallback) {
        ThrowingLongConsumer<Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingLongConsumer<Y> orTry(ThrowingLongConsumer<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingLongConsumer<Y> orTry(ThrowingLongConsumer<? extends Y> f,
            Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.orTry(() -> f.accept(t), thrown).run();
        };
    }

    default public <Y extends Throwable> ThrowingLongConsumer<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.rethrow(x, mapper).run();
        };
    }
}
