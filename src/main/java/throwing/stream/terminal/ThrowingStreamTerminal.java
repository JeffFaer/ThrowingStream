package throwing.stream.terminal;

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

import throwing.ThrowingComparator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingStream;
import throwing.stream.adapter.ThrowingBridge;

/**
 * Contains all of the terminal stream operations for a {@link ThrowingStream}.
 *
 *
 * @param <T>
 *          the type of the stream elements
 * @param <X>
 *          the type of exception this stream accepts
 * @param <Y>
 *          the type of the exception that might be thrown
 */
public interface ThrowingStreamTerminal<T, X extends Throwable, Y extends Throwable> extends
    ThrowingBaseStreamTerminal<T, X, Y> {
  default public void normalForEach(Consumer<? super T> action) throws Y {
    forEach(action::accept);
  }

  public void forEach(ThrowingConsumer<? super T, ? extends X> action) throws Y;

  default public void normalForEachOrdered(Consumer<? super T> action) throws Y {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingConsumer<? super T, ? extends X> action) throws Y;

  public Object[] toArray() throws Y;

  public <A> A[] toArray(IntFunction<A[]> generator) throws Y;

  default public T normalReduce(T identity, BinaryOperator<T> accumulator) throws Y {
    return reduce(identity, accumulator::apply);
  }

  public T reduce(T identity, ThrowingBinaryOperator<T, ? extends X> accumulator) throws Y;

  default public Optional<T> normalReduce(BinaryOperator<T> accumulator) throws Y {
    return reduce(accumulator::apply);
  }

  public Optional<T> reduce(ThrowingBinaryOperator<T, ? extends X> accumulator) throws Y;

  default public <U> U normalReduce(U identity, BiFunction<U, ? super T, U> accumulator,
      BinaryOperator<U> combiner) throws Y {
    return reduce(identity, accumulator::apply, combiner::apply);
  }

  public <U> U reduce(U identity, ThrowingBiFunction<U, ? super T, U, ? extends X> accumulator,
      ThrowingBinaryOperator<U, ? extends X> combiner) throws Y;

  default public <R> R normalCollect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator,
      BiConsumer<R, R> combiner) throws Y {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingBiConsumer<R, ? super T, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws Y;

  default public <R, A> R collect(Collector<? super T, A, R> collector) throws Y {
    return collect(ThrowingBridge.of(collector));
  }

  public <R, A> R collect(ThrowingCollector<? super T, A, R, ? extends X> collector) throws Y;

  default public Optional<T> normalMin(Comparator<? super T> comparator) throws Y {
    return min(comparator::compare);
  }

  public Optional<T> min(ThrowingComparator<? super T, ? extends X> comparator) throws Y;

  default public Optional<T> normalMax(Comparator<? super T> comparator) throws Y {
    return max(comparator::compare);
  }

  public Optional<T> max(ThrowingComparator<? super T, ? extends X> comparator) throws Y;

  public long count() throws Y;

  default public boolean normalAnyMatch(Predicate<? super T> predicate) throws Y {
    return anyMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws Y;

  default public boolean normalAllMatch(Predicate<? super T> predicate) throws Y {
    return allMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws Y;

  default public boolean normalNoneMatch(Predicate<? super T> predicate) throws Y {
    return noneMatch((ThrowingPredicate<? super T, ? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingPredicate<? super T, ? extends X> predicate) throws Y;

  public Optional<T> findFirst() throws Y;

  public Optional<T> findAny() throws Y;
}
