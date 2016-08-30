package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator.OfDouble;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;

class UncheckedDoubleStream<X extends Throwable> extends
    UncheckedBaseStream<Double, X, DoubleStream, ThrowingDoubleStream<X>> implements DoubleStream {
  UncheckedDoubleStream(ThrowingDoubleStream<X> delegate, Class<X> x) {
    super(delegate, x);
  }

  @Override
  public DoubleStream getSelf() {
    return this;
  }

  @Override
  public DoubleStream createNewAdapter(ThrowingDoubleStream<X> delegate) {
    return new UncheckedDoubleStream<>(delegate, getExceptionClass());
  }

  @Override
  public OfDouble iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionClass());
  }

  @Override
  public Spliterator.OfDouble spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionClass());
  }

  @Override
  public DoubleStream filter(DoublePredicate predicate) {
    return chain(ThrowingDoubleStream::normalFilter, predicate);
  }

  @Override
  public DoubleStream map(DoubleUnaryOperator mapper) {
    return chain(ThrowingDoubleStream::normalMap, mapper);
  }

  @Override
  public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToObj(mapper), getExceptionClass());
  }

  @Override
  public LongStream mapToLong(DoubleToLongFunction mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToLong(mapper), getExceptionClass());
  }

  @Override
  public IntStream mapToInt(DoubleToIntFunction mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToInt(mapper), getExceptionClass());
  }

  @Override
  public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
    DoubleFunction<? extends ThrowingDoubleStream<? extends X>> f = i -> ThrowingBridge.of(
        mapper.apply(i), getExceptionClass());
    return chain(ThrowingDoubleStream::normalFlatMap, f);
  }

  @Override
  public DoubleStream distinct() {
    return chain(ThrowingDoubleStream::distinct);
  }

  @Override
  public DoubleStream sorted() {
    return chain(ThrowingDoubleStream::sorted);
  }

  @Override
  public DoubleStream peek(DoubleConsumer action) {
    return chain(ThrowingDoubleStream::normalPeek, action);
  }

  @Override
  public DoubleStream limit(long maxSize) {
    return chain(ThrowingDoubleStream::limit, maxSize);
  }

  @Override
  public DoubleStream skip(long n) {
    return chain(ThrowingDoubleStream::skip, n);
  }

  @Override
  public void forEach(DoubleConsumer action) {
    maskException(() -> getDelegate().normalForEach(action));
  }

  @Override
  public void forEachOrdered(DoubleConsumer action) {
    maskException(() -> getDelegate().normalForEachOrdered(action));
  }

  @Override
  public double[] toArray() {
    return maskException(getDelegate()::toArray);
  }

  @Override
  public double reduce(double identity, DoubleBinaryOperator op) {
    return maskException(() -> getDelegate().normalReduce(identity, op));
  }

  @Override
  public OptionalDouble reduce(DoubleBinaryOperator op) {
    return maskException(() -> getDelegate().normalReduce(op));
  }

  @Override
  public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator,
      BiConsumer<R, R> combiner) {
    return maskException(() -> getDelegate().normalCollect(supplier, accumulator, combiner));
  }

  @Override
  public double sum() {
    return maskException(getDelegate()::sum);
  }

  @Override
  public OptionalDouble min() {
    return maskException(getDelegate()::min);
  }

  @Override
  public OptionalDouble max() {
    return maskException(getDelegate()::max);
  }

  @Override
  public long count() {
    return maskException(getDelegate()::count);
  }

  @Override
  public OptionalDouble average() {
    return maskException(getDelegate()::average);
  }

  @Override
  public DoubleSummaryStatistics summaryStatistics() {
    return maskException(getDelegate()::summaryStatistics);
  }

  @Override
  public boolean anyMatch(DoublePredicate predicate) {
    return maskException(() -> getDelegate().normalAnyMatch(predicate));
  }

  @Override
  public boolean allMatch(DoublePredicate predicate) {
    return maskException(() -> getDelegate().normalAllMatch(predicate));
  }

  @Override
  public boolean noneMatch(DoublePredicate predicate) {
    return maskException(() -> getDelegate().normalNoneMatch(predicate));
  }

  @Override
  public OptionalDouble findFirst() {
    return maskException(getDelegate()::findFirst);
  }

  @Override
  public OptionalDouble findAny() {
    return maskException(getDelegate()::findAny);
  }

  @Override
  public Stream<Double> boxed() {
    return ThrowingBridge.of(getDelegate().boxed(), getExceptionClass());
  }
}
