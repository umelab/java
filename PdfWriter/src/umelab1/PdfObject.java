package umelab;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public abstract class PdfObject {

    private static final String TYPE = "/Type";
    protected String name;
    protected HashMap<String, String> entry = new HashMap<>();
    protected ArrayList<PdfObject> list = new ArrayList<>();
    protected static AtomicInteger ai = new AtomicInteger(1);

    public PdfObject() {
        this("");
    }

    public PdfObject(String name) {
        this.name = name;
        if (name != null && name.length() != 0) {
            entry.put(TYPE, name);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract String dumpInfo();

    public abstract int getObjSize();
}