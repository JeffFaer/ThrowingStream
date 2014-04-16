package throwing.function;

@FunctionalInterface
public interface ThrowingDoubleToIntFunction<X extends Throwable> {
    public int applyAsInt(double value) throws X;
}
