package throwing.function;

@FunctionalInterface
public interface ThrowingObjDoubleConsumer<T, X extends Throwable> {
    public void accept(T t, double value) throws X;
}
