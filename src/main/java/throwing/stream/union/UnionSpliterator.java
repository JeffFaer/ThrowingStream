package throwing.stream.union;

import throwing.ThrowingSpliterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface UnionSpliterator<T, X extends UnionThrowable> extends
        ThrowingSpliterator<T, Throwable> {
    public interface OfInt<X extends UnionThrowable> extends UnionSpliterator<Integer, X>,
            ThrowingSpliterator.OfInt<Throwable> {
        @Override
        public UnionSpliterator.OfInt<X> trySplit();

        @Override
        public boolean tryAdvance(ThrowingIntConsumer<? extends Throwable> action) throws X;
    }

    public interface OfLong<X extends UnionThrowable> extends UnionSpliterator<Long, X>,
            ThrowingSpliterator.OfLong<Throwable> {
        @Override
        public UnionSpliterator.OfLong<X> trySplit();

        @Override
        public boolean tryAdvance(ThrowingLongConsumer<? extends Throwable> action) throws X;
    }

    public interface OfDouble<X extends UnionThrowable> extends UnionSpliterator<Double, X>,
            ThrowingSpliterator.OfDouble<Throwable> {
        @Override
        public UnionSpliterator.OfDouble<X> trySplit();

        @Override
        public boolean tryAdvance(ThrowingDoubleConsumer<? extends Throwable> action) throws X;
    }

    @Override
    public UnionSpliterator<T, X> trySplit();

    @Override
    public boolean tryAdvance(ThrowingConsumer<? super T, ? extends Throwable> action) throws X;
}
