package throwing.stream.adapter;

import java.util.Iterator;

import throwing.ThrowingIterator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

class CheckedIterator<E, I extends Iterator<E>, X extends Throwable> extends CheckedAdapter<I, X>
        implements ThrowingIterator<E, X> {
    static class OfInt<X extends Throwable> extends
            CheckedIterator<Integer, java.util.PrimitiveIterator.OfInt, X> implements
            ThrowingIterator.OfInt<X> {
        OfInt(java.util.PrimitiveIterator.OfInt delegate, FunctionAdapter<X> functionAdapter) {
            super(delegate, functionAdapter);
        }

        @Override
        public void forEachRemaining(ThrowingIntConsumer<X> action) throws X {
            unmaskException(() -> getDelegate().forEachRemaining(
                    getFunctionAdapter().convert(action)));
        }

        @Override
        public int nextInt() throws X {
            return unmaskException(getDelegate()::nextInt);
        }
    }

    static class OfLong<X extends Throwable> extends
            CheckedIterator<Long, java.util.PrimitiveIterator.OfLong, X> implements
            ThrowingIterator.OfLong<X> {
        OfLong(java.util.PrimitiveIterator.OfLong delegate, FunctionAdapter<X> functionAdapter) {
            super(delegate, functionAdapter);
        }

        @Override
        public void forEachRemaining(ThrowingLongConsumer<X> action) throws X {
            unmaskException(() -> getDelegate().forEachRemaining(
                    getFunctionAdapter().convert(action)));
        }

        @Override
        public long nextLong() throws X {
            return unmaskException(getDelegate()::nextLong);
        }
    }

    static class OfDouble<X extends Throwable> extends
            CheckedIterator<Double, java.util.PrimitiveIterator.OfDouble, X> implements
            ThrowingIterator.OfDouble<X> {
        OfDouble(java.util.PrimitiveIterator.OfDouble delegate, FunctionAdapter<X> functionAdapter) {
            super(delegate, functionAdapter);
        }

        @Override
        public void forEachRemaining(ThrowingDoubleConsumer<X> action) throws X {
            unmaskException(() -> getDelegate().forEachRemaining(
                    getFunctionAdapter().convert(action)));
        }

        @Override
        public double nextDouble() throws X {
            return unmaskException(getDelegate()::nextDouble);
        }
    }

    CheckedIterator(I delegate, FunctionAdapter<X> functionAdapter) {
        super(delegate, functionAdapter);
    }

    @Override
    public boolean hasNext() throws X {
        return unmaskException(getDelegate()::hasNext);
    }

    @Override
    public E next() throws X {
        return unmaskException(getDelegate()::next);
    }
}
