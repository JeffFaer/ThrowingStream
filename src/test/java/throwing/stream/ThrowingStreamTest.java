package throwing.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import throwing.Nothing;
import throwing.bridge.ThrowingBridge;
import throwing.function.ThrowingIntPredicate;

public class ThrowingStreamTest {
    public IntStream numbers = IntStream.range(0, 20);
    
    @Test
    public void worksCorrectlyWithExceptions() {
        ThrowingIntStream<Exception> s = ThrowingBridge.of(numbers, Exception.class);
        
        List<Integer> collected = new ArrayList<>();
        Exception e = new Exception();
        ThrowingIntPredicate<Exception> p = i -> {
            if (i >= 10) {
                throw e;
            } else {
                return true;
            }
        };
        try {
            s.filter(p).forEach(collected::add);
            fail();
        } catch (Exception e2) {
            assertSame(e, e2);
        }
        
        assertEquals(10, collected.size());
    }
    
    @Test
    public void worksCorrectlyWithoutExceptions() {
        ThrowingIntStream<Nothing> s = ThrowingBridge.of(numbers);
        long l = s.filter(i -> i < 10).count();
        assertEquals(10, l);
    }
    
    @Test
    public void flatMapExceptionsAreHandledCorrectly() {
        ThrowingIntStream<Exception> s = ThrowingBridge.of(numbers, Exception.class);
        
        List<Integer> collected = new ArrayList<>();
        Exception e = new Exception();
        try {
            s.flatMap(i -> {
                if (i >= 10) {
                    throw e;
                }
                return ThrowingBridge.of(IntStream.of(i), Exception.class);
            }).forEach(collected::add);
            fail();
        } catch (Exception e2) {
            assertSame(e, e2);
        }
        
        assertEquals(10, collected.size());
    }
}
