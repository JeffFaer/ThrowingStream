package name.falgout.jeffrey.throwing.stream.terminal;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.ThrowingLongBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingLongPredicate;
import name.falgout.jeffrey.throwing.ThrowingObjLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingSupplier;

public interface ThrowingLongStreamTerminal<X extends Throwable>
    extends ThrowingBaseStreamTerminal<Long, X> {
  @Override
  public ThrowingIterator.OfLong<X> iterator();

  @Override
  public ThrowingBaseSpliterator.OfLong<X> spliterator();

  default public void normalForEach(LongConsumer action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingLongConsumer<? extends X> action) throws X;

  default public void normalForEachOrdered(LongConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingLongConsumer<? extends X> action) throws X;

  public long[] toArray() throws X;

  default public long normalReduce(long identity, LongBinaryOperator op) throws X {
    return reduce(identity, op::applyAsLong);
  }

  public long reduce(long identity, ThrowingLongBinaryOperator<? extends X> op) throws X;

  default public OptionalLong normalReduce(LongBinaryOperator op) throws X {
    return reduce(op::applyAsLong);
  }

  public OptionalLong reduce(ThrowingLongBinaryOperator<? extends X> op) throws X;

  default public <R> R normalCollect(Supplier<R> supplier, ObjLongConsumer<R> accumulator,
      BiConsumer<R, R> combiner)
    throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjLongConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner)
    throws X;

  public long sum() throws X;

  public OptionalLong min() throws X;

  public OptionalLong max() throws X;

  public long count() throws X;

  public OptionalDouble average() throws X;

  public LongSummaryStatistics summaryStatistics() throws X;

  default public boolean normalAnyMatch(LongPredicate predicate) throws X {
    return anyMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

  default public boolean normalAllMatch(LongPredicate predicate) throws X {
    return allMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

  default public boolean normalNoneMatch(LongPredicate predicate) throws X {
    return noneMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingLongPredicate<? extends X> predicate) throws X;

  public OptionalLong findFirst() throws X;

  public OptionalLong findAny() throws X;
}
