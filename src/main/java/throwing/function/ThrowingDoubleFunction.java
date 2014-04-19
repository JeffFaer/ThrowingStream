package throwing.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ThrowingDoubleFunction<R, X extends Throwable> {
    public R apply(double value) throws X;
    
    default public <RR> ThrowingDoubleFunction<RR, X> andThen(Function<? super R, ? extends RR> after) {
        return andThen((ThrowingFunction<? super R, ? extends RR, ? extends X>) after::apply);
    }
    
    default public <RR> ThrowingDoubleFunction<RR, X> andThen(
            ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
        Objects.requireNonNull(after);
        return d -> after.apply(apply(d));
    }
}
