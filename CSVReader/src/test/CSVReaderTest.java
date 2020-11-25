package test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;


/**
 * Test case of the CSVReader class
 *
 * @author e.umeda
 */
public class CSVReaderTest {

    // テストを実施するクラス
    @InjectMocks
    CSVReader reader = null;

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
        reader = new CSVReader(path);

        assertThat(reader, is(instanceOf(CSVReader.class)));
    }

    /**
     * Test IOException handling in case of an invalid file path
     */
    @Test
    public void Instanciate_IOException_Test() {
        String testPath = "c:\\bar\\foo\\test.csv";
        try {
            reader = new CSVReader(testPath);
        } catch (IOException e) {
            return;
        }

        Assert.fail("IOException never throws... ");
    }

    /**
     * Test findSeparater method.
     * @throws Exception
     */
    @Test
    public void Check_Separater() throws Exception {
        String path = "c:\\tools\\test.csv";
        reader = new CSVReader(path);
        char inputSep = ',';
        char failSep = '.';

        Method method = CSVReader.class.getDeclaredMethod("findSeparater", char.class);
        method.setAccessible(true);

        //execute test for true
        boolean isSep = (boolean) method.invoke(reader, inputSep);
        assertThat(isSep, is(true));

        //execute test for false
        boolean fSep = (boolean) method.invoke(reader, failSep);
        assertThat(fSep, is(false));
    }

    /**
     * Test findLineFeed method for DEFAULT LineFeed
     */
    @Test
    public void Check_LF() throws Exception {
        String path = "c:\\tools\\test.csv";
        reader = new CSVReader(path);

        char inLF_R = '\r';
        char inLF_N = '\n';
        char failLF = '\t';

        Method method = CSVReader.class.getDeclaredMethod("findLineFeed", char.class);
        method.setAccessible(true);

        //execute test for true
        boolean isLF_1 = (boolean) method.invoke(reader, inLF_R);
        assertThat(isLF_1, is(true));

        //execute test for true
        boolean isLF_2 = (boolean) method.invoke(reader, inLF_N);
        assertThat(isLF_2, is(true));

        //execute test for false
        boolean isnLF = (boolean) method.invoke(reader, failLF);
        assertThat(isnLF, is(false));

    }

    /**
     * test nextToken method
     * @throws Exception
     */
    @Test
    public void Check_ReadNext() throws Exception {
        String path = "c:\\tools\\test.csv";
        reader = new CSVReader(path);
        String actual_token;
        int cnt = 0;
        String expected_token[] = {"date", "time", "firstname", "lastname",
                                    "20201122", "12:23:45", "eizo", "umeda"};
        //while((actual_token = reader.nextToken()) != null) {
        //テスト実施は先頭２行のみ
        for (int i = 0; i < expected_token.length; i++) {
            actual_token = reader.nextToken();
            System.out.println("token: " + actual_token + " expected: " + expected_token[cnt]);
            assertThat(expected_token[cnt], equalTo(actual_token));
            cnt++;
        }
    }

    /**
     * test for empty string in csv data
     * ex: 
     * 0) ,12:34:56,string1,string2
     * 1) 20201122,,string1,string2
     * 2) 20201122,12:34:56,,string2
     * 3) 20201122,12:34:56,string1, 
     * 
     * @throws Exception 
     */
    @Test
    public void Check_nextToken_With_EmptyData() throws Exception {
        String path = "c:\\tools\\test_empty.csv";
        int cnt = 0;
        reader = new CSVReader(path);
        String expected_token[] =   {"", "15:23:45", "eizo", "umeda",
                                    "20201122", "", "eizo", "umeda",
                                    "20201122", "14:23:45", "", "umeda",
                                    "20201122", "12:23:45", "eizo", ""};
        String actual_token;
        //while((actual_token = reader.nextToken()) != null) {
        for (int i = 0; i < expected_token.length; i++) {
            actual_token = reader.nextToken();
            System.out.println("token: " + actual_token);
            assertThat(expected_token[cnt], equalTo(actual_token));
            cnt++;
        }
    }

    /**
     * test for csv file with empty lines.
     * @throws Exception
     */
    @Test
    public void Check_nextToken_with_EmptyLines() throws Exception {
        String path = "c:\\tools\\test_emptyline.csv";
        int cnt = 0;
        reader = new CSVReader(path);
        String expected_token[] =   {"20201122", "15:23:45", "eizo", "umeda",
                                    "20201122", "14:23:45", "eizo", "umeda",
                                    "20201122", "12:23:45", "eizo", "umeda"};
        String actual_token;
        while((actual_token = reader.nextToken()) != null) {
            System.out.println("token: " + actual_token);
            assertThat(expected_token[cnt], equalTo(actual_token));
            cnt++;
        }       
    }

    /**
     * test read line count
     * pattern:
     * 1) normal line count
     * @throws Exception
     */
    @Test
    public void Check_ReadLineCount() throws Exception {
        String path = "c:\\tools\\test.csv";
        reader = new CSVReader(path);
        int actual_lineCount;
        int expected_lineCount = 2;

        actual_lineCount = reader.getLineCount();
        System.out.println("lineCount: " + actual_lineCount);
        assertThat(expected_lineCount, equalTo(actual_lineCount));
    }

   /**
     * test read line count
     * pattern:
     * 2) csv file with containing empty lines
     * @throws Exception
     */
    @Test
    public void Check_ReadLineCount_With_EmptyLines() throws Exception {
        String path = "c:\\tools\\test_emptyline.csv";
        reader = new CSVReader(path);
        int actual_lineCount;
        int expected_lineCount = 3;

        actual_lineCount = reader.getLineCount();
        System.out.println("lineCount: " + actual_lineCount);
        assertThat(expected_lineCount, equalTo(actual_lineCount));
    }
}
