package throwing.function;

@FunctionalInterface
public interface ThrowingDoubleConsumer<X extends Throwable> {
    void accept(double value) throws X;
}
