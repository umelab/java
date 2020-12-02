package umelab;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayDeque;

/**
 * @author e.umeda
 * 
 * CSVReader has simple interface and easy to use for reading a csv file. 
 * The main interface of the class is something like legacy StringTokenizer
 * functions:
 * 1) skip empty lines which means if there are empty lines, the code skip the line.
 * 2) skip empty items. if items were empty item(something like , , ,), the item is empty String as well.
 *
 * String path = /pathto/file.csv
 * CSVReader reader = new CSVReader(path);
 * String token;
 * while((token=reader.nextToken()) != null) {
 *      System.out.println("token: " + token);
 * }
 */
public class CSVReader {

    private static final String LF = "\r\n";
    private static final String DEFAULT_SEPARATOR = ",";
    private static final String DEFAULT_ENCODE = "utf-8";
    /**
     * BufferedReader obj 
     */
    private BufferedReader bufferedReader = null;

    /**
     * save lines of the file into queue obj 
     */
    private ArrayDeque<String> queue = new ArrayDeque<>();

    /**
     * save tokens of the file into queue obj
     */
    private ArrayDeque<String> tokenQueue = new ArrayDeque<>();

    /**
     * encode as String
     */
    private String encode;

    /**
     * separater as String
     */
    private String separater;

    /**
     * line feed as String
     */
    private String lf;

    /**
     * quotation as String
     */
    private String quotation;

    /**
     * line count
     */
    private int lineCount = 0;

    /**
     * coulumn count
     */
    private int columnCount = 0;

    /**
     * token count
     */
    private int tokenCount = 0;
    
    /**
     * boolean  true: if file is closed 
     *          false: if file is opened
     */
    private boolean isClosed = false;

    private int columnPos = 1;

    /**
     * Constructor
     * @param path file path
     * @param encode specified encode
     * @throws IOException
     */
    public CSVReader(String path, String encode) throws IOException {
        this(new InputStreamReader(new FileInputStream(path), encode));
        this.encode = encode;
    }

    /**
     * Constructor
     * @param path file path
     * @throws IOException
     */
    public CSVReader(String path) throws IOException {
        this(new InputStreamReader(new FileInputStream(path)));
    }

    /**
     * Constructor
     */
    public CSVReader(Reader reader) throws IOException {
        bufferedReader = new BufferedReader(reader);
        this.encode = DEFAULT_ENCODE;
        this.separater = DEFAULT_SEPARATOR;
        this.lf = LF;
        //ready for tokens
        scanAllFile();
    }

    /**
     * set Separater for csv file.
     * @param separater
     */
    public void setSeparator(String separater) {
        this.separater = separater;
    }

    /**
     * set Encode for specifid encode.
     * @param encode
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     * set Line Feed for a csv file.
     * for Win: \r\n, Unix/Linux \n, Mac9 \n\r, Mac10 \n
     * @param lf 
     */
    public void setLF(String lf) {
        this.lf = lf;
    }

    /**
     * 
     * @return
     */
    public void setQuote(String quote) {
        this.quotation = quote;
    }

    /**
     * get LineFeed for a csv file.
     * @return Line feed character
     */
    public String getLF() {
        return this.lf;
    }

    /**
     * return   true: if the quotation string is set
     *          false: if it is not
     * @return
     */
    public boolean isQuotation() {
        boolean isQuote = false;
        if (quotation != null && quotation != "") {
            isQuote = true;
        }
        return isQuote;
    }

    /**
     * return next tokens from the queue buffer 
     * @return token
     * @throws IOException
     */
    public String nextToken() throws IOException {
        String next;
        //poll from queue obj
        next = tokenQueue.poll();
        if (next != null) {
            next = next.trim();
            if (isQuotation()) {
                next = stripQuotation(next);
            }
        }
        return next;
    }

    /**
     * return tokens with specified column from queue buffer
     * @param token
     * @return
     */
    public String nextColumn(int index) throws IOException {
        String next = "";

        while (true) {
            int mod = columnPos % index;
            columnPos++;
            //do refactoring laster
            if (mod != 0 || columnPos == 0) {
                tokenQueue.poll();
            } else {
                next = tokenQueue.poll();
                break;
            }
        }

        if (next != null) {
            next = next.trim();
            if (isQuotation()) {
                next = stripQuotation(next);
            }
        }

        if (tokenCount < (columnPos - 1)) {
            next = null;
        }
        return next;
    }

    /**
     * stripped string with quotation string
     * ex:
     *  'foo' -> foo when quotation str set such that '
     *  "bar" -> bar when quotation str set such that "
     * 
     * @param token input string
     * @return
     */
    private String stripQuotation(String token) {
        String strip;
        strip = token.replaceAll(quotation, "");
        return strip;
    }

    /**
     * figure out if file is closed
     * @return  true: file close
     *          false: file open
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * scan all of the file and push queue obj into tokens
     * @throws IOException
     */
    private void scanAllFile() throws IOException {
        String line;
        String tmp_token[] = null;
        //read all file
        readAll();

        while((line = queue.poll())!= null) {
            tmp_token = line.split(separater, -1);
            columnCount = tmp_token.length;
            for (int i = 0; i < tmp_token.length; i++) {
                pushToken(tmp_token[i]);
                tokenCount++;
            }
        }
    }

    /**
     * queue tokens
     * @param token
     */
    private void pushToken(String token) {
        tokenQueue.add(token);
    }

    /**
     * read file context and queuing the line string
     * @throws IOException
     */
    private void readAll() throws IOException {
        String next;
        try {
            while((next = bufferedReader.readLine()) != null) {
                if(next.length() > 0) {
                    queue.add(next);
                    lineCount++;
                }
            }
        } finally {
            //reader close
            bufferedReader.close();
            isClosed = true;
            bufferedReader = null;
        }
    }

    /**
     * get line count
     * @return  line count
     */
    public int getLineCount() {
        return this.lineCount;
    }

    /**
     * get token count
     * @return  token count
     */
    public int countTokeCount() {
        return this.tokenCount;
    }

    /**
     * figure out seperater
     * @param sep
     * @return true: is data is sperator false: if not
     */
    private boolean findSeparater(char sep) {
        boolean isSep = false;
        char[] checkSep = separater.toCharArray();
        if (sep == checkSep[0]) {
            isSep = true;
        }
        return isSep;
    }

    /**
     * figure out line feel code
     * @param sep
     * @return true: if line feed code false: if not
     */
    private boolean findLineFeed(char lf) {
        boolean isLF = false;
        if (lf == '\n' || lf == '\r') {
            isLF = true;
        }
        return isLF;
    }
}
