package umelab;

import java.util.ArrayDeque;

public class PdfTextStream extends PdfObject {
    private static final String BT = "BT" + PdfConstant.PDF_LF;
    private static final String ET = "ET" + PdfConstant.PDF_LF;
    private static final String TF = "Tf" + PdfConstant.PDF_LF;
    private static final String Td = "Td" + PdfConstant.PDF_LF;
    private static final String TD = "TD" + PdfConstant.PDF_LF;
    private static final String Tj = "Tj" + PdfConstant.PDF_LF;

    private int refno;

    private PdfDocument pdfDoc;
    private PdfPage pdfPage;

    /**
     * data size of this obj.
     */
    private int objLength;


    /**
     * ArrayDeque for text stream
     */
    private ArrayDeque<String> que = new ArrayDeque<>();
    
    public PdfTextStream(PdfDocument doc, PdfPage page) {
        pdfDoc = doc;
        pdfPage = page;

        //PdfDocumentにPdfResourceを登録する
        //printInfoがコールされた時にdumpInfoがコールされる
        pdfDoc.add(this);
        pdfPage.setTextStream(this);
        setRefID(ai.getAndIncrement());
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

    public void beginText() {
        que.add(BT);
    }

    /**
     * set specified font obj and its size with textstream.
     * @param font  specifed font instance
     * @param fonsize specified font size
     */
    public void setFont(PdfFont font, int fontsize) {
        String tfLine = font.getIndirectFont() + " " + String.valueOf(fontsize) + " " + TF;
        que.add(tfLine);
    }

    public void setTextPosition(int dx, int dy) {
        String tdLine = String.valueOf(dx) + " " + String.valueOf(dy) + " " + Td;
        que.add(tdLine);
    }

    public void setText(String text) {
        String txtLine = PdfConstant.PDF_OP_CIR_BRACKET + text + PdfConstant.PDF_CL_CIR_BRACKET + " " + Tj;
        que.add(txtLine);
    }

    public void endText() {
        que.add(ET);
    }

    /**
     * This method is for an unit-test.
     * @return
     */
    protected int getQueueSize() {
        return que.size();
    }

    /**
     * This method is for an unit-test.
     * @return
     */
    protected ArrayDeque<String> getQueue() {
        return que;
    }

    public byte[] dumpInfo() {
        String binfo = "";//"1. 0. 0. 1. 50. 790. cm" + PdfConstant.PDF_LF;
        for (String info : que) {
            binfo += info;
        } 
        int length = binfo.length();
        String streamInfo = String.valueOf(getRefID()) + " 0 obj " + PdfConstant.PDF_LF;
        streamInfo += "<< /Length " + String.valueOf(length) + " >>" + PdfConstant.PDF_LF;
        streamInfo += "stream" + PdfConstant.PDF_LF;
        //streamInfo += "1. 0. 0. 1. 50. 790. cm" + PdfConstant.PDF_LF;
        //ここが問題 String⇒byte[]
        for (String info : que) {
            streamInfo += info;
        } 
        streamInfo += "endstream" + PdfConstant.PDF_LF;
        streamInfo += "endobj " + PdfConstant.PDF_LF;

        objLength = streamInfo.length();
        return streamInfo.getBytes();
    }

    public int getObjSize() {
        return objLength;
    }

}
