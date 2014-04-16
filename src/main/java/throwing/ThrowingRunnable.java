package throwing;

@FunctionalInterface
public interface ThrowingRunnable<X extends Throwable> {
    public void run() throws X;
}
