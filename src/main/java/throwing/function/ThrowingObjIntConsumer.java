package throwing.function;

import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingObjIntConsumer<T, X extends Throwable> {
    public void accept(T t, int value) throws X;

    default public ObjIntConsumer<T> fallbackTo(ObjIntConsumer<? super T> fallback) {
        ThrowingObjIntConsumer<T, Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingObjIntConsumer<T, Y> orTry(
            ThrowingObjIntConsumer<? super T, ? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingObjIntConsumer<T, Y> orTry(
            ThrowingObjIntConsumer<? super T, ? extends Y> f, Consumer<? super Throwable> thrown) {
        return (t, v) -> {
            ThrowingRunnable<X> s = () -> accept(t, v);
            s.orTry(() -> f.accept(t, v)).run();
        };
    }
}
