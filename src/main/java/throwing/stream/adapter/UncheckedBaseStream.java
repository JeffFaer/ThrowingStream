package throwing.stream.adapter;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

import throwing.stream.ThrowingBaseStream;

abstract class UncheckedBaseStream<T, X extends Throwable, S extends BaseStream<T, S>, D extends ThrowingBaseStream<T, X, D>> extends
    UncheckedAdapter<D, X> implements BaseStream<T, S>, ChainingAdapter<D, S> {
  UncheckedBaseStream(D delegate, Class<X> x) {
    super(delegate, x);
  }

  @Override
  public Iterator<T> iterator() {
    return ThrowingBridge.of(getDelegate().iterator(), getExceptionClass());
  }

  @Override
  public Spliterator<T> spliterator() {
    return ThrowingBridge.of(getDelegate().spliterator(), getExceptionClass());
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
