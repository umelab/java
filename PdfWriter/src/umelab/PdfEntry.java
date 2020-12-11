package umelab;

import java.util.HashMap;

public class PdfEntry {
    
    private HashMap<String, String> map = new HashMap<String, String>();

    private String key;
    private String value;

    public PdfEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    
    public String getKey() {
        String keyvalue = "";
        keyvalue = key;
        return keyvalue;
    }

    public String getValue() {
        String val = "";
        val = value;
        return val;
    }
}
