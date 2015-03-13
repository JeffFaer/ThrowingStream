package throwing.function;

import java.util.function.DoubleConsumer;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingDoubleConsumer<X extends Throwable> {
    void accept(double value) throws X;

    default public DoubleConsumer fallbackTo(DoubleConsumer fallback) {
        ThrowingDoubleConsumer<Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingDoubleConsumer<Y> orTry(ThrowingDoubleConsumer<? extends Y> f) {
        return t -> {
            ThrowingRunnable<X> s = () -> accept(t);
            s.orTry(() -> f.accept(t)).run();
        };
    }
}
