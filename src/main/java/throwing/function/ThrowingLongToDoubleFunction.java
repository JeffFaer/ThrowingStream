package throwing.function;

@FunctionalInterface
public interface ThrowingLongToDoubleFunction<X extends Throwable> {
    public double applyAsDouble(long value) throws X;
}
