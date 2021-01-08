package umelab;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.IOException;
import java.util.ArrayDeque;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PdfTextStreamTest {

    // instance for test
    @InjectMocks
    PdfTextStream stream = null;

    /**
    * @throws java.lang.Exception
    */
    @Before
    public void setUp() throws Exception {
    }

    /**
    * @throws java.lang.Exception
    */
    @After
    public void tearDown() throws Exception {
    }

    /**
    * Check if instanciate the object properly
    * @throws IOException
    */
    @Test
    public void Test_Instanciate() throws Exception {
        PdfDocument doc = new PdfDocument();
        PdfPage page = new PdfPage();
        PdfTextStream stream = new PdfTextStream(doc, page);

        assertThat(stream, is(instanceOf(PdfTextStream.class)));
    }

    /**
     * Test Reference ID is properly created.
     * @throws Exception
     */
    @Test
    public void Test_RefID() throws Exception {
        int actual_value = 0;
        int expected_value = 1;

        PdfTextStream stream = mock(PdfTextStream.class);

        //setup mock data
        doReturn(expected_value).when(stream).getRefID();
        //execute test
        actual_value = stream.getRefID();

        //confirm
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Referece String is properly created.
     */
    @Test
    public void Test_RefStr() throws Exception {
        String actual_value = "";
        String expected_value = "5 0 R";

        PdfTextStream stream = mock(PdfTextStream.class);

        // setup mock data
        doReturn(expected_value).when(stream).getRefStr();
        //execute test
        actual_value = stream.getRefStr();

        //confirm
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test queue size
     */
    @Test
    public void Test_StackSize() throws Exception {
        int actual_value = 0;
        int expected_value = 3;

        PdfTextStream stream = mock(PdfTextStream.class);

        //setup mock data
        doReturn(expected_value).when(stream).getQueueSize();
        //execute test
        actual_value = stream.getQueueSize();

        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test queue context when beginText method is called.
     */
    @Test
    public void Test_Queue_beginText() throws Exception {
        String actual_value = "";
        String expected_value = "BT \n";

        PdfTextStream stream = spy(new PdfTextStream(new PdfDocument(), new PdfPage()));
        //setup data
        stream.beginText();

        //execute test
        ArrayDeque<String> queue = stream.getQueue();
        actual_value = queue.poll();

        //confirm
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test queue context when beginText method is called.
     */
    @Test
    public void Test_Queue_endText() throws Exception {
        String actual_value = "";
        String expected_value = "ET \n";

        PdfTextStream stream = spy(new PdfTextStream(new PdfDocument(), new PdfPage()));
        //setup data
        stream.endText();

        //execute test
        ArrayDeque<String> queue = stream.getQueue();
        actual_value = queue.poll();

        //confirm
        assertThat(actual_value, equalTo(expected_value));
    }

   /**
     * Test queue context when setFont method is called.
     */
    @Test
    public void Test_Queue_setFont() throws Exception {
        String actual_value = "";
        String expected_value = "/F1 14 Tf \n";

        PdfFont font = mock(PdfFont.class);
        PdfTextStream stream = spy(new PdfTextStream(new PdfDocument(), new PdfPage()));

        //setup mock data
        doReturn("/F1").when(font).getIndirectFont();
        //setup data
        stream.setFont(font, 14);

        //execute test
        ArrayDeque<String> queue = stream.getQueue();
        actual_value = queue.poll();

        //confirm
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test queue context when setTextPosition method is called.
     */
    @Test
    public void Test_Queue_setTextPosition() throws Exception {
        String actual_value = "";
        String expected_value = "123 456 Td \n";

        PdfTextStream stream = spy(new PdfTextStream(new PdfDocument(), new PdfPage()));

        //setup data
        stream.setTextPosition(123, 456);

        //execute test
        ArrayDeque<String> queue = stream.getQueue();
        actual_value = queue.poll();

        //confirm
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test queue context when setText method is called.
     */
    @Test
    public void Test_Queue_setText() throws Exception {
        String actual_value = "";
        String expected_value = "(Hello World) Tj \n";
        String tested_value = "Hello World";

        PdfTextStream stream = spy(new PdfTextStream(new PdfDocument(), new PdfPage()));

        //setup data
        stream.setText(tested_value);

        //execute test
        ArrayDeque<String> queue = stream.getQueue();
        actual_value = queue.poll();

        //confirm
        assertThat(actual_value, equalTo(expected_value));
    }

    
}

