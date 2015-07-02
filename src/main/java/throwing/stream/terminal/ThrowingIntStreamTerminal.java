package throwing.stream.terminal;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingIntBinaryOperator;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingObjIntConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingIntStreamTerminal<X extends Throwable, Y extends Throwable> extends
    ThrowingBaseStreamTerminal<Integer, X, Y> {
  @Override
  public Iterator.OfInt<X, Y> iterator();

  @Override
  public BaseSpliterator.OfInt<X, Y, ?> spliterator();

  default public void normalForEach(IntConsumer action) throws Y {
    forEach(action::accept);
  }

  public void forEach(ThrowingIntConsumer<? extends X> action) throws Y;

  default public void normalForEachOrdered(IntConsumer action) throws Y {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingIntConsumer<? extends X> action) throws Y;

  public int[] toArray() throws Y;

  default public int normalReduce(int identity, IntBinaryOperator op) throws Y {
    return reduce(identity, op::applyAsInt);
  }

  public int reduce(int identity, ThrowingIntBinaryOperator<? extends X> op) throws Y;

  default public OptionalInt normalReduce(IntBinaryOperator op) throws Y {
    return reduce(op::applyAsInt);
  }

  public OptionalInt reduce(ThrowingIntBinaryOperator<? extends X> op) throws Y;

  default public <R> R normalCollect(Supplier<R> supplier, ObjIntConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws Y {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjIntConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws Y;

  public int sum() throws Y;

  public OptionalInt min() throws Y;

  public OptionalInt max() throws Y;

  public long count() throws Y;

  public OptionalDouble average() throws Y;

  public IntSummaryStatistics summaryStatistics() throws Y;

  default public boolean normalAnyMatch(IntPredicate predicate) throws Y {
    return anyMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingIntPredicate<? extends X> predicate) throws Y;

  default public boolean normalAllMatch(IntPredicate predicate) throws Y {
    return allMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingIntPredicate<? extends X> predicate) throws Y;

  default public boolean normalNoneMatch(IntPredicate predicate) throws Y {
    return noneMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingIntPredicate<? extends X> predicate) throws Y;

  public OptionalInt findFirst() throws Y;

  public OptionalInt findAny() throws Y;
}
