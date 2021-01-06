package umelab;

import java.util.HashMap;

public class PdfFont extends PdfObject {

    public static final int TIMES_ROMAN = 0;
    public static final int HELVETICA   = 1;
    public static final int COURIER     = 2;

    public static final int TYPE0       = 0;
    public static final int TYPE1       = 1;
    public static final int TYPE2       = 2;
    public static final int TYPE3       = 3;

    private Font font = new Font();

   /**
     * 参照番号
     */
    private int refno;

    /**
     * このオブジェクトの出力データサイズ
     */
    private int objLength;

    public PdfFont(){
        this(0);
    }
    
    public PdfFont(int name) {
        this(name, false, false);
    }

    public PdfFont(int fontID, boolean isBold, boolean isItalic) {
        super(PdfConstant.PDF_FONT);
        switch (fontID) {
            case TIMES_ROMAN:
                font.setFontName(PdfConstant.TIMES_ROMAN);
                font.setType(TYPE1);
                break;
            case HELVETICA:
                font.setFontName(PdfConstant.HELVETICA);
                font.setType(TYPE1);
                break;
            case COURIER:
                font.setFontName(PdfConstant.COURIER);
                font.setType(TYPE1);
                break;
        }
        font.setBold(isBold);
        font.setItalic(isItalic);

        entry.put(PdfConstant.PDF_BASEFONT, font.getBaseFont());
        entry.put(PdfConstant.PDF_SUBTYPE, font.getFontType());
        setRefID(ai.getAndIncrement());
    }

    /**
     * 参照番号を設定する
     * @param no    設定する参照番号
     */
    private void setRefID(int no) {
        this.refno = no;
    }

    /**
     * 参照番号を取得する
     * @return  取得する参照番号
     */
    public int getRefID() {
        return refno;
    }

    /**
     * get refid label
     * @return  specified refid label
     */
    public String getRefStr() {
        String refInfo;
        refInfo = String.valueOf(refno) + " 0 R";
        return refInfo;
    }

    public String getIndirectFont() {
        return "/F" + String.valueOf(getRefID());
    }

    public String dumpInfo() {
        String fontInfo = "";
        String value;
        fontInfo += "/F" + String.valueOf(getRefID()) + PdfConstant.PDF_LF;
        fontInfo += PdfConstant.PDF_OP_BRACKET + PdfConstant.PDF_LF;
        for (String key : entry.keySet()) {
            value = entry.get(key);
            fontInfo += key + " " + value + " \n";
        }
        fontInfo += PdfConstant.PDF_CL_BRACKET + PdfConstant.PDF_LF;

        objLength = fontInfo.length();
        return fontInfo;
    }

    /**
     * This method is for an unit-test
     * @return
     */
    protected HashMap<String, String> getEntry() {
        return entry;
    }
    
    public int getObjSize() {
        return objLength;
    }
}
