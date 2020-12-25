package umelab;

import java.util.ArrayList;

public class PdfPage extends PdfObject {
 
    private PdfFont pdfFont;

    private int width;
    private int height;


    public PdfPage() {

    }

    public void setPdfFont(PdfFont font) {
        this.pdfFont = font;
    }

    public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    protected ArrayList<PdfObject> parseObj(ArrayList<PdfObject> list) {
        list.add(this);

        return list;
    }

    public String toString() {
        return "PdfPage";
    }
}
