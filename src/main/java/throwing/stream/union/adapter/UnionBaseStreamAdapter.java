package throwing.stream.union.adapter;

import throwing.stream.ThrowingBaseStream;
import throwing.stream.adapter.ChainingAdapter;
import throwing.stream.union.UnionBaseStream;
import throwing.stream.union.UnionIterator;
import throwing.stream.union.UnionSpliterator;
import throwing.stream.union.UnionThrowable;

abstract class UnionBaseStreamAdapter<T, X extends UnionThrowable, D extends ThrowingBaseStream<T, X, D>, S extends S2, S2 extends ThrowingBaseStream<T, Throwable, S2>> extends
    UnionAdapter<D, X> implements UnionBaseStream<T, X, S, S2>, ChainingAdapter<D, S> {
  protected UnionBaseStreamAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public boolean isParallel() {
    return getDelegate().isParallel();
  }

  @Override
  public void close() {
    getDelegate().close();
  }

  @Override
  public UnionIterator<T, X> iterator() {
    return new UnionIteratorAdapter<>(getDelegate().iterator(), getFunctionAdapter());
  }

  @Override
  public UnionSpliterator<T, X> spliterator() {
    return new UnionSpliteratorAdapter.Basic<>(getDelegate().spliterator(), getFunctionAdapter());
  }

  @Override
  public S onClose(Runnable closeHandler) {
    return chain(D::onClose, closeHandler);
  }

  @Override
  public S parallel() {
    return chain(D::parallel);
  }

  @Override
  public S sequential() {
    return chain(D::sequential);
  }

  @Override
  public S unordered() {
    return chain(D::unordered);
  }
}
