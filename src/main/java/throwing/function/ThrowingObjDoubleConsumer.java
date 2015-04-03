package throwing.function;

import java.util.function.Consumer;
import java.util.function.ObjDoubleConsumer;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingObjDoubleConsumer<T, X extends Throwable> {
    public void accept(T t, double value) throws X;

    default public ObjDoubleConsumer<T> fallbackTo(ObjDoubleConsumer<? super T> fallback) {
        ThrowingObjDoubleConsumer<T, Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingObjDoubleConsumer<T, Y> orTry(
            ThrowingObjDoubleConsumer<? super T, ? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingObjDoubleConsumer<T, Y> orTry(
            ThrowingObjDoubleConsumer<? super T, ? extends Y> f, Consumer<? super Throwable> thrown) {
        return (t, v) -> {
            ThrowingRunnable<X> s = () -> accept(t, v);
            s.orTry(() -> f.accept(t, v), thrown).run();
        };
    }
}
