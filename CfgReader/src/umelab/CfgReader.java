package umelab;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * CfgReader class is to read the file extention such as cfg, ini, and config.
 * the Cfg file format: section, name ,value, comment 
 * 
 * Ex) 
 * [drivers] 
 * wave=soundwaver.dll
 * timer=timer.drv 
 * graphics=gr.dll
 */
public class CfgReader {

    /**
     * default encode is UTF-8
     */
    private static final String DEFAULT_ENCODE = "utf-8";

    /**
     * BufferedReader obj 
     */
    private BufferedReader bufferedReader = null;
    
    /**
     * section queue obj 
     */
    private ArrayDeque<Section> queue = new ArrayDeque<Section>();

    /**
     * Section obj
     */
    private Section section = null;

    /**
     * flag for file obj is closed
     */
    private boolean isClosed = false;

    /**
     * encode string
     */
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
        bufferedReader = new BufferedReader(reader);
        this.encode = DEFAULT_ENCODE;
        //parse reader object
        readAll();
    }

    /**
     * 指定されたセクションのパラメータを返却する
     * @param name
     * @return
     */
    public ArrayList<HashMap<String, String>> getBySection(String secName) {
        ArrayList<HashMap<String, String>> list = null;
        Iterator<Section> iterator = queue.iterator();
        while(iterator.hasNext()) {
            Section section = (Section) iterator.next();
            if (section.getName().equals(secName)){
                list = section.getParams();
            }
        }
        return list;
    }

    /**
     * 指定したセクション名とキーから値を取得する
     * Get value specified section name and key name
     * @param secName specified section name
     * @param key specified key name
     * @return value
     */
    public String getValue(String secName, String key) {
        String value = "";
        ArrayList<HashMap<String, String>> list = null;
        list = getBySection(secName);
        for (HashMap<String, String> mapper : list) {
            if (mapper.containsKey(key)) {
                value = (String) mapper.get(key);
                break;
            }
        }
        return value;
    }

    /**
     * this method is used for unit test.
     * @param reader
     */
    public void setBufferedReader(BufferedReader reader) {
        this.bufferedReader = reader;
    }

    private void readAll() throws IOException {
        String next;
        try {
            while((next = bufferedReader.readLine()) != null) {
                if(next.length() > 0) {
                    next = removeComment(next);
                    next = findNewSection(next);
                    storeParameter(next);
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
     * パラメータをセクションオブジェクトに追加する
     * find formatted string "name = value" and add to the section object.
     * @param data
     */
    private void storeParameter(String data) {
        char cha;
        char equalChar = '=';
        String name = "", value = "";
        for (int i = 0; i < data.length(); i++) {
            cha = data.charAt(i);
            if (cha == equalChar) {
                name = data.substring(0, i).trim();
                value = data.substring(i+1, data.length()).trim();
            }
        }
        //return if name and value is empty
        if (name.equals("") && value.equals("")) return;

        //instanciate parameter obj
        HashMap<String, String> param = new HashMap<String, String>();
        param.put(name, value);
        if (section == null) {
            //create no name section
            section = new Section("");
            queue.add(section);       
        } 
        section.addParam(param);
    }

    /**
     * Create Section obj and add the object to queue
     * セクションオブジェクトを生成して、キューに追加する
     * @param data processed data
     * @return rest of String
     */
    private String findNewSection(String data) {
        String str = "";
        char cha;
        char headChar = '[';
        char tailChar = ']';
        int startPos = -1;
        String secName;
        boolean containsSection = false;
        for (int i = 0; i < data.length(); i++) {
            cha = data.charAt(i);
            if (cha == headChar) {
                containsSection = true;
                storeParameter(data.substring(0, i));
                //start new section
                startPos = i;
            }
            if (cha == tailChar) {
                secName = data.substring(startPos+1, i);
                if (secName != "" && startPos >= 0) {
                    //create Section obj
                    section = new Section(secName);
                    queue.add(section);
                    //return rest of string
                    str = data.substring(i+1, data.length()).trim();
                    break;
                }
            }
        }
        //just returns entire string if section does not find in the input parameter.
        if(!containsSection)    str = data;

        return str;
    }

    /**
     * 行中のセミコロンを削除する
     * remove string if semicolon character find 
     * @param data specified data
     * @return string without pounds
     */
    private String removeComment(String data) {
        String str = "";
        char semicolon = ';';
        char cha;
        boolean isSemiColon = false;
        for (int i = 0; i < data.length(); i++) {
            cha = data.charAt(i);
            if (semicolon == cha) {
                isSemiColon = true;
                if (i == 0) {
                    str = "";
                    break;
                } 
                str = data.substring(0, (i-1));
            }
        }
        if(!isSemiColon) {
            str = data;
        }
        return str;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getEncode() {
        return encode;
    }

    public Section getSection() {
        return section;
    }

    public int getSecSize() {
        return queue.size();
    }

    public class Section {
        /**
         * section name
         */
        private String name;

        /**
         * ArrayList which restored parameter objects
         */
        private ArrayList<HashMap<String, String>> paramList = new ArrayList<HashMap<String, String>>();

        /**
         * Constructor
         * @param name specified section name
         */
        public Section(String name) {
            this.name = name;
        }

        /**
         * セクション名を指定する
         * Set section name
         * @param name specified section name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * セクション名を取得する
         * Get section name
         * @return section name
         */
        public String getName() {
            return this.name;
        }

        /**
         * パラメータを追加する
         * add sepecified Parameter
         * @param param
         */
        public void addParam(HashMap<String, String> param) {
            paramList.add(param);
        }

        /**
         * セクションに格納されているパラメータを取得する
         * Get restored paramter objects in the class
         * @return
         */
        public ArrayList<HashMap<String, String>> getParams() {
            return paramList;
        }

        /**
         * パラメータサイズを取得する
         * Get size of parameters
         * @return size of param
         */
        public int size() {
            return paramList.size();
        }
    }
}
