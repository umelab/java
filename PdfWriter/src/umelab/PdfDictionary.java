package umelab;

import java.util.ArrayList;

/**
 * @author e.umeda
 * @date 2020/12/11
 * 
 * PdfDectionary class is to define dictionary object.
 * an expample dictionary is below:
 * ex)
 * <<               <-- start dictionary
 *  /Size 5         <-- first entry
 *  /ROOT 1 0 R     <-- second entry
 * >>               <-- end dictionary
 * 
 * In this package, /ROOT is alway defined "1 0 R"
 */
public class PdfDictionary {
    private static final String HEAD = "<<";
    private static final String TAIL = ">>";
    private static final String LF = " \n";

    private ArrayList<PdfEntry> entryList = new ArrayList<PdfEntry>();

    /**
     * Constractor
     */
    public PdfDictionary() {

    }

    /**
     * Add entry object
     * @param entry
     */
    public void addEntry(PdfEntry entry) {
        //if the same entry exists, remove entry first.
        if(containsSameEntry(entry)) {
            entryList.remove(entry);
        }
        entryList.add(entry);
    }

    /**
     * return true if the same entry object exists.
     * @param tmpEntry
     * @return
     */
    private boolean containsSameEntry(PdfEntry tmpEntry) {
        String searchKey = tmpEntry.getKey();
        boolean containEntry = false;
        for(PdfEntry entry : entryList) {
            if (searchKey.equals(entry.getKey())) {
                containEntry = true;
            }
        }
        return containEntry;
    }

    public String doOutput() {
        String output = "";
        output = HEAD + LF;
        output+= entryOutput();
        output+= TAIL + LF;
        return output;
    }

    /**
     * Output all of entry object
     * @return output of entry obejcts
     */
    private String entryOutput() {
        String entryOut = "";
        //output each of PdfEntry
        for(PdfEntry entry : entryList) {
            entryOut += entry.getKey() + " " + entry.getValue() + LF;
        }
        return entryOut;
    }
    
}
