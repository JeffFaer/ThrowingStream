package throwing.stream.union;

import javax.annotation.CheckReturnValue;

import throwing.RethrowChain;
import edu.umd.cs.findbugs.annotations.CleanupObligation;
import edu.umd.cs.findbugs.annotations.CreatesObligation;
import edu.umd.cs.findbugs.annotations.DischargesObligation;

@CleanupObligation
public class UnionThrowable extends Throwable {
    private static final long serialVersionUID = -9089451802483526560L;

    public UnionThrowable(Throwable cause) {
        super(cause);
    }

    @CreatesObligation
    public <X extends Throwable> UnionThrowable rethrow(Class<X> x) throws X {
        if (x.isInstance(getCause())) { throw x.cast(getCause()); }
        return this;
    }

    @DischargesObligation
    @CheckReturnValue
    public Error finish() {
        return RethrowChain.END_FUNCTION.apply(getCause());
    }
}
