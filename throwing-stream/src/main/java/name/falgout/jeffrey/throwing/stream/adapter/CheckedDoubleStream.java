package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.ThrowingBaseSpliterator;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingDoublePredicate;
import name.falgout.jeffrey.throwing.ThrowingDoubleToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingIterator.OfDouble;
import name.falgout.jeffrey.throwing.ThrowingObjDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.ThrowingStream;

class CheckedDoubleStream<X extends Throwable> extends
    CheckedBaseStream<Double, X, DoubleStream, ThrowingDoubleStream<X>> implements
    ThrowingDoubleStream<X> {
  CheckedDoubleStream(DoubleStream delegate, ExceptionMasker<X> ExceptionMasker) {
    super(delegate, ExceptionMasker);
  }

  CheckedDoubleStream(DoubleStream delegate, ExceptionMasker<X> ExceptionMasker,
      RethrowChain<Throwable, X> chain) {
    super(delegate, ExceptionMasker, chain);
  }

  @Override
  public ThrowingDoubleStream<X> getSelf() {
    return this;
  }

  @Override
  public ThrowingDoubleStream<X> createNewAdapter(DoubleStream delegate) {
    return new CheckedDoubleStream<>(delegate, getExceptionMasker(), getChain());
  }

  @Override
  public OfDouble<X> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionMasker());
  }

  @Override
  public ThrowingBaseSpliterator.OfDouble<X> spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionMasker());
  }

  @Override
  public ThrowingDoubleStream<X> filter(ThrowingDoublePredicate<? extends X> predicate) {
    return chain(DoubleStream::filter, getExceptionMasker().mask(predicate));
  }

  @Override
  public ThrowingDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends X> mapper) {
    return chain(DoubleStream::map, getExceptionMasker().mask(mapper));
  }

  @Override
  public <U> ThrowingStream<U, X> mapToObj(ThrowingDoubleFunction<? extends U, ? extends X> mapper) {
    DoubleFunction<? extends U> f = getExceptionMasker().mask(mapper);
    return ThrowingBridge.of(getDelegate().mapToObj(f), getExceptionMasker());
  }

  @Override
  public ThrowingIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToInt(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToLong(getExceptionMasker().mask(mapper)),
        getExceptionMasker());
  }

  @Override
  public ThrowingDoubleStream<X> flatMap(
      ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked") Function<? super ThrowingDoubleStream<? extends X>, ? extends DoubleStream> c = s -> ThrowingBridge.of(
        (ThrowingDoubleStream<X>) s, getExceptionClass());
    return chain(DoubleStream::flatMap, getExceptionMasker().mask(mapper.andThen(c::apply)));
  }

  @Override
  public ThrowingDoubleStream<X> distinct() {
    return chain(DoubleStream::distinct);
  }

  @Override
  public ThrowingDoubleStream<X> sorted() {
    return chain(DoubleStream::sorted);
  }

  @Override
  public ThrowingDoubleStream<X> peek(ThrowingDoubleConsumer<? extends X> action) {
    return chain(DoubleStream::peek, getExceptionMasker().mask(action));
  }

  @Override
  public ThrowingDoubleStream<X> limit(long maxSize) {
    return chain(DoubleStream::limit, maxSize);
  }

  @Override
  public ThrowingDoubleStream<X> skip(long n) {
    return chain(DoubleStream::skip, n);
  }

  @Override
  public void forEach(ThrowingDoubleConsumer<? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEach(getExceptionMasker().mask(action)));
  }

  @Override
  public void forEachOrdered(ThrowingDoubleConsumer<? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEachOrdered(getExceptionMasker().mask(action)));
  }

  @Override
  public double[] toArray() throws X {
    return unmaskException(getDelegate()::toArray);
  }

  @Override
  public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(identity, getExceptionMasker().mask(op)));
  }

  @Override
  public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(getExceptionMasker().mask(op)));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjDoubleConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X {
    return unmaskException(() -> getDelegate().collect(getExceptionMasker().mask(supplier),
        getExceptionMasker().mask(accumulator), getExceptionMasker().mask(combiner)));
  }

  @Override
  public double sum() throws X {
    return unmaskException(getDelegate()::sum);
  }

  @Override
  public OptionalDouble min() throws X {
    return unmaskException(getDelegate()::min);
  }

  @Override
  public OptionalDouble max() throws X {
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
  public DoubleSummaryStatistics summaryStatistics() throws X {
    return unmaskException(getDelegate()::summaryStatistics);
  }

  @Override
  public boolean anyMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().anyMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean allMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().allMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public boolean noneMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().noneMatch(getExceptionMasker().mask(predicate)));
  }

  @Override
  public OptionalDouble findFirst() throws X {
    return unmaskException(getDelegate()::findFirst);
  }

  @Override
  public OptionalDouble findAny() throws X {
    return unmaskException(getDelegate()::findAny);
  }

  @Override
  public ThrowingStream<Double, X> boxed() {
    return ThrowingBridge.of(getDelegate().boxed(), getExceptionMasker());
  }

  @Override
  public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> e,
      Function<? super X, ? extends Y> mapper) {
    RethrowChain<Throwable, Y> c = getChain().rethrow(mapper);
    return new CheckedDoubleStream<>(getDelegate(), new ExceptionMasker<>(e), c);
  }
}
