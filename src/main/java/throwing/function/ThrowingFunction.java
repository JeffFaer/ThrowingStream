package throwing.function;

@FunctionalInterface
public interface ThrowingFunction<T, R, X extends Throwable> {
    public R apply(T t) throws X;
}
