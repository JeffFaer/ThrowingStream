package throwing.stream.union.adapter;

import throwing.stream.adapter.AbstractAdapter;
import throwing.stream.union.UnionThrowable;

abstract class UnionAdapter<D, X extends UnionThrowable> extends AbstractAdapter<D> {
  private final UnionFunctionAdapter<X> adapter;

  UnionAdapter(D delegate, UnionFunctionAdapter<X> adapter) {
    super(delegate);
    this.adapter = adapter;
  }

  public UnionFunctionAdapter<X> getFunctionAdapter() {
    return adapter;
  }
}
