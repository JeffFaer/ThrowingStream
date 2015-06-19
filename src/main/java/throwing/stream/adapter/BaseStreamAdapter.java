package throwing.stream.adapter;

interface BaseStreamAdapter<S, D> extends Adapter<D> {
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
