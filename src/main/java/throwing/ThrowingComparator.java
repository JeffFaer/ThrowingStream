package throwing;

@FunctionalInterface
public interface ThrowingComparator<T, X extends Throwable> {
    public int compare(T o1, T o2) throws X;
}
