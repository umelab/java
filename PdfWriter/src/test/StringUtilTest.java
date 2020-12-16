package umelab;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.ArrayDeque;
//import java.util.Locale;

//import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(StringUtil.class)
public class StringUtilTest {

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
    public void Test_IsAscii_Norm() throws Exception {
//      PowerMockito.mockStatic(StringUtil.class);
//      when(StringUtil.isAscii(1)).thenReturn(true);
        char testedChar = 'a';
        boolean actual_val;
        boolean expected_val = true;

        //execute test
        actual_val = StringUtil.isAscii(testedChar);
        assertThat(actual_val, equalTo(expected_val));
    }

    @Test
    public void Test_IsAscii_False_Str() throws Exception {
        String testedStr = "あいうアイウ";
        boolean actual_val;
        boolean expected_val = false;

        for(int i = 0; i < testedStr.length(); i++) {
            char cha = testedStr.charAt(i);
            actual_val = StringUtil.isAscii(cha);
            assertThat(actual_val,equalTo(expected_val));
        }
    }

    /**
     * Test convertion for Japanese Text
     * @throws Exception
     */
    @Test
    public void Test_convUTFStr() throws Exception {
        String testedStr = "アメンボ赤いよ楽しいな";
        String actual_val;
        String expected_val = "30a230e130f330dc8d6430443088697d30573044306a";

        actual_val = StringUtil.convUTFStr(testedStr);
        assertThat(actual_val, equalTo(expected_val));
    }

    /**
     * Test convertion for English Text
     * @throws Exception
     */
    @Test
    public void Test_convUTFStr_Ascii() throws Exception {
        String testedStr = "I Live in N.Y.";
        String actual_val;
        String expected_val = "00490020004C00690076006500200069006E0020004E002E0059002E";

        actual_val = StringUtil.convUTFStr(testedStr).toUpperCase();
        assertThat(actual_val, equalTo(expected_val));
    }

    /**
     * Test convertion with Japanese and Ascii String
     * @throws Exception
     */
    @Test
    public void Test_convUTFStr_Mix() throws Exception {
        String testedStr = "本を1冊読みました!";
        String actual_val;
        String expected_val = "672C30920031518A8AAD307F307E3057305F0021";

        actual_val = StringUtil.convUTFStr(testedStr).toUpperCase();
        assertThat(actual_val, equalTo(expected_val));
    }

    @Test
    public void Test_convSJISStr() throws Exception {
        String testedStr = "アメンボは赤いよ楽しいな";
        String actual_val;
        String expected_val = "834183818393837b82cd90d482a282e68a7982b582a282c8";

        actual_val = StringUtil.convSJISStr(testedStr);
        assertThat(actual_val, equalTo(expected_val));        
    }

    @Test
    public void Test_convSJISStr_Ascii() throws Exception {
        String testedStr = "I Live in N.Y.";
        String actual_val;
        String expected_val = "49204c69766520696e204e2e592e";

        actual_val = StringUtil.convSJISStr(testedStr);
        assertThat(actual_val, equalTo(expected_val));
    }


}