package name.falgout.jeffrey.throwing;

public interface ThrowingIterator<E, X extends Throwable> {
  public static interface OfPrimitive<E, E_CONS, X extends Throwable>
      extends ThrowingIterator<E, X> {
    public void forEachRemaining(E_CONS action) throws X;
  }

  public static interface OfInt<X extends Throwable>
      extends OfPrimitive<Integer, ThrowingIntConsumer<? extends X>, X> {
    public int nextInt() throws X;

    @Override
    default public Integer next() throws X {
      return nextInt();
    }
  }

  public static interface OfLong<X extends Throwable>
      extends OfPrimitive<Long, ThrowingLongConsumer<? extends X>, X> {
    public long nextLong() throws X;

    @Override
    default public Long next() throws X {
      return nextLong();
    }
  }

  public static interface OfDouble<X extends Throwable>
      extends OfPrimitive<Double, ThrowingDoubleConsumer<? extends X>, X> {
    public double nextDouble() throws X;

    @Override
    default public Double next() throws X {
      return nextDouble();
    }
  }

  public E next() throws X;

  public boolean hasNext() throws X;

  default public void remove() throws X {
    throw new UnsupportedOperationException();
  }

  public void forEachRemaining(ThrowingConsumer<? super E, ? extends X> action) throws X;
}
