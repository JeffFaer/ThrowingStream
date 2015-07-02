package throwing.stream.terminal;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingLongBinaryOperator;
import throwing.function.ThrowingLongConsumer;
import throwing.function.ThrowingLongPredicate;
import throwing.function.ThrowingObjLongConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingLongStreamTerminal<X extends Throwable, Y extends Throwable> extends
    ThrowingBaseStreamTerminal<Long, X, Y> {
  @Override
  public Iterator.OfLong<X, Y> iterator();

  @Override
  public BaseSpliterator.OfLong<X, Y, ?> spliterator();

  default public void normalForEach(LongConsumer action) throws Y {
    forEach(action::accept);
  }

  public void forEach(ThrowingLongConsumer<? extends X> action) throws Y;

  default public void normalForEachOrdered(LongConsumer action) throws Y {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingLongConsumer<? extends X> action) throws Y;

  public long[] toArray() throws Y;

  default public long normalReduce(long identity, LongBinaryOperator op) throws Y {
    return reduce(identity, op::applyAsLong);
  }

  public long reduce(long identity, ThrowingLongBinaryOperator<? extends X> op) throws Y;

  default public OptionalLong normalReduce(LongBinaryOperator op) throws Y {
    return reduce(op::applyAsLong);
  }

  public OptionalLong reduce(ThrowingLongBinaryOperator<? extends X> op) throws Y;

  default public <R> R normalCollect(Supplier<R> supplier, ObjLongConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws Y {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjLongConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws Y;

  public long sum() throws Y;

  public OptionalLong min() throws Y;

  public OptionalLong max() throws Y;

  public long count() throws Y;

  public OptionalDouble average() throws Y;

  public LongSummaryStatistics summaryStatistics() throws Y;

  default public boolean normalAnyMatch(LongPredicate predicate) throws Y {
    return anyMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingLongPredicate<? extends X> predicate) throws Y;

  default public boolean normalAllMatch(LongPredicate predicate) throws Y {
    return allMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingLongPredicate<? extends X> predicate) throws Y;

  default public boolean normalNoneMatch(LongPredicate predicate) throws Y {
    return noneMatch((ThrowingLongPredicate<? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingLongPredicate<? extends X> predicate) throws Y;

  public OptionalLong findFirst() throws Y;

  public OptionalLong findAny() throws Y;
}
