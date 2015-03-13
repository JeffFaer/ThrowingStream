package throwing.bridge;

import java.util.function.Function;
import java.util.function.Predicate;

class AbstractBridge<D, X extends Throwable> implements Bridge<D> {
    private final D delegate;
    private final Class<X> x;

    AbstractBridge(D delegate, Class<X> x) {
        this.delegate = delegate;
        this.x = x;
    }

    @Override
    public D getDelegate() {
        return delegate;
    }

    public Class<X> getExceptionClass() {
        return x;
    }

    protected <Y extends Throwable> Y launder(Throwable t, Predicate<Throwable> p, Function<? super Throwable, ? extends Y> cast) {
        if (p.test(t)) {
            return cast.apply(t);
        } else if (t instanceof Error) {
            throw (Error) t;
        } else if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else {
            throw new AssertionError(t);
        }
    }
}
