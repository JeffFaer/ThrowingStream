package throwing.stream.adapter;

import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;

import throwing.RethrowChain;
import throwing.ThrowingIterator.OfDouble;
import throwing.ThrowingSpliterator;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

class CheckedDoubleStream<X extends Throwable> extends
    CheckedBaseStream<Double, X, ThrowingDoubleStream<X>, DoubleStream> implements
    ThrowingDoubleStream<X> {
  CheckedDoubleStream(DoubleStream delegate, FunctionAdapter<X> functionAdapter) {
    super(delegate, functionAdapter);
  }

  CheckedDoubleStream(DoubleStream delegate, FunctionAdapter<X> functionAdapter,
      RethrowChain<AdapterException, X> chain) {
    super(delegate, functionAdapter, chain);
  }

  @Override
  public ThrowingDoubleStream<X> getSelf() {
    return this;
  }

  @Override
  public ThrowingDoubleStream<X> createNewAdapter(DoubleStream delegate) {
    return new CheckedDoubleStream<>(delegate, getFunctionAdapter(), getChain());
  }

  @Override
  public OfDouble<X> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getFunctionAdapter());
  }

  @Override
  public ThrowingSpliterator.OfDouble<X> spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getFunctionAdapter());
  }

  @Override
  public ThrowingDoubleStream<X> filter(ThrowingDoublePredicate<? extends X> predicate) {
    return chain(DoubleStream::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  public ThrowingDoubleStream<X> map(ThrowingDoubleUnaryOperator<? extends X> mapper) {
    return chain(DoubleStream::map, getFunctionAdapter().convert(mapper));
  }

  @Override
  public <U> ThrowingStream<U, X> mapToObj(ThrowingDoubleFunction<? extends U, ? extends X> mapper) {
    DoubleFunction<? extends U> f = getFunctionAdapter().convert(mapper);
    return ThrowingBridge.of(getDelegate().mapToObj(f), getFunctionAdapter());
  }

  @Override
  public ThrowingIntStream<X> mapToInt(ThrowingDoubleToIntFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToInt(getFunctionAdapter().convert(mapper)),
        getFunctionAdapter());
  }

  @Override
  public ThrowingLongStream<X> mapToLong(ThrowingDoubleToLongFunction<? extends X> mapper) {
    return ThrowingBridge.of(getDelegate().mapToLong(getFunctionAdapter().convert(mapper)),
        getFunctionAdapter());
  }

  @Override
  public ThrowingDoubleStream<X> flatMap(
      ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper) {
    @SuppressWarnings("unchecked") Function<? super ThrowingDoubleStream<? extends X>, ? extends DoubleStream> c = s -> ThrowingBridge.of(
        (ThrowingDoubleStream<X>) s, getExceptionClass());
    return chain(DoubleStream::flatMap, getFunctionAdapter().convert(mapper.andThen(c)));
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
    return chain(DoubleStream::peek, getFunctionAdapter().convert(action));
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
    unmaskException(() -> getDelegate().forEach(getFunctionAdapter().convert(action)));
  }

  @Override
  public void forEachOrdered(ThrowingDoubleConsumer<? extends X> action) throws X {
    unmaskException(() -> getDelegate().forEachOrdered(getFunctionAdapter().convert(action)));
  }

  @Override
  public double[] toArray() throws X {
    return unmaskException(getDelegate()::toArray);
  }

  @Override
  public double reduce(double identity, ThrowingDoubleBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(identity, getFunctionAdapter().convert(op)));
  }

  @Override
  public OptionalDouble reduce(ThrowingDoubleBinaryOperator<? extends X> op) throws X {
    return unmaskException(() -> getDelegate().reduce(getFunctionAdapter().convert(op)));
  }

  @Override
  public <R> R collect(ThrowingSupplier<R, ? extends X> supplier,
      ThrowingObjDoubleConsumer<R, ? extends X> accumulator,
      ThrowingBiConsumer<R, R, ? extends X> combiner) throws X {
    return unmaskException(() -> getDelegate().collect(getFunctionAdapter().convert(supplier),
        getFunctionAdapter().convert(accumulator), getFunctionAdapter().convert(combiner)));
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
    return unmaskException(() -> getDelegate().anyMatch(getFunctionAdapter().convert(predicate)));
  }

  @Override
  public boolean allMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().allMatch(getFunctionAdapter().convert(predicate)));
  }

  @Override
  public boolean noneMatch(ThrowingDoublePredicate<? extends X> predicate) throws X {
    return unmaskException(() -> getDelegate().noneMatch(getFunctionAdapter().convert(predicate)));
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
    return ThrowingBridge.of(getDelegate().boxed(), getFunctionAdapter());
  }

  @Override
  public <Y extends Throwable> ThrowingDoubleStream<Y> rethrow(Class<Y> e,
      Function<? super X, ? extends Y> mapper) {
    RethrowChain<AdapterException, Y> c = getChain().rethrow(mapper);
    return new CheckedDoubleStream<>(getDelegate(), new FunctionAdapter<>(e), c);
  }
}
