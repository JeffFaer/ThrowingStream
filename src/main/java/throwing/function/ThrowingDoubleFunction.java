package throwing.function;

@FunctionalInterface
public interface ThrowingDoubleFunction<R, X extends Throwable> {
    public R apply(double value) throws X;
}
