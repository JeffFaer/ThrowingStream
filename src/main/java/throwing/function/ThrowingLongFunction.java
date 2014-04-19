package throwing.function;

import java.util.Objects;

@FunctionalInterface
public interface ThrowingLongFunction<R, X extends Throwable> {
    public R apply(long value) throws X;
    
    default public <RR> ThrowingLongFunction<RR, X> andThen(ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
        Objects.requireNonNull(after);
        return l -> after.apply(apply(l));
    }
}
