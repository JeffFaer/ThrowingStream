package throwing.function;

@FunctionalInterface
public interface ThrowingDoubleBinaryOperator<X extends Throwable> {
    public double applyAsDouble(double left, double right) throws X;
}
