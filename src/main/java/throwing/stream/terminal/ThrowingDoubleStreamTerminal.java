package throwing.stream.terminal;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingSupplier;

public interface ThrowingDoubleStreamTerminal<X extends Throwable, Y extends Throwable> extends
    ThrowingBaseStreamTerminal<Double, X, Y> {
  @Override
  public Iterator.OfDouble<X, Y> iterator();

  @Override
  public BaseSpliterator.OfDouble<X, Y, ?> spliterator();

  default public void normalForEach(DoubleConsumer action) throws Y {
    forEach(action::accept);
  }

  public void forEach(ThrowingDoubleConsumer<? extends X> action) throws Y;

  default public void normalForEachOrdered(DoubleConsumer action) throws Y {
    forEachOrdered(action::accept);
  }

  public void forEachOrdered(ThrowingDoubleConsumer<? extends X> action) throws Y;

  public double[] toArray() throws Y;

  default public double normalReduce(double identity, DoubleBinaryOperator op) throws Y {
    return reduce(identity, op::applyAsDouble);
  }

  public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends X> op) throws Y;

  default public OptionalDouble normalReduce(DoubleBinaryOperator op) throws Y {
    return reduce(op::applyAsDouble);
  }

  public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends X> op) throws Y;

  default public <R> R normalCollect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator,
      BiConsumer<R, R> combiner) throws Y {
    return collect(supplier::get, accumulator::accept, combiner::accept);
  }

  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjDoubleConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws Y;

  public double sum() throws Y;

  public OptionalDouble min() throws Y;

  public OptionalDouble max() throws Y;

  public long count() throws Y;

  public OptionalDouble average() throws Y;

  public DoubleSummaryStatistics summaryStatistics() throws Y;

  default public boolean normalAnyMatch(DoublePredicate predicate) throws Y {
    return anyMatch(predicate::test);
  }

  public boolean anyMatch(ThrowingDoublePredicate<? extends X> predicate) throws Y;

  default public boolean normalAllMatch(DoublePredicate predicate) throws Y {
    return allMatch(predicate::test);
  }

  public boolean allMatch(ThrowingDoublePredicate<? extends X> predicate) throws Y;

  default public boolean normalNoneMatch(DoublePredicate predicate) throws Y {
    return noneMatch(predicate::test);
  }

  public boolean noneMatch(ThrowingDoublePredicate<? extends X> predicate) throws Y;

  public OptionalDouble findFirst() throws Y;

  public OptionalDouble findAny() throws Y;
}
