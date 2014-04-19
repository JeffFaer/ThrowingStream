package throwing.function;

@FunctionalInterface
public interface ThrowingLongUnaryOperator<X extends Throwable> {
    public long applyAsLong(long operand) throws X;
}
