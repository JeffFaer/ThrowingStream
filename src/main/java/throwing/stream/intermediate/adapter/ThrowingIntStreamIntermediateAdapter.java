package throwing.stream.intermediate.adapter;

import java.util.function.BiFunction;
import java.util.function.Function;

import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingIntFunction;
import throwing.function.ThrowingIntPredicate;
import throwing.function.ThrowingIntToDoubleFunction;
import throwing.function.ThrowingIntToLongFunction;
import throwing.function.ThrowingIntUnaryOperator;
import throwing.stream.ThrowingIntStream;
import throwing.stream.intermediate.ThrowingDoubleStreamIntermediate;
import throwing.stream.intermediate.ThrowingIntStreamIntermediate;
import throwing.stream.intermediate.ThrowingLongStreamIntermediate;

public interface ThrowingIntStreamIntermediateAdapter<X extends Throwable, XD extends Throwable, ID extends ThrowingIntStreamIntermediate<? super XD, ID, LD, DD>, LD extends ThrowingLongStreamIntermediate<? super XD, ID, LD, DD>, DD extends ThrowingDoubleStreamIntermediate<? super XD, ID, LD, DD>, IS extends ThrowingIntStreamIntermediate<X, IS, LS, DS>, LS extends ThrowingLongStreamIntermediate<X, IS, LS, DS>, DS extends ThrowingDoubleStreamIntermediate<X, IS, LS, DS>> extends
    ThrowingBaseStreamIntermediateAdapter<ID, IS>,
    ThrowingIntStreamIntermediate<X, IS, LS, DS> {
  public ThrowingFunctionAdapter<XD, X> getFunctionAdapter();

  public LS newLongStream(LD delegate);

  public DS newDoubleStream(DD delegate);

  default public <U> LS newLongStream(BiFunction<? super ID, U, LD> function, U secondArgument) {
    return newLongStream(function.apply(getDelegate(), secondArgument));
  }

  default public <U> DS newDoubleStream(BiFunction<? super ID, U, DD> function, U secondArgument) {
    return newDoubleStream(function.apply(getDelegate(), secondArgument));
  }

  @Override
  default public IS filter(ThrowingIntPredicate<? extends X> predicate) {
    return chain(ID::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  default public IS map(ThrowingIntUnaryOperator<? extends X> mapper) {
    return chain(ID::map, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public LS mapToLong(ThrowingIntToLongFunction<? extends X> mapper) {
    return newLongStream(ID::mapToLong, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public DS mapToDouble(ThrowingIntToDoubleFunction<? extends X> mapper) {
    return newDoubleStream(ID::mapToDouble, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public IS flatMap(
      ThrowingIntFunction<? extends ThrowingIntStream<? extends X>, ? extends X> mapper) {
    Function<ThrowingIntStream<? extends X>, ThrowingIntStream<XD>> streamMapper = getFunctionAdapter()::convert;
    ThrowingIntFunction<? extends ThrowingIntStream<XD>, ? extends X> f = mapper.andThen(streamMapper);
    return chain(ID::flatMap, getFunctionAdapter().convert(f));
  }

  @Override
  default public IS distinct() {
    return chain(ID::distinct);
  }

  @Override
  default public IS sorted() {
    return chain(ID::sorted);
  }

  @Override
  default public IS peek(ThrowingIntConsumer<? extends X> action) {
    return chain(ID::peek, getFunctionAdapter().convert(action));
  }

  @Override
  default public IS limit(long maxSize) {
    return chain(ID::limit, maxSize);
  }

  @Override
  default public IS skip(long n) {
    return chain(ID::skip, n);
  }

  @Override
  default public LS asLongStream() {
    return newLongStream(getDelegate().asLongStream());
  }

  @Override
  default public DS asDoubleStream() {
    return newDoubleStream(getDelegate().asDoubleStream());
  }
}
