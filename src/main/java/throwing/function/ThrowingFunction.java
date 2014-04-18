package throwing.function;

import java.util.Objects;

@FunctionalInterface
public interface ThrowingFunction<T, R, X extends Throwable> {
    public R apply(T t) throws X;
    
    default public <RR> ThrowingFunction<T, RR, X> andThen(ThrowingFunction<? super R, ? extends RR, ? extends X> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(apply(t));
    }
}
