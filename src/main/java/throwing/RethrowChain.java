package throwing;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface RethrowChain<X extends Throwable, Y extends Throwable> extends
        Function<X, Optional<Y>> {
    public static final RethrowChain<Throwable, Throwable> START = t -> Optional.empty();
    public static final RethrowChain<Throwable, Throwable> END = t -> {
        if (t instanceof Error) {
            throw (Error) t;
        } else if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else {
            throw new AssertionError(t);
        }
    };
    public static final Function<Throwable, Error> END_FUNCTION = RethrowChain.<Throwable, Error> start()
            .finish();

    @SuppressWarnings("unchecked")
    public static <X extends Throwable, Y extends Throwable> RethrowChain<X, Y> start() {
        return (RethrowChain<X, Y>) START;
    }

    @SuppressWarnings("unchecked")
    public static <X extends Throwable, Y extends Throwable> RethrowChain<X, Y> end() {
        return (RethrowChain<X, Y>) END;
    }

    public static <Y extends Throwable> RethrowChain<Throwable, Y> rethrowAs(Class<Y> clazz) {
        return e -> clazz.isInstance(e) ? Optional.of(clazz.cast(e)) : Optional.empty();
    }

    default public <Z extends Throwable> RethrowChain<X, Z> rethrow(
            Function<? super Y, ? extends Z> mapper) {
        return t -> apply(t).map(mapper);
    }

    default public RethrowChain<X, Y> chain(RethrowChain<X, Y> second) {
        return this == START ? second : t -> {
            Optional<Y> o = apply(t);
            return o.isPresent() ? o : second.apply(t);
        };
    };

    default public Function<X, Y> finish() {
        RethrowChain<X, Y> c = this.chain(end());
        return t -> c.apply(t).get();
    }
}
