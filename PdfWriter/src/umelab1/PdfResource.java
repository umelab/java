package umelab;

import java.util.ArrayList;

/**
 * PdfResource
 * 
 * Define Resource Tag
 * In this project, the resource property is defined
 * Font and ProcSet.
 * The ProcSet is fixed string '[ /PDF /Text /ImageB /ImageC /ImageI ]'
 * The output of the Font is defined by PdfFont obj with the indirect obj no.
 * 
 * ex)
 * 6 0 obj
 * <<
 * /ProcSet [ /PDF /Text /ImageB /ImageC /ImageI ]
 * /Font
 * <<
 *      /F7 <<
 *              /Type /Font
 *              /BaseFont /Courier
 *              /SubType /Type1
 *          >>
 *      /F8 <<
 *              /Type /Font
 *              /BaseFont /Times-Roman
 *              /SubType /Type1
 *          >>
 * >>
 * >>
 */
public class PdfResource extends PdfObject {

    /**
     * FontList
     */
    private ArrayList<PdfFont> fontList = new ArrayList<>();
    
   /**
     * reference no
     */
    private int refno;

    /**
     * data size of this obj.
     */
    private int objLength;

    /**
     * Constructor
     */
    public PdfResource() {
        this(null);
    }

    /**
     * Constructor
     */
    public PdfResource(String name) {
        super(name);
        setRefID(ai.getAndIncrement());
        entry.put(PdfConstant.PDF_PROCSET, PdfConstant.PDF_PROCSET_DEFAULT);
        entry.put(PdfConstant.PDF_FONT, null);
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
     * 参照番号ラベルを取得する
     * @return  取得する参照番号ラベル
     */
    public String getRefStr() {
        String refInfo;
        refInfo = String.valueOf(refno) + " 0 R";
        return refInfo;
    }

    public void addFont(PdfFont font) {
        fontList.add(font);
    }

    public String dumpInfo() {
        String str = String.valueOf(getRefID()) + " 0 obj " + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_OP_BRACKET + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_PROCSET + " " + PdfConstant.PDF_PROCSET_DEFAULT + PdfConstant.PDF_LF;
        if (fontList.size() > 0) {
            str += PdfConstant.PDF_FONT + PdfConstant.PDF_LF;
            str += PdfConstant.PDF_OP_BRACKET + PdfConstant.PDF_LF;
            for (PdfFont font : fontList) {
                str += font.dumpInfo();
            }
            str += PdfConstant.PDF_CL_BRACKET + PdfConstant.PDF_LF;
        } 
        str += PdfConstant.PDF_CL_BRACKET + PdfConstant.PDF_LF;

        objLength = str.length();
        /**
         * /F0 
         * <<
         *      /Type /Font
         *      /BaseFont /Arial
         *      /SubType /Type1
         * >>
         * 
         * /F1
         * <<
         *      /Type /Font
         *      /BaseFont /TimesRoman
         *      /SubType /Type1
         * >>
         */

        return str;
    }

    /**
     * Returns ArrayList<PdfFont>.
     * This method is only for an unit-testing.
     * @return
     */
    protected ArrayList<PdfFont> getFontList() {
        return fontList;
    }

    public int getObjSize() {
        return objLength;
    }
}
