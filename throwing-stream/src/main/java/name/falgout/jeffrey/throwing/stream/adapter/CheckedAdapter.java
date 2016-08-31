package name.falgout.jeffrey.throwing.stream.adapter;

import java.util.function.Function;
import java.util.function.Supplier;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.adapter.ExceptionMasker;

abstract class CheckedAdapter<D, X extends Throwable> extends ThrowingAbstractAdapter<D, X> {
  private static final ExceptionMasker<Throwable> GLOBAL_UNMASKER =
      new ExceptionMasker<>(Throwable.class);

  private final ExceptionMasker<X> exceptionMasker;
  private final RethrowChain<Throwable, X> chain;
  private final Function<Throwable, X> unmasker;

  CheckedAdapter(D delegate, ExceptionMasker<X> exceptionMasker) {
    this(delegate, exceptionMasker, RethrowChain.start());
  }

  CheckedAdapter(D delegate, ExceptionMasker<X> exceptionMasker, RethrowChain<Throwable, X> chain) {
    super(delegate, exceptionMasker.getExceptionClass());
    this.exceptionMasker = exceptionMasker;

    this.chain = chain.connect(RethrowChain.start(getExceptionClass()));
    unmasker = this.chain.finish();
  }

  public ExceptionMasker<X> getExceptionMasker() {
    return exceptionMasker;
  }

  public RethrowChain<Throwable, X> getChain() {
    return chain;
  }

  protected void unmaskException(Runnable runnable) throws X {
    unmaskException(() -> {
      runnable.run();
      return null;
    });
  }

  protected <R> R unmaskException(Supplier<R> supplier) throws X {
    try {
      return GLOBAL_UNMASKER.unmaskException(supplier);
    } catch (Throwable e) {
      throw unmasker.apply(e);
    }
  }
}
