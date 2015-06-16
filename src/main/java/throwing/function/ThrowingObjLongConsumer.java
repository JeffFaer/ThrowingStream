package throwing.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;

import throwing.Nothing;
import throwing.ThrowingRunnable;

@FunctionalInterface
public interface ThrowingObjLongConsumer<T, X extends Throwable> {
    public void accept(T t, long value) throws X;

    default public ObjLongConsumer<T> fallbackTo(ObjLongConsumer<? super T> fallback) {
        ThrowingObjLongConsumer<T, Nothing> t = fallback::accept;
        return orTry(t)::accept;
    }

    default public <Y extends Throwable> ThrowingObjLongConsumer<T, Y> orTry(
            ThrowingObjLongConsumer<? super T, ? extends Y> f) {
        return orTry(f, null);
    }

    default public <Y extends Throwable> ThrowingObjLongConsumer<T, Y> orTry(
            ThrowingObjLongConsumer<? super T, ? extends Y> f, Consumer<? super Throwable> thrown) {
        return (t, v) -> {
            ThrowingRunnable<X> s = () -> accept(t, v);
            s.orTry(() -> f.accept(t, v), thrown).run();
        };
    }

    default public <Y extends Throwable> ThrowingObjLongConsumer<T, Y> rethrow(Class<X> x,
            Function<? super X, ? extends Y> mapper) {
        return (t, v) -> {
            ThrowingRunnable<X> s = () -> accept(t, v);
            s.rethrow(x, mapper).run();
        };
    }
}
