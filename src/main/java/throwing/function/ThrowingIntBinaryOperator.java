package throwing.function;

@FunctionalInterface
public interface ThrowingIntBinaryOperator<X extends Throwable> {
    public int applyAsInt(int left, int right) throws X;
}
