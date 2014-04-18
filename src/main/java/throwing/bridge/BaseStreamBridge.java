package throwing.bridge;

public interface BaseStreamBridge<S, D> extends Bridge<D> {
    default public S chain(D newDelegate) {
        if (newDelegate == getDelegate()) {
            return getSelf();
        } else {
            return createNewStream(newDelegate);
        }
    }
    
    public S getSelf();
    
    public S createNewStream(D delegate);
}
