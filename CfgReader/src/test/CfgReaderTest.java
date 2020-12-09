package test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import javassist.bytecode.Descriptor.Iterator;
import umelab.CfgReader.Section;


public class CfgReaderTest {

    // テストを実施するクラス
    @InjectMocks
    CfgReader reader = null;

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
        String path = "c:\\resource\\test\\test.ini";
        reader = new CfgReader(path);

        assertThat(reader, is(instanceOf(CfgReader.class)));
    }

   /**
     * Test IOException handling in case of an invalid file path
     */
    @Test
    public void Instanciate_IOException_Test() {
        String testPath = "c:\\invalid_path\\test.ini";
        try {
            reader = new CfgReader(testPath);
        } catch (IOException e) {
            assertThat(e, is(instanceOf(IOException.class)));
            return;
        }

        Assert.fail("IOException never throws... ");
    }

    /**
     * Test if Section obj is instanciated properly.
     * Test Case:
     * [TestSection]
     * @throws Exception
     */
    @Test
    public void Check_FindNewSection_Norm() throws Exception {
        Section actual_section = null;
        String actual_value = "";
        String actual_sec_name = "";
        String expected_sec_name1 = "TestSection" ;

        //normal
        String testStr1 = "[TestSection]";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("findNewSection", String.class);
        method.setAccessible(true);

        //execute test
        actual_value = (String) method.invoke(reader, testStr1);
        actual_section = reader.getSection();
        actual_sec_name = actual_section.getName();
        //check section name
        assertThat(actual_sec_name, equalTo(expected_sec_name1));
        //check returned string
        assertThat(actual_value, equalTo(""));
    }
    
    /**
     * Test Case for a section: 
     * Section definition is after the parameter.
     * Ex)
     * name1=value [FooTest]
     * @throws Exception
     */

    @Test
    public void Check_FindNewSection_Head() throws Exception {
        Section actual_section = null;
        String actual_value = "";
        String actual_sec_name = "";
        int actual_sec_size = 0;

        String expected_sec_name = "FooTest";
        int expected_sec_size = 2;

        //need head clipping
        String testStr = "name1=value [FooTest]";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("findNewSection", String.class);
        method.setAccessible(true);

        //execute test for tail clipping
        actual_value = (String) method.invoke(reader, testStr);
        actual_section = reader.getSection();
        actual_sec_name = actual_section.getName();
        //check section name
        assertThat(actual_sec_name, equalTo(expected_sec_name));
        //check returned string
        assertThat(actual_value, equalTo(""));
        //check section size
        actual_sec_size = reader.getSecSize();
        assertThat(actual_sec_size, equalTo(expected_sec_size));
    }

    /**
     * Test Case for a section: 
     * Parameter is added after Section Definition in a row.
     * [HogeTest] name=value
     * @throws Exception
     */
    @Test
    public void Check_FindNewSection_Tail() throws Exception {
        Section actual_section = null;
        String actual_value = "";
        String actual_sec_name = "";
        int actual_sec_size = 0;
        String expected_sec_name = "HogeTest";
        int expected_sec_size = 1;
        
        //need tail clipping 
        String testStr = "[HogeTest] name=value";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("findNewSection", String.class);
        method.setAccessible(true);
    
        //execute test for tail clipping
        actual_value = (String) method.invoke(reader, testStr);
        actual_section = reader.getSection();
        actual_sec_name = actual_section.getName();
        //check section name
        assertThat(actual_sec_name, equalTo(expected_sec_name));
        //check returned string
        assertThat(actual_value, equalTo("name=value"));
        //check section size
        actual_sec_size = reader.getSecSize();
        assertThat(actual_sec_size, equalTo(expected_sec_size));
    }

   /**
     * Test Case for a comment: 
     * Normal pattern. there is a semicolon in a row.
     * Ex)
     * ;
     * @throws Exception
     */
    @Test
    public void Check_RemoveComment_Normal() throws Exception {    
        String actual_value;
        String expected_value = "";

        //need tail clipping 
        String testStr = ";";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("removeComment", String.class);
        method.setAccessible(true);

        actual_value = (String) method.invoke(reader, testStr);
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Case for a comment: 
     * there is some strings before semicolon appears in a row.
     * Ex)
     * [drivers] ; driver section
     * 
     * above the case, the function should be return [drivers]
     * 
     * @throws Exception
     */
    @Test
    public void Check_RemoveComment_Head() throws Exception {    
        String actual_value;
        String expected_value = "[drivers]";

        //need tail clipping 
        String testStr = "[drivers] ; this is a comment section";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("removeComment", String.class);
        method.setAccessible(true);

        actual_value = (String) method.invoke(reader, testStr);
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Case for a comment: 
     * there is some strings after semicolon appears in a row.
     * 
     * Ex)
     * ; driver section
     * 
     * above the case, the method returns empty string ''
     * 
     * @throws Exception
     */
    @Test
    public void Check_RemoveComment_Tail() throws Exception {    
        String actual_value;
        String expected_value = "";

        //need tail clipping 
        String testStr = "; this is a comment section";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("removeComment", String.class);
        method.setAccessible(true);

        actual_value = (String) method.invoke(reader, testStr);
        assertThat(actual_value, equalTo(expected_value));
    }

    /**
     * Test Case for a comment: 
     * there is no semicolon appears in a row.
     * 
     * Ex)
     * [TestSection] keyname=value
     * 
     * above the case, the method returns empty string '[TestSection] keyname=value'
     * 
     * @throws Exception
     */
    @Test
    public void Check_RemoveComment_NoComment() throws Exception {    
        String actual_value;
        String expected_value = "[TestSection] keyname=value";

        //need tail clipping 
        String testStr = "[TestSection] keyname=value";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("removeComment", String.class);
        method.setAccessible(true);

        actual_value = (String) method.invoke(reader, testStr);
        assertThat(actual_value, equalTo(expected_value));
    }

    @Test
    public void Check_StoreParameter_Norm() throws Exception {
        String actual_value;
        int actual_sec_size;
        int expected_sec_size = 1;
        HashMap<String, String> expected_map = new HashMap<String, String>();
        expected_map.put("keyname", "value1");

        String testStr = "keyname=value1";

        //dummy file path
        String path = "c:\\resource\\test.ini";
        reader = new CfgReader(path);

        Method method = CfgReader.class.getDeclaredMethod("storeParameter", String.class);
        method.setAccessible(true);

        //execute test
        method.invoke(reader, testStr);
        
        //check section size
        actual_sec_size = reader.getSecSize();
        assertThat(actual_sec_size, equalTo(expected_sec_size));

        //check hashmap
        Section section = reader.getSection();
        ArrayList<HashMap<String, String>> list = section.getParams();
        HashMap<String, String> actual_map = (HashMap<String, String>) list.get(0);
        assertThat(actual_map, equalTo(expected_map));
    }

    /**
     * Test when bufferedReader.readLine() throws IOException
     * issue: need to pass jvm option --illegal-access=deny
     * Although add --illegal-access=deny in launch.json, it does not work.
     * 
     * @throws Exception
     */
    @Test
    public void readAll_readLine_IOException() throws Exception {
        String actual_val;
        String expected_val = "ReadLineError";
        //mock
        BufferedReader bufReader = mock(BufferedReader.class);

        //dummy file path
        String path = "c:\\resource\\test.ini";

        reader = new CfgReader(path);

        //input mock
        reader.setBufferedReader(bufReader);

        //reflection method
        Method method = CfgReader.class.getDeclaredMethod("readAll");
        method.setAccessible(true);

        try {
            doThrow(new IOException("ReadLineError")).when(bufReader).readLine();
            method.invoke(reader);
        } catch(IOException e) {
            actual_val = e.getMessage();
            assertThat(actual_val, equalTo(expected_val));
            return;
        }

        Assert.fail("IOException never happened");
    }

    /**
     * Test Case for getBySection:
     * As specified file path which compose of one section and two parameters, 
     * check section size, name, parameter size and parameters.
     * Ex)
     * [Section1]
     * section_name1=value1
     * section_name2=value2
     * 
     * @throws Exception
     */
    @Test
    public void Check_GetBySection_OneSection() throws Exception {
        int actual_sec_size;
        int actual_param_size;
        String actual_val;
        String actual_sec_name;

        int expected_sec_size = 1;
        int expected_param_size = 2;
        String[] expected_key = {"section_name1", "section_name2"};
        String[] expected_val = {"value1", "value2"};
        String expected_sec_name = "Section1";

        //test file path
        String path = "c:\\resource\\one_section.ini";
        reader = new CfgReader(path);

        //execute test
        ArrayList<HashMap<String, String>> list = reader.getBySection(expected_sec_name);
        actual_param_size = list.size();
        //check param size
        assertThat(actual_param_size, equalTo(expected_param_size));

        actual_sec_size = reader.getSecSize();
        //check section size
        assertThat(actual_sec_size, equalTo(expected_sec_size));

        int cnt = 0;
        //check each value
        for(int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = (HashMap<String, String>) list.get(i);
            actual_val = (String) map.get(expected_key[cnt]);
            assertThat(actual_val, equalTo(expected_val[cnt++]));
        }

        Section actual_section = reader.getSection();
        actual_sec_name = actual_section.getName();
        assertThat(actual_sec_name, equalTo(expected_sec_name));
    }

    /**
     * Test Case for getBySection:
     * As specified file path which compose of three sections associated parameters, 
     * check section size, name, parameter size and parameters.
     * Ex)
     * [Section1]
     * section_name1=value1
     * 
     * [Section2]
     * sect_name1=test.dll ; dynamic link library
     * sect_name2=hoge.lib ; static library
     * 
     * [Section3]
     * appname = TestApplication
     * 
     * @throws Exception
     */
    @Test
    public void Check_GetBySection_ThreeSection() throws Exception {
        int actual_sec_size;
        int actual_param_size;
        String actual_val;
        String actual_sec_name;

        int expected_sec_size = 1;
        int expected_param_size = 2;
        String[] expected_key = {"section_name1", "section_name2"};
        String[] expected_val = {"value1", "value2"};
        String expected_sec_name = "Section1";

        //test file path
        String path = "c:\\resource\\one_section.ini";
        reader = new CfgReader(path);

        //execute test
        ArrayList<HashMap<String, String>> list = reader.getBySection(expected_sec_name);
        actual_param_size = list.size();
        //check param size
        assertThat(actual_param_size, equalTo(expected_param_size));

        actual_sec_size = reader.getSecSize();
        //check section size
        assertThat(actual_sec_size, equalTo(expected_sec_size));

        int cnt = 0;
        //check each value
        for(int i = 0; i < list.size(); i++) {
            HashMap<String, String> map = (HashMap<String, String>) list.get(i);
            actual_val = (String) map.get(expected_key[cnt]);
            assertThat(actual_val, equalTo(expected_val[cnt++]));
        }

        Section actual_section = reader.getSection();
        actual_sec_name = actual_section.getName();
        assertThat(actual_sec_name, equalTo(expected_sec_name));
    }

}
