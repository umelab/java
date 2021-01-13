package umelab;

import java.util.ArrayList;

public class PdfPages extends PdfObject {

    /**
     * PdfPageを格納するArrayList
     */
    private ArrayList<PdfPage> pageList = new ArrayList<>();

    /**
     * 参照番号
     */
    private int refno;

    /**
     * このオブジェクトの出力データサイズ
     */
    private int objLength;

    /**
     * Constrctor
     */
    public PdfPages() {
        super(PdfConstant.PDF_PAGES);
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
     * 参照番号ラベルを取得する
     * @return  取得する参照番号ラベル
     */
    public String getRefStr() {
        String refInfo;
        refInfo = String.valueOf(refno) + " 0 R";
        return refInfo;
    }

    /**
     * Pageオブジェクトを設定する
     * @param page  設定するPageオブジェクト
     */
    public void setPage(PdfPage page) {
        pageList.add(page);
        //this.page = page;
        entry.put(PdfConstant.PDF_KIDS, null);
        entry.put(PdfConstant.PDF_COUNT, null);
    }
    
    /**
     * Kidsを設定する
     * @param page  指定するPdfPage
     */
    public void setKids(PdfPage page) {
        entry.put(PdfConstant.PDF_KIDS, String.valueOf(page.getRefID()));
    }

    /**
     * PdfPagesの情報を出力する
     */
    public byte[] dumpInfo() {
        String value;
        String str = String.valueOf(getRefID()) + " 0 obj " + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_OP_BRACKET + PdfConstant.PDF_LF;
        for (String key : entry.keySet()) {
            if (key.equals(PdfConstant.PDF_COUNT)){
                value = getPageCount();
            } else if (key.equals(PdfConstant.PDF_KIDS)) {
                value = getPageListRef();
            } else {
                value = entry.get(key);
            }
            str += key + " " + value + PdfConstant.PDF_LF;
        }
        str += PdfConstant.PDF_CL_BRACKET + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_END_OBJ + " " + PdfConstant.PDF_LF;

        objLength = str.length();
        return str.getBytes();
    }

    /**
     * Pageサイズを取得する
     * @return
     */
    private String getPageCount() {
        return String.valueOf(pageList.size());
    }
    
    /**
     * Page Arrayラベルを取得する
     * @return
     */
    private String getPageListRef() {
        String result = "[ ";
        for(PdfPage page : pageList) {
            result += page.getRefStr()+" ";
        }
        result += "]";
        return result;
    }

    /**
     * PdfPagesのバイトサイズを取得する
     * @return 取得するバイトサイズ
     */
    public int getObjSize() {
        return objLength;
    }
}