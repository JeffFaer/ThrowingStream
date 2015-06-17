package throwing.stream.union;

import throwing.ThrowingSpliterator;
import throwing.function.ThrowingConsumer;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

public interface UnionSpliterator<T, X extends UnionThrowable> extends
        ThrowingSpliterator<T, Throwable> {
    public interface OfInt<X extends UnionThrowable> extends ThrowingSpliterator.OfInt<Throwable> {
        @Override
        public boolean tryAdvance(ThrowingIntConsumer<Throwable> action) throws X;
    }

    public interface OfLong<X extends UnionThrowable> extends ThrowingSpliterator.OfLong<Throwable> {
        @Override
        public boolean tryAdvance(ThrowingLongConsumer<Throwable> action) throws X;
    }

    public interface OfDouble<X extends UnionThrowable> extends
            ThrowingSpliterator.OfDouble<Throwable> {
        @Override
        public boolean tryAdvance(ThrowingDoubleConsumer<Throwable> action) throws X;
    }

    @Override
    public boolean tryAdvance(ThrowingConsumer<? super T, ? extends Throwable> action) throws X;

    @Override
    public UnionSpliterator<T, X> trySplit();
}
