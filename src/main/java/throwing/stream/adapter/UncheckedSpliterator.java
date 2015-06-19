package throwing.stream.adapter;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import throwing.ThrowingSpliterator;
import throwing.function.ThrowingDoubleConsumer;
import throwing.function.ThrowingIntConsumer;
import throwing.function.ThrowingLongConsumer;

class UncheckedSpliterator<T, S extends ThrowingSpliterator<T, X>, X extends Throwable> extends
        UncheckedAdapter<S, X> implements Spliterator<T> {
    static class OfInt<X extends Throwable> extends
            UncheckedSpliterator<Integer, ThrowingSpliterator.OfInt<X>, X> implements
            Spliterator.OfInt {
        OfInt(ThrowingSpliterator.OfInt<X> delegate, Class<X> x) {
            super(delegate, x);
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return new UncheckedSpliterator.OfInt<>(getDelegate().trySplit(), getExceptionClass());
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            return maskException(() -> getDelegate().tryAdvance(
                    (ThrowingIntConsumer<X>) action::accept));
        }
    }

    static class OfLong<X extends Throwable> extends
            UncheckedSpliterator<Long, ThrowingSpliterator.OfLong<X>, X> implements
            Spliterator.OfLong {
        OfLong(ThrowingSpliterator.OfLong<X> delegate, Class<X> x) {
            super(delegate, x);
        }

        @Override
        public Spliterator.OfLong trySplit() {
            return new UncheckedSpliterator.OfLong<>(getDelegate().trySplit(), getExceptionClass());
        }

        @Override
        public boolean tryAdvance(LongConsumer action) {
            return maskException(() -> getDelegate().tryAdvance(
                    (ThrowingLongConsumer<X>) action::accept));
        }
    }

    static class OfDouble<X extends Throwable> extends
            UncheckedSpliterator<Double, ThrowingSpliterator.OfDouble<X>, X> implements
            Spliterator.OfDouble {
        OfDouble(ThrowingSpliterator.OfDouble<X> delegate, Class<X> x) {
            super(delegate, x);
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            return new UncheckedSpliterator.OfDouble<>(getDelegate().trySplit(),
                    getExceptionClass());
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            return maskException(() -> getDelegate().tryAdvance(
                    (ThrowingDoubleConsumer<X>) action::accept));
        }
    }

    UncheckedSpliterator(S delegate, Class<X> x) {
        super(delegate, x);
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return maskException(() -> getDelegate().tryAdvance(action::accept));
    }

    @Override
    public Spliterator<T> trySplit() {
        return new UncheckedSpliterator<>(getDelegate().trySplit(), getExceptionClass());
    }

    @Override
    public long estimateSize() {
        return getDelegate().estimateSize();
    }

    @Override
    public int characteristics() {
        return getDelegate().characteristics();
    }
}
