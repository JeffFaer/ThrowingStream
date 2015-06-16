package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingIntConsumer<X extends Throwable> {
    void accept(int value) throws X;

    default public IntConsumer fallbackTo(IntConsumer fallback) {
        ThrowingIntConsumer<Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingIntConsumer<Y> orTry(ThrowingIntConsumer<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingIntConsumer<Y> orTry(ThrowingIntConsumer<? extends Y> f,
            Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.orTry(() -> f.accept(t), thrown).run();
        };
    }

    default public <Y extends Throwable> ThrowingIntConsumer<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.rethrow(x, mapper).run();
        };
    }
}
