package throwing.function;

@FunctionalInterface
public interface ThrowingIntPredicate<X extends Throwable> {
    public boolean test(int value) throws X;
}
