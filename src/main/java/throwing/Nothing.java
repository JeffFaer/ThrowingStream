package throwing;

public final class Nothing extends RuntimeException {
    private static final long serialVersionUID = -5459023265330371793L;
    
    private Nothing() {
        throw new Error("No instances!");
    }
}
