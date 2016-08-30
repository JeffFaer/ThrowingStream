package name.falgout.jeffrey.throwing.stream.terminal;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoublePredicate;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.ThrowingObjDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingSupplier;

public interface ThrowingDoubleStreamTerminal<X extends Throwable>
    extends ThrowingBaseStreamTerminal<Double, X> {
  @Override
  public ThrowingIterator.OfDouble<X> iterator();

  @Override
  public ThrowingBaseSpliterator.OfDouble<X> spliterator();

  default public void normalForEach(DoubleConsumer action) throws X {
    forEach(action::accept);
  }

  public void forEach(ThrowingDoubleConsumer<? extends X> action) throws X;

  default public void normalForEachOrdered(DoubleConsumer action) throws X {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingDoubleConsumer<? extends X> action) throws X;

  public double[] toArray() throws X;

  default public double normalReduce(double identity, DoubleBinaryOperator op) throws X {
    return reduce(identity, op::applyAsDouble);
  }

  public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends X> op) throws X;

  default public OptionalDouble normalReduce(DoubleBinaryOperator op) throws X {
    return reduce(op::applyAsDouble);
  }

  public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends X> op) throws X;

  default public <R> R normalCollect(Supplier<R> supplier,
      ObjDoubleConsumer<R> accumulator,
      BiConsumer<R, R> combiner)
    throws X {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjDoubleConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner)
    throws X;

  public double sum() throws X;

  public OptionalDouble min() throws X;

  public OptionalDouble max() throws X;

  public long count() throws X;

  public OptionalDouble average() throws X;

  public DoubleSummaryStatistics summaryStatistics() throws X;

  default public boolean normalAnyMatch(DoublePredicate predicate) throws X {
    return anyMatch(predicate::test);
  }

  public boolean anyMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

  default public boolean normalAllMatch(DoublePredicate predicate) throws X {
    return allMatch(predicate::test);
  }

  public boolean allMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

  default public boolean normalNoneMatch(DoublePredicate predicate) throws X {
    return noneMatch(predicate::test);
  }

  public boolean noneMatch(ThrowingDoublePredicate<? extends X> predicate) throws X;

  public OptionalDouble findFirst() throws X;

  public OptionalDouble findAny() throws X;
}
