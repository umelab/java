package umelab;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class StringUtil {
    private static final String SJIS = "SJIS";
    private static final String UTF8 = "UTF-8";

    /**
     * Ascii start code for space " "
     */
    private static final int ASCII_START = 0x20;

    /**
     * Ascii end code for tiruda ~
     */
    private static final int ASCII_END   = 0x7E;

    /**
     * four zeropads format
     */
    private static final String ZEROPAD_4 = "%04x";

    /**
     * two zeropads format
     */
    private static final String ZEROPAD_2 = "%02x";

    /**
     * Convert from ascii char to ascii string with zero-padding
     * @param cha
     * @return zeropad string
     */
    public static String convAsciiChar(int cha) {
        String convertedStr = "";
        if (ASCII_START <= cha && cha <= ASCII_END) {
            convertedStr = String.format(Locale.ENGLISH, ZEROPAD_4, cha);
        } 
        return convertedStr;
    }

    /**
     * Convert from strings to ascii string with zero-padding
     * @param str
     * @return zeropad string
     */
    public static String convAsciiStr(String str) {
        StringBuilder convertedStr = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            String tmp = convAsciiChar(str.charAt(i));
            convertedStr.append(tmp);
        }
        return convertedStr.toString();
    }

    /**
     * true if the specified parameter is ascii string
     * @param cha
     * @return true : if ascii character false : it not
     */
    public static boolean isAscii(int cha) {
        boolean isAscii = false;
        if (ASCII_START <= cha && cha <= ASCII_END) {
            isAscii = true;
        }
        return isAscii;
    }

    /**
     * Convert from text to hex format string for UTF-8
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String convUTFStr(String text) throws UnsupportedEncodingException {
        String tmp = "";
        StringBuilder builder = new StringBuilder();
        byte[] byteUtf = text.getBytes(UTF8);

        for(char b: text.toCharArray()) {
            if (isAscii(b)){
                tmp = convAsciiChar(b);
            } else {
                tmp = Integer.toHexString(b);
            }
            builder.append(tmp);
        }
        return builder.toString();
    }

    /**
     * Convert from text to hex format string For SJIS
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String convSJISStr(String text) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        byte[] byteSjis = text.getBytes(SJIS);
        for (byte b : byteSjis) {
            String tmp = String.format(Locale.ENGLISH, ZEROPAD_2, Byte.valueOf(b));
            builder.append(tmp);
        }
        return builder.toString();
    }
}
