package throwing.function;

@FunctionalInterface
public interface ThrowingToLongFunction<T, X extends Throwable> {
    public long applyAsLong(T value) throws X;
}
