package throwing.function;

import java.util.Objects;
import java.util.function.DoubleFunction;
import java.util.function.Function;

import throwing.Nothing;

@FunctionalInterface
public interface ThrowingDoubleFunction<R, X extends Throwable> {
    public R apply(double value) throws X;

    default public DoubleFunction<R> fallbackTo(DoubleFunction<? extends R> fallback) {
        ThrowingDoubleFunction<R, Nothing> t = fallback::apply;
        return orTry(t)::apply;
    }

    default public <Y extends Throwable> ThrowingDoubleFunction<R, Y> orTry(
            ThrowingDoubleFunction<? extends R, ? extends Y> f) {
        return t -> {
            ThrowingSupplier<R, X> s = () -> apply(t);
            return s.orTry(() -> f.apply(t)).get();
        };
    }

    default public <RR> ThrowingDoubleFunction<RR, X> andThen(Function<? super R, ? extends RR> after) {
        return andThen((ThrowingFunction<? super R, ? extends RR, ? extends X>) after::apply);
    }

    default public <RR> ThrowingDoubleFunction<RR, X> andThen(
            ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
        Objects.requireNonNull(after);
        return d -> after.apply(apply(d));
    }
}
