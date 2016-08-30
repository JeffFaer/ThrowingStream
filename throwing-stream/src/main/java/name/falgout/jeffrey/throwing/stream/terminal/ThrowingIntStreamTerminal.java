package name.falgout.jeffrey.throwing.stream.terminal;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntPredicate;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.ThrowingObjIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingSupplier;

public interface ThrowingIntStreamTerminal<X extends Throwable>
    extends ThrowingBaseStreamTerminal<Integer, X> {
  @Override
  public ThrowingIterator.OfInt<X> iterator();

  @Override
  public ThrowingBaseSpliterator.OfInt<X> spliterator();

  default public void normalForEach(IntConsumer action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingIntConsumer<? extends X> action) throws X;

  default public void normalForEachOrdered(IntConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingIntConsumer<? extends X> action) throws X;

  public int[] toArray() throws X;

  default public int normalReduce(int identity, IntBinaryOperator op) throws X {
    return reduce(identity, op::applyAsInt);
  }

  public int reduce(int identity, ThrowingIntBinaryOperator<? extends X> op) throws X;

  default public OptionalInt normalReduce(IntBinaryOperator op) throws X {
    return reduce(op::applyAsInt);
  }

  public OptionalInt reduce(ThrowingIntBinaryOperator<? extends X> op) throws X;

  default public <R> R normalCollect(Supplier<R> supplier, ObjIntConsumer<R> accumulator,
      BiConsumer<R, R> combiner)
    throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjIntConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner)
    throws X;

  public int sum() throws X;

  public OptionalInt min() throws X;

  public OptionalInt max() throws X;

  public long count() throws X;

  public OptionalDouble average() throws X;

  public IntSummaryStatistics summaryStatistics() throws X;

  default public boolean normalAnyMatch(IntPredicate predicate) throws X {
    return anyMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean anyMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

  default public boolean normalAllMatch(IntPredicate predicate) throws X {
    return allMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean allMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

  default public boolean normalNoneMatch(IntPredicate predicate) throws X {
    return noneMatch((ThrowingIntPredicate<? extends X>) predicate::test);
  }

  public boolean noneMatch(ThrowingIntPredicate<? extends X> predicate) throws X;

  public OptionalInt findFirst() throws X;

  public OptionalInt findAny() throws X;
}
