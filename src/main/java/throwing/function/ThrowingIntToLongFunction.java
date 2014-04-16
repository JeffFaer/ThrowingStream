package throwing.function;

@FunctionalInterface
public interface ThrowingIntToLongFunction<X extends Throwable> {
    public long applyAsLong(int value) throws X;
}
