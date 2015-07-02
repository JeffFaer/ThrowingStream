package throwing.stream.intermediate.adapter;

import java.util.function.Function;

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
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.ThrowingIntStream;
import throwing.stream.ThrowingLongStream;
import throwing.stream.ThrowingStream;

class RethrowingFunctionAdapter<X extends Throwable, Y extends Throwable> implements
    ThrowingFunctionAdapter<X, Y> {
  private final Class<X> x;
  private final Class<Y> y;
  private final Function<? super Y, X> ctor;

  RethrowingFunctionAdapter(Class<X> x, Class<Y> y, Function<? super Y, X> ctor) {
    this.x = x;
    this.y = y;
    this.ctor = ctor;
  }

  @Override
  public ThrowingRunnable<X> convert(ThrowingRunnable<? extends Y> runnable) {
    ThrowingRunnable<Y> r = runnable::run;
    return r.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingConsumer<T, X> convert(ThrowingConsumer<? super T, ? extends Y> consumer) {
    ThrowingConsumer<T, Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingPredicate<T, X> convert(ThrowingPredicate<? super T, ? extends Y> predicate) {
    ThrowingPredicate<T, Y> p = predicate::test;
    return p.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingBinaryOperator<T, X> convert(ThrowingBinaryOperator<T, ? extends Y> operator) {
    ThrowingBinaryOperator<T, Y> o = operator::apply;
    return o.rethrow(y, ctor);
  }

  @Override
  public <T, U, R> ThrowingBiFunction<T, U, R, X> convert(
      ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends Y> function) {
    ThrowingBiFunction<T, U, R, Y> f = function::apply;
    return f.rethrow(y, ctor);
  }

  @Override
  public <R> ThrowingSupplier<R, X> convert(ThrowingSupplier<? extends R, ? extends Y> supplier) {
    ThrowingSupplier<R, Y> s = supplier::get;
    return s.rethrow(y, ctor);
  }

  @Override
  public <T, U> ThrowingBiConsumer<T, U, X> convert(
      ThrowingBiConsumer<? super T, ? super U, ? extends Y> consumer) {
    ThrowingBiConsumer<T, U, Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingComparator<T, X> convert(ThrowingComparator<? super T, ? extends Y> comparator) {
    ThrowingComparator<T, Y> c = comparator::compare;
    return c.rethrow(y, ctor);
  }

  @Override
  public <T, R> ThrowingFunction<T, R, X> convert(
      ThrowingFunction<? super T, ? extends R, ? extends Y> function) {
    ThrowingFunction<T, R, Y> f = function::apply;
    return f.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingStream<? extends T, X> convert(ThrowingStream<? extends T, ? extends Y> stream) {
    return stream.rethrow(x, ctor);
  }

  // int

  @Override
  public ThrowingIntConsumer<X> convert(ThrowingIntConsumer<? extends Y> consumer) {
    ThrowingIntConsumer<Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public ThrowingIntPredicate<X> convert(ThrowingIntPredicate<? extends Y> predicate) {
    ThrowingIntPredicate<Y> p = predicate::test;
    return p.rethrow(y, ctor);
  }

  @Override
  public ThrowingIntBinaryOperator<X> convert(ThrowingIntBinaryOperator<? extends Y> operator) {
    ThrowingIntBinaryOperator<Y> o = operator::applyAsInt;
    return o.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingObjIntConsumer<T, X> convert(ThrowingObjIntConsumer<T, ? extends Y> consumer) {
    ThrowingObjIntConsumer<T, Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public ThrowingIntUnaryOperator<X> convert(ThrowingIntUnaryOperator<? extends Y> operator) {
    ThrowingIntUnaryOperator<Y> o = operator::applyAsInt;
    return o.rethrow(y, ctor);
  }

  @Override
  public <R> ThrowingIntFunction<R, X> convert(ThrowingIntFunction<R, ? extends Y> function) {
    ThrowingIntFunction<R, Y> f = function::apply;
    return f.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingToIntFunction<T, X> convert(ThrowingToIntFunction<T, ? extends Y> function) {
    ThrowingToIntFunction<T, Y> f = function::applyAsInt;
    return f.rethrow(y, ctor);
  }

  @Override
  public ThrowingIntToLongFunction<X> convert(ThrowingIntToLongFunction<? extends Y> function) {
    ThrowingIntToLongFunction<Y> f = function::applyAsLong;
    return f.rethrow(y, ctor);
  }

  @Override
  public ThrowingIntToDoubleFunction<X> convert(ThrowingIntToDoubleFunction<? extends Y> function) {
    ThrowingIntToDoubleFunction<Y> f = function::applyAsDouble;
    return f.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingIntStream<X> convert(ThrowingIntStream<? extends Y> stream) {
    return stream.rethrow(x, ctor);
  }

  // long

  @Override
  public ThrowingLongConsumer<X> convert(ThrowingLongConsumer<? extends Y> consumer) {
    ThrowingLongConsumer<Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public ThrowingLongPredicate<X> convert(ThrowingLongPredicate<? extends Y> predicate) {
    ThrowingLongPredicate<Y> p = predicate::test;
    return p.rethrow(y, ctor);
  }

  @Override
  public ThrowingLongBinaryOperator<X> convert(ThrowingLongBinaryOperator<? extends Y> operator) {
    ThrowingLongBinaryOperator<Y> o = operator::applyAsLong;
    return o.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingObjLongConsumer<T, X> convert(ThrowingObjLongConsumer<T, ? extends Y> consumer) {
    ThrowingObjLongConsumer<T, Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public ThrowingLongUnaryOperator<X> convert(ThrowingLongUnaryOperator<? extends Y> operator) {
    ThrowingLongUnaryOperator<Y> o = operator::applyAsLong;
    return o.rethrow(y, ctor);
  }

  @Override
  public <R> ThrowingLongFunction<R, X> convert(ThrowingLongFunction<R, ? extends Y> function) {
    ThrowingLongFunction<R, Y> f = function::apply;
    return f.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingToLongFunction<T, X> convert(ThrowingToLongFunction<T, ? extends Y> function) {
    ThrowingToLongFunction<T, Y> f = function::applyAsLong;
    return f.rethrow(y, ctor);
  }

  @Override
  public ThrowingLongToIntFunction<X> convert(ThrowingLongToIntFunction<? extends Y> function) {
    ThrowingLongToIntFunction<Y> f = function::applyAsInt;
    return f.rethrow(y, ctor);
  }

  @Override
  public ThrowingLongToDoubleFunction<X> convert(ThrowingLongToDoubleFunction<? extends Y> function) {
    ThrowingLongToDoubleFunction<Y> f = function::applyAsDouble;
    return f.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingLongStream<X> convert(ThrowingLongStream<? extends Y> stream) {
    return stream.rethrow(x, ctor);
  }

  // Double

  @Override
  public ThrowingDoubleConsumer<X> convert(ThrowingDoubleConsumer<? extends Y> consumer) {
    ThrowingDoubleConsumer<Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public ThrowingDoublePredicate<X> convert(ThrowingDoublePredicate<? extends Y> predicate) {
    ThrowingDoublePredicate<Y> p = predicate::test;
    return p.rethrow(y, ctor);
  }

  @Override
  public ThrowingDoubleBinaryOperator<X> convert(ThrowingDoubleBinaryOperator<? extends Y> operator) {
    ThrowingDoubleBinaryOperator<Y> o = operator::applyAsDouble;
    return o.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingObjDoubleConsumer<T, X> convert(
      ThrowingObjDoubleConsumer<T, ? extends Y> consumer) {
    ThrowingObjDoubleConsumer<T, Y> c = consumer::accept;
    return c.rethrow(y, ctor);
  }

  @Override
  public ThrowingDoubleUnaryOperator<X> convert(ThrowingDoubleUnaryOperator<? extends Y> operator) {
    ThrowingDoubleUnaryOperator<Y> o = operator::applyAsDouble;
    return o.rethrow(y, ctor);
  }

  @Override
  public <R> ThrowingDoubleFunction<R, X> convert(ThrowingDoubleFunction<R, ? extends Y> function) {
    ThrowingDoubleFunction<R, Y> f = function::apply;
    return f.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingToDoubleFunction<T, X> convert(
      ThrowingToDoubleFunction<T, ? extends Y> function) {
    ThrowingToDoubleFunction<T, Y> f = function::applyAsDouble;
    return f.rethrow(y, ctor);
  }

  @Override
  public ThrowingDoubleToIntFunction<X> convert(ThrowingDoubleToIntFunction<? extends Y> function) {
    ThrowingDoubleToIntFunction<Y> f = function::applyAsInt;
    return f.rethrow(y, ctor);
  }

  @Override
  public ThrowingDoubleToLongFunction<X> convert(ThrowingDoubleToLongFunction<? extends Y> function) {
    ThrowingDoubleToLongFunction<Y> f = function::applyAsLong;
    return f.rethrow(y, ctor);
  }

  @Override
  public <T> ThrowingDoubleStream<X> convert(ThrowingDoubleStream<? extends Y> stream) {
    return stream.rethrow(x, ctor);
  }
}
