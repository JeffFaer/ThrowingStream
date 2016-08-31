package name.falgout.jeffrey.throwing.adapter;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import name.falgout.jeffrey.throwing.RethrowChain;
import name.falgout.jeffrey.throwing.ThrowingBiConsumer;
import name.falgout.jeffrey.throwing.ThrowingBiFunction;
import name.falgout.jeffrey.throwing.ThrowingBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingComparator;
import name.falgout.jeffrey.throwing.ThrowingConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingDoublePredicate;
import name.falgout.jeffrey.throwing.ThrowingDoubleToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingDoubleUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingFunction;
import name.falgout.jeffrey.throwing.ThrowingIntBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingIntFunction;
import name.falgout.jeffrey.throwing.ThrowingIntPredicate;
import name.falgout.jeffrey.throwing.ThrowingIntToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingIntToLongFunction;
import name.falgout.jeffrey.throwing.ThrowingIntUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongBinaryOperator;
import name.falgout.jeffrey.throwing.ThrowingLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingLongFunction;
import name.falgout.jeffrey.throwing.ThrowingLongPredicate;
import name.falgout.jeffrey.throwing.ThrowingLongToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingLongToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingLongUnaryOperator;
import name.falgout.jeffrey.throwing.ThrowingObjDoubleConsumer;
import name.falgout.jeffrey.throwing.ThrowingObjIntConsumer;
import name.falgout.jeffrey.throwing.ThrowingObjLongConsumer;
import name.falgout.jeffrey.throwing.ThrowingPredicate;
import name.falgout.jeffrey.throwing.ThrowingRunnable;
import name.falgout.jeffrey.throwing.ThrowingSupplier;
import name.falgout.jeffrey.throwing.ThrowingToDoubleFunction;
import name.falgout.jeffrey.throwing.ThrowingToIntFunction;
import name.falgout.jeffrey.throwing.ThrowingToLongFunction;

public final class ExceptionMasker<X extends Throwable> {
  private static final class MaskedException extends RuntimeException {
    private static final long serialVersionUID = -6116256229712974011L;

    private MaskedException(Throwable cause) {
      super("Uh oh, someone forgot to unmask!", cause);
    }
  }

  public static Throwable unmask(Throwable throwable) {
    if (throwable instanceof MaskedException) {
      return throwable.getCause();
    }

    return throwable;
  }

  private final Class<X> exceptionClass;
  private final Function<Throwable, MaskedException> masker;
  private final Function<MaskedException, X> unmasker;

  public ExceptionMasker(Class<X> exceptionClass) {
    this.exceptionClass = exceptionClass;
    RethrowChain<Throwable, X> caster = RethrowChain.start(exceptionClass);
    masker = caster.rethrow(MaskedException::new).finish();
    unmasker = RethrowChain.<MaskedException, X>start().connect(masked -> {
      return caster.apply(masked.getCause());
    }).finish();
  }

  public Class<X> getExceptionClass() {
    return exceptionClass;
  }

  public void maskException(ThrowingRunnable<? extends X> runnable) {
    maskException(() -> {
      runnable.run();
      return null;
    });
  }

  public <R> R maskException(ThrowingSupplier<? extends R, ? extends X> supplier) {
    try {
      return supplier.get();
    } catch (Throwable t) {
      throw masker.apply(t);
    }
  }

  public Runnable mask(ThrowingRunnable<? extends X> runnable) {
    Objects.requireNonNull(runnable);
    return () -> maskException(runnable);
  }

  public <R> Supplier<R> mask(ThrowingSupplier<? extends R, ? extends X> supplier) {
    Objects.requireNonNull(supplier);
    return () -> maskException(supplier);
  }

  public <T> Predicate<T> mask(ThrowingPredicate<? super T, ? extends X> predicate) {
    Objects.requireNonNull(predicate);
    return t -> maskException(() -> predicate.test(t));
  }

  public <T, R> Function<T, R> mask(
      ThrowingFunction<? super T, ? extends R, ? extends X> function) {
    Objects.requireNonNull(function);
    return t -> maskException(() -> function.apply(t));
  }

  public <T> Consumer<T> mask(ThrowingConsumer<? super T, ? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return t -> maskException(() -> consumer.accept(t));
  }

  public <T, U, R> BiFunction<T, U, R> mask(
      ThrowingBiFunction<? super T, ? super U, ? extends R, ? extends X> function) {
    Objects.requireNonNull(function);
    return (t, u) -> maskException(() -> function.apply(t, u));
  }

