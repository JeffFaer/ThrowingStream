package throwing.function;

@FunctionalInterface
public interface ThrowingLongPredicate<X extends Throwable> {
    public boolean test(long value) throws X;
}
