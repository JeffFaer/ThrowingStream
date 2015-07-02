package throwing.stream.adapter;

import java.util.stream.BaseStream;

import throwing.RethrowChain;
import throwing.ThrowingIterator;
import throwing.stream.ThrowingBaseStream;

abstract class CheckedBaseStream<T, X extends Throwable, D extends BaseStream<T, D>, S extends ThrowingBaseStream<T, X, S>> extends
    CheckedAdapter<D, X> implements ThrowingBaseStream<T, X, S>, ChainingAdapter<D, S> {
  CheckedBaseStream(D delegate, FunctionAdapter<X> functionAdapter) {
    super(delegate, functionAdapter);
  }

  CheckedBaseStream(D delegate, FunctionAdapter<X> functionAdapter,
      RethrowChain<AdapterException, X> chain) {
    super(delegate, functionAdapter, chain);
  }

  @Override
  public ThrowingIterator<T, X> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getFunctionAdapter());
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