  public <T> BinaryOperator<T> mask(ThrowingBinaryOperator<T, ? extends X> operator) {
    Objects.requireNonNull(operator);
    return (t1, t2) -> maskException(() -> operator.apply(t1, t2));
  }

  public <T> Comparator<T> mask(ThrowingComparator<? super T, ? extends X> comparator) {
    Objects.requireNonNull(comparator);
    return (t1, t2) -> maskException(() -> comparator.compare(t1, t2));
  }

  public <T, U> BiConsumer<T, U> mask(
      ThrowingBiConsumer<? super T, ? super U, ? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return (t, u) -> maskException(() -> consumer.accept(t, u));
  }

  // int

  public IntConsumer mask(ThrowingIntConsumer<? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return i -> maskException(() -> consumer.accept(i));
  }

  public IntPredicate mask(ThrowingIntPredicate<? extends X> predicate) {
    Objects.requireNonNull(predicate);
    return i -> maskException(() -> predicate.test(i));
  }

  public IntBinaryOperator mask(ThrowingIntBinaryOperator<? extends X> operator) {
    Objects.requireNonNull(operator);
    return (i1, i2) -> maskException(() -> operator.applyAsInt(i1, i2));
  }

  public <T> ObjIntConsumer<T> mask(ThrowingObjIntConsumer<T, ? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return (t, i) -> maskException(() -> consumer.accept(t, i));
  }

  public IntUnaryOperator mask(ThrowingIntUnaryOperator<? extends X> operator) {
    Objects.requireNonNull(operator);
    return i -> maskException(() -> operator.applyAsInt(i));
  }

  public <R> IntFunction<R> mask(ThrowingIntFunction<R, ? extends X> function) {
    Objects.requireNonNull(function);
    return i -> maskException(() -> function.apply(i));
  }

  public <T> ToIntFunction<T> mask(ThrowingToIntFunction<T, ? extends X> function) {
    Objects.requireNonNull(function);
    return t -> maskException(() -> function.applyAsInt(t));
  }

  public IntToLongFunction mask(ThrowingIntToLongFunction<? extends X> function) {
    Objects.requireNonNull(function);
    return i -> maskException(() -> function.applyAsLong(i));
  }

  public IntToDoubleFunction mask(ThrowingIntToDoubleFunction<? extends X> function) {
    Objects.requireNonNull(function);
    return i -> maskException(() -> function.applyAsDouble(i));
  }

  // long

  public LongConsumer mask(ThrowingLongConsumer<? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return l -> maskException(() -> consumer.accept(l));
  }

  public LongPredicate mask(ThrowingLongPredicate<? extends X> predicate) {
    Objects.requireNonNull(predicate);
    return l -> maskException(() -> predicate.test(l));
  }

  public LongBinaryOperator mask(ThrowingLongBinaryOperator<? extends X> operator) {
    Objects.requireNonNull(operator);
    return (l1, l2) -> maskException(() -> operator.applyAsLong(l1, l2));
  }

  public <T> ObjLongConsumer<T> mask(ThrowingObjLongConsumer<T, ? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return (t, l) -> maskException(() -> consumer.accept(t, l));
  }

  public LongUnaryOperator mask(ThrowingLongUnaryOperator<? extends X> operator) {
    Objects.requireNonNull(operator);
    return l -> maskException(() -> operator.applyAsLong(l));
  }

  public <R> LongFunction<R> mask(ThrowingLongFunction<R, ? extends X> function) {
    Objects.requireNonNull(function);
    return l -> maskException(() -> function.apply(l));
  }

  public <T> ToLongFunction<T> mask(ThrowingToLongFunction<T, ? extends X> function) {
    Objects.requireNonNull(function);
    return t -> maskException(() -> function.applyAsLong(t));
  }

  public LongToIntFunction mask(ThrowingLongToIntFunction<? extends X> function) {
    Objects.requireNonNull(function);
    return l -> maskException(() -> function.applyAsInt(l));
  }

  public LongToDoubleFunction mask(ThrowingLongToDoubleFunction<? extends X> function) {
    Objects.requireNonNull(function);
    return l -> maskException(() -> function.applyAsDouble(l));
  }

  // double

  public DoubleConsumer mask(ThrowingDoubleConsumer<? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return d -> maskException(() -> consumer.accept(d));
  }

  public DoublePredicate mask(ThrowingDoublePredicate<? extends X> predicate) {
    Objects.requireNonNull(predicate);
    return d -> maskException(() -> predicate.test(d));
  }

  public DoubleBinaryOperator mask(ThrowingDoubleBinaryOperator<? extends X> operator) {
    Objects.requireNonNull(operator);
    return (d1, d2) -> maskException(() -> operator.applyAsDouble(d1, d2));
  }

