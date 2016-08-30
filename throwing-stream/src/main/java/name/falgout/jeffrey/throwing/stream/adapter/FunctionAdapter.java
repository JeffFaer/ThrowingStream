package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.Comparator;
import java.util.Objects;
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

import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingBiFunction;
import name.falgout.jeffrey.throwing.ThrowingBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingComparator;
import name.falgout.jeffrey.throwing.ThrowingConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingDoublePredicate;
import name.falgout.jeffrey.throwing.ThrowingDoubleToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingIntBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntFunction;
import name.falgout.jeffrey.throwing.ThrowingIntPredicate;
import name.falgout.jeffrey.throwing.ThrowingIntToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingIntToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingIntUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingLongFunction;
import name.falgout.jeffrey.throwing.ThrowingLongPredicate;
import name.falgout.jeffrey.throwing.ThrowingLongToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingLongToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingLongUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingObjDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingObjIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingObjLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingPredicate;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.ThrowingToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingToLongFunction;
import name.falgout.jeffrey.throwing.stream.ThrowingCollector;

class FunctionAdapter<X extends Throwable> extends UncheckedAdapter<Void, X> {
  FunctionAdapter(Class<X> x) {
    super(null, x);
  }

  public <R> Supplier<R> convert(ThrowingSupplier<? extends R, ? extends X> supplier) {
    return () -> maskException(supplier);
  }

  public <T> Predicate<T> convert(ThrowingPredicate<? super T, ? extends X> predicate) {
    return t -> maskException(() -> predicate.test(t));
  }

  public <T, R> Function<T, R>
      convert(ThrowingFunction<? super T, ? extends R, ? extends X> function) {
    return t -> maskException(() -> function.apply(t));
  }

  public <T> Consumer<T> convert(ThrowingConsumer<? super T, ? extends X> consumer) {
    return t -> maskException(() -> consumer.accept(t));
  }

  public <T, U, R> BiFunction<T, U, R>
      convert(ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends X> function) {
    return (t, u) -> maskException(() -> function.apply(t, u));
  }

  public <T> BinaryOperator<T> convert(ThrowingBinaryOperator<T, ? extends X> operator) {
    return (t1, t2) -> maskException(() -> operator.apply(t1, t2));
  }

  public <T> Comparator<T> convert(ThrowingComparator<? super T, ? extends X> comparator) {
    return (t1, t2) -> maskException(() -> comparator.compare(t1, t2));
  }

  public <T, U> BiConsumer<T, U>
      convert(ThrowingBiConsumer<? super T, ? super U, ? extends X> consumer) {
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
    return Collector.of(convert(collector.supplier()), convert(collector.accumulator()),
        convert(collector.combiner()), convert(collector.finisher()),
        collector.characteristics().stream().toArray(Collector.Characteristics[]::new));
  }
}
