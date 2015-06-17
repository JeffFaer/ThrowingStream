package throwing.stream.adapter;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
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
import java.util.stream.Collector;

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
import throwing.stream.ThrowingCollector;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

class FunctionAdapter<X extends Throwable> extends UncheckedAdapter<Void, X> {

    @SuppressFBWarnings(value = "NP_NONNULL_PARAM_VIOLATION", justification = "Delegate not needed")
    FunctionAdapter(Class<X> x) {
        super(null, x);
    }

    public <R> Supplier<R> convert(ThrowingSupplier<? extends R, ? extends X> supplier) {
        return () -> maskException(supplier);
    }

    public <T> Predicate<T> convert(ThrowingPredicate<? super T, ? extends X> predicate) {
        return t -> maskException(() -> predicate.test(t));
    }

    public <T, R> Function<T, R> convert(
            ThrowingFunction<? super T, ? extends R, ? extends X> function) {
        return t -> maskException(() -> function.apply(t));
    }

    public <T> Consumer<T> convert(ThrowingConsumer<? super T, ? extends X> consumer) {
        return t -> maskException(() -> consumer.accept(t));
    }

    public <T, U, R> BiFunction<T, U, R> convert(
            ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends X> function) {
        return (t, u) -> maskException(() -> function.apply(t, u));
    }

    public <T> BinaryOperator<T> convert(ThrowingBinaryOperator<T, ? extends X> operator) {
        return (t1, t2) -> maskException(() -> operator.apply(t1, t2));
    }

    public <T> Comparator<T> convert(ThrowingComparator<? super T, ? extends X> comparator) {
        return (t1, t2) -> maskException(() -> comparator.compare(t1, t2));
    }

    public <T, U> BiConsumer<T, U> convert(
            ThrowingBiConsumer<? super T, ? super U, ? extends X> consumer) {
        return (t, u) -> maskException(() -> consumer.accept(t, u));
    }

    // int

    public IntConsumer convert(ThrowingIntConsumer<? extends X> consumer) {
        return i -> maskException(() -> consumer.accept(i));
    }

    public IntPredicate convert(ThrowingIntPredicate<? extends X> predicate) {
        return i -> maskException(() -> predicate.test(i));
    }

    public IntBinaryOperator convert(ThrowingIntBinaryOperator<? extends X> operator) {
        return (i1, i2) -> maskException(() -> operator.applyAsInt(i1, i2));
    }

    public <T> ObjIntConsumer<T> convert(ThrowingObjIntConsumer<T, ? extends X> consumer) {
        return (t, i) -> maskException(() -> consumer.accept(t, i));
    }

    public IntUnaryOperator convert(ThrowingIntUnaryOperator<? extends X> operator) {
        return i -> maskException(() -> operator.applyAsInt(i));
    }

    public <R> IntFunction<R> convert(ThrowingIntFunction<R, ? extends X> function) {
        return i -> maskException(() -> function.apply(i));
    }

    public <T> ToIntFunction<T> convert(ThrowingToIntFunction<T, ? extends X> function) {
        return t -> maskException(() -> function.applyAsInt(t));
    }

    public IntToLongFunction convert(ThrowingIntToLongFunction<? extends X> function) {
        return i -> maskException(() -> function.applyAsLong(i));
    }

    public IntToDoubleFunction convert(ThrowingIntToDoubleFunction<? extends X> function) {
        return i -> maskException(() -> function.applyAsDouble(i));
    }

    // long

    public LongConsumer convert(ThrowingLongConsumer<? extends X> consumer) {
        return l -> maskException(() -> consumer.accept(l));
    }

    public LongPredicate convert(ThrowingLongPredicate<? extends X> predicate) {
        return l -> maskException(() -> predicate.test(l));
    }

    public LongBinaryOperator convert(ThrowingLongBinaryOperator<? extends X> operator) {
        return (l1, l2) -> maskException(() -> operator.applyAsLong(l1, l2));
    }

    public <T> ObjLongConsumer<T> convert(ThrowingObjLongConsumer<T, ? extends X> consumer) {
        return (t, l) -> maskException(() -> consumer.accept(t, l));
    }

    public LongUnaryOperator convert(ThrowingLongUnaryOperator<? extends X> operator) {
        return l -> maskException(() -> operator.applyAsLong(l));
    }

    public <R> LongFunction<R> convert(ThrowingLongFunction<R, ? extends X> function) {
        return l -> maskException(() -> function.apply(l));
    }

    public <T> ToLongFunction<T> convert(ThrowingToLongFunction<T, ? extends X> function) {
        return t -> maskException(() -> function.applyAsLong(t));
    }

    public LongToIntFunction convert(ThrowingLongToIntFunction<? extends X> function) {
        return l -> maskException(() -> function.applyAsInt(l));
    }

    public LongToDoubleFunction convert(ThrowingLongToDoubleFunction<? extends X> function) {
        return l -> maskException(() -> function.applyAsDouble(l));
    }

    // double

    public DoubleConsumer convert(ThrowingDoubleConsumer<? extends X> consumer) {
        return d -> maskException(() -> consumer.accept(d));
    }

    public DoublePredicate convert(ThrowingDoublePredicate<? extends X> predicate) {
        return d -> maskException(() -> predicate.test(d));
    }

    public DoubleBinaryOperator convert(ThrowingDoubleBinaryOperator<? extends X> operator) {
        return (d1, d2) -> maskException(() -> operator.applyAsDouble(d1, d2));
    }

    public <T> ObjDoubleConsumer<T> convert(ThrowingObjDoubleConsumer<T, ? extends X> consumer) {
        return (t, d) -> maskException(() -> consumer.accept(t, d));
    }

    public DoubleUnaryOperator convert(ThrowingDoubleUnaryOperator<? extends X> operator) {
        return d -> maskException(() -> operator.applyAsDouble(d));
    }

    public <R> DoubleFunction<R> convert(ThrowingDoubleFunction<R, ? extends X> function) {
        return d -> maskException(() -> function.apply(d));
    }

    public <T> ToDoubleFunction<T> convert(ThrowingToDoubleFunction<T, ? extends X> function) {
        return t -> maskException(() -> function.applyAsDouble(t));
    }

    public DoubleToIntFunction convert(ThrowingDoubleToIntFunction<? extends X> function) {
        return d -> maskException(() -> function.applyAsInt(d));
    }

    public DoubleToLongFunction convert(ThrowingDoubleToLongFunction<? extends X> function) {
        return d -> maskException(() -> function.applyAsLong(d));
    }

    public <T, A, R> Collector<T, A, R> convert(ThrowingCollector<T, A, R, ? extends X> collector) {
        Objects.requireNonNull(collector);
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
