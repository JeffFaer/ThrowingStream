package throwing.stream.adapter;

import java.util.Iterator;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.Nothing;
import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

public final class ThrowingAdapter {
    private ThrowingAdapter() {}
    
    // checked
    
    // streams
    
    public static <T> ThrowingStream<T, Nothing> of(Stream<T> stream) {
        return of(stream, Nothing.class);
    }
    
    public static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionAdapter<>(x));
    }
    
    static <T, X extends Throwable> ThrowingStream<T, X> of(Stream<T> stream, FunctionAdapter<X> x) {
        Objects.requireNonNull(stream, "stream");
        return new CheckedStream<>(stream, x);
    }
    
    // int stream
    
    public static ThrowingIntStream<Nothing> of(IntStream stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingIntStream<X> of(IntStream stream, FunctionAdapter<X> x) {
        Objects.requireNonNull(stream, "stream");
        return new CheckedIntStream<>(stream, x);
    }
    
    // long stream
    
    public static ThrowingLongStream<Nothing> of(LongStream stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingLongStream<X> of(LongStream stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingLongStream<X> of(LongStream stream, FunctionAdapter<X> x) {
        Objects.requireNonNull(stream, "stream");
        return new CheckedLongStream<>(stream, x);
    }
    
    // double stream
    
    public static ThrowingDoubleStream<Nothing> of(DoubleStream stream) {
        return of(stream, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingDoubleStream<X> of(DoubleStream stream, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(stream, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingDoubleStream<X> of(DoubleStream stream, FunctionAdapter<X> x) {
        Objects.requireNonNull(stream, "stream");
        return new CheckedDoubleStream<>(stream, x);
    }
    
    // utilities
    
    // iterator
    
    public static <T> ThrowingIterator<T, Nothing> of(Iterator<T> itr) {
        return of(itr, Nothing.class);
    }
    
    public static <T, X extends Throwable> ThrowingIterator<T, X> of(Iterator<T> itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <T, X extends Throwable> ThrowingIterator<T, X> of(Iterator<T> itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator<>(itr, x);
    }
    
    // int
    
    public static <T> ThrowingIterator.OfInt<Nothing> of(PrimitiveIterator.OfInt itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIterator.OfInt<X> of(PrimitiveIterator.OfInt itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingIterator.OfInt<X> of(PrimitiveIterator.OfInt itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator.OfInt<>(itr, x);
    }
    
    // long
    
    public static <T> ThrowingIterator.OfLong<Nothing> of(PrimitiveIterator.OfLong itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIterator.OfLong<X> of(PrimitiveIterator.OfLong itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingIterator.OfLong<X> of(PrimitiveIterator.OfLong itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator.OfLong<>(itr, x);
    }
    
    // double
    
    public static <T> ThrowingIterator.OfDouble<Nothing> of(PrimitiveIterator.OfDouble itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingIterator.OfDouble<X> of(PrimitiveIterator.OfDouble itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingIterator.OfDouble<X> of(PrimitiveIterator.OfDouble itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedIterator.OfDouble<>(itr, x);
    }
    
    // spliterator
    
    public static <T> ThrowingSpliterator<T, Nothing> of(Spliterator<T> itr) {
        return of(itr, Nothing.class);
    }
    
    public static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator<>(itr, x);
    }
    
    // int
    
    public static ThrowingSpliterator.OfInt<Nothing> of(Spliterator.OfInt itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingSpliterator.OfInt<X> of(Spliterator.OfInt itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingSpliterator.OfInt<X> of(Spliterator.OfInt itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator.OfInt<>(itr, x);
    }
    
    // long
    
    public static ThrowingSpliterator.OfLong<Nothing> of(Spliterator.OfLong itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingSpliterator.OfLong<X> of(Spliterator.OfLong itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingSpliterator.OfLong<X> of(Spliterator.OfLong itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator.OfLong<>(itr, x);
    }
    
    // double
    
    public static ThrowingSpliterator.OfDouble<Nothing> of(Spliterator.OfDouble itr) {
        return of(itr, Nothing.class);
    }
    
    public static <X extends Throwable> ThrowingSpliterator.OfDouble<X> of(Spliterator.OfDouble itr, Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(itr, new FunctionAdapter<>(x));
    }
    
    static <X extends Throwable> ThrowingSpliterator.OfDouble<X> of(Spliterator.OfDouble itr, FunctionAdapter<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new CheckedSpliterator.OfDouble<>(itr, x);
    }
    
    public static <T, A, R, X extends Throwable> ThrowingCollector<T, A, R, X> of(Collector<T, A, R> collector) {
        Objects.requireNonNull(collector);
        return new ThrowingCollector<T, A, R, X>() {
            @Override
            public ThrowingSupplier<A, X> supplier() {
                return collector.supplier()::get;
            }
            
            @Override
            public ThrowingBiConsumer<A, T, X> accumulator() {
                return collector.accumulator()::accept;
            }
            
            @Override
            public ThrowingBinaryOperator<A, X> combiner() {
                return collector.combiner()::apply;
            }
            
            @Override
            public ThrowingFunction<A, R, X> finisher() {
                return collector.finisher()::apply;
            }
            
            @Override
            public Set<Characteristics> characteristics() {
                return collector.characteristics();
            }
        };
    }
    
    // unchecked
    
    // streams
    
    public static <T> Stream<T> of(ThrowingStream<T, Nothing> stream) {
        return of(stream, Nothing.class);
    }
    
    static <T, X extends Throwable> Stream<T> of(ThrowingStream<T, X> stream, Class<X> x) {
        Objects.requireNonNull(stream, "stream");
        Objects.requireNonNull(x, "x");
        return stream instanceof CheckedStream ? ((CheckedStream<T, X>) stream).getDelegate() : new UncheckedStream<>(
                stream, x);
    }
    
    // int stream
    
    public static IntStream of(ThrowingIntStream<Nothing> stream) {
        return of(stream, Nothing.class);
    }
    
    static <X extends Throwable> IntStream of(ThrowingIntStream<X> stream, Class<X> x) {
        Objects.requireNonNull(stream, "stream");
        Objects.requireNonNull(x, "x");
        return new UncheckedIntStream<>(stream, x);
    }
    
    // long stream
    
    public static LongStream of(ThrowingLongStream<Nothing> stream) {
        return of(stream, Nothing.class);
    }
    
    static <X extends Throwable> LongStream of(ThrowingLongStream<X> stream, Class<X> x) {
        Objects.requireNonNull(stream, "stream");
        Objects.requireNonNull(x, "x");
        return new UncheckedLongStream<>(stream, x);
    }
    
    // double stream
    
    public static DoubleStream of(ThrowingDoubleStream<Nothing> stream) {
        return of(stream, Nothing.class);
    }
    
    static <X extends Throwable> DoubleStream of(ThrowingDoubleStream<X> stream, Class<X> x) {
        Objects.requireNonNull(stream, "stream");
        Objects.requireNonNull(x, "x");
        return new UncheckedDoubleStream<>(stream, x);
    }
    
    // utilities
    
    // spliterator
    
    public static <T> Spliterator<T> of(ThrowingSpliterator<T, Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <T, X extends Throwable> Spliterator<T> of(ThrowingSpliterator<T, X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedSpliterator<>(itr, x);
    }
    
    // int
    
    public static <T> Spliterator.OfInt of(ThrowingSpliterator.OfInt<Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <T, X extends Throwable> Spliterator.OfInt of(ThrowingSpliterator.OfInt<X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedSpliterator.OfInt<>(itr, x);
    }
    
    // long
    
    public static <T> Spliterator.OfLong of(ThrowingSpliterator.OfLong<Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <T, X extends Throwable> Spliterator.OfLong of(ThrowingSpliterator.OfLong<X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedSpliterator.OfLong<>(itr, x);
    }
    
    // double
    
    public static <T> Spliterator.OfDouble of(ThrowingSpliterator.OfDouble<Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <T, X extends Throwable> Spliterator.OfDouble of(ThrowingSpliterator.OfDouble<X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedSpliterator.OfDouble<>(itr, x);
    }
    
    // iterator
    
    public static <T> Iterator<T> of(ThrowingIterator<T, Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <T, X extends Throwable> Iterator<T> of(ThrowingIterator<T, X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedIterator<>(itr, x);
    }
    
    // int
    
    public static PrimitiveIterator.OfInt of(ThrowingIterator.OfInt<Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <X extends Throwable> PrimitiveIterator.OfInt of(ThrowingIterator.OfInt<X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedIterator.OfInt<>(itr, x);
    }
    
    // long
    
    public static PrimitiveIterator.OfLong of(ThrowingIterator.OfLong<Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <X extends Throwable> PrimitiveIterator.OfLong of(ThrowingIterator.OfLong<X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedIterator.OfLong<>(itr, x);
    }
    
    // double
    
    public static PrimitiveIterator.OfDouble of(ThrowingIterator.OfDouble<Nothing> itr) {
        return of(itr, Nothing.class);
    }
    
    static <X extends Throwable> PrimitiveIterator.OfDouble of(ThrowingIterator.OfDouble<X> itr, Class<X> x) {
        Objects.requireNonNull(itr, "itr");
        return new UncheckedIterator.OfDouble<>(itr, x);
    }
    
    public static <T, A, R, X extends Throwable> Collector<T, A, R> of(ThrowingCollector<T, A, R, X> collector,
            Class<X> x) {
        Objects.requireNonNull(x, "x");
        return of(collector, new FunctionAdapter<>(x));
    }
    
    static <T, A, R, X extends Throwable> Collector<T, A, R> of(ThrowingCollector<T, A, R, ? extends X> collector,
            FunctionAdapter<X> x) {
        Objects.requireNonNull(collector);
        return new Collector<T, A, R>() {
            @Override
            public Supplier<A> supplier() {
                return x.convert(collector.supplier());
            }
            
            @Override
            public BiConsumer<A, T> accumulator() {
                return x.convert(collector.accumulator());
            }
            
            @Override
            public BinaryOperator<A> combiner() {
                return x.convert(collector.combiner());
            }
            
            @Override
            public Function<A, R> finisher() {
                return x.convert(collector.finisher());
            }
            
            @Override
            public Set<Collector.Characteristics> characteristics() {
                return collector.characteristics();
            }
        };
    }
}
