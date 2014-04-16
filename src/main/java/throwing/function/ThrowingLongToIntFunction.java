package throwing.function;

@FunctionalInterface
public interface ThrowingLongToIntFunction<X extends Throwable> {
    public int applyAsInt(long value) throws X;
}
