package throwing.stream.union.adapter;

import java.util.function.Function;

import throwing.stream.ThrowingStream;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

public final class UnionBridge {
    private UnionBridge() {}

    public static <T> UnionStream<T, UnionThrowable> of(ThrowingStream<T, UnionThrowable> stream) {
        return of(stream, UnionThrowable.class, UnionThrowable::new);
    }

    public static <T, X extends UnionThrowable> UnionStream<T, X> of(ThrowingStream<T, X> stream,
            Class<X> x, Function<? super Throwable, X> ctor) {
        return new UnionStreamAdapter<>(stream, new UnionFunctionAdapter<>(x, ctor));
    }
}
