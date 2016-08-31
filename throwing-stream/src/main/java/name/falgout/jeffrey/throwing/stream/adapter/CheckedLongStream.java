package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.LongStream;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingIterator.OfLong;
import name.falgout.jeffrey.throwing.ThrowingLongBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingLongFunction;
import name.falgout.jeffrey.throwing.ThrowingLongPredicate;
import name.falgout.jeffrey.throwing.ThrowingLongToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingLongToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingLongUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingObjLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

class CheckedLongStream<X extends Throwable> extends
    CheckedBaseStream<Long, X, LongStream, ThrowingLongStream<X>> implements ThrowingLongStream<X> {
  CheckedLongStream(LongStream delegate, ExceptionMasker<X> ExceptionMasker) {
    super(delegate, ExceptionMasker);
  }

  CheckedLongStream(LongStream delegate, ExceptionMasker<X> ExceptionMasker,
      RethrowChain<Throwable, X> chain) {
    super(delegate, ExceptionMasker, chain);
  }

  @Override
  public ThrowingLongStream<X> getSelf() {
    return this;
  }

  @Override
  public ThrowingLongStream<X> createNewAdapter(LongStream delegate) {
    return new CheckedLongStream<>(delegate, getExceptionMasker(), getChain());
  }

  @Override
  public OfLong<X> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionMasker());
  }

  @Override
  public ThrowingBaseSpliterator.OfLong<X> spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionMasker());
  }

  @Override
  public ThrowingLongStream<X> filter(ThrowingLongPredicate<? extends X> predicate) {
    return chain(LongStream::filter, getExceptionMasker().mask(predicate));
  }

  @Override
  public ThrowingLongStream<X> map(ThrowingLongUnaryOperator<? extends X> mapper) {
    return chain(LongStream::map, getExceptionMasker().mask(mapper));
  }

  @Override
  public <U> ThrowingStream<U, X> mapToObj(ThrowingLongFunction<? extends U, ? extends X> mapper) {
    LongFunction<? extends U> f = getExceptionMasker().mask(mapper);
    return ThrowingBridge.of(getDelegate().mapToObj(f), getExceptionMasker());
  }

  @Override
  public ThrowingIntStream<X> mapToInt(ThrowingLongToIntFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToInt(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingDoubleStream<X> mapToDouble(ThrowingLongToDoubleFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToDouble(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingLongStream<X> flatMap(
      ThrowingLongFunction<? extends ThrowingLongStream<? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked")
    Function<? super ThrowingLongStream<? extends X>, ? extends LongStream> c =
        s -> ThrowingBridge.of((ThrowingLongStream<X>) s, getExceptionClass());
    return chain(LongStream::flatMap, getExceptionMasker().mask(mapper.andThen(c::apply)));
  }

  @Override
  public ThrowingLongStream<X> distinct() {
    return chain(LongStream::distinct);
  }

  @Override
  public ThrowingLongStream<X> sorted() {
    return chain(LongStream::sorted);
  }

  @Override
  public ThrowingLongStream<X> peek(ThrowingLongConsumer<? extends X> action) {
    return chain(LongStream::peek, getExceptionMasker().mask(action));
  }

  @Override
  public ThrowingLongStream<X> limit(long maxSize) {
    return chain(LongStream::limit, maxSize);
  }

  @Override
  public ThrowingLongStream<X> skip(long n) {
    return chain(LongStream::skip, n);
  }

  @Override
  public void forEach(ThrowingLongConsumer<? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEach(getExceptionMasker().mask(action)));
  }

  @Override
  public void forEachOrdered(ThrowingLongConsumer<? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEachOrdered(getExceptionMasker().mask(action)));
  }

  @Override
  public long[] toArray() throws X {
    return unmaskException(getDelegate()::toArray);
  }

  @Override
  public long reduce(long identity, ThrowingLongBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(identity, getExceptionMasker().mask(op)));
  }

  @Override
  public OptionalLong reduce(ThrowingLongBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(getExceptionMasker().mask(op)));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjLongConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X {
    return unmaskException(() -> getDelegate().collect(getExceptionMasker().mask(supplier),
        getExceptionMasker().mask(accumulator), getExceptionMasker().mask(combiner)));
  }

  @Override
  public long sum() throws X {
    return unmaskException(getDelegate()::sum);
  }

  @Override
  public OptionalLong min() throws X {
    return unmaskException(getDelegate()::min);
  }

  @Override
  public OptionalLong max() throws X {
    return unmaskException(getDelegate()::max);
  }

  @Override
  public long count() throws X {
    return unmaskException(getDelegate()::count);
  }

  @Override
  public OptionalDouble average() throws X {
    return unmaskException(getDelegate()::average);
  }

  @Override
  public LongSummaryStatistics summaryStatistics() throws X {
    return unmaskException(getDelegate()::summaryStatistics);
  }

  @Override
  public boolean anyMatch(ThrowingLongPredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().anyMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean allMatch(ThrowingLongPredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().allMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean noneMatch(ThrowingLongPredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().noneMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public OptionalLong findFirst() throws X {
    return unmaskException(getDelegate()::findFirst);
  }

  @Override
  public OptionalLong findAny() throws X {
    return unmaskException(getDelegate()::findAny);
  }

  @Override
  public ThrowingDoubleStream<X> asDoubleStream() {
    return ThrowingBridge.of(getDelegate().asDoubleStream(), getExceptionMasker());
  }

  @Override
  public ThrowingStream<Long, X> boxed() {
    return ThrowingBridge.of(getDelegate().boxed(), getExceptionMasker());
  }

  @Override
  public <Y extends Throwable> ThrowingLongStream<Y> rethrow(Class<Y> e,
      Function<? super X, ? extends Y> mapper) {
    RethrowChain<Throwable, Y> c = getChain().rethrow(mapper);
    return new CheckedLongStream<>(getDelegate(), new ExceptionMasker<>(e), c);
  }
}
