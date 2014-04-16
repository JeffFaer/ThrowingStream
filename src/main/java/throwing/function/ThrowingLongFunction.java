package throwing.function;

@FunctionalInterface
public interface ThrowingLongFunction<R, X extends Throwable> {
    public R apply(long value) throws X;
}
