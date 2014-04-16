package throwing;

import java.util.Iterator;

public interface ThrowingIterator<E, X extends Throwable> {
    public boolean hasNext() throws X;
    
    public E next() throws X;
    
    public static <E, X extends Throwable> ThrowingIterator<E, X> of(Iterator<E> bridged, Class<X> x) {
        return new IteratorBridge<>(bridged, x);
    }
}
