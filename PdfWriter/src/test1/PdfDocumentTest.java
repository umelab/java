package umelab;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Locale;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PdfDocumentTest {

    // instance for test
    @InjectMocks
    PdfDocument doc = null;

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

        assertThat(doc, is(instanceOf(PdfDocument.class)));
    }

    /**
     * Test entry tag which is:
     * /Type and /Pages
     * @throws Exception
     */
    @Test
    public void Test_Entry() throws Exception {
        String[] expected_value = {"/Type", "/Pages"};

        PdfDocument doc = new PdfDocument();
        //execute test
        HashMap<String, String> entry = doc.getEntry();
        //confirm
        assertThat(entry.keySet(), hasItems(expected_value));
    }
}