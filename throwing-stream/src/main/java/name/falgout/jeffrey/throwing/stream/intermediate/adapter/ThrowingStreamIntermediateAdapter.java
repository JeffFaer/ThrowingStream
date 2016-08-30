package name.falgout.jeffrey.throwing.stream.intermediate.adapter;

import java.util.function.BiFunction;
import java.util.function.Function;

import name.falgout.jeffrey.throwing.ThrowingComparator;
import name.falgout.jeffrey.throwing.ThrowingConsumer;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingPredicate;
import name.falgout.jeffrey.throwing.ThrowingToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingToLongFunction;
import name.falgout.jeffrey.throwing.stream.ThrowingDoubleStream;
import name.falgout.jeffrey.throwing.stream.ThrowingIntStream;
import name.falgout.jeffrey.throwing.stream.ThrowingLongStream;
import name.falgout.jeffrey.throwing.stream.intermediate.ThrowingDoubleStreamIntermediate;
import name.falgout.jeffrey.throwing.stream.intermediate.ThrowingIntStreamIntermediate;
import name.falgout.jeffrey.throwing.stream.intermediate.ThrowingLongStreamIntermediate;
import name.falgout.jeffrey.throwing.stream.intermediate.ThrowingStreamIntermediate;

public interface ThrowingStreamIntermediateAdapter<T, X extends Throwable, XD extends Throwable, D extends ThrowingStreamIntermediate<T, ? super XD, D, ID, LD, DD>, ID extends ThrowingIntStreamIntermediate<? super XD, ID, LD, DD>, LD extends ThrowingLongStreamIntermediate<? super XD, ID, LD, DD>, DD extends ThrowingDoubleStreamIntermediate<? super XD, ID, LD, DD>, S extends ThrowingStreamIntermediate<T, X, S, IS, LS, DS>, IS extends ThrowingIntStreamIntermediate<X, IS, LS, DS>, LS extends ThrowingLongStreamIntermediate<X, IS, LS, DS>, DS extends ThrowingDoubleStreamIntermediate<X, IS, LS, DS>> extends
    ThrowingBaseStreamIntermediateAdapter<D, S>,
    ThrowingStreamIntermediate<T, X, S, IS, LS, DS> {
  public ThrowingFunctionAdapter<XD, X> getFunctionAdapter();

  public IS newIntStream(ID delegate);

  public LS newLongStream(LD delegate);

  public DS newDoubleStream(DD delegate);

  default public <U> IS newIntStream(BiFunction<? super D, U, ID> function, U secondArgument) {
    return newIntStream(function.apply(getDelegate(), secondArgument));
  }

  default public <U> LS newLongStream(BiFunction<? super D, U, LD> function, U secondArgument) {
    return newLongStream(function.apply(getDelegate(), secondArgument));
  }

  default public <U> DS newDoubleStream(BiFunction<? super D, U, DD> function, U secondArgument) {
    return newDoubleStream(function.apply(getDelegate(), secondArgument));
  }

  @Override
  default public S filter(ThrowingPredicate<? super T, ? extends X> predicate) {
    return chain(D::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  default public IS mapToInt(ThrowingToIntFunction<? super T, ? extends X> mapper) {
    return newIntStream(D::mapToInt, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public LS mapToLong(ThrowingToLongFunction<? super T, ? extends X> mapper) {
    return newLongStream(D::mapToLong, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public DS mapToDouble(ThrowingToDoubleFunction<? super T, ? extends X> mapper) {
    return newDoubleStream(D::mapToDouble, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public IS flatMapToInt(
      ThrowingFunction<? super T, ? extends ThrowingIntStream<? extends X>, ? extends X> mapper) {
    Function<ThrowingIntStream<? extends X>, ThrowingIntStream<XD>> streamMapper = getFunctionAdapter()::convert;
    ThrowingFunction<? super T, ? extends ThrowingIntStream<XD>, ? extends X> f = mapper.andThen(streamMapper);
    return newIntStream(D::flatMapToInt, getFunctionAdapter().convert(f));
  }

  @Override
  default public LS flatMapToLong(
      ThrowingFunction<? super T, ? extends ThrowingLongStream<? extends X>, ? extends X> mapper) {
    Function<ThrowingLongStream<? extends X>, ThrowingLongStream<XD>> streamMapper = getFunctionAdapter()::convert;
    ThrowingFunction<? super T, ? extends ThrowingLongStream<XD>, ? extends X> f = mapper.andThen(streamMapper);
    return newLongStream(D::flatMapToLong, getFunctionAdapter().convert(f));
  }

  @Override
  default public DS flatMapToDouble(
      ThrowingFunction<? super T, ? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper) {
    Function<ThrowingDoubleStream<? extends X>, ThrowingDoubleStream<XD>> streamMapper = getFunctionAdapter()::convert;
    ThrowingFunction<? super T, ? extends ThrowingDoubleStream<XD>, ? extends X> f = mapper.andThen(streamMapper);
    return newDoubleStream(D::flatMapToDouble, getFunctionAdapter().convert(f));
  }

  @Override
  default public S distinct() {
    return chain(D::distinct);
  }

  @Override
  default public S sorted() {
    return chain(D::sorted);
  }

  @Override
  default public S sorted(ThrowingComparator<? super T, ? extends X> comparator) {
    return this.<ThrowingComparator<? super T, XD>> chain(D::sorted,
        getFunctionAdapter().convert(comparator));
  }

  @Override
  default public S peek(ThrowingConsumer<? super T, ? extends X> action) {
    return chain(D::peek, getFunctionAdapter().convert(action));
  }

  @Override
  default public S limit(long maxSize) {
    return chain(D::limit, maxSize);
  }

  @Override
  default public S skip(long n) {
    return chain(D::skip, n);
  }
}
