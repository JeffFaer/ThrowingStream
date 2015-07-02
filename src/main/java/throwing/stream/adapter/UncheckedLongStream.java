package throwing.stream.adapter;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator.OfLong;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import throwing.stream.ThrowingLongStream;

class UncheckedLongStream<X extends Throwable> extends
    UncheckedBaseStream<Long, X, LongStream, ThrowingLongStream<X>> implements LongStream {
  UncheckedLongStream(ThrowingLongStream<X> delegate, Class<X> x) {
    super(delegate, x);
  }

  @Override
  public LongStream getSelf() {
    return this;
  }

  @Override
  public LongStream createNewAdapter(ThrowingLongStream<X> delegate) {
    return new UncheckedLongStream<>(delegate, getExceptionClass());
  }

  @Override
  public OfLong iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionClass());
  }

  @Override
  public Spliterator.OfLong spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionClass());
  }

  @Override
  public LongStream filter(LongPredicate predicate) {
    return chain(ThrowingLongStream::normalFilter, predicate);
  }

  @Override
  public LongStream map(LongUnaryOperator mapper) {
    return chain(ThrowingLongStream::normalMap, mapper);
  }

  @Override
  public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToObj(mapper), getExceptionClass());
  }

  @Override
  public IntStream mapToInt(LongToIntFunction mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToInt(mapper), getExceptionClass());
  }

  @Override
  public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
    return ThrowingBridge.of(getDelegate().normalMapToDouble(mapper), getExceptionClass());
  }

  @Override
  public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
    LongFunction<? extends ThrowingLongStream<? extends X>> f = i -> ThrowingBridge.of(
        mapper.apply(i), getExceptionClass());
    return chain(ThrowingLongStream::normalFlatMap, f);
  }

  @Override
  public LongStream distinct() {
    return chain(ThrowingLongStream::distinct);
  }

  @Override
  public LongStream sorted() {
    return chain(ThrowingLongStream::sorted);
  }

  @Override
  public LongStream peek(LongConsumer action) {
    return chain(ThrowingLongStream::normalPeek, action);
  }

  @Override
  public LongStream limit(long maxSize) {
    return chain(ThrowingLongStream::limit, maxSize);
  }

  @Override
  public LongStream skip(long n) {
    return chain(ThrowingLongStream::skip, n);
  }

  @Override
  public void forEach(LongConsumer action) {
    maskException(() -> getDelegate().normalForEach(action));
  }

  @Override
  public void forEachOrdered(LongConsumer action) {
    maskException(() -> getDelegate().normalForEachOrdered(action));
  }

  @Override
  public long[] toArray() {
    return maskException(getDelegate()::toArray);
  }

  @Override
  public long reduce(long identity, LongBinaryOperator op) {
    return maskException(() -> getDelegate().normalReduce(identity, op));
  }

  @Override
  public OptionalLong reduce(LongBinaryOperator op) {
    return maskException(() -> getDelegate().normalReduce(op));
  }

  @Override
  public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator,
      BiConsumer<R, R> combiner) {
    return maskException(() -> getDelegate().normalCollect(supplier, accumulator, combiner));
  }

  @Override
  public long sum() {
    return maskException(getDelegate()::sum);
  }

  @Override
  public OptionalLong min() {
    return maskException(getDelegate()::min);
  }

  @Override
  public OptionalLong max() {
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
  public LongSummaryStatistics summaryStatistics() {
    return maskException(getDelegate()::summaryStatistics);
  }

  @Override
  public boolean anyMatch(LongPredicate predicate) {
    return maskException(() -> getDelegate().normalAnyMatch(predicate));
  }

  @Override
  public boolean allMatch(LongPredicate predicate) {
    return maskException(() -> getDelegate().normalAllMatch(predicate));
  }

  @Override
  public boolean noneMatch(LongPredicate predicate) {
    return maskException(() -> getDelegate().normalNoneMatch(predicate));
  }

  @Override
  public OptionalLong findFirst() {
    return maskException(getDelegate()::findFirst);
  }

  @Override
  public OptionalLong findAny() {
    return maskException(getDelegate()::findAny);
  }

  @Override
  public DoubleStream asDoubleStream() {
    return ThrowingBridge.of(getDelegate().asDoubleStream(), getExceptionClass());
  }

  @Override
  public Stream<Long> boxed() {
    return ThrowingBridge.of(getDelegate().boxed(), getExceptionClass());
  }
}
