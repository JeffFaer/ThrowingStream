package name.falgout.jeffrey.throwing.stream.terminal;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingBiFunction;
import name.falgout.jeffrey.throwing.ThrowingBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingComparator;
import name.falgout.jeffrey.throwing.ThrowingConsumer;
import name.falgout.jeffrey.throwing.ThrowingPredicate;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.stream.ThrowingCollector;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;
import name.falgout.jeffrey.throwing.stream.adapter.ThrowingBridge;

/**
 * Contains all of the terminal stream operations for a {@link ThrowingStream}.
 *
 *
 * @param <T>
 *          the type of the stream elements
 * @param <X>
 *          the type of exception this stream accepts
 */
public interface ThrowingStreamTerminal<T, X extends Throwable>
    extends ThrowingBaseStreamTerminal<T, X> {
  default public void normalForEach(Consumer<? super T> action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingConsumer<? super T, ? extends X> action) throws X;

  default public void normalForEachOrdered(Consumer<? super T> action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingConsumer<? super T, ? extends X> action) throws X;

  public Object[] toArray() throws X;

  public <A> A[] toArray(IntFunction<A[]> generator) throws X;

  default public T normalReduce(T identity, BinaryOperator<T> accumulator) throws X {
    return reduce(identity, accumulator::apply);
  }

  public T reduce(T identity, ThrowingBinaryOperator<T, ? extends X> accumulator) throws X;

  default public Optional<T> normalReduce(BinaryOperator<T> accumulator) throws X {
    return reduce(accumulator::apply);
  }

  public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends X> accumulator) throws X;

  default public <U> U normalReduce(U identity, BiFunction<U, ? super T, U> accumulator,
      BinaryOperator<U> combiner)
    throws X {
    return reduce(identity, accumulator::apply, combiner::apply);
  }

  public <U> U reduce(U identity, ThrowingBiFunction<U, ? super T, U, ? extends X> accumulator,
      ThrowingBinaryOperator<U, ? extends X> combiner)
    throws X;

  default public <R> R normalCollect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator,
      BiConsumer<R, R> combiner)
    throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingBiConsumer<R, ? super T, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner)
    throws X;

  default public <R, A> R collect(Collector<? super T, A, R> collector) throws X {
    return collect(ThrowingBridge.of(collector));
  }

  public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends X> collector) throws X;

  default public Optional<T> normalMin(Comparator<? super T> comparator) throws X {
    return min(comparator::compare);
  }

  public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws X;

  default public Optional<T> normalMax(Comparator<? super T> comparator) throws X {
    return max(comparator::compare);
  }

  public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws X;

  public long count() throws X;

  default public boolean normalAnyMatch(Predicate<? super T> predicate) throws X {
    return anyMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

  default public boolean normalAllMatch(Predicate<? super T> predicate) throws X {
    return allMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

  default public boolean normalNoneMatch(Predicate<? super T> predicate) throws X {
    return noneMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws X;

  public Optional<T> findFirst() throws X;

  public Optional<T> findAny() throws X;
}
