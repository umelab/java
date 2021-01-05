package umelab;

public class PdfPage extends PdfObject {

    /**
     * PdfPages
     */
    private PdfPages pages = null;

    /**
     * PdfResource
     */
    private PdfResource resource = null;

    /**
     * 参照番号
     */
    private int refno;

    /**
     * このオブジェクトの出力データサイズ
     */
    private int objLength;

    /**
     * Constructor
     */
    public PdfPage() {
        super(PdfConstant.PDF_PAGE);
        entry.put(PdfConstant.PDF_PARENT, null);
        entry.put(PdfConstant.PDF_RESOURCE, null);
        setRefID(ai.getAndIncrement());
        resource = new PdfResource();
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

    /**
     * Pagesオブジェクトを設定する
     * @param pages 設定するPagesオブジェクト
     */
    public void setPages(PdfPages pages) {
        this.pages = pages;
    }

    public void addFont(PdfFont font) {
        //ResourceのDictionaryにFontが追加される
        // /Fontがなかったら追加
        resource.addFont(font);
    }

    public PdfResource getResource() {
        return resource;
    }

    /**
     * PdfPageの情報を出力する
     * @return 出力するPdfPage情報
     */
    public String dumpInfo() {
        String value;
        String str = String.valueOf(getRefID()) + " 0 obj " + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_OP_BRACKET + PdfConstant.PDF_LF;
        //loop for dictionary
        for (String key : entry.keySet()) {
            value = entry.get(key);
            if (key.equals(PdfConstant.PDF_PARENT) && value == null) {
                value = pages.getRefStr();
            } else if (key.equals(PdfConstant.PDF_RESOURCE) && value == null) {
                value = resource.getRefStr();
            }
            str += key + " " + value + " \n";
        }
        str += PdfConstant.PDF_CL_BRACKET + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_END_OBJ + PdfConstant.PDF_LF;

        objLength = str.length();
        return str;
    }

    /**
     * PdfPageのバイトサイズを取得する
     * @return 取得するバイトサイズ
     */
    public int getObjSize() {
        return objLength;
    }

}