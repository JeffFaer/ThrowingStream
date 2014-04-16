package throwing.function;

@FunctionalInterface
public interface ThrowingToIntFunction<T, X extends Throwable> {
    public int applyAsInt(T value) throws X;
}
