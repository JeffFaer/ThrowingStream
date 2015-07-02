package throwing.stream.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import throwing.RethrowChain;

abstract class CheckedAdapter<D, X extends Throwable> extends ThrowingAbstractAdapter<D, X> {
    private static final String PACKAGE = CheckedAdapter.class.getPackage().getName();
    // it might be useful to have a way to turn this off
    public static volatile boolean FILTER_STACK = true;

    private final FunctionAdapter<X> functionAdapter;
    private final RethrowChain<AdapterException, X> chain;
    private final Function<AdapterException, X> unmasker;

    CheckedAdapter(D delegate, FunctionAdapter<X> functionAdapter) {
        this(delegate, functionAdapter, RethrowChain.start());
    }

    CheckedAdapter(D delegate, FunctionAdapter<X> functionAdapter,
            RethrowChain<AdapterException, X> chain) {
        super(delegate, functionAdapter.getExceptionClass());
        this.functionAdapter = functionAdapter;

        RethrowChain<Throwable, X> link = RethrowChain.rethrowAs(getExceptionClass());
        this.chain = chain.chain(t -> {
            Throwable cause = t.getCause();
            return link.apply(cause);
        });
        unmasker = this.chain.finish();
    }

    private void filterStackTrace(Throwable cause) {
        List<StackTraceElement> ste = Arrays.asList(cause.getStackTrace());

        IntSummaryStatistics stats = IntStream.range(0, ste.size())
                .filter(i -> ste.get(i).getClassName().startsWith(PACKAGE))
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
    }

    public FunctionAdapter<X> getFunctionAdapter() {
        return functionAdapter;
    }

    public RethrowChain<AdapterException, X> getChain() {
        return chain;
    }

    protected void unmaskException(Runnable runnable) throws X {
        unmaskException(() -> {
            runnable.run();
            return null;
        });
    }

    protected <R> R unmaskException(Supplier<R> supplier) throws X {
        try {
            return supplier.get();
        } catch (AdapterException e) {
            X x = unmasker.apply(e);
            // filter out adapter lines from the exception. They can get
            // rather verbose.
            if (FILTER_STACK) {
                filterStackTrace(x);
            }
            throw x;
        }
    }
}
