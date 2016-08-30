package name.falgout.jeffrey.throwing.stream;

import java.util.Set;
import java.util.stream.Collector.Characteristics;

import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingSupplier;

public interface ThrowingCollector<T, A, R, X extends Throwable> {
  public ThrowingSupplier<A, X> supplier();

  public ThrowingBiConsumer<A, T, X> accumulator();

  public ThrowingBinaryOperator<A, X> combiner();

  public ThrowingFunction<A, R, X> finisher();

  public Set<Characteristics> characteristics();
}
