package throwing.bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

abstract class CheckedBridge<D, X extends Throwable> extends AbstractBridge<D, X> {
    private static final String PACKAGE = CheckedBridge.class.getPackage().getName();
    // it might be useful to have a way to turn this off
    public static volatile boolean FILTER_STACK = true;

    private final FunctionBridge<X> bridge;
    private final RethrowChain<BridgeException, X> chain;
    private final Function<BridgeException, X> launder;

    CheckedBridge(D delegate, FunctionBridge<X> bridge) {
        this(delegate, bridge, RethrowChain.start());
    }

    CheckedBridge(D delegate, FunctionBridge<X> bridge, RethrowChain<BridgeException, X> chain) {
        super(delegate, bridge.getExceptionClass());
        this.bridge = bridge;
        this.chain = chain.chain(t -> {
            Throwable cause = t.getCause();
            if (getExceptionClass().isInstance(cause)) {
                // filter out bridge lines from the exception. They can get
                // rather verbose.
                if (FILTER_STACK) {
                    filterStackTrace(cause);
                }

                return Optional.of(getExceptionClass().cast(cause));
            }

            return Optional.empty();
        });
        launder = this.chain.finish();
    }

    private void filterStackTrace(Throwable cause) {
        List<StackTraceElement> ste = Arrays.asList(cause.getStackTrace());

        IntSummaryStatistics stats = IntStream.range(0, ste.size()).filter(
                i -> ste.get(i).getClassName().startsWith(PACKAGE)).summaryStatistics();
        if (stats.getCount() > 0) {
            List<StackTraceElement> filtered = new ArrayList<>(ste.size() - (stats.getMax() - stats.getMin()));
            for (int x = 0; x < stats.getMin(); x++) {
                filtered.add(ste.get(x));
            }
            for (int x = stats.getMax() + 1; x < ste.size(); x++) {
                filtered.add(ste.get(x));
            }
            cause.setStackTrace(filtered.toArray(new StackTraceElement[filtered.size()]));
        }
    }

    public FunctionBridge<X> getBridge() {
        return bridge;
    }

    public RethrowChain<BridgeException, X> getChain() {
        return chain;
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
        } catch (BridgeException e) {
            X x = launder.apply(e);
            // it's possible that we've mapped from Y -> X with a new X(Y) call.
            // this would result in a brand new stack trace free of filtering
            filterStackTrace(x);
            throw x;
        }
    }
}
