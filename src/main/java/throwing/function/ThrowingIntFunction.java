package throwing.function;

import java.util.Objects;

@FunctionalInterface
public interface ThrowingIntFunction<R, X extends Throwable> {
    public R apply(int value) throws X;
    
    default public <RR> ThrowingIntFunction<RR, X> andThen(ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
        Objects.requireNonNull(after);
        return i -> after.apply(apply(i));
    }
}
