package throwing.function;

@FunctionalInterface
public interface ThrowingConsumer<T, X extends Throwable> {
    public void accept(T t) throws X;
}
