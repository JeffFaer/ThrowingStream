package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.stream.BaseStream;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.ThrowingIterator;
import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;
import name.falgout.jeffrey.throwing.stream.ThrowingBaseStream;

abstract class CheckedBaseStream<T, X extends Throwable, D extends BaseStream<T, D>, S extends ThrowingBaseStream<T, X, S>>
    extends CheckedAdapter<D, X> implements ThrowingBaseStream<T, X, S>, ChainingAdapter<D, S> {
  CheckedBaseStream(D delegate, ExceptionMasker<X> ExceptionMasker) {
    super(delegate, ExceptionMasker);
  }

  CheckedBaseStream(D delegate, ExceptionMasker<X> ExceptionMasker,
      RethrowChain<Throwable, X> chain) {
    super(delegate, ExceptionMasker, chain);
  }

  @Override
  public ThrowingIterator<T, X> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionMasker());
  }

  @Override
  public boolean isParallel() {
    return getDelegate().isParallel();
  }

  @Override
  public S sequential() {
    return chain(D::sequential);
  }

  @Override
  public S parallel() {
    return chain(D::parallel);
  }

  @Override
  public S unordered() {
    return chain(D::unordered);
  }

  @Override
  public S onClose(Runnable closeHandler) {
    return chain(D::onClose, closeHandler);
  }

  @Override
  public void close() {
    getDelegate().close();
  }
}
