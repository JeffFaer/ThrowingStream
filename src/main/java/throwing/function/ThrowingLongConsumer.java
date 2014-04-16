package throwing.function;

@FunctionalInterface
public interface ThrowingLongConsumer<X extends Throwable> {
    void accept(long value) throws X;
}
