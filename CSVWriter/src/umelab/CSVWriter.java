package umelab;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author e.umeda
 * 
 * CSVWriter has simple interface and easy to use for writing a csv file. 
 * The main interface of the class is following:
 * functions:
 * 1) writeRow - with row data, adding with LF and writing to the file obj.
 *               if there is double quotations in data, added another double quotation.
 * 2) writeALL - with row data array, adding with LF, writing the file obj and
 *               finally close the file object.
 *
 * example:
 * 
 * String path = /pathto/file.csv
 * CSVWriter writer = null;
 * String rowdata = "test, doit, hoge";
 * try{
 *      writer =new CSVWriter(path);
 *      writer.writeRow(rowdata);
 * }catch(IOException e) {
 *      e.printStackTrace();
 * }finally{
 *      writer.close();
 * }
 */
public class CSVWriter {

    private static final String LINEFEED = "\r";
    private static final String DEF_ENCODE = "utf-8";

    private OutputStreamWriter osw = null;

    /**
     * line breaks
     */
    private String lf;

    /**
     * encode
     */
    private String encode;

    /**
     * Constructor with default encode as utf-8.
     * @param path filepath
     * @throws IOException
     */
    public CSVWriter(String path) throws IOException {
        this(path, DEF_ENCODE);
    }

    /**
     * Constructor 
     * @param path filepath
     * @param encode specified encode
     * @throws IOException
     */
    public CSVWriter(String path, String encode) throws IOException {
        osw = new OutputStreamWriter(new FileOutputStream(path), encode);
        lf = LINEFEED;
        this.encode = encode;
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
     * write data in a row for a csv file. automatically add line breaks
     * which is set by setLF method.
     * flush method is called inside of this method. however it still need
     * to close FileWriter.
     * @param rowdata written data
     * @throws IOException error
     */
    public void writeRow(String rowdata) throws IOException {
        String convdata = replaceValidStr(rowdata);
        byte b[] = convdata.getBytes(encode);
        StringBuffer sb = new StringBuffer();
        try {
            //append the encoded data
            sb.append(b);
            //append the linefeed
            sb.append(lf);
            //write to the file obj
            osw.write(sb.toString());
        } finally {
            //should it flushes file obj every time?
            osw.flush();
        }
    }

    /**
     * write all data with data array in the csv file.
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
     * If the data contains double quotation, add another double quotation 
     * for the csv file.
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
}