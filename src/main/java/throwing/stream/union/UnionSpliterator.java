package throwing.stream.union;

import throwing.ThrowingSpliterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface UnionSpliterator<T> extends ThrowingSpliterator<T, Throwable> {
    public interface OfInt extends ThrowingSpliterator.OfInt<Throwable> {
        @Override
        public boolean tryAdvance(ThrowingIntConsumer<Throwable> action) throws UnionThrowable;
    }
    
    public interface OfLong extends ThrowingSpliterator.OfLong<Throwable> {
        @Override
        public boolean tryAdvance(ThrowingLongConsumer<Throwable> action) throws UnionThrowable;
    }
    
    public interface OfDouble extends ThrowingSpliterator.OfDouble<Throwable> {
        @Override
        public boolean tryAdvance(ThrowingDoubleConsumer<Throwable> action) throws UnionThrowable;
    }

    @Override
    public boolean tryAdvance(ThrowingConsumer<? super T, ? extends Throwable> action) throws UnionThrowable;
    
    @Override
    public UnionSpliterator<T> trySplit();
}
