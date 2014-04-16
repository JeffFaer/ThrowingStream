package throwing.function;

@FunctionalInterface
public interface ThrowingObjLongConsumer<T, X extends Throwable> {
    public void accept(T t, long value) throws X;
}
