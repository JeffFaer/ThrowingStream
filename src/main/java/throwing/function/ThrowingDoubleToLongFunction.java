package throwing.function;

@FunctionalInterface
public interface ThrowingDoubleToLongFunction<X extends Throwable> {
    public long applyAsLong(double value) throws X;
}
