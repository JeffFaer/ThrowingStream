package throwing;

import java.util.Spliterator;
import java.util.function.Consumer;

import throwing.function.ThrowingConsumer;

public interface ThrowingSpliterator<T, X extends Throwable> {
    public boolean tryAdvance(ThrowingConsumer<? super T, ? extends X> action) throws X;
    
    public boolean tryAdvance(Consumer<? super T> action);
    
    public ThrowingSpliterator<T, X> trySplit();
    
    public long estimateSize();
    
    public int characteristics();
    
    public static <T, X extends Throwable> ThrowingSpliterator<T, X> of(Spliterator<T> bridged, Class<X> x) {
        return new SpliteratorBridge<>(bridged, x);
    }
}
