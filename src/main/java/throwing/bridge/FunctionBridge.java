package throwing.bridge;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import throwing.ThrowingComparator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingIntBinaryOperator;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingIntToDoubleFunction;
import throwing.function.ThrowingIntToLongFunction;
import throwing.function.ThrowingIntUnaryOperator;
import throwing.function.ThrowingLongBinaryOperator;
import throwing.function.ThrowingLongConsumer;
import throwing.function.ThrowingLongFunction;
import throwing.function.ThrowingLongPredicate;
import throwing.function.ThrowingLongToDoubleFunction;
import throwing.function.ThrowingLongToIntFunction;
import throwing.function.ThrowingLongUnaryOperator;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingObjIntConsumer;
import throwing.function.ThrowingObjLongConsumer;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;
import throwing.function.ThrowingToDoubleFunction;
import throwing.function.ThrowingToIntFunction;
import throwing.function.ThrowingToLongFunction;

class FunctionBridge<X extends Throwable> extends UncheckedBridge<Void, X> {
    FunctionBridge(Class<X> x) {
        super(null, x);
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
    
    // int
    
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
    
    public IntToLongFunction convert(ThrowingIntToLongFunction<? extends X> function) {
        return i -> launder(() -> function.applyAsLong(i));
    }
    
    public IntToDoubleFunction convert(ThrowingIntToDoubleFunction<? extends X> function) {
        return i -> launder(() -> function.applyAsDouble(i));
    }
    
    // long
    
    public LongConsumer convert(ThrowingLongConsumer<? extends X> consumer) {
        return l -> launder(() -> consumer.accept(l));
    }
    
    public LongPredicate convert(ThrowingLongPredicate<? extends X> predicate) {
        return l -> launder(() -> predicate.test(l));
    }
    
    public LongBinaryOperator convert(ThrowingLongBinaryOperator<? extends X> operator) {
        return (l1, l2) -> launder(() -> operator.applyAsLong(l1, l2));
    }
    
    public <T> ObjLongConsumer<T> convert(ThrowingObjLongConsumer<T, ? extends X> consumer) {
        return (t, l) -> launder(() -> consumer.accept(t, l));
    }
    
    public LongUnaryOperator convert(ThrowingLongUnaryOperator<? extends X> operator) {
        return l -> launder(() -> operator.applyAsLong(l));
    }
    
    public <R> LongFunction<R> convert(ThrowingLongFunction<R, ? extends X> function) {
        return l -> launder(() -> function.apply(l));
    }
    
    public <T> ToLongFunction<T> convert(ThrowingToLongFunction<T, ? extends X> function) {
        return t -> launder(() -> function.applyAsLong(t));
    }
    
    public LongToIntFunction convert(ThrowingLongToIntFunction<? extends X> function) {
        return l -> launder(() -> function.applyAsInt(l));
    }
    
    public LongToDoubleFunction convert(ThrowingLongToDoubleFunction<? extends X> function) {
        return l -> launder(() -> function.applyAsDouble(l));
    }
    
    // double
    
    public DoubleConsumer convert(ThrowingDoubleConsumer<? extends X> consumer) {
        return d -> launder(() -> consumer.accept(d));
    }
    
    public DoublePredicate convert(ThrowingDoublePredicate<? extends X> predicate) {
        return d -> launder(() -> predicate.test(d));
    }
    
    public DoubleBinaryOperator convert(ThrowingDoubleBinaryOperator<? extends X> operator) {
        return (d1, d2) -> launder(() -> operator.applyAsDouble(d1, d2));
    }
    
    public <T> ObjDoubleConsumer<T> convert(ThrowingObjDoubleConsumer<T, ? extends X> consumer) {
        return (t, d) -> launder(() -> consumer.accept(t, d));
    }
    
    public DoubleUnaryOperator convert(ThrowingDoubleUnaryOperator<? extends X> operator) {
        return d -> launder(() -> operator.applyAsDouble(d));
    }
    
    public <R> DoubleFunction<R> convert(ThrowingDoubleFunction<R, ? extends X> function) {
        return d -> launder(() -> function.apply(d));
    }
    
    public <T> ToDoubleFunction<T> convert(ThrowingToDoubleFunction<T, ? extends X> function) {
        return t -> launder(() -> function.applyAsDouble(t));
    }
    
    public DoubleToIntFunction convert(ThrowingDoubleToIntFunction<? extends X> function) {
        return d -> launder(() -> function.applyAsInt(d));
    }
    
    public DoubleToLongFunction convert(ThrowingDoubleToLongFunction<? extends X> function) {
        return d -> launder(() -> function.applyAsLong(d));
    }
}