  public <T> ObjDoubleConsumer<T> mask(ThrowingObjDoubleConsumer<T, ? extends X> consumer) {
    Objects.requireNonNull(consumer);
    return (t, d) -> maskException(() -> consumer.accept(t, d));
  }

  public DoubleUnaryOperator mask(ThrowingDoubleUnaryOperator<? extends X> operator) {
    Objects.requireNonNull(operator);
    return d -> maskException(() -> operator.applyAsDouble(d));
  }

  public <R> DoubleFunction<R> mask(ThrowingDoubleFunction<R, ? extends X> function) {
    Objects.requireNonNull(function);
    return d -> maskException(() -> function.apply(d));
  }

  public <T> ToDoubleFunction<T> mask(ThrowingToDoubleFunction<T, ? extends X> function) {
    Objects.requireNonNull(function);
    return t -> maskException(() -> function.applyAsDouble(t));
  }

  public DoubleToIntFunction mask(ThrowingDoubleToIntFunction<? extends X> function) {
    Objects.requireNonNull(function);
    return d -> maskException(() -> function.applyAsInt(d));
  }

  public DoubleToLongFunction mask(ThrowingDoubleToLongFunction<? extends X> function) {
    Objects.requireNonNull(function);
    return d -> maskException(() -> function.applyAsLong(d));
  }

  public void unmaskException(Runnable runnable) throws X {
    unmaskException(() -> {
      runnable.run();
      return null;
    });
  }

  public <R> R unmaskException(Supplier<? extends R> supplier) throws X {
    try {
      return supplier.get();
    } catch (MaskedException e) {
      throw unmasker.apply(e);
    }
  }

  public ThrowingRunnable<X> unmask(Runnable runnable) {
    Objects.requireNonNull(runnable);
    return () -> unmaskException(runnable);
  }

  public <R> ThrowingSupplier<R, X> unmask(Supplier<? extends R> supplier) {
    Objects.requireNonNull(supplier);
    return () -> unmaskException(supplier);
  }

  public <T> ThrowingPredicate<T, X> unmask(Predicate<? super T> predicate) {
    Objects.requireNonNull(predicate);
    return t -> unmaskException(() -> predicate.test(t));
  }

  public <T, R> ThrowingFunction<T, R, X> unmask(Function<? super T, ? extends R> function) {
    Objects.requireNonNull(function);
    return t -> unmaskException(() -> function.apply(t));
  }

  public <T> ThrowingConsumer<T, X> unmask(Consumer<? super T> consumer) {
    Objects.requireNonNull(consumer);
    return t -> unmaskException(() -> consumer.accept(t));
  }

  public <T, U, R> ThrowingBiFunction<T, U, R, X> unmask(
      BiFunction<? super T, ? super U, ? extends R> function) {
    Objects.requireNonNull(function);
    return (t, u) -> unmaskException(() -> function.apply(t, u));
  }

  public <T> ThrowingBinaryOperator<T, X> unmask(BinaryOperator<T> operator) {
    Objects.requireNonNull(operator);
    return (t1, t2) -> unmaskException(() -> operator.apply(t1, t2));
  }

  public <T> ThrowingComparator<T, X> unmask(Comparator<? super T> comparator) {
    Objects.requireNonNull(comparator);
    return (t1, t2) -> unmaskException(() -> comparator.compare(t1, t2));
  }

  public <T, U> ThrowingBiConsumer<T, U, X> unmask(BiConsumer<? super T, ? super U> consumer) {
    Objects.requireNonNull(consumer);
    return (t, u) -> unmaskException(() -> consumer.accept(t, u));
  }

  // int

  public ThrowingIntConsumer<X> unmask(IntConsumer consumer) {
    Objects.requireNonNull(consumer);
    return i -> unmaskException(() -> consumer.accept(i));
  }

  public ThrowingIntPredicate<X> unmask(IntPredicate predicate) {
    Objects.requireNonNull(predicate);
    return i -> unmaskException(() -> predicate.test(i));
  }

  public ThrowingIntBinaryOperator<X> unmask(IntBinaryOperator operator) {
    Objects.requireNonNull(operator);
    return (i1, i2) -> unmaskException(() -> operator.applyAsInt(i1, i2));
  }

  public <T> ThrowingObjIntConsumer<T, X> unmask(ObjIntConsumer<T> consumer) {
    Objects.requireNonNull(consumer);
    return (t, i) -> unmaskException(() -> consumer.accept(t, i));
  }

  public ThrowingIntUnaryOperator<X> unmask(IntUnaryOperator operator) {
    Objects.requireNonNull(operator);
    return i -> unmaskException(() -> operator.applyAsInt(i));
  }

