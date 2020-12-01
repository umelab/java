package test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;


/**
 * Test case of the CSVWriterTest class
 *
 * @author e.umeda
 */
public class MultipleMapTest {

    // tested object
    @InjectMocks
    MultipleMap mapper = null;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        mapper = new MultipleMap();
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
        assertThat(mapper, is(instanceOf(MultipleMap.class)));
    }

    /**
     * Test 
     * @throws Exception
     */
    @Test
    public void put_singlevalue_Test() throws Exception {
        String actual_str;
        Object keyObj = new Object();
        String valObj = "TestObj";
        // execute test
        mapper.put(keyObj, valObj);
 
        // result
        Object[] o = (Object[]) mapper.get(keyObj);
        actual_str = (String) o[0];
        assertThat(actual_str, equalTo(valObj));
    }

    /**
     * Test put method with multiple object restore
     */
    @Test
    public void put_mutiplevalue_Test() throws Exception {
        String[] actual_str;
        Object keyObj = new Object();
        String valObj1 = "TestObj1";
        String valObj2 = "TestObj2";
        // execute test
        mapper.put(keyObj, valObj1);
        mapper.put(keyObj, valObj2);
 
        // result
        Object[] o = (Object[]) mapper.get(keyObj);
        actual_str = new String[o.length];
        for(int i = 0; i < o.length; i++) {
            actual_str[i] = (String) o[i];
        }
        assertThat(actual_str[0], equalTo(valObj1));
        assertThat(actual_str[1], equalTo(valObj2));   
    }

    /**
     * Test for initial size of the mapp obj.
     * @throws Exception
     */
    @Test
    public void size_initial_Test() throws Exception {
        int actual_size;
        int expected_size = 0;

        //execute test
        actual_size = mapper.size();
        //expected size should be 0 for initial condition
        assertThat(actual_size, equalTo(expected_size));
    }

    /**
     * Test for size of the mapp obj after calling put method.
     */
    @Test
    public void size_keys_Test() throws Exception {
        int actual_size;
        int expected_size = 1;

        Object key = new Object();
        Object val = new Object();

        //prepared for test
        mapper.put(key, val);

        //execute test
        actual_size = mapper.size();
        assertThat(actual_size, equalTo(expected_size));
    }

    /**
     * Test mapper's size in case of the size of mapper object changed.
     * @throws Exception
     */
    @Test
    public void size_change_Test() throws Exception {
        int actual_size;
        int expected_size = 0;

        //size :0
        actual_size = mapper.size();
        assertThat(actual_size, equalTo(expected_size));

        Object key = new Object();
        Object val = new Object();
        mapper.put(key, val);

        expected_size = 1;
        //size :1
        actual_size = mapper.size();
        assertThat(actual_size, equalTo(expected_size));

        mapper.remove(key);
        expected_size = 0;
        //size :0
        actual_size = mapper.size();
        assertThat(actual_size, equalTo(expected_size));
    }

    /**
     * Test for get method as registering single object
     * @throws Exception
     */
    @Test
    public void get_singleObj_Test() throws Exception {
        Object key = new Object();
        Object val = new Object();
        Object actual_obj = null;
        mapper.put(key, val);

        //execute test
        Object tmp_obj[] = (Object[]) mapper.get(key);
        actual_obj = tmp_obj[0];
        assertThat(actual_obj, equalTo(val));
    }

    /**
     * Test for get method as registering multiple objects
     * @throws Exception
     */
    @Test
    public void get_multiObj_Test() throws Exception {
        String actual_str = "";
        String[] expected_str ={"test0", "test1", "test2", "test3"};

        int test_size = 4;
        Object key = new Object();
        String[] val = new String[test_size];
        for(int i = 0; i < test_size; i++) {
            val[i] = new String("test" + String.valueOf(i));
        }

        // prepare test
        mapper.put(key, val);

        //execute test
        Object[] tmp_obj = (Object[]) mapper.get(key);
        for (int j = 0; j < tmp_obj.length; j++) {
            actual_str = (String) tmp_obj[j];
            assertThat(actual_str, equalTo(expected_str[j]));
        }
    }


    /**
     * Test ArrayIndexOutOfBoundsException as exceeding MaxSize
     */
    @Test
    public void put_ArrayIndexOutOfBound_Test() {
        Object key = new Object();
        String[] val = new String[3];
        int test_size = 1;

        //set max size 1
        mapper.setValMaxSize(test_size);

        try {
            //try to put three objs so that the exception should be thrown
            mapper.put(key, val);
        } catch(Exception e) {
            assertThat(e, is(instanceOf(ArrayIndexOutOfBoundsException.class)));
            return;
        }

        Assert.fail("could not catch Exception");
    }

    /**
     * Test for findKey method. Check if the method returns key obj with specified value obj 
     * @throws Exception
     */
    @Test
    public void findKey_Normal_Test() throws Exception {
        String[] key = {"key1", "key2"};
        String[] val1 = {"test1", "test2", "test3"};
        String[] val2 = {"foo1", "foo2", "foo3"};
    
        mapper.put(key[0], val1);
        mapper.put(key[1], val2);

        //execute test
        Object tmp_obj = mapper.findKey(val2[2]);
        String actual_str = (String) tmp_obj;
        String expected_str = key[1];

        assertThat(actual_str, equalTo(expected_str));    
    }

    /**
     * Test for findKey method. Check if the method return null with nonmatches keys.
     * @throws Exception
     */
    @Test
    public void findKey_Null_Test() throws Exception {
        String key = "key1";
        String[] val = {"test1", "test2", "test3"};
        String test_str = "test4";

        mapper.put(key, val);

        //execute test
        Object actual_obj = mapper.findKey(test_str);
        assertThat(actual_obj, nullValue());
    }
 }
