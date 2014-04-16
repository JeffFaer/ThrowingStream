package throwing.function;

@FunctionalInterface
public interface ThrowingBinaryOperator<T, X extends Throwable> extends ThrowingBiFunction<T, T, T, X> {}
