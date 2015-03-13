package throwing;

@FunctionalInterface
public interface ThrowingRunnable<X extends Throwable> {
    public void run() throws X;

    default public Runnable fallbackTo(Runnable fallback) {
        ThrowingRunnable<Nothing> t = fallback::run;
        return orTry(t)::run;
    }

    default public <Y extends Throwable> ThrowingRunnable<Y> orTry(ThrowingRunnable<? extends Y> r) {
        return () -> {
            try {
                run();
            } catch (Throwable x) {
                try {
                    r.run();
                } catch (Throwable x2) {
                    x2.addSuppressed(x);
                    throw x2;
                }
            }
        };
    }
}
