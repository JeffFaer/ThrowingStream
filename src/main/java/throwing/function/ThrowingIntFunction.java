package throwing.function;

@FunctionalInterface
public interface ThrowingIntFunction<R, X extends Throwable> {
    public R apply(int value) throws X;
}
