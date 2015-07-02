package throwing.stream.intermediate.adapter;

import java.util.function.BiFunction;
import java.util.function.Function;

import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingDoubleFunction;
import throwing.function.ThrowingDoublePredicate;
import throwing.function.ThrowingDoubleToIntFunction;
import throwing.function.ThrowingDoubleToLongFunction;
import throwing.function.ThrowingDoubleUnaryOperator;
import throwing.stream.ThrowingDoubleStream;
import throwing.stream.intermediate.ThrowingDoubleStreamIntermediate;
import throwing.stream.intermediate.ThrowingIntStreamIntermediate;
import throwing.stream.intermediate.ThrowingLongStreamIntermediate;

public interface ThrowingDoubleStreamIntermediateAdapter<X extends Throwable, XD extends Throwable, ID extends ThrowingIntStreamIntermediate<XD, ID, LD, DD>, LD extends ThrowingLongStreamIntermediate<XD, ID, LD, DD>, DD extends ThrowingDoubleStreamIntermediate<XD, ID, LD, DD>, IS extends ThrowingIntStreamIntermediate<X, IS, LS, DS>, LS extends ThrowingLongStreamIntermediate<X, IS, LS, DS>, DS extends ThrowingDoubleStreamIntermediate<X, IS, LS, DS>> extends
    ThrowingBaseStreamIntermediateAdapter<DD, DS>,
    ThrowingDoubleStreamIntermediate<X, IS, LS, DS> {
  public ThrowingFunctionAdapter<XD, X> getFunctionAdapter();

  public IS newIntStream(ID delegate);

  public LS newLongStream(LD delegate);

  default public <U> IS newIntStream(BiFunction<? super DD, U, ID> function, U secondArgument) {
    return newIntStream(function.apply(getDelegate(), secondArgument));
  }

  default public <U> LS newLongStream(BiFunction<? super DD, U, LD> function, U secondArgument) {
    return newLongStream(function.apply(getDelegate(), secondArgument));
  }

  @Override
  default public DS filter(ThrowingDoublePredicate<? extends X> predicate) {
    return chain(DD::filter, getFunctionAdapter().convert(predicate));
  }

  @Override
  default public DS map(ThrowingDoubleUnaryOperator<? extends X> mapper) {
    return chain(DD::map, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public IS mapToInt(ThrowingDoubleToIntFunction<? extends X> mapper) {
    return newIntStream(DD::mapToInt, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public LS mapToLong(ThrowingDoubleToLongFunction<? extends X> mapper) {
    return newLongStream(DD::mapToLong, getFunctionAdapter().convert(mapper));
  }

  @Override
  default public DS flatMap(
      ThrowingDoubleFunction<? extends ThrowingDoubleStream<? extends X>, ? extends X> mapper) {
    Function<ThrowingDoubleStream<? extends X>, ThrowingDoubleStream<XD>> streamMapper = getFunctionAdapter()::convert;
    ThrowingDoubleFunction<? extends ThrowingDoubleStream<XD>, ? extends X> f = mapper.andThen(streamMapper);
    return chain(DD::flatMap, getFunctionAdapter().convert(f));
  }

  @Override
  default public DS distinct() {
    return chain(DD::distinct);
  }

  @Override
  default public DS sorted() {
    return chain(DD::sorted);
  }

  @Override
  default public DS peek(ThrowingDoubleConsumer<? extends X> action) {
    return chain(DD::peek, getFunctionAdapter().convert(action));
  }

  @Override
  default public DS limit(long maxSize) {
    return chain(DD::limit, maxSize);
  }

  @Override
  default public DS skip(long n) {
    return chain(DD::skip, n);
  }
}
