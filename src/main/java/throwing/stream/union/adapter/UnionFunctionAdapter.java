package throwing.stream.union.adapter;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector.Characteristics;

import throwing.ThrowingComparator;
import throwing.ThrowingRunnable;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBiFunction;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleBinaryOperator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingIntBinaryOperator;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingIntToDoubleFunction;
import throwing.function.ThrowingIntToLongFunction;
import throwing.function.ThrowingIntUnaryOperator;
import throwing.function.ThrowingLongBinaryOperator;
import throwing.function.ThrowingLongConsumer;
import throwing.function.ThrowingLongFunction;
import throwing.function.ThrowingLongPredicate;
import throwing.function.ThrowingLongToDoubleFunction;
import throwing.function.ThrowingLongToIntFunction;
import throwing.function.ThrowingLongUnaryOperator;
import throwing.function.ThrowingObjDoubleConsumer;
import throwing.function.ThrowingObjIntConsumer;
import throwing.function.ThrowingObjLongConsumer;
import throwing.function.ThrowingPredicate;
import throwing.function.ThrowingSupplier;
import throwing.function.ThrowingToDoubleFunction;
import throwing.function.ThrowingToIntFunction;
import throwing.function.ThrowingToLongFunction;
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;
import throwing.stream.intermediate.adapter.ThrowingFunctionAdapter;
import throwing.stream.union.UnionThrowable;

class UnionFunctionAdapter<X extends UnionThrowable> implements ThrowingFunctionAdapter<X, Throwable> {
  private static final Class<Throwable> x = Throwable.class;
  private final Class<X> exceptionClass;
  private final Function<? super Throwable, ? extends X> mapper;

  UnionFunctionAdapter(Class<X> exceptionClass, Function<? super Throwable, ? extends X> mapper) {
    this.exceptionClass = exceptionClass;
    this.mapper = mapper;
  }

