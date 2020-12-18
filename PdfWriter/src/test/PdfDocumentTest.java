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

    @Test
    public void Test_Pages() throws Exception {
        PdfDocument doc = new PdfDocument();
        PdfPages pages = doc.getPages();

        assertThat(pages, is(instanceOf(PdfPages.class)));
    }

    @Test
    public void Test_ParseObj() throws Exception {
        PdfDocument doc = new PdfDocument();
        PdfPage page1 = new PdfPage();
        PdfPage page2 = new PdfPage();
        doc.addPage(page1);
        doc.addPage(page2);
        ArrayList<PdfObject> list = new ArrayList<>();
        doc.parseObj(list);
        int count = list.size();
        for (int i = 0; i < list.size(); i++) {
            PdfObject obj = (PdfObject) list.get(i);
            System.out.println(obj.toString());
        }
    }
}
