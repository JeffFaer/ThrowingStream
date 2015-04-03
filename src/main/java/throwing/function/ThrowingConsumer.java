package throwing.function;

import java.util.function.Consumer;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingConsumer<T, X extends Throwable> {
    public void accept(T t) throws X;

    default public Consumer<T> fallbackTo(Consumer<? super T> fallback) {
        ThrowingConsumer<T, Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingConsumer<T, Y> orTry(ThrowingConsumer<? super T, ? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingConsumer<T, Y> orTry(ThrowingConsumer<? super T, ? extends Y> f,
            Consumer<? super Throwable> thrown) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.orTry(() -> f.accept(t), thrown).run();
        };
    }
}
