package throwing.function;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U, X extends Throwable> {
    public void accept(T t, U u) throws X;
}
