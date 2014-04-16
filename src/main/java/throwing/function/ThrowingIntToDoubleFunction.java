package throwing.function;

@FunctionalInterface
public interface ThrowingIntToDoubleFunction<X extends Throwable> {
    public double applyAsDouble(int value) throws X;
}
