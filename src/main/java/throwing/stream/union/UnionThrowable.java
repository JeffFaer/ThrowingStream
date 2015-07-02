package throwing.stream.union;

import java.util.function.Consumer;

import javax.annotation.CheckReturnValue;

import throwing.ExceptionHandler;
import throwing.RethrowChain;
import edu.umd.cs.findbugs.annotations.CleanupObligation;
import edu.umd.cs.findbugs.annotations.CreatesObligation;
import edu.umd.cs.findbugs.annotations.DischargesObligation;

@CleanupObligation
public class UnionThrowable extends Throwable {
  public class ExceptionHandlerBuilder {
    private ExceptionHandler handler = ExceptionHandler.start();

    @CreatesObligation
    public <X> ExceptionHandlerBuilder handle(Class<X> x, Consumer<? super X> action) {
      handler.connect(ExceptionHandler.handle(x, action));
      return this;
    }

    @DischargesObligation
    public void finish() {
      if (!handler.apply(UnionThrowable.this.getCause()).isPresent()) {
        throw UnionThrowable.this.finish();
      }
    }
  }

  private static final long serialVersionUID = -9089451802483526560L;

  public UnionThrowable(Throwable cause) {
    super(cause);
  }

  @CreatesObligation
  public <X extends Throwable> UnionThrowable rethrow(Class<X> x) throws X {
    if (x.isInstance(getCause())) {
      throw x.cast(getCause());
    }

    return this;
  }

  public <X extends Throwable> ExceptionHandlerBuilder handle(Class<X> x, Consumer<? super X> action) {
    return new ExceptionHandlerBuilder().handle(x, action);
  }

  @DischargesObligation
  @CheckReturnValue
  public Error finish() {
    return RethrowChain.END_FUNCTION.apply(getCause());
  }
}
