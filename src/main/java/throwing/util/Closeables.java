package throwing.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import throwing.ThrowingRunnable;
import throwing.bridge.ThrowingBridge;
import throwing.function.ThrowingBiConsumer;
import throwing.function.ThrowingBinaryOperator;
import throwing.function.ThrowingFunction;
import throwing.function.ThrowingSupplier;
import throwing.stream.ThrowingCollector;
import throwing.stream.ThrowingStream;

public final class Closeables {
    private static class CloseableChainState<R, X extends Throwable> {
        private final List<R> results;
        private X e;
        
        public CloseableChainState(List<R> results, X e) {
            this.results = results;
            this.e = e;
        }
    }
    
    private Closeables() {}
    
    public static <X extends Throwable> ThrowingCollector<ThrowingRunnable<? extends X>, ?, ?, X> throwFirst(Class<X> x) {
        ThrowingCollector<ThrowingSupplier<Void, ? extends X>, CloseableChainState<Void, X>, List<? extends Void>, X> coll = getThrowOrReturn(x);
        
        return new ThrowingCollector<ThrowingRunnable<? extends X>, CloseableChainState<Void, X>, List<? extends Void>, X>() {
            @Override
            public ThrowingSupplier<CloseableChainState<Void, X>, X> supplier() {
                return coll.supplier();
            }
            
            @Override
            public ThrowingBiConsumer<CloseableChainState<Void, X>, ThrowingRunnable<? extends X>, X> accumulator() {
                return (r, s) -> {
                    coll.accumulator().accept(r, () -> {
                        s.run();
                        return null;
                    });
                };
            }
            
            @Override
            public ThrowingBinaryOperator<CloseableChainState<Void, X>, X> combiner() {
                return coll.combiner();
            }
            
            @Override
            public ThrowingFunction<CloseableChainState<Void, X>, List<? extends Void>, X> finisher() {
                return coll.finisher();
            }
            
            @Override
            public Set<Characteristics> characteristics() {
                return coll.characteristics();
            }
        };
    }
    
    public static <R, X extends Throwable> ThrowingCollector<ThrowingSupplier<R, ? extends X>, ?, List<? extends R>, X> throwFirstOrReturn(
            Class<X> x) {
        return getThrowOrReturn(x);
    }
    
    private static <R, X extends Throwable> ThrowingCollector<ThrowingSupplier<R, ? extends X>, CloseableChainState<R, X>, List<? extends R>, X> getThrowOrReturn(
            Class<X> x) {
        return new ThrowingCollector<ThrowingSupplier<R, ? extends X>, CloseableChainState<R, X>, List<? extends R>, X>() {
            @Override
            public ThrowingSupplier<CloseableChainState<R, X>, X> supplier() {
                return () -> new CloseableChainState<>(new ArrayList<>(), null);
            }
            
            @Override
            public ThrowingBiConsumer<CloseableChainState<R, X>, ThrowingSupplier<R, ? extends X>, X> accumulator() {
                return (r, s) -> {
                    try {
                        R result = s.get();
                        r.results.add(result);
                    } catch (Throwable e) {
                        X ex = ThrowingBridge.launder(e, x);
                        if (r.e == null) {
                            r.e = ex;
                        } else {
                            r.e.addSuppressed(ex);
                        }
                    }
                };
            }
            
            @Override
            public ThrowingBinaryOperator<CloseableChainState<R, X>, X> combiner() {
                return (r1, r2) -> {
                    r1.results.addAll(r2.results);
                    if (r1.e == null) {
                        r1.e = r2.e;
                    } else {
                        r1.e.addSuppressed(r2.e);
                    }
                    
                    return r1;
                };
            }
            
            @Override
            public ThrowingFunction<CloseableChainState<R, X>, List<? extends R>, X> finisher() {
                return r -> {
                    if (r.e != null) {
                        throw r.e;
                    } else {
                        return r.results;
                    }
                };
            }
            
            @Override
            public Set<Characteristics> characteristics() {
                return Collections.EMPTY_SET;
            }
        };
    }
    
    public static void closeAll(Closeable... cs) throws IOException {
        closeAll(Arrays.stream(cs));
    }
    
    /**
     * Close every {@code Closeable} in a manner similar to a
     * try-with-resources.
     * 
     * @param itr
     *            The {@code Closeable}s to close
     * @throws IOException
     *             If an {@code IOException} is thrown from one of the
     *             {@code Closeable}s
     */
    public static void closeAll(Iterable<? extends Closeable> itr) throws IOException {
        closeAll(StreamSupport.stream(itr.spliterator(), false));
    }
    
    public static void closeAll(Stream<? extends Closeable> s) throws IOException {
        ThrowingFunction<Closeable, ThrowingRunnable<IOException>, IOException> mapper = c -> c::close;
        ThrowingStream.of(s, IOException.class).map(mapper).collect(throwFirst(IOException.class));
    }
}
