package umelab;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

/**
 * CfgReader class is to read the file extention such as cfg, ini, and config.
 * the Cfg file format: section, name ,value, comment 
 * 
 * ex) 
 * [drivers] 
 * wave=soundwaver.dll
 * timer=timer.drv 
 * graphics=gr.dll
 */
public class CfgReader {

    private static final String DEFAULT_ENCODE = "utf-8";

    private BufferedReader bufReader = null;
    private String encode;

    /**
     * Constructor
     * @param path
     * @param encode
     * @throws IOException
     */
    public CfgReader(String path, String encode) throws IOException {
        this(new InputStreamReader(new FileInputStream(path), encode));
        this.encode = encode;
    }

    /**
     * Constructor
     * @param path
     * @throws IOException
     */
    public CfgReader(String path) throws IOException {
        this(new InputStreamReader(new FileInputStream(path)));
    }

    /**
     * Constructor
     */
    public CfgReader(Reader reader) throws IOException {
        bufReader = new BufferedReader(reader);
        this.encode = DEFAULT_ENCODE;
        
        scanAllFile();
    }


    /**
     * 指定されたセクションのパラメータを返却する
     * @param name
     * @return
     */
    public HashMap<String, String> getBySection(String secName) {
        HashMap<String, String> params = new HashMap<String, String>();

        return params;
    }

    private void scanAllFile() throws IOException {

        readAll();
    }

    private void readAll() throws IOException {
        
    }

    
}
