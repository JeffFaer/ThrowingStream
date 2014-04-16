package throwing.stream;

import throwing.ThrowingIterator;
import throwing.ThrowingSpliterator;

public interface ThrowingBaseStream<T, X extends Throwable, S extends ThrowingBaseStream<T, X, S>> {
    public ThrowingIterator<T, X> iterator();
    
    public ThrowingSpliterator<T, X> spliterator();
    
    public boolean isParallel();
    
    public S sequential();
    
    public S parallel();
    
    public S unordered();
    
    public S onClose(Runnable closeHandler);
    
    public void close();
}
