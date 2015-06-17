package throwing.stream.union;

import throwing.stream.ThrowingBaseStream;

public interface UnionBaseStream<T, X extends UnionThrowable, S extends S2, S2 extends ThrowingBaseStream<T, Throwable, S2>>
        extends ThrowingBaseStream<T, Throwable, S2> {
    @Override
    public UnionIterator<T, X> iterator();

    @Override
    public UnionSpliterator<T, X> spliterator();

    @Override
    public S onClose(Runnable closeHandler);

    @Override
    public S parallel();

    @Override
    public S sequential();

    @Override
    public S unordered();
}
