package throwing;

import java.util.function.Consumer;

import throwing.function.ThrowingSupplier;

@FunctionalInterface
public interface ThrowingRunnable<X extends Throwable> {
    public void run() throws X;

    default public Runnable fallbackTo(Runnable fallback) {
        ThrowingRunnable<Nothing> t = fallback::run;
        return orTry(t)::run;
    }

    default public <Y extends Throwable> ThrowingRunnable<Y> orTry(ThrowingRunnable<? extends Y> r) {
        return orTry(r, null);
    }

    default public <Y extends Throwable> ThrowingRunnable<Y> orTry(ThrowingRunnable<? extends Y> r,
            Consumer<? super Throwable> thrown) {
        ThrowingSupplier<Void, X> s1 = () -> {
            run();
            return null;
        };
        return s1.orTry(() -> {
            r.run();
            return null;
        }, thrown)::get;
    }
}
