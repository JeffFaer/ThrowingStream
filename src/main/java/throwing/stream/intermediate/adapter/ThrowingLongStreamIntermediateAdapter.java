package throwing.stream.intermediate.adapter;

import java.util.function.BiFunction;
import java.util.function.Function;

import throwing.function.ThrowingLongConsumer;
import throwing.function.ThrowingLongFunction;
import throwing.function.ThrowingLongPredicate;
import throwing.function.ThrowingLongToDoubleFunction;
import throwing.function.ThrowingLongToIntFunction;
import throwing.function.ThrowingLongUnaryOperator;
import throwing.stream.ThrowingLongStream;
import throwing.stream.intermediate.ThrowingDoubleStreamIntermediate;
import throwing.stream.intermediate.ThrowingIntStreamIntermediate;
import throwing.stream.intermediate.ThrowingLongStreamIntermediate;

public interface ThrowingLongStreamIntermediateAdapter<X extends Throwable, XD extends Throwable, ID extends ThrowingIntStreamIntermediate<? super XD, ID, LD, DD>, LD extends ThrowingLongStreamIntermediate<? super XD, ID, LD, DD>, DD extends ThrowingDoubleStreamIntermediate<? super XD, ID, LD, DD>, IS extends ThrowingIntStreamIntermediate<X, IS, LS, DS>, LS extends ThrowingLongStreamIntermediate<X, IS, LS, DS>, DS extends ThrowingDoubleStreamIntermediate<X, IS, LS, DS>> extends
    ThrowingBaseStreamIntermediateAdapter<LD, LS>,
    ThrowingLongStreamIntermediate<X, IS, LS, DS> {
  public ThrowingFunctionAdapter<XD, X> getFunctionAdapter();

  public IS newIntStream(ID delegate);

  public DS newDoubleStream(DD delegate);

  default public <U> IS newIntStream(BiFunction<? super LD, U, ID> function, U secondArgument) {
    return newIntStream(function.apply(getDelegate(), secondArgument));
  }

  default public <U> DS newDoubleStream(BiFunction<? super LD, U, DD> function, U secondArgument) {
    return newDoubleStream(function.apply(getDelegate(), secondArgument));
  }

  @Override
  default public LS filter(ThrowingLongPredicate<? extends X> predicate) {
    return chain(LD::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  default public LS map(ThrowingLongUnaryOperator<? extends X> mapper) {
    return chain(LD::map, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public IS mapToInt(ThrowingLongToIntFunction<? extends X> mapper) {
    return newIntStream(LD::mapToInt, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public DS mapToDouble(ThrowingLongToDoubleFunction<? extends X> mapper) {
    return newDoubleStream(LD::mapToDouble, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public LS flatMap(
      ThrowingLongFunction<? extends ThrowingLongStream<? extends X>, ? extends X> mapper) {
    Function<ThrowingLongStream<? extends X>, ThrowingLongStream<XD>> streamMapper = getFunctionAdapter()::convert;
    ThrowingLongFunction<? extends ThrowingLongStream<XD>, ? extends X> f = mapper.andThen(streamMapper);
    return chain(LD::flatMap, getFunctionAdapter().convert(f));
  }

  @Override
  default public LS distinct() {
    return chain(LD::distinct);
  }

  @Override
  default public LS sorted() {
    return chain(LD::sorted);
  }

  @Override
  default public LS peek(ThrowingLongConsumer<? extends X> action) {
    return chain(LD::peek, getFunctionAdapter().convert(action));
  }

  @Override
  default public LS limit(long maxSize) {
    return chain(LD::limit, maxSize);
  }

  @Override
  default public LS skip(long n) {
    return chain(LD::skip, n);
  }

  @Override
  default public DS asDoubleStream() {
    return newDoubleStream(getDelegate().asDoubleStream());
  }
}
