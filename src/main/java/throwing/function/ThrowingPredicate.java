package throwing.function;

@FunctionalInterface
public interface ThrowingPredicate<T, X extends Throwable> {
    public boolean test(T t) throws X;
}
