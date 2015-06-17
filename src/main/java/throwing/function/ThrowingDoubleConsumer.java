package throwing.function;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingDoubleConsumer<X extends Throwable> {
    void accept(double value) throws X;

    default public DoubleConsumer fallbackTo(DoubleConsumer fallback) {
        ThrowingDoubleConsumer<Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingDoubleConsumer<Y> orTry(
            ThrowingDoubleConsumer<? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingDoubleConsumer<Y> orTry(
            ThrowingDoubleConsumer<? extends Y> f, @Nullable Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.orTry(() -> f.accept(t), thrown).run();
        };
    }

    default public <Y extends Throwable> ThrowingDoubleConsumer<Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.rethrow(x, mapper).run();
        };
    }
}
