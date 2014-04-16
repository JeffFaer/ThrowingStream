package throwing.function;

@FunctionalInterface
public interface ThrowingIntConsumer<X extends Throwable> {
    void accept(int value) throws X;
}