  @Override
  public <T> ThrowingConsumer<T, X> convert(
      ThrowingConsumer<? super T, ? extends Throwable> consumer) {
    ThrowingConsumer<T, Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingPredicate<T, X> convert(
      ThrowingPredicate<? super T, ? extends Throwable> predicate) {
    ThrowingPredicate<T, Throwable> p = predicate::test;
    return p.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingBinaryOperator<T, X> convert(
      ThrowingBinaryOperator<T, ? extends Throwable> operator) {
    ThrowingBinaryOperator<T, Throwable> o = operator::apply;
    return o.rethrow(x, mapper);
  }

  @Override
  public <T, U, R> ThrowingBiFunction<T, U, R, X> convert(
      ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends Throwable> function) {
    ThrowingBiFunction<T, U, R, Throwable> f = function::apply;
    return f.rethrow(x, mapper);
  }

  @Override
  public <R> ThrowingSupplier<R, X> convert(
      ThrowingSupplier<? extends R, ? extends Throwable> supplier) {
    ThrowingSupplier<R, Throwable> s = supplier::get;
    return s.rethrow(x, mapper);
  }

  @Override
  public <T, U> ThrowingBiConsumer<T, U, X> convert(
      ThrowingBiConsumer<? super T, ? super U, ? extends Throwable> consumer) {
    ThrowingBiConsumer<T, U, Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingComparator<T, X> convert(
      ThrowingComparator<? super T, ? extends Throwable> comparator) {
    ThrowingComparator<T, Throwable> c = comparator::compare;
    return c.rethrow(x, mapper);
  }

  @Override
  public <T, R> ThrowingFunction<T, R, X> convert(
      ThrowingFunction<? super T, ? extends R, ? extends Throwable> function) {
    ThrowingFunction<T, R, Throwable> f = function::apply;
    return f.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingStream<? extends T, X> convert(
      ThrowingStream<? extends T, ? extends Throwable> stream) {
    return stream.rethrow(exceptionClass, mapper);
  }

  // int

  @Override
  public ThrowingIntConsumer<X> convert(ThrowingIntConsumer<? extends Throwable> consumer) {
    ThrowingIntConsumer<Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public ThrowingIntPredicate<X> convert(ThrowingIntPredicate<? extends Throwable> predicate) {
    ThrowingIntPredicate<Throwable> p = predicate::test;
    return p.rethrow(x, mapper);
  }

  @Override
  public ThrowingIntBinaryOperator<X> convert(
      ThrowingIntBinaryOperator<? extends Throwable> operator) {
    ThrowingIntBinaryOperator<Throwable> o = operator::applyAsInt;
    return o.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingObjIntConsumer<T, X> convert(
      ThrowingObjIntConsumer<T, ? extends Throwable> consumer) {
    ThrowingObjIntConsumer<T, Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public ThrowingIntUnaryOperator<X> convert(ThrowingIntUnaryOperator<? extends Throwable> operator) {
    ThrowingIntUnaryOperator<Throwable> o = operator::applyAsInt;
    return o.rethrow(x, mapper);
  }

  @Override
  public <R> ThrowingIntFunction<R, X> convert(ThrowingIntFunction<R, ? extends Throwable> function) {
    ThrowingIntFunction<R, Throwable> f = function::apply;
    return f.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingToIntFunction<T, X> convert(
      ThrowingToIntFunction<T, ? extends Throwable> function) {
    ThrowingToIntFunction<T, Throwable> f = function::applyAsInt;
    return f.rethrow(x, mapper);
  }

  @Override
  public ThrowingIntToLongFunction<X> convert(
      ThrowingIntToLongFunction<? extends Throwable> function) {
    ThrowingIntToLongFunction<Throwable> f = function::applyAsLong;
    return f.rethrow(x, mapper);
  }

  @Override
  public ThrowingIntToDoubleFunction<X> convert(
      ThrowingIntToDoubleFunction<? extends Throwable> function) {
    ThrowingIntToDoubleFunction<Throwable> f = function::applyAsDouble;
    return f.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingIntStream<X> convert(ThrowingIntStream<? extends Throwable> stream) {
    return stream.rethrow(exceptionClass, mapper);
  }

  // long

  @Override
  public ThrowingLongConsumer<X> convert(ThrowingLongConsumer<? extends Throwable> consumer) {
    ThrowingLongConsumer<Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public ThrowingLongPredicate<X> convert(ThrowingLongPredicate<? extends Throwable> predicate) {
    ThrowingLongPredicate<Throwable> p = predicate::test;
    return p.rethrow(x, mapper);
  }

  @Override
  public ThrowingLongBinaryOperator<X> convert(
      ThrowingLongBinaryOperator<? extends Throwable> operator) {
    ThrowingLongBinaryOperator<Throwable> o = operator::applyAsLong;
    return o.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingObjLongConsumer<T, X> convert(
      ThrowingObjLongConsumer<T, ? extends Throwable> consumer) {
    ThrowingObjLongConsumer<T, Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public ThrowingLongUnaryOperator<X> convert(
      ThrowingLongUnaryOperator<? extends Throwable> operator) {
    ThrowingLongUnaryOperator<Throwable> o = operator::applyAsLong;
    return o.rethrow(x, mapper);
  }

  @Override
  public <R> ThrowingLongFunction<R, X> convert(
      ThrowingLongFunction<R, ? extends Throwable> function) {
    ThrowingLongFunction<R, Throwable> f = function::apply;
    return f.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingToLongFunction<T, X> convert(
      ThrowingToLongFunction<T, ? extends Throwable> function) {
    ThrowingToLongFunction<T, Throwable> f = function::applyAsLong;
    return f.rethrow(x, mapper);
  }

  @Override
  public ThrowingLongToIntFunction<X> convert(
      ThrowingLongToIntFunction<? extends Throwable> function) {
    ThrowingLongToIntFunction<Throwable> f = function::applyAsInt;
    return f.rethrow(x, mapper);
  }

  @Override
  public ThrowingLongToDoubleFunction<X> convert(
      ThrowingLongToDoubleFunction<? extends Throwable> function) {
    ThrowingLongToDoubleFunction<Throwable> f = function::applyAsDouble;
    return f.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingLongStream<X> convert(ThrowingLongStream<? extends Throwable> stream) {
    return stream.rethrow(exceptionClass, mapper);
  }

  // Double

  @Override
  public ThrowingDoubleConsumer<X> convert(ThrowingDoubleConsumer<? extends Throwable> consumer) {
    ThrowingDoubleConsumer<Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public ThrowingDoublePredicate<X> convert(ThrowingDoublePredicate<? extends Throwable> predicate) {
    ThrowingDoublePredicate<Throwable> p = predicate::test;
    return p.rethrow(x, mapper);
  }

  @Override
  public ThrowingDoubleBinaryOperator<X> convert(
      ThrowingDoubleBinaryOperator<? extends Throwable> operator) {
    ThrowingDoubleBinaryOperator<Throwable> o = operator::applyAsDouble;
    return o.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingObjDoubleConsumer<T, X> convert(
      ThrowingObjDoubleConsumer<T, ? extends Throwable> consumer) {
    ThrowingObjDoubleConsumer<T, Throwable> c = consumer::accept;
    return c.rethrow(x, mapper);
  }

  @Override
  public ThrowingDoubleUnaryOperator<X> convert(
      ThrowingDoubleUnaryOperator<? extends Throwable> operator) {
    ThrowingDoubleUnaryOperator<Throwable> o = operator::applyAsDouble;
    return o.rethrow(x, mapper);
  }

  @Override
  public <R> ThrowingDoubleFunction<R, X> convert(
      ThrowingDoubleFunction<R, ? extends Throwable> function) {
    ThrowingDoubleFunction<R, Throwable> f = function::apply;
    return f.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingToDoubleFunction<T, X> convert(
      ThrowingToDoubleFunction<T, ? extends Throwable> function) {
    ThrowingToDoubleFunction<T, Throwable> f = function::applyAsDouble;
    return f.rethrow(x, mapper);
  }

  @Override
  public ThrowingDoubleToIntFunction<X> convert(
      ThrowingDoubleToIntFunction<? extends Throwable> function) {
    ThrowingDoubleToIntFunction<Throwable> f = function::applyAsInt;
    return f.rethrow(x, mapper);
  }

  @Override
  public ThrowingDoubleToLongFunction<X> convert(
      ThrowingDoubleToLongFunction<? extends Throwable> function) {
    ThrowingDoubleToLongFunction<Throwable> f = function::applyAsLong;
    return f.rethrow(x, mapper);
  }

  @Override
  public <T> ThrowingDoubleStream<X> convert(ThrowingDoubleStream<? extends Throwable> stream) {
    return stream.rethrow(exceptionClass, mapper);
  }

  @Override
  public <T, A, R> ThrowingCollector<T, A, R, X> convert(
      ThrowingCollector<T, A, R, ? extends Throwable> collector) {
    return new ThrowingCollector<T, A, R, X>() {
      @Override
      public ThrowingSupplier<A, X> supplier() {
        return convert(collector.supplier());
      }

      @Override
      public ThrowingBiConsumer<A, T, X> accumulator() {
        return convert(collector.accumulator());
      }

      @Override
      public ThrowingBinaryOperator<A, X> combiner() {
        return convert(collector.combiner());
      }

      @Override
      public ThrowingFunction<A, R, X> finisher() {
        return convert(collector.finisher());
      }

      @Override
      public Set<Characteristics> characteristics() {
        return collector.characteristics();
      }
    };
  }

  @Override
  public ThrowingRunnable<X> convert(ThrowingRunnable<? extends Throwable> runnable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("TODO");
  }
}
