package name.falgout.jeffrey.throwing.stream.adapter;

import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;

class UncheckedAdapter<D, X extends Throwable> extends ThrowingAbstractAdapter<D, X> {
  private final ExceptionMasker<X> exceptionMasker;

  UncheckedAdapter(D delegate, Class<X> x) {
    super(delegate, x);
    exceptionMasker = new ExceptionMasker<>(x);
  }

  public ExceptionMasker<X> getExceptionMasker() {
    return exceptionMasker;
  }
}
