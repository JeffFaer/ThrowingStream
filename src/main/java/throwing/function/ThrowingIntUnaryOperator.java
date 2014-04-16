package throwing.function;

@FunctionalInterface
public interface ThrowingIntUnaryOperator<X extends Throwable> {
    public int applyAsInt(int operand) throws X;
}
