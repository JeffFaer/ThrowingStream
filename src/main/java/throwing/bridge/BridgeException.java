package throwing.bridge;

final class BridgeException extends RuntimeException {
    private static final long serialVersionUID = -3986425123148316828L;
    
    BridgeException(Throwable cause) {
        super(cause);
    }
}
