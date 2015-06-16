package throwing.stream.union;

import throwing.RethrowChain;

public class UnionThrowable extends Throwable {
    private static final long serialVersionUID = -9089451802483526560L;
    
    public UnionThrowable(Throwable cause) {
        super(cause);
    }
    
    public <X extends Throwable> UnionThrowable rethrow(Class<X> x) throws X {
        if (x.isInstance(getCause())) {
            throw x.cast(getCause());
        }
        return this;
    }
    
    public Error end() {
        return RethrowChain.END_FUNCTION.apply(getCause());
    }
}
