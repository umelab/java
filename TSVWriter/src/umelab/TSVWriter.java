package umelab;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author e.umeda
 * 
 * TSVWriter has simple interface and easy to use for writing a tsv file(tabbed seperated values). 
 * The main interface of the class is following:
 * functions:
 * 1) writeRow - with row data, adding with LF and writing to the file obj.
 *               if there is double quotations in data, added another double quotation.
 * 2) writeALL - with row data array, adding with LF, writing the file obj and
 *               finally close the file object.
 *
 * example:
 * 
 * String path = /pathto/file.tsv
 * TSVWriter writer = null;
 * String rowdata = "test \t doit \t hoge";
 * try{
 *      writer =new TSVWriter(path);
 *      writer.writeRow(rowdata);
 * }catch(IOException e) {
 *      e.printStackTrace();
 * }finally{
 *      writer.close();
 * }
 */
public class TSVWriter {

    private static final String LINEFEED = "\r";
    private static final char DOUBLE_QUOTATION = '\"';
    private static final String DEF_DECODE = "utf-8";

    private OutputStreamWriter osw = null;

    /**
     * line breaks
     */
    private String lf;

    /**
     * encode
     */
    private String decode;

    /**
     * Constructor with default encode as utf-8.
     * @param path filepath
     * @throws IOException
     */
    public TSVWriter(String path) throws IOException {
        this(path, DEF_DECODE);
    }

    /**
     * Constructor 
     * @param path filepath
     * @param encode specified decode
     * @throws IOException
     */
    public TSVWriter(String path, String decode) throws IOException {
        osw = new OutputStreamWriter(new FileOutputStream(path), decode);
        lf = LINEFEED;
        this.decode = decode;
    }

    /**
     * set OutputStreamWriter object. this method is defined for unit test.
     * @param osw OutputStreamWriter
     */
    public void setOutputStreamWriter(OutputStreamWriter osw) {
        this.osw = osw;
    }

    /**
     * set line breaks with parameter
     * @param lf
     */
    public void setLF(String lf) {
        this.lf = lf;
    }

    /**
     * write data in a row for a tsv file. automatically add line breaks
     * which is set by setLF method.
     * flush method is called inside of this method. however it still need
     * to close FileWriter.
     * @param rowdata written data
     * @throws IOException error
     */
    public void writeRow(String rowdata) throws IOException {
        String convData = "";
        String writeData = "";
        convData = buildModRowData(rowdata);
        //create String for specified character code
        writeData = convertStr(convData);
        try {
            //write data for the file obj
            osw.write(writeData);
            osw.write(lf);
        } finally {
            //should it flushes file obj every time?
            osw.flush();
        }
    }

    /**
     * decode string with specified character encode
     * @param data input data
     * @return converted string
     * @throws IOException UnsupportEncodeException
     */
    private String convertStr(String data) throws IOException {
        byte[] decodedByte = data.getBytes(decode);
        return new String(decodedByte, decode);
    }
    /**
     * build modified row data with converting with double quotated operation
     * ["test",foo,bar] -> ["test",foo,bar]
     * [test,fo"o,bar] -> [test, "fo""o", bar]
     * @param data
     * @return
     */
    private String buildModRowData(String data) {
        String modData = "";
        String[] tokens = null;
        tokens = data.split("\t");
        for (int i = 0; i < tokens.length; i++) {
            //check if token contains double quotation
            //check position of double quote
            // ,"foobar", -> no operation
            // ,"foo"bar",-> ,"foo""bar", 
            modData += replaceQuote(tokens[i]);
            if (i != (tokens.length-1)) {
                modData = modData + "\t";
            }
        }
        return modData;
    }

    /**
     * replace double quotation
     * @param data
     * @return converted string with double quotation
     */
    private String replaceQuote(String data) {
        StringBuffer sb = new StringBuffer();
        char head, tail;
        data = data.trim();
        head = data.charAt(0);
        tail = data.charAt(data.length() - 1);

        if (head == DOUBLE_QUOTATION && tail == DOUBLE_QUOTATION) {
            String tmp_str = replaceValidStr(data.substring(1, data.length() -1));
             sb.append(head);
             sb.append(tmp_str);
             sb.append(tail);
        } else if (containsDoubleQuote(data)){
            String tmp = replaceValidStr(data);
            sb.append(DOUBLE_QUOTATION);
            sb.append(tmp);
            sb.append(DOUBLE_QUOTATION);
        } else {
            sb.append(data);
        }

        return sb.toString();
    }

    /**
     * write all data with data array in the tsv file.
     * @param data wrtten data
     * @throws IOException error
     */
    public void writeAllData(String[] data) throws IOException {
        int len = data.length;
        try {
            for (int i = 0; i < len; i++) {
                writeRow(data[i]);
            }
        } finally {
            osw.close();
        }
    }

    /**
     * Returns true if and only if this string contains the double quotation
     * @param data
     * @return
     */
    private boolean containsDoubleQuote(String data) {
        boolean containDoubleQuote = false;
        CharSequence seq = String.valueOf(DOUBLE_QUOTATION);
        containDoubleQuote = data.contains(seq);
        return containDoubleQuote;
    }

    /**
     * If the data contains double quotation, add another double quotation 
     * for the tsv file.
     * @param data 
     * @return string data for replaced with double quotation.
     */
    private String replaceValidStr(String data) {
        String double_quote = "\"";
        String conv_double_quote = "\"\"";
        String result;
        result = data.replaceAll(double_quote, conv_double_quote);
        return result;
    }

    /**
     * close file object
     * @throws IOException
     */
    public void close() throws IOException {
        osw.close();
    }

    public static void main(String args[]) {
        String path = "c:\\tools\\write_test.tsv";
        TSVWriter writer = null;
        String[] data ={
                        "20201130 \t 12:34:56 \t 鈴木 \t 太郎 \t ",
                        "20201130 \t 12:34:57 \t 山\"田 \t 花子"
                        };
        try{
            writer = new TSVWriter(path, "SJIS");
            for(int i = 0; i < data.length; i++) {
                writer.writeRow(data[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}