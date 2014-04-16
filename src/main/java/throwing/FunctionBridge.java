package throwing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingCollector;

public class FunctionBridge<X extends Throwable> {
    public static final class Nothing extends Throwable {
        private static final long serialVersionUID = -5459023265330371793L;
        
        private Nothing() {
            throw new Error("No instances!");
        }
    }
    
    public static final class BridgeException extends RuntimeException {
        private static final long serialVersionUID = -3986425123148316828L;
        
        BridgeException(Throwable cause) {
            super(cause);
        }
    }
    
    private final Class<X> x;
    
    public FunctionBridge(Class<X> x) {
        this.x = x;
    }
    
    private <R> void launder(ThrowingRunnable<? extends X> r) {
        launder(() -> {
            r.run();
            return null;
        });
    }
    
    private <R> R launder(ThrowingSupplier<R, ? extends X> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            if (x.isInstance(e)) {
                throw new BridgeException(e);
            } else if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else if (e instanceof Error) {
                throw (Error) e;
            } else {
                throw new AssertionError(e);
            }
        }
    }
    
    public <R> Supplier<R> convert(ThrowingSupplier<R, ? extends X> supplier) {
        return () -> launder(supplier);
    }
    
    public <T> Predicate<T> convert(ThrowingPredicate<T, ? extends X> predicate) {
        return t -> launder(() -> predicate.test(t));
    }
    
    public <T, R> Function<T, R> convert(ThrowingFunction<T, R, ? extends X> function) {
        return t -> launder(() -> function.apply(t));
    }
    
    public <T> Consumer<T> convert(ThrowingConsumer<T, ? extends X> consumer) {
        return t -> launder(() -> consumer.accept(t));
    }
    
    public <T, U, R> BiFunction<T, U, R> convert(ThrowingBiFunction<T, U, R, ? extends X> function) {
        return (t, u) -> launder(() -> function.apply(t, u));
    }
    
    public <T> BinaryOperator<T> convert(ThrowingBinaryOperator<T, ? extends X> operator) {
        return (t1, t2) -> launder(() -> operator.apply(t1, t2));
    }
    
    public <T> Comparator<T> convert(ThrowingComparator<T, ? extends X> comparator) {
        return (t1, t2) -> launder(() -> comparator.compare(t1, t2));
    }
    
    public <T, U> BiConsumer<T, U> convert(ThrowingBiConsumer<T, U, ? extends X> consumer) {
        return (t, u) -> launder(() -> consumer.accept(t, u));
    }
    
    public <T, A, R> Collector<T, A, R> convert(ThrowingCollector<T, A, R, ? extends X> collector) {
        return new Collector<T, A, R>() {
            @Override
            public Supplier<A> supplier() {
                return convert(collector.supplier());
            }
            
            @Override
            public BiConsumer<A, T> accumulator() {
                return convert(collector.accumulator());
            }
            
            @Override
            public BinaryOperator<A> combiner() {
                return convert(collector.combiner());
            }
            
            @Override
            public Function<A, R> finisher() {
                return convert(collector.finisher());
            }
            
            @Override
            public Set<Collector.Characteristics> characteristics() {
                return collector.characteristics();
            }
        };
    }
}
