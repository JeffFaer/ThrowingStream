package throwing.stream.union.adapter;

import throwing.stream.ThrowingBaseStream;
import throwing.stream.intermediate.adapter.ThrowingBaseStreamIntermediateAdapter;
import throwing.stream.union.UnionBaseStream;
import throwing.stream.union.UnionThrowable;

abstract class UnionBaseStreamAdapter<T, X extends UnionThrowable, D extends ThrowingBaseStream<T, X, D>, S extends UnionBaseStream<T, X, S>> extends
    UnionAdapter<D, X> implements
    ThrowingBaseStreamIntermediateAdapter<D, S>,
    UnionBaseStream<T, X, S> {
  protected UnionBaseStreamAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate, adapter);
  }

  @Override
  public void close() {
    getDelegate().close();
  }
}
