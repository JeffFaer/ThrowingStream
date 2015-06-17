package throwing.stream.adapter;

final class AdapterException extends RuntimeException {
    private static final long serialVersionUID = -3986425123148316828L;
    
    AdapterException(Throwable cause) {
        super(cause);
    }
}
