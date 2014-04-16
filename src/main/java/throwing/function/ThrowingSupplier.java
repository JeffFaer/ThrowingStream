package throwing.function;

@FunctionalInterface
public interface ThrowingSupplier<R, X extends Throwable> {
    public R get() throws X;
}
