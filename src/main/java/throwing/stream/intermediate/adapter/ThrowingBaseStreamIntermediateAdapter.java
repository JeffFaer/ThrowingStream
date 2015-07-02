package throwing.stream.intermediate.adapter;

import throwing.stream.adapter.ChainingAdapter;
import throwing.stream.intermediate.ThrowingBaseStreamIntermediate;

public interface ThrowingBaseStreamIntermediateAdapter<D extends ThrowingBaseStreamIntermediate<D>, S extends ThrowingBaseStreamIntermediate<S>> extends
    ChainingAdapter<D, S>,
    ThrowingBaseStreamIntermediate<S> {
  @Override
  default public S sequential() {
    return chain(D::sequential);
  }

  @Override
  default public S parallel() {
    return chain(D::sequential);
  }

  @Override
  default public S unordered() {
    return chain(D::unordered);
  }

  @Override
  default public S onClose(Runnable closeHandler) {
    return chain(D::onClose, closeHandler);
  }

  @Override
  default public boolean isParallel() {
    return getDelegate().isParallel();
  }
}
