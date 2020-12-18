package umelab;

import java.util.ArrayList;

public class PdfPages extends PdfObject {
    
    private static final String TYPE  = "/Type";
    private static final String PAGES = "/Pages";
    private static final String KIDS  = "/KIDS";
    private static final String COUNT = "/Count";

    private PdfPage page;
    private ArrayList<PdfPage> pageList = new ArrayList<>();
    private PdfDictionary pdfDic;
    private PdfEntry kidsEntry;
    private PdfEntry cntEntry;

    public PdfPages(){
        pdfDic = new PdfDictionary();
        pdfDic.addEntry(new PdfEntry(TYPE, PAGES));
        pdfDic.addEntry(kidsEntry = new PdfEntry(KIDS));
        pdfDic.addEntry(cntEntry  = new PdfEntry(COUNT));
    }

    public void setPage(PdfPage page) {
        if(containsPdfPage(page)) {
            pageList.remove(page);
        }
        pageList.add(page);
    }

    private boolean containsPdfPage(PdfPage page) {
        boolean isContainsPdfPage = false;
        for(PdfPage tmpPage : pageList) {
            if (tmpPage.equals(page)) {
                isContainsPdfPage = true;
            }
        }
        return isContainsPdfPage;
    }

    public String doOutput() {
        String output = "";
        String pageRef;
        pageRef = getPageRef();
        kidsEntry.setValue(pageRef);                        //KIDS [5 0 R 6 0 R]
        cntEntry.setValue(String.valueOf(getPageCount()));  //Count 2

        return output;
    }

    private int getPageCount() {
        return pageList.size();
    }

    private String getPageRef() {
        String ref = "";
        for(PdfPage tmpPage : pageList) {
            
        }
        return ref;
    }

    protected ArrayList<PdfObject> parseObj(ArrayList<PdfObject> list) {
        list.add(this);
        for(PdfPage tmpPage : pageList) {
            tmpPage.parseObj(list);
        }
        return list;
    }

    public String toString() {
        return "PdfPages";
    }
}
