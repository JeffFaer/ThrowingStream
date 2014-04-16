package throwing.function;

@FunctionalInterface
public interface ThrowingLongUnaryOperator<X extends Throwable> {
    public long applyAsDouble(long operand) throws X;
}
