package throwing.stream.union;

import throwing.ThrowingIterator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface UnionIterator<E> extends ThrowingIterator<E, Throwable> {
    public interface OfInt extends UnionIterator<Integer>, ThrowingIterator.OfInt<Throwable> {
        @Override
        public int nextInt() throws UnionThrowable;
        
        @Override
        public void forEachRemaining(ThrowingIntConsumer<Throwable> action) throws UnionThrowable;
        
        @Override
        default public Integer next() throws UnionThrowable {
            return nextInt();
        }
    }
    
    public interface OfLong extends UnionIterator<Long>, ThrowingIterator.OfLong<Throwable> {
        @Override
        public long nextLong() throws UnionThrowable;

        @Override
        public void forEachRemaining(ThrowingLongConsumer<Throwable> action) throws UnionThrowable;
        
        @Override
        default public Long next() throws UnionThrowable {
            return nextLong();
        }
    }
    
    public interface OfDouble extends UnionIterator<Double>, ThrowingIterator.OfDouble<Throwable> {
        @Override
        public double nextDouble() throws UnionThrowable;
        
        @Override
        public void forEachRemaining(ThrowingDoubleConsumer<Throwable> action) throws UnionThrowable;
        
        @Override
        default public Double next() throws UnionThrowable {
            return nextDouble();
        }
    }

    @Override
    public boolean hasNext() throws UnionThrowable;
    
    @Override
    public E next() throws UnionThrowable;
    
    @Override
    default public void remove() throws UnionThrowable {
        throw new UnsupportedOperationException();
    }
}
