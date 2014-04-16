package throwing.function;

@FunctionalInterface
public interface ThrowingDoubleUnaryOperator<X extends Throwable> {
    public double applyAsDouble(double operand) throws X;
}
