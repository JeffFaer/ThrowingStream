package throwing.function;

@FunctionalInterface
public interface ThrowingObjIntConsumer<T, X extends Throwable> {
    public void accept(T t, int value) throws X;
}
