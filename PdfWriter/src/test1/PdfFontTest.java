package umelab;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PdfFontTest {

    // instance for test
    @InjectMocks
    PdfFont font = null;

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
        PdfFont font = new PdfFont();

        assertThat(font, is(instanceOf(PdfFont.class)));
    }

    /**
     * Test Reference ID is properly created.
     * @throws Exception
     */
    @Test
    public void Test_RefID() throws Exception {
        int actual_value = 0;
        int expected_value = 1;

        PdfFont font = new PdfFont();
        actual_value = font.getRefID();

        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Reference ID is properly incremented in the case of multiple instance is defined.
     */
    @Test
    public void Test_RefID_Multi() throws Exception {
        int actual_value = 0;
        int expected_value = 2;

        PdfFont font1 = new PdfFont();
        PdfFont font2 = new PdfFont();
        actual_value = font2.getRefID();

        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Referece String is properly created.
     */
    @Test
    public void Test_RefStr() throws Exception {
        String actual_value = "";
        String expected_value = "1 0 R";

        PdfFont font = new PdfFont();
        actual_value = font.getRefStr();
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Helvetica font
     */
    @Test
    public void Test_Helvetica() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Helvetica";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.HELVETICA, false, false);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));
    }

    /**
     * Test Helvetica Bold
     * @throws Exception
     */
    @Test
    public void Test_Helvetica_Bold() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Helvetica-Bold";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.HELVETICA, true, false);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));
    }

    /**
     * Test Helvetica Italic
     * @throws Exception
     */
    @Test
    public void Test_Helvetica_Italic() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Helvetica-Oblique";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.HELVETICA, false, true);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Helvetica Bold Italic
     * @throws Exception
     */
    @Test
    public void Test_Helvetica_BoldItalic() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Helvetica-BoldOblique";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.HELVETICA, true, true);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Times-Roman
     * @throws Exception
     */
    @Test
    public void Test_TimesRoman() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Times-Roman";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.TIMES_ROMAN, false, false);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Time-Roman Bold
     * @throws Exception
     */
    @Test
    public void Test_TimesRoman_Bold() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Times-Bold";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.TIMES_ROMAN, true, false);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));
    }

    /**
     * Test Times-Roman Italic
     * @throws Exception
     */
    @Test
    public void Test_TimesRoman_Italic() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Times-Italic";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.TIMES_ROMAN, false, true);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Times-Roman Bold Italic
     * @throws Exception
     */
    @Test
    public void Test_TimesRoman_BoldItalic() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Times-BoldItalic";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.TIMES_ROMAN, true, true);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Courier
     * @throws Exception
     */
    @Test
    public void Test_Courier() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Courier";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.COURIER, false, false);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Courier Italic
     * @throws Exception
     */
    @Test
    public void Test_Courier_Italic() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Courier-Oblique";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.COURIER, false, true);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Courier Bold
     * @throws Exception
     */
    @Test
    public void Test_Courier_Bold() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Courier-Bold";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.COURIER, true, false);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));

        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));

    }

    /**
     * Test Courier Bold Italic
     * @throws Exception
     */
    @Test
    public void Test_Courier_BoldItalic() throws Exception {
        String actual_value = "";
        String actual_type = "";
        String expected_value = "/Courier-BoldOblique";
        String expected_type = "/Type1";

        PdfFont font = new PdfFont(PdfFont.COURIER, true, true);
        actual_value = font.getEntry().get(PdfConstant.PDF_BASEFONT);

        assertThat(actual_value, equalTo(expected_value));
        
        actual_type = font.getEntry().get(PdfConstant.PDF_SUBTYPE);
        assertThat(actual_type, equalTo(expected_type));
    }

    /**
     * Test Indirected Obj label
     */
    @Test
    public void Test_IndirectObj() throws Exception {
        String actual_value = "";
        String expected_value = "/F1";

        PdfFont font = new PdfFont(PdfFont.HELVETICA, false, false);
        actual_value = font.getIndirectFont();

        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test output : Font dictionary is properly expressed 
     */
    @Test
    public void Test_Output_Font() throws Exception {
        String actual_value = "";
        int actual_size = 0;
        String expected_value = "/F1 \n<< \n/Type /Font \n/BaseFont /Helvetica-Bold \n/SubType /Type1 \n";
        expected_value += ">> \n";
        int expected_size = expected_value.length();

        PdfFont font = new PdfFont(PdfFont.HELVETICA, true, false);

        actual_value = new String(font.dumpInfo());

        assertThat(actual_value, equalTo(expected_value));

        actual_size = font.getObjSize();
        assertThat(actual_size, equalTo(expected_size));
    }

}
