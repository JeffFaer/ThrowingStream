package throwing.function;

@FunctionalInterface
public interface ThrowingLongBinaryOperator<X extends Throwable> {
    public long applyAsLong(long left, long right) throws X;
}
