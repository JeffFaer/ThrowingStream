package throwing.function;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingIntFunction<R, X extends Throwable> {
    public R apply(int value) throws X;

    default public IntFunction<R> fallbackTo(IntFunction<? extends R> fallback) {
        ThrowingIntFunction<R, Nothing> t = fallback::apply;
        return orTry(t)::apply;
    }

    default public <Y extends Throwable> ThrowingIntFunction<R, Y> orTry(ThrowingIntFunction<? extends R, ? extends Y> f) {
        return t -> {
            ThrowingSupplier<R, X> s = () -> apply(t);
            return s.orTry(() -> f.apply(t)).get();
        };
    }

    default public <RR> ThrowingIntFunction<RR, X> andThen(Function<? super R, ? extends RR> after) {
        return andThen((ThrowingFunction<? super R, ? extends RR, ? extends X>) after::apply);
    }

    default public <RR> ThrowingIntFunction<RR, X> andThen(ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
        Objects.requireNonNull(after);
        return i -> after.apply(apply(i));
    }
}
