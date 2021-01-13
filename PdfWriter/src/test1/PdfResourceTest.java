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

public class PdfResourceTest {

    // instance for test
    @InjectMocks
    PdfResource resrc = null;

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
        PdfResource resource = new PdfResource();

        assertThat(resource, is(instanceOf(PdfResource.class)));
    }

    /**
     * Test Reference ID is properly created.
     * @throws Exception
     */
    @Test
    public void Test_RefID() throws Exception {
        int actual_value = 0;
        int expected_value = 1;

        PdfResource resource = new PdfResource();
        actual_value = resource.getRefID();

        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Reference ID is properly incremented in the case of multiple instance is defined.
     */
    @Test
    public void Test_RefID_Multi() throws Exception {
        int actual_value = 0;
        int expected_value = 2;

        PdfResource resource1 = new PdfResource();
        PdfResource resource2 = new PdfResource();
        actual_value = resource2.getRefID();

        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Referece String is properly created.
     */
    @Test
    public void Test_RefStr() throws Exception {
        String actual_value = "";
        String expected_value = "1 0 R";

        PdfResource resource = new PdfResource();
        actual_value = resource.getRefStr();
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Registed font list size
     */
    @Test
    public void Test_FontList_Size() throws Exception {
        int actual_value = 0;
        int expected_value = 2;

        PdfResource resrc = new PdfResource();
        resrc.addFont(new PdfFont(PdfFont.HELVETICA, false, false));
        resrc.addFont(new PdfFont(PdfFont.COURIER, true, true));

        actual_value = resrc.getFontList().size();

        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Registed font list instances
     */
    @Test
    public void Test_FontList_Instances() throws Exception {
        PdfFont actual_font = null;
        PdfFont[] expected_font = new PdfFont[2];

        PdfResource resrc = new PdfResource();
        resrc.addFont(expected_font[0] = new PdfFont(PdfFont.HELVETICA, false, false));
        resrc.addFont(expected_font[1] = new PdfFont(PdfFont.COURIER, true, true));

        ArrayList<PdfFont> list = resrc.getFontList();
        for (int i = 0; i < list.size(); i++) {
            actual_font = (PdfFont) list.get(i);
            assertThat(actual_font, equalTo(expected_font[i]));
        }
    }

    /**
     * Test output procset
     */
    @Test
    public void Test_Ouput_Procset() throws Exception {
        String actual_value = "";
        String expected_value = "1 0 obj \n<< \n/ProcSet [ /PDF /Text /ImageB /ImageC /ImageI ] \n>> \n";

        PdfResource resrc = new PdfResource();
        actual_value = new String(resrc.dumpInfo());
 
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test output procset and font
     */
    @Test
    public void Test_Output_Font() throws Exception {
        String actual_value = "";
        String expected_value = "1 0 obj \n<< \n/ProcSet [ /PDF /Text /ImageB /ImageC /ImageI ] \n";
        expected_value += "/Font \n<< \n/F2 \n<< \n/Type /Font \n/BaseFont /Helvetica \n/SubType /Type1 \n";
        expected_value += ">> \n>> \n>> \n";

        PdfResource resrc = new PdfResource();
        resrc.addFont(new PdfFont(PdfFont.HELVETICA, false, false));
        actual_value = new String(resrc.dumpInfo());
 
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test entry keys are properly defined.
     * @throws Exception
     */
    @Test
    public void Test_Entry() throws Exception {
        String[] expected_value = {"/ProcSet", "/Font"};

        PdfResource resrc = new PdfResource();
        //execute test
        HashMap<String, String> entry = resrc.getEntry();
        //confirm
        assertThat(entry.keySet(), hasItems(expected_value));        
    }

}
