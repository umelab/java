package umelab;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Locale;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PdfTextStreamTest {

    // instance for test
    @InjectMocks
    PdfTextStream textStream = null;

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
        PdfTextStream textStream = new PdfTextStream();

        assertThat(textStream, is(instanceOf(PdfTextStream.class)));
    }

    /**
    * Test BT label is inserted in PdfTextStream
    * @throws Exception
    */
    @Test
    public void Test_BT() throws Exception {
    String actual_val;
    String expected_val = "BT";
    PdfTextStream textStream = new PdfTextStream();

    //execute test
    textStream.beginText();
    
    ArrayDeque<String> q = textStream.getQueue();
    actual_val = q.poll();
    //check value
    assertThat(actual_val, equalTo(expected_val));
    }

    /**
    * Test ET label is inserted in PdfTextStream
    * @throws Exception
    */
    @Test
    public void Test_ET() throws Exception {
        String actual_val;
        String expected_val = "ET";
        PdfTextStream textStream = new PdfTextStream();

        //execute test
        textStream.endText();

        ArrayDeque<String> q = textStream.getQueue();
        actual_val = q.poll();

        //check value
        assertThat(actual_val, equalTo(expected_val));
    }

    @Test
    public void Test_SetText() throws Exception {
        String testStr = "This is a Test String";
        String actual_val;
        String expected_val = "(" + testStr + ") Tj";

        PdfTextStream textStream = new PdfTextStream();
        //execute test
        textStream.setText(testStr);

        ArrayDeque<String> q = textStream.getQueue();
        actual_val = q.poll();

        //check value
        assertThat(actual_val, equalTo(expected_val));
    }

    /**
     * Test setText with escape sequence string
     * @throws Exception
     */
    @Test
    public void Test_SetText_Escape() throws Exception {

    }

    /**
     * Test setText with Japanese string (UTF-8)
     * @throws Exception
     */
    @Test
    public void Test_SetText_UTF8() throws Exception {

    }

    /**
     * Test setText with Japanese string (Shift-JIS)
     * @throws Exception
     */
    @Test
    public void Test_SetText_SJIS() throws Exception {

    }

    /**
     * Test setFont 
     * @throws Exception
     */
    @Test
    public void Test_SetFont() throws Exception {
        String actual_val;
        String expected_val = "/F0 12. Tf";
        PdfFont font = new PdfFont();
        font.setSize(12);
        font.setRefLabel("F0");
        PdfTextStream textStream = new PdfTextStream();

        //execute test
        textStream.setFont(font);

        ArrayDeque<String> q = textStream.getQueue();
        actual_val = q.poll();

        //check value
        assertThat(actual_val, equalTo(expected_val));
    }

    /**
     * Test setTextPos
     * @throws Exception
     */
    @Test
    public void Test_SetTextPos() throws Exception {
        int testX = 10;
        int testY = 26;
        String actual_val;
        String expected_val ="10 -26 Td";
        PdfTextStream textStream = new PdfTextStream();

        //execute test
        textStream.setTextPos(testX, testY);

        ArrayDeque<String> q = textStream.getQueue();
        actual_val = q.poll();

        //check value
        assertThat(actual_val, equalTo(expected_val));
    }

    /**
     * test findBasePos
     * @throws Exception
     */
    @Test
    public void Test_FindBasePos() throws Exception {
        String actual_val;
        String expected_val = "1. 0. 0. 1. 0. 0. cm";
        PdfTextStream textStream = new PdfTextStream();

        Method method = PdfTextStream.class.getDeclaredMethod("findBasePosition");
        method.setAccessible(true);

        //execute test
        actual_val = (String) method.invoke(textStream);
        //check value
        assertThat(actual_val, equalTo(expected_val));
    }

    /**
     * Test findBasePos with speicified Page instance
     * @throws Exception
     */
    @Test
    public void Test_FindBasePos_Page() throws Exception {
        int testX = 612;
        int testY = 792;
        String actual_val;
        String expected_val = "1. 0. 0. 1. 0. 792. cm";
        PdfPage pdfPage = new PdfPage();
        pdfPage.setDimension(testX, testY);
        PdfDocument pdfDoc = new PdfDocument();

        PdfTextStream textStream = new PdfTextStream(pdfDoc, pdfPage);

        Method method = PdfTextStream.class.getDeclaredMethod("findBasePosition");
        method.setAccessible(true);

        //execute test
        actual_val = (String) method.invoke(textStream);
        //check value
        assertThat(actual_val, equalTo(expected_val));
    }

    @Test
    public void Test_doOutput() throws Exception {
        String actual_val;
        String expected_val = "<< \n"; 
        expected_val       += ">> \n";
        expected_val       += "stream \n";
        expected_val       += "1. 0. 0. 1. 0. 790. cm \n";
        expected_val       += "BT \n";
        expected_val       += "/F0 26. Tf \n";
        expected_val       += "0 -20 Td \n";
        expected_val       += "(Hello world!) Tj \n";
        expected_val       += "ET \n";
        expected_val       += "endstream \n";

        PdfDocument pdfDoc = new PdfDocument();
        PdfPage pdfPage = new PdfPage();
        pdfPage.setDimension(0, 790);
        PdfFont pdfFont = new PdfFont();
        pdfFont.setSize(26);
        pdfFont.setRefLabel("F0");

        PdfTextStream textStream = new PdfTextStream(pdfDoc, pdfPage);
        textStream.beginText();
        textStream.setFont(pdfFont);
        textStream.setTextPos(0, 20);
        textStream.setText("Hello world!");
        textStream.endText();

        //execute test
        actual_val = textStream.doOutput();
        System.out.println(actual_val);
        assertThat(actual_val, equalTo(expected_val));
    }

    @Test
    public void Test_Jap_UTF() throws Exception {
        String hex ="";
        String test = "アメンボは赤いよ楽しいな";//"I Live in N.Y.";//"aア!";//メンボ赤いよ楽しいな"; //"abcdef";
        byte[] byteUtf = test.getBytes("UTF-8");
        
        String strUtf = new String(byteUtf, "UTF-8");
        for(char c: strUtf.toCharArray()) {
            if (isAsciiChar(c)) {
                hex += convAsciiChar(c);
            } else {
                hex += Integer.toHexString(c);
            }
        }
        System.out.println("hex: " + hex);

        for (byte b : byteUtf) {
            //System.out.printf("%02x ", b);
            System.out.printf("%04x", b);
        }

        System.out.println("");
        char ch;
        for (int i = 0; i < test.length(); i++) {
            ch = test.charAt(i);
            checkAscii(ch);
            //System.out.print(ch);
        }
//        for (int i = 0; i < b.length; i++) {
//            System.out.print(b[i]);
//        }
    }

    private boolean isAsciiChar(int cha) {
        if (cha >= 0x20 && cha <= 0x7e) {
            return true;
        }
        return false;
    }

    private String convAsciiChar(int cha) {
        String zeropadAscii;
        String hex = String.format(Locale.ENGLISH, "%04X", cha);
        System.out.println("hex1: " + hex);
        return hex;
    }

    private void checkAscii(int cha) {
        int startIndex = 0x21;  //!
        int endIndex = 0x7E;    //~
        if (cha >= startIndex && cha <= endIndex) {
            System.out.printf("ascii %02x", cha);
        }
    }

    @Test
    public void Test_Jap_SJIS() throws Exception {
        String hex ="";
        //String test = "I Live in N.Y."; //"アメンボ";
        String test = "アメンボは赤いよ楽しいな!12345";
        byte[] byteUtf = test.getBytes("SJIS");
        
        String strUtf = new String(byteUtf, "SJIS");
        for(char c: strUtf.toCharArray()) {
            hex += Integer.toHexString(c);
        }
        System.out.println("hex: " + hex);
        StringBuilder builder = new StringBuilder();
 
        for (byte b : byteUtf) {
            String tmp = String.format(Locale.ENGLISH, "%02x", Byte.valueOf(b));
            builder.append(tmp);
        }

        System.out.println("result: " + builder.toString());
    }
}