  public <R> ThrowingIntFunction<R, X> unmask(IntFunction<R> function) {
    Objects.requireNonNull(function);
    return i -> unmaskException(() -> function.apply(i));
  }

  public <T> ThrowingToIntFunction<T, X> unmask(ToIntFunction<T> function) {
    Objects.requireNonNull(function);
    return t -> unmaskException(() -> function.applyAsInt(t));
  }

  public ThrowingIntToLongFunction<X> unmask(IntToLongFunction function) {
    Objects.requireNonNull(function);
    return i -> unmaskException(() -> function.applyAsLong(i));
  }

  public ThrowingIntToDoubleFunction<X> unmask(IntToDoubleFunction function) {
    Objects.requireNonNull(function);
    return i -> unmaskException(() -> function.applyAsDouble(i));
  }

  // long

  public ThrowingLongConsumer<X> unmask(LongConsumer consumer) {
    Objects.requireNonNull(consumer);
    return l -> unmaskException(() -> consumer.accept(l));
  }

  public ThrowingLongPredicate<X> unmask(LongPredicate predicate) {
    Objects.requireNonNull(predicate);
    return l -> unmaskException(() -> predicate.test(l));
  }

  public ThrowingLongBinaryOperator<X> unmask(LongBinaryOperator operator) {
    Objects.requireNonNull(operator);
    return (l1, l2) -> unmaskException(() -> operator.applyAsLong(l1, l2));
  }

  public <T> ThrowingObjLongConsumer<T, X> unmask(ObjLongConsumer<T> consumer) {
    Objects.requireNonNull(consumer);
    return (t, l) -> unmaskException(() -> consumer.accept(t, l));
  }

  public ThrowingLongUnaryOperator<X> unmask(LongUnaryOperator operator) {
    Objects.requireNonNull(operator);
    return l -> unmaskException(() -> operator.applyAsLong(l));
  }

  public <R> ThrowingLongFunction<R, X> unmask(LongFunction<R> function) {
    Objects.requireNonNull(function);
    return l -> unmaskException(() -> function.apply(l));
  }

  public <T> ThrowingToLongFunction<T, X> unmask(ToLongFunction<T> function) {
    Objects.requireNonNull(function);
    return t -> unmaskException(() -> function.applyAsLong(t));
  }

  public ThrowingLongToIntFunction<X> unmask(LongToIntFunction function) {
    Objects.requireNonNull(function);
    return l -> unmaskException(() -> function.applyAsInt(l));
  }

  public ThrowingLongToDoubleFunction<X> unmask(LongToDoubleFunction function) {
    Objects.requireNonNull(function);
    return l -> unmaskException(() -> function.applyAsDouble(l));
  }

  // double

  public ThrowingDoubleConsumer<X> unmask(DoubleConsumer consumer) {
    Objects.requireNonNull(consumer);
    return d -> unmaskException(() -> consumer.accept(d));
  }

  public ThrowingDoublePredicate<X> unmask(DoublePredicate predicate) {
    Objects.requireNonNull(predicate);
    return d -> unmaskException(() -> predicate.test(d));
  }

  public ThrowingDoubleBinaryOperator<X> unmask(DoubleBinaryOperator operator) {
    Objects.requireNonNull(operator);
    return (d1, d2) -> unmaskException(() -> operator.applyAsDouble(d1, d2));
  }

  public <T> ThrowingObjDoubleConsumer<T, X> unmask(ObjDoubleConsumer<T> consumer) {
    Objects.requireNonNull(consumer);
    return (t, d) -> unmaskException(() -> consumer.accept(t, d));
  }

  public ThrowingDoubleUnaryOperator<X> unmask(DoubleUnaryOperator operator) {
    Objects.requireNonNull(operator);
    return d -> unmaskException(() -> operator.applyAsDouble(d));
  }

  public <R> ThrowingDoubleFunction<R, X> unmask(DoubleFunction<R> function) {
    Objects.requireNonNull(function);
    return d -> unmaskException(() -> function.apply(d));
  }

  public <T> ThrowingToDoubleFunction<T, X> unmask(ToDoubleFunction<T> function) {
    Objects.requireNonNull(function);
    return t -> unmaskException(() -> function.applyAsDouble(t));
  }

  public ThrowingDoubleToIntFunction<X> unmask(DoubleToIntFunction function) {
    Objects.requireNonNull(function);
    return d -> unmaskException(() -> function.applyAsInt(d));
  }

  public ThrowingDoubleToLongFunction<X> unmask(DoubleToLongFunction function) {
    Objects.requireNonNull(function);
    return d -> unmaskException(() -> function.applyAsLong(d));
  }
}
