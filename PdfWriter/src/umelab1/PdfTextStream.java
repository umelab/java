package umelab;

public class PdfTextStream extends PdfObject {
    
    private int refno;

    private PdfDocument pdfDoc;
    private PdfPage pdfPage;

    public PdfTextStream(PdfDocument doc, PdfPage page) {
        pdfDoc = doc;
        pdfPage = page;

        //PdfDocumentにPdfResourceを登録する
        //printInfoがコールされた時にdumpInfoがコールされる
        doc.add(this);
    }

    public void setFont(PdfFont font) {
        pdfPage.addFont(font);
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

    public String dumpInfo() {
        return "";
    }

    public int getObjSize() {
        return 0;
    }

}
