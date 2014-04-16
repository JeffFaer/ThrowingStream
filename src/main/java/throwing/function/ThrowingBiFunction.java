package throwing.function;

@FunctionalInterface
public interface ThrowingBiFunction<T, U, R, X extends Throwable> {
    public R apply(T t, U u) throws X;
}
