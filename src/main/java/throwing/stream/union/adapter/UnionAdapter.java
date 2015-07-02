package throwing.stream.union.adapter;

import throwing.stream.adapter.AbstractAdapter;
import throwing.stream.intermediate.adapter.ThrowingFunctionAdapter;
import throwing.stream.union.UnionThrowable;

abstract class UnionAdapter<D, X extends UnionThrowable> extends AbstractAdapter<D> {
  private final ThrowingFunctionAdapter<X, Throwable> adapter;

  UnionAdapter(D delegate, ThrowingFunctionAdapter<X, Throwable> adapter) {
    super(delegate);
    this.adapter = adapter;
  }

  public ThrowingFunctionAdapter<X, Throwable> getFunctionAdapter() {
    return adapter;
  }
}
