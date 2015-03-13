package throwing.bridge;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
interface RethrowChain<X extends Throwable> {
    public static final RethrowChain<Throwable> START = t -> Optional.empty();
    public static final RethrowChain<Throwable> END = t -> {
        if (t instanceof Error) {
            throw (Error) t;
        } else if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else {
            throw new AssertionError(t);
        }
    };

    @SuppressWarnings("unchecked")
    public static <Y extends Throwable> RethrowChain<Y> start() {
        return (RethrowChain<Y>) START;
    }

    @SuppressWarnings("unchecked")
    public static <Y extends Throwable> RethrowChain<Y> end() {
        return (RethrowChain<Y>) END;
    }

    public Optional<X> handle(Throwable t);

    default public <Y extends Throwable> RethrowChain<Y> rethrow(Function<? super X, ? extends Y> mapper) {
        return t -> this.handle(t).map(mapper);
    }

    default public RethrowChain<X> chain(RethrowChain<X> second) {
        return this == START ? second : t -> {
            Optional<X> o = this.handle(t);
            return o.isPresent() ? o : second.handle(t);
        };
    };

    default public Function<Throwable, X> finish() {
        return t -> this.chain(end()).handle(t).get();
    }
}
