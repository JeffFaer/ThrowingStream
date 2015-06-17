package throwing.stream.union;

import throwing.ThrowingIterator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface UnionIterator<E, X extends UnionThrowable> extends ThrowingIterator<E, Throwable> {
    public interface OfInt<X extends UnionThrowable> extends UnionIterator<Integer, X>,
            ThrowingIterator.OfInt<Throwable> {
        @Override
        public int nextInt() throws X;

        @Override
        public void forEachRemaining(ThrowingIntConsumer<Throwable> action) throws X;

        @Override
        default public Integer next() throws X {
            return nextInt();
        }
    }

    public interface OfLong<X extends UnionThrowable> extends UnionIterator<Long, X>,
            ThrowingIterator.OfLong<Throwable> {
        @Override
        public long nextLong() throws X;

        @Override
        public void forEachRemaining(ThrowingLongConsumer<Throwable> action) throws X;

        @Override
        default public Long next() throws X {
            return nextLong();
        }
    }

    public interface OfDouble<X extends UnionThrowable> extends UnionIterator<Double, X>,
            ThrowingIterator.OfDouble<Throwable> {
        @Override
        public double nextDouble() throws X;

        @Override
        public void forEachRemaining(ThrowingDoubleConsumer<Throwable> action) throws X;

        @Override
        default public Double next() throws X {
            return nextDouble();
        }
    }

    @Override
    public boolean hasNext() throws X;

    @Override
    public E next() throws X;

    @Override
    default public void remove() throws X {
        throw new UnsupportedOperationException();
    }
}
