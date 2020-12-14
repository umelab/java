package umelab;

public class PdfFont {
    
    private int fontSize;
    private String label;

    public PdfFont() {

    }

    public void setSize(int size) {
        this.fontSize = size;
    }

    public int getSize() {
        return this.fontSize;
    }

    public void setRefLabel(String label) {
        this.label = label;
    }

    public String RefLabel() {
        return "/"+label;
    }
}
