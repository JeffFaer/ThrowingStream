package throwing;

import java.util.Objects;

import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface ThrowingIterator<E, X extends Throwable> {
    public interface PrimitiveIterator<T, T_CONS, X extends Throwable> extends ThrowingIterator<T, X> {
        public void forEachRemaining(T_CONS action) throws X;
    }
    
    public interface OfInt<X extends Throwable> extends PrimitiveIterator<Integer, ThrowingIntConsumer<X>, X> {
        public int nextInt() throws X;
        
        @Override
        default public void forEachRemaining(ThrowingIntConsumer<X> action) throws X {
            Objects.requireNonNull(action);
            while (hasNext()) {
                action.accept(nextInt());
            }
        }
        
        @Override
        default public Integer next() throws X {
            return nextInt();
        }
    }
    
    public interface OfLong<X extends Throwable> extends PrimitiveIterator<Long, ThrowingLongConsumer<X>, X> {
        public long nextLong() throws X;
        
        @Override
        default public void forEachRemaining(ThrowingLongConsumer<X> action) throws X {
            Objects.requireNonNull(action);
            while (hasNext()) {
                action.accept(nextLong());
            }
        }
        
        @Override
        default public Long next() throws X {
            return nextLong();
        }
    }
    
    public interface OfDouble<X extends Throwable> extends PrimitiveIterator<Double, ThrowingDoubleConsumer<X>, X> {
        public double nextDouble() throws X;
        
        @Override
        default public void forEachRemaining(ThrowingDoubleConsumer<X> action) throws X {
            Objects.requireNonNull(action);
            while (hasNext()) {
                action.accept(nextDouble());
            }
        }
        
        @Override
        default public Double next() throws X {
            return nextDouble();
        }
    }
    
    public boolean hasNext() throws X;
    
    public E next() throws X;
    
    default public void remove() throws X {
        throw new UnsupportedOperationException();
    }
}
