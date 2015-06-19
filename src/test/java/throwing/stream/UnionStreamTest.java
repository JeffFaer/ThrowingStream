package throwing.stream;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.CheckReturnValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import throwing.function.ThrowingFunction;
import throwing.stream.union.UnionStream;
import throwing.stream.union.UnionThrowable;

@RunWith(MockitoJUnitRunner.class)
public class UnionStreamTest {
    public static class SpecificThrowable extends UnionThrowable {
        private static final long serialVersionUID = 8120372376407370693L;

        public SpecificThrowable(Throwable cause) {
            super(cause);
        }

        @CheckReturnValue
        public Error rethrow() throws InterruptedException, IOException {
            return rethrow(IOException.class).rethrow(InterruptedException.class).finish();
        }
    }

    public interface AnnoyingObject {
        public String multipleThrows(String s) throws IOException, InterruptedException;
    }

    public enum AnnoyingObjects implements AnnoyingObject {
        GOOD {
            @Override
            public String multipleThrows(String s) throws IOException, InterruptedException {
                return s;
            }
        },
        BAD {
            @Override
            public String multipleThrows(String s) throws IOException, InterruptedException {
                throw new IOException("uh oh");
            }
        };

    }

    private Stream<String> strings;

    @Before
    public void setup() {
        strings = Stream.of("1", "2", "3");
    }

    @Test
    public void testPlain() throws UnionThrowable {
            List<String> l = collectStrings(strings, UnionThrowable::new,
                    AnnoyingObjects.GOOD::multipleThrows);
            assertThat(l, contains("1", "2", "3"));
    }

    private <X extends UnionThrowable> List<String> collectStrings(Stream<String> stream,
            Function<Throwable, X> ctor, ThrowingFunction<String, String, Throwable> mapper)
            throws X {
        return UnionStream.of(stream, ctor).map(mapper).collect(Collectors.<String> toList());
    }

    @Test
    public void testCustom() throws InterruptedException, IOException {
        List<String> l = collectStrings(strings, AnnoyingObjects.GOOD::multipleThrows);
        assertThat(l, contains("1", "2", "3"));
    }

    private List<String> collectStrings(Stream<String> stream,
            ThrowingFunction<String, String, Throwable> mapper) throws InterruptedException,
            IOException {
        try {
            return collectStrings(stream, SpecificThrowable::new, mapper);
        } catch (SpecificThrowable e) {
            throw e.rethrow();
        }
    }

    @Test
    public void testException() {
        try {
            collectStrings(strings, AnnoyingObjects.BAD::multipleThrows);
            fail("expecting exception");
        } catch (InterruptedException e) {
            fail("expecting other exception");
        } catch (IOException e) {
            // expected
        }
    }
}
