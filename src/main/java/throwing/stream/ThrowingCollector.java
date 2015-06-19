package throwing.stream;

import java.util.Set;
import java.util.stream.Collector.Characteristics;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingSupplier;

public interface ThrowingCollector<T, A, R, X extends Throwable> {
    public ThrowingSupplier<A, X> supplier();

    public ThrowingBiConsumer<A, T, X> accumulator();

    public ThrowingBinaryOperator<A, X> combiner();

    public ThrowingFunction<A, R, X> finisher();

    public Set<Characteristics> characteristics();
}
