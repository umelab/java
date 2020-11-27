package test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.OutputStreamWriter;

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

    // InjectMock
    @InjectMocks
    CSVWriter writer = null;

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

}
