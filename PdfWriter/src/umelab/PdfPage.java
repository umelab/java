package umelab;

public class PdfPage {
 
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
}
