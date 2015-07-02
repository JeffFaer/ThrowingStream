package throwing.stream.adapter;

abstract class ThrowingAbstractAdapter<D, X extends Throwable> extends AbstractAdapter<D> {
  private final Class<X> x;

  ThrowingAbstractAdapter(D delegate, Class<X> x) {
    super(delegate);
    this.x = x;
  }

  public Class<X> getExceptionClass() {
    return x;
  }
}
