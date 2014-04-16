package throwing.function;

@FunctionalInterface
public interface ThrowingDoublePredicate<X extends Throwable> {
    public boolean test(double value) throws X;
}
