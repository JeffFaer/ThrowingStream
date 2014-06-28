package throwing.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.Closeable;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CloseablesTest {
    @Mock public Closeable c1;
    @Mock public Closeable c2;
    
    public IOException e1;
    public IOException e2;
    
    @Before
    public void before() throws IOException {
        e1 = new IOException();
        e2 = new IOException();
        doThrow(e1).when(c1).close();
        doThrow(e2).when(c2).close();
    }
    
    @Test
    public void CloseablesCallsEveryCloseable() throws IOException {
        try {
            Closeables.closeAll(c1, c2);
            fail();
        } catch (IOException e) {}
        
        verify(c1).close();
        verify(c2).close();
    }
    
    @Test
    public void CloseablesExceptionHasSuppressed() {
        try {
            Closeables.closeAll(c1, c2);
            fail();
        } catch (IOException e) {
            assertSame(e1, e);
            assertEquals(1, e.getSuppressed().length);
            assertSame(e2, e.getSuppressed()[0]);
        }
    }
    
    @Test
    public void ClosingNothingDoesNothing() throws IOException {
        Closeables.closeAll();
    }
}
