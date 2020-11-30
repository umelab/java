package umelab;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;


/**
 * Test case of the CSVWriterTest class
 *
 * @author e.umeda
 */
public class CSVWriterTest {

    // tested object
    @InjectMocks
    CSVWriter writer = null;
    private String expected_str;

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
    public void Instanciate_Test() throws IOException {
        String path = "c:\\tools\\test.csv";
        writer = new CSVWriter(path);

        assertThat(writer, is(instanceOf(CSVWriter.class)));
    }

    /**
     * Test IOException handling in case of an invalid file path
     */
    @Test
    public void Instanciate_IOException_Test() {
        String invalid_test_Path = "c:\\bar\\foo\\write_test.csv";
        try {
            writer = new CSVWriter(invalid_test_Path);
        } catch (Exception e) {
            //test the instance of Exception
            assertThat(e, is(instanceOf(IOException.class)));
            return;
        }

        Assert.fail("IOException never throws... ");
    }

    /**
     * Test for Exception handling in case of IOException at writeRow method
     */
    @Test
    public void writerow_IOException_Test() {
        String testPath = "c:\\tools\\write_test.csv";
        OutputStreamWriter osw = mock(OutputStreamWriter.class);
        String rowdata = "foo, bar, this";

        try {
            doThrow(new IOException("WriteError")).when(osw).write(Matchers.anyString());
        
            writer = new CSVWriter(testPath);
            //inject OutputStreamWriter to the CSVWriter
            writer.setOutputStreamWriter(osw);

            writer.writeRow(rowdata);
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IOException.class)));
            return;
        }
        
        Assert.fail("IOException never throws... ");
    }

    /**
     * Test for Exception handling in case of IOException with writeAllData method
     */
    @Test
    public void writeall_IOException_Test() {
        String testPath = "c:\\tools\\write_test.csv";
        OutputStreamWriter osw = mock(OutputStreamWriter.class);
        String[] rowdata = {"foo, bar, this", "hoge, do, test"};

        try {
            doThrow(new IOException("WriteError")).when(osw).write(Matchers.anyString());
        
            writer = new CSVWriter(testPath);
            //inject OutputStreamWriter to the CSVWriter
            writer.setOutputStreamWriter(osw);

            writer.writeAllData(rowdata);
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IOException.class)));
            return;
        }
        
        Assert.fail("IOException never throws... ");
    }

    /**
     * Test for Exception Handling in case of IOException in close method
     */
    @Test
    public void close_IOException_Test() {
        String testPath = "c:\\tools\\write_test.csv";
        OutputStreamWriter osw = mock(OutputStreamWriter.class);

        try {
            doThrow(new IOException("WriteError")).when(osw).close();
        
            writer = new CSVWriter(testPath);
            //inject OutputStreamWriter to the CSVWriter
            writer.setOutputStreamWriter(osw);

            writer.close();
        } catch (Exception e) {
            assertThat(e, is(instanceOf(IOException.class)));
            return;
        }
        
        Assert.fail("IOException never throws... ");
    }

    /**
     * Test for replaceQuote method
     * @throws Exception
     */
    @Test
    public void replaceQuote_Test() throws Exception {
        String actual_str;
        String test_str_normal = "foo";
        String test_str_quote  = "\"foo\"";
        String test_str_doublequote = "fo\"o";
        String test_str_conquote = "\"fo\"\"o\"";

        String path ="c:\\tools\\replaceQuote.csv";
        writer = new CSVWriter(path);

        Method method = CSVWriter.class.getDeclaredMethod("replaceQuote", String.class);
        method.setAccessible(true);

        //execute test for normal string
        actual_str = (String) method.invoke(writer, test_str_normal);
        expected_str = test_str_normal;
        assertThat(actual_str, equalTo(expected_str));

        //execute test for quoted string
        actual_str = (String) method.invoke(writer, test_str_quote);
        expected_str = test_str_quote;
        assertThat(actual_str, equalTo(expected_str));

        //execute test for string containing double quotation
        actual_str = (String) method.invoke(writer, test_str_doublequote);
        expected_str = test_str_conquote;
        assertThat(actual_str, equalTo(expected_str));
    }

    /**
     * test for rebuild row data with double quotated operation
     * @throws Exception
     */
    @Test
    public void buildModRowData_Test() throws Exception {
        String actual_str;
        String expected_str;
        String test_str_normal = "test,bar,foo";
        String test_str_doublequote ="\"test\",bar,foo";
        String test_str_quote = "test,bb\"ar,foo";

        String path ="c:\\tools\\buildModRow.csv";
        writer = new CSVWriter(path);

        Method method = CSVWriter.class.getDeclaredMethod("buildModRowData", String.class);
        method.setAccessible(true);

        //execute test for normal string
        actual_str = (String) method.invoke(writer, test_str_normal);
        expected_str = test_str_normal;
        assertThat(actual_str, equalTo(expected_str));  
        
        //execute test for quoted string
        actual_str = (String) method.invoke(writer, test_str_quote);
        expected_str = "test,\"bb\"\"ar\",foo";
        assertThat(actual_str, equalTo(expected_str));
        
        //execute test for quoted string
        actual_str = (String) method.invoke(writer, test_str_doublequote);
        expected_str = "\"test\",bar,foo";
        assertThat(actual_str, equalTo(expected_str));  
    }

    /**
     * Test if string can convert utf-8 to shift jis code.
     * @throws Exception
     */
    @Test
    public void convertStr_Test() throws Exception {
        String path ="c:\\tools\\convertStr.csv";
        String decode = "SJIS";

        String test_str = "山田進";
        String actual_str;
        byte[] expected_byte = test_str.getBytes(decode);
        byte[] actual_byte = null;
        writer = new CSVWriter(path, decode);

        Method method = CSVWriter.class.getDeclaredMethod("convertStr", String.class);
        method.setAccessible(true);

        //execute test
        actual_str = (String) method.invoke(writer, test_str);
        actual_byte = actual_str.getBytes(decode);

        assertArrayEquals(expected_byte, actual_byte);
    }

 }
