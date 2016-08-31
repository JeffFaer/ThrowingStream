package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator.OfInt;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;

class UncheckedIntStream<X extends Throwable> extends
    UncheckedBaseStream<Integer, X, IntStream, ThrowingIntStream<X>> implements IntStream {
  UncheckedIntStream(ThrowingIntStream<X> delegate, Class<X> x) {
    super(delegate, x);
  }

  @Override
  public IntStream getSelf() {
    return this;
  }

  @Override
  public IntStream createNewAdapter(ThrowingIntStream<X> delegate) {
    return new UncheckedIntStream<>(delegate, getExceptionClass());
  }

  @Override
  public OfInt iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionClass());
  }

  @Override
  public Spliterator.OfInt spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionClass());
  }

  @Override
  public IntStream filter(IntPredicate predicate) {
    return chain(ThrowingIntStream::normalFilter, predicate);
  }

  @Override
  public IntStream map(IntUnaryOperator mapper) {
    return chain(ThrowingIntStream::normalMap, mapper);
  }

  @Override
  public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToObj(mapper), getExceptionClass());
  }

  @Override
  public LongStream mapToLong(IntToLongFunction mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToLong(mapper), getExceptionClass());
  }

  @Override
  public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToDouble(mapper), getExceptionClass());
  }

  @Override
  public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
    IntFunction<? extends ThrowingIntStream<? extends X>> f = i -> ThrowingBridge.of(
        mapper.apply(i), getExceptionClass());
    return chain(ThrowingIntStream::normalFlatMap, f);
  }

  @Override
  public IntStream distinct() {
    return chain(ThrowingIntStream::distinct);
  }

  @Override
  public IntStream sorted() {
    return chain(ThrowingIntStream::sorted);
  }

  @Override
  public IntStream peek(IntConsumer action) {
    return chain(ThrowingIntStream::normalPeek, action);
  }

  @Override
  public IntStream limit(long maxSize) {
    return chain(ThrowingIntStream::limit, maxSize);
  }

  @Override
  public IntStream skip(long n) {
    return chain(ThrowingIntStream::skip, n);
  }

  @Override
  public void forEach(IntConsumer action) {
    getExceptionMasker().maskException(() -> getDelegate().normalForEach(action));
  }

  @Override
  public void forEachOrdered(IntConsumer action) {
    getExceptionMasker().maskException(() -> getDelegate().normalForEachOrdered(action));
  }

  @Override
  public int[] toArray() {
    return getExceptionMasker().maskException(getDelegate()::toArray);
  }

  @Override
  public int reduce(int identity, IntBinaryOperator op) {
    return getExceptionMasker().maskException(() -> getDelegate().normalReduce(identity, op));
  }

  @Override
  public OptionalInt reduce(IntBinaryOperator op) {
    return getExceptionMasker().maskException(() -> getDelegate().normalReduce(op));
  }

  @Override
  public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator,
      BiConsumer<R, R> combiner) {
    return getExceptionMasker()
        .maskException(() -> getDelegate().normalCollect(supplier, accumulator, combiner));
  }

  @Override
  public int sum() {
    return getExceptionMasker().maskException(getDelegate()::sum);
  }

  @Override
  public OptionalInt min() {
    return getExceptionMasker().maskException(getDelegate()::min);
  }

  @Override
  public OptionalInt max() {
    return getExceptionMasker().maskException(getDelegate()::max);
  }

  @Override
  public long count() {
    return getExceptionMasker().maskException(getDelegate()::count);
  }

  @Override
  public OptionalDouble average() {
    return getExceptionMasker().maskException(getDelegate()::average);
  }

  @Override
  public IntSummaryStatistics summaryStatistics() {
    return getExceptionMasker().maskException(getDelegate()::summaryStatistics);
  }

  @Override
  public boolean anyMatch(IntPredicate predicate) {
    return getExceptionMasker().maskException(() -> getDelegate().normalAnyMatch(predicate));
  }

  @Override
  public boolean allMatch(IntPredicate predicate) {
    return getExceptionMasker().maskException(() -> getDelegate().normalAllMatch(predicate));
  }

  @Override
  public boolean noneMatch(IntPredicate predicate) {
    return getExceptionMasker().maskException(() -> getDelegate().normalNoneMatch(predicate));
  }

  @Override
  public OptionalInt findFirst() {
    return getExceptionMasker().maskException(getDelegate()::findFirst);
  }

  @Override
  public OptionalInt findAny() {
    return getExceptionMasker().maskException(getDelegate()::findAny);
  }

  @Override
  public LongStream asLongStream() {
    return ThrowingBridge.of(getDelegate().asLongStream(), getExceptionClass());
  }

  @Override
  public DoubleStream asDoubleStream() {
    return ThrowingBridge.of(getDelegate().asDoubleStream(), getExceptionClass());
  }

  @Override
  public Stream<Integer> boxed() {
    return ThrowingBridge.of(getDelegate().boxed(), getExceptionClass());
  }
}
