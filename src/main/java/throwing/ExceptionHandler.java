package throwing;

import java.util.Optional;
import java.util.function.Consumer;

@FunctionalInterface
public interface ExceptionHandler extends Chain<Throwable, Boolean> {
  public static ExceptionHandler start() {
    return Chain.<Throwable, Boolean> start()::apply;
  }

  public static <X> ExceptionHandler handle(Class<X> x, Consumer<? super X> action) {
    return t -> {
      if (x.isInstance(t)) {
        action.accept(x.cast(t));
        return Optional.of(true);
      } else {
        return Optional.empty();
      }
    };
  }

  @Override
  default public ExceptionHandler connect(Chain<Throwable, Boolean> link) {
    return Chain.super.connect(link)::apply;
  }
}
