package throwing.bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

abstract class CheckedBridge<D, X extends Throwable> extends AbstractBridge<D> {
    private final FunctionBridge<X> bridge;
    
    CheckedBridge(D delegate, FunctionBridge<X> bridge) {
        super(delegate);
        this.bridge = bridge;
    }
    
    protected FunctionBridge<X> getBridge() {
        return bridge;
    }
    
    protected void filterBridgeException(Runnable runnable) throws X {
        filterBridgeException(() -> {
            runnable.run();
            return null;
        });
    }
    
    protected <R> R filterBridgeException(Supplier<R> supplier) throws X {
        try {
            return supplier.get();
        } catch (Throwable t) {
            if (t instanceof BridgeException) {
                Throwable cause = t.getCause();
                if (getBridge().getExceptionClass().isInstance(cause)) {
                    // filter out bridge lines from the exception. They can get
                    // rather verbose.
                    List<StackTraceElement> ste = Arrays.asList(cause.getStackTrace());
                    IntSummaryStatistics stats = IntStream.range(0, ste.size())
                            .filter(i -> ste.get(i).getClassName().startsWith("throwing.bridge"))
                            .summaryStatistics();
                    if (stats.getCount() > 0) {
                        List<StackTraceElement> filtered = new ArrayList<>(ste.size()
                                - (stats.getMax() - stats.getMin()));
                        for (int x = 0; x < stats.getMin(); x++) {
                            filtered.add(ste.get(x));
                        }
                        for (int x = stats.getMax() + 1; x < ste.size(); x++) {
                            filtered.add(ste.get(x));
                        }
                        cause.setStackTrace(filtered.toArray(new StackTraceElement[filtered.size()]));
                    }
                    
                    throw getBridge().getExceptionClass().cast(cause);
                } else {
                    throw t;
                }
            } else if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else if (t instanceof Error) {
                throw (Error) t;
            } else {
                throw new AssertionError(t);
            }
        }
    }
}
