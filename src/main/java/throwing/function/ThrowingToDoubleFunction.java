package throwing.function;

@FunctionalInterface
public interface ThrowingToDoubleFunction<T, X extends Throwable> {
    public double applyAsDouble(T value) throws X;
}
