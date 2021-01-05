package umelab;

public class Font {

    private static final String TYPE0_LABEL = "/Type0";
    private static final String TYPE1_LABEL = "/Type1";
    private static final String TYPE2_LABEL = "/Type2";
    private static final String TYPE3_LABEL = "/Type3";

    private String fontName = "";
    private boolean isBold = false;
    private boolean isItalic = false;
    private int type = 1;

    public void setFontName(String name) {
        this.fontName = name;
    }

    public void setBold(boolean isBold) {
        this.isBold = isBold;
    }

    public void setItalic(boolean isItalic) {
        this.isItalic = isItalic;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBaseFont() {
        String baseFont = "";
        if (fontName.equals("Times-Roman")) {
            if (isBold && isItalic) {
                baseFont = "/Times-BoldItalic";
            } else if (isBold && !isItalic) {
                baseFont = "/Times-Bold";
            } else if (!isBold && isItalic) {
                baseFont = "/Times-Italic";
            } else if (!isBold && !isItalic) {
                baseFont = "/Times-Roman";
            }
        }

        if (fontName.equals("Helvetica")) {
            if (isBold && isItalic) {
                baseFont = "/Helvetica-BoldOblique";
            } else if (isBold && !isItalic) {
                baseFont = "/Helvetica-Bold";
            } else if (!isBold && isItalic) {
                baseFont = "/Helvetica-Oblique";
            } else if (!isBold && !isItalic) {
                baseFont = "/Helvetica";
            }
        }

        if (fontName.equals("Courier")) {
            if (isBold && isItalic) {
                baseFont = "/Courier-BoldOblique";
            } else if (isBold && !isItalic) {
                baseFont = "/Courier-Bold";
            } else if (!isBold && isItalic) {
                baseFont = "/Courier-Oblique";
            } else if (!isBold && !isItalic) {
                baseFont = "/Courier";
            }            
        }

        return baseFont;
    }

    public String getFontType() {
        String fonttype = "";
        switch(type) {
            case PdfFont.TYPE0:
                fonttype = TYPE0_LABEL;
                break;
            case PdfFont.TYPE1:
                fonttype = TYPE1_LABEL;
                break;
            default:
                fonttype = TYPE1_LABEL;
        }
        return fonttype;
    }
}
