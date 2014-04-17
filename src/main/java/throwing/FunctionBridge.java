package throwing;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingIntBinaryOperator;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingIntUnaryOperator;
import throwing.function.ThrowingObjIntConsumer;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;
import throwing.function.ThrowingToIntFunction;
import throwing.stream.ThrowingCollector;

public class FunctionBridge<X extends Throwable> {
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
    
    public Class<X> getExceptionClass() {
        return x;
    }
    
    private void launder(ThrowingRunnable<? extends X> r) {
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
    
    public <R> Supplier<R> convert(ThrowingSupplier<? extends R, ? extends X> supplier) {
        return () -> launder(supplier);
    }
    
    public <T> Predicate<T> convert(ThrowingPredicate<? super T, ? extends X> predicate) {
        return t -> launder(() -> predicate.test(t));
    }
    
    public <T, R> Function<T, R> convert(ThrowingFunction<? super T, ? extends R, ? extends X> function) {
        return t -> launder(() -> function.apply(t));
    }
    
    public <T> Consumer<T> convert(ThrowingConsumer<? super T, ? extends X> consumer) {
        return t -> launder(() -> consumer.accept(t));
    }
    
    public <T, U, R> BiFunction<T, U, R> convert(
            ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends X> function) {
        return (t, u) -> launder(() -> function.apply(t, u));
    }
    
    public <T> BinaryOperator<T> convert(ThrowingBinaryOperator<T, ? extends X> operator) {
        return (t1, t2) -> launder(() -> operator.apply(t1, t2));
    }
    
    public <T> Comparator<T> convert(ThrowingComparator<? super T, ? extends X> comparator) {
        return (t1, t2) -> launder(() -> comparator.compare(t1, t2));
    }
    
    public <T, U> BiConsumer<T, U> convert(ThrowingBiConsumer<? super T, ? super U, ? extends X> consumer) {
        return (t, u) -> launder(() -> consumer.accept(t, u));
    }
    
    public <T, A, R> Collector<T, A, R> convert(ThrowingCollector<? super T, A, ? extends R, ? extends X> collector) {
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
    
    public IntConsumer convert(ThrowingIntConsumer<? extends X> consumer) {
        return i -> launder(() -> consumer.accept(i));
    }
    
    public IntPredicate convert(ThrowingIntPredicate<? extends X> predicate) {
        return i -> launder(() -> predicate.test(i));
    }
    
    public IntBinaryOperator convert(ThrowingIntBinaryOperator<? extends X> operator) {
        return (i1, i2) -> launder(() -> operator.applyAsInt(i1, i2));
    }
    
    public <T> ObjIntConsumer<T> convert(ThrowingObjIntConsumer<T, ? extends X> consumer) {
        return (t, i) -> launder(() -> consumer.accept(t, i));
    }
    
    public IntUnaryOperator convert(ThrowingIntUnaryOperator<? extends X> operator) {
        return i -> launder(() -> operator.applyAsInt(i));
    }
    
    public <R> IntFunction<R> convert(ThrowingIntFunction<R, ? extends X> function) {
        return i -> launder(() -> function.apply(i));
    }
    
    public <T> ToIntFunction<T> convert(ThrowingToIntFunction<T, ? extends X> function) {
        return t -> launder(() -> function.applyAsInt(t));
    }
}
