package throwing.stream.adapter;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @param <D>
 *          the type of the delegate object
 * @param <S>
 *          the type of this object (or the one you want to use in chainable methods)
 */
public interface ChainingAdapter<D, S> extends Adapter<D> {
  default public S chain(D newDelegate) {
    if (newDelegate.equals(getDelegate())) {
      return getSelf();
    } else {
      return createNewAdapter(newDelegate);
    }
  }

  default public S chain(Function<? super D, ? extends D> function) {
    return chain(function.apply(getDelegate()));
  }

  default public <U> S chain(BiFunction<? super D, U, ? extends D> function, U secondArgument) {
    return chain(function.apply(getDelegate(), secondArgument));
  }

  public S getSelf();

  public S createNewAdapter(D newDelegate);
}
