package umelab;

import java.util.ArrayList;

public class PdfDocument extends PdfObject {
    private static final String TYPE = "/TYPE";
    private static final String CATALOG = "/Catalog";
    private static final String PAGES = "/Pages";

    private PdfFont pdfFont;
    private String filePath;

    private PdfPages pages;
    private PdfDictionary pdfDic = new PdfDictionary();
    private PdfEntry pageEntry;

    public PdfDocument() {
        pdfDic = new PdfDictionary();
        pdfDic.addEntry(new PdfEntry(TYPE, CATALOG));
        pdfDic.addEntry(pageEntry = new PdfEntry(PAGES));
        pages = new PdfPages();
    }

    public void setPdfFont(PdfFont font) {
        this.pdfFont = font;
    }

    /**
     * add Page object 
     * @param page
     */
    public void addPage(PdfPage page) {
        //set page for the PdfPages obj
        pages.setPage(page);
        //create page obj
    }

    public String doOutput() {
        String output = "";
        return output;
    }

    protected PdfDictionary getDictionary() {
        return this.pdfDic;
    }

    protected PdfPages getPages() {
        return this.pages;
    }

    /**
     * parse registered obj and set refrence number
     */
    public ArrayList<PdfObject> parseObj(ArrayList<PdfObject> list){
        if (list != null) {
            list.add(this);
            list = pages.parseObj(list);
        }
        return list;
    }

    /**
     * save the pdf document with specific filePath
     * @param filePath file path
     */
    public void save(String filePath) {
        
    }

    public String toString() {
        return "PdfDocument ";
    }
}
