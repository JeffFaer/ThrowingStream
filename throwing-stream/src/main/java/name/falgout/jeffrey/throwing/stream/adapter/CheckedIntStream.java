package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntFunction;
import name.falgout.jeffrey.throwing.ThrowingIntPredicate;
import name.falgout.jeffrey.throwing.ThrowingIntToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingIntToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingIntUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingIterator.OfInt;
import name.falgout.jeffrey.throwing.ThrowingObjIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

class CheckedIntStream<X extends Throwable> extends
    CheckedBaseStream<Integer, X, IntStream, ThrowingIntStream<X>> implements ThrowingIntStream<X> {
  CheckedIntStream(IntStream delegate, ExceptionMasker<X> ExceptionMasker) {
    super(delegate, ExceptionMasker);
  }

  CheckedIntStream(IntStream delegate, ExceptionMasker<X> ExceptionMasker,
      RethrowChain<Throwable, X> chain) {
    super(delegate, ExceptionMasker, chain);
  }

  @Override
  public ThrowingIntStream<X> getSelf() {
    return this;
  }

  @Override
  public ThrowingIntStream<X> createNewAdapter(IntStream delegate) {
    return new CheckedIntStream<>(delegate, getExceptionMasker(), getChain());
  }

  @Override
  public OfInt<X> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionMasker());
  }

  @Override
  public ThrowingBaseSpliterator.OfInt<X> spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionMasker());
  }

  @Override
  public ThrowingIntStream<X> filter(ThrowingIntPredicate<? extends X> predicate) {
    return chain(IntStream::filter, getExceptionMasker().mask(predicate));
  }

  @Override
  public ThrowingIntStream<X> map(ThrowingIntUnaryOperator<? extends X> mapper) {
    return chain(IntStream::map, getExceptionMasker().mask(mapper));
  }

  @Override
  public <U> ThrowingStream<U, X> mapToObj(ThrowingIntFunction<? extends U, ? extends X> mapper) {
    IntFunction<? extends U> f = getExceptionMasker().mask(mapper);
    return ThrowingBridge.of(getDelegate().mapToObj(f), getExceptionMasker());
  }

  @Override
  public ThrowingLongStream<X> mapToLong(ThrowingIntToLongFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToLong(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingDoubleStream<X> mapToDouble(ThrowingIntToDoubleFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToDouble(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingIntStream<X> flatMap(
      ThrowingIntFunction<? extends ThrowingIntStream<? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked") Function<? super ThrowingIntStream<? extends X>, ? extends IntStream> c = s -> ThrowingBridge.of(
        (ThrowingIntStream<X>) s, getExceptionClass());
    return chain(IntStream::flatMap, getExceptionMasker().mask(mapper.andThen(c::apply)));
  }

  @Override
  public ThrowingIntStream<X> distinct() {
    return chain(IntStream::distinct);
  }

  @Override
  public ThrowingIntStream<X> sorted() {
    return chain(IntStream::sorted);
  }

  @Override
  public ThrowingIntStream<X> peek(ThrowingIntConsumer<? extends X> action) {
    return chain(IntStream::peek, getExceptionMasker().mask(action));
  }

  @Override
  public ThrowingIntStream<X> limit(long maxSize) {
    return chain(IntStream::limit, maxSize);
  }

  @Override
  public ThrowingIntStream<X> skip(long n) {
    return chain(IntStream::skip, n);
  }

  @Override
  public void forEach(ThrowingIntConsumer<? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEach(getExceptionMasker().mask(action)));
  }

  @Override
  public void forEachOrdered(ThrowingIntConsumer<? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEachOrdered(getExceptionMasker().mask(action)));
  }

  @Override
  public int[] toArray() throws X {
    return unmaskException(getDelegate()::toArray);
  }

  @Override
  public int reduce(int identity, ThrowingIntBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(identity, getExceptionMasker().mask(op)));
  }

  @Override
  public OptionalInt reduce(ThrowingIntBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(getExceptionMasker().mask(op)));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjIntConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X {
    return unmaskException(() -> getDelegate().collect(getExceptionMasker().mask(supplier),
        getExceptionMasker().mask(accumulator), getExceptionMasker().mask(combiner)));
  }

  @Override
  public int sum() throws X {
    return unmaskException(getDelegate()::sum);
  }

  @Override
  public OptionalInt min() throws X {
    return unmaskException(getDelegate()::min);
  }

  @Override
  public OptionalInt max() throws X {
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
  public IntSummaryStatistics summaryStatistics() throws X {
    return unmaskException(getDelegate()::summaryStatistics);
  }

  @Override
  public boolean anyMatch(ThrowingIntPredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().anyMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean allMatch(ThrowingIntPredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().allMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean noneMatch(ThrowingIntPredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().noneMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public OptionalInt findFirst() throws X {
    return unmaskException(getDelegate()::findFirst);
  }

  @Override
  public OptionalInt findAny() throws X {
    return unmaskException(getDelegate()::findAny);
  }

  @Override
  public ThrowingLongStream<X> asLongStream() {
    return ThrowingBridge.of(getDelegate().asLongStream(), getExceptionMasker());
  }

  @Override
  public ThrowingDoubleStream<X> asDoubleStream() {
    return ThrowingBridge.of(getDelegate().asDoubleStream(), getExceptionMasker());
  }

  @Override
  public ThrowingStream<Integer, X> boxed() {
    return ThrowingBridge.of(getDelegate().boxed(), getExceptionMasker());
  }

  @Override
  public <Y extends Throwable> ThrowingIntStream<Y> rethrow(Class<Y> e,
      Function<? super X, ? extends Y> mapper) {
    RethrowChain<Throwable, Y> c = getChain().rethrow(mapper);
    return new CheckedIntStream<>(getDelegate(), new ExceptionMasker<>(e), c);
  }
}
