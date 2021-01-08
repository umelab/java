package umelab;

import java.util.ArrayDeque;
import java.util.Iterator;

public class PdfDocument extends PdfObject {

    /**
     * PdfPages obj
     */
    private PdfPages pages;

    /**
     * 参照番号
     */
    private int refno;

    /**
     * このオブジェクトの出力データサイズ
     */
    private int objLength;

    /**
     * XRefテーブル格納リスト
     */
    private ArrayDeque<Integer> xrefList = new ArrayDeque<>();

    /**
     * Constructor
     */
    public PdfDocument() {
        super(PdfConstant.PDF_CATALOG);
        init();
    }

    /**
     * 初期処理
     */
    private void init() {
        add(this);
        setRefID(ai.getAndIncrement());
        //必要
        entry("/Pages", Pages.getRefStr());
        pages = new PdfPages();
        add(pages);
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
     * Pageオブジェクトをドキュメントに追加する
     * @param page  追加するPageオブジェクト
     */
    public void addPage(PdfPage page) {
        //PagesオブジェクトにPageを関連付けする
        pages.setPage(page);
        //PageオブジェクトにPagesを関連付けする
        page.setPages(pages);

        add(page);
        PdfResource resource = page.getResource();
        if (resource != null) {
            add(resource);
        }
    }

    public void add(PdfObject obj) {
        list.add(obj);
    }

    /**
     * PdfDocumentに登録されているオブジェクト情報を出力する
     */
    public void printInfo() {
        Iterator<PdfObject> iterator = list.iterator();
        String dumpInfo = "";
        int incOffset = 0;
        while(iterator.hasNext()) {
            PdfObject o = iterator.next();
            dumpInfo += o.dumpInfo();
            incOffset += o.getObjSize();
            xrefList.add(incOffset);
        }
        System.out.print(dumpInfo);
        createXRef();
        createPdfTrailer(incOffset);
        //System.out.println("");
    }

    /**
     * PdfDocumentの情報を出力する
     */
    public String dumpInfo() {
        String str = "%PDF-1.4" + PdfConstant.PDF_LF;
        str += "%\\0xf2\\0xf3\\0xcf\\0xf3" + PdfConstant.PDF_LF;
        str += String.valueOf(getRefID()) + " 0 obj " + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_OP_BRACKET + PdfConstant.PDF_LF;
        for (String key : entry.keySet()) {
            str += key + " " + entry.get(key) + PdfConstant.PDF_LF;
        }
        str += PdfConstant.PDF_CL_BRACKET + PdfConstant.PDF_LF;
        str += "endobj";

        objLength = str.length();
        return str;
    }

    /**
     * XRefオブジェクトを作成する
     */
    private void createXRef() {
        PdfXref xref = new PdfXref();
        xref.setCount(list.size());
        xref.setTable(xrefList);

        //
        System.out.print(xref.dumpInfo());
    }

    /**
     * Trailerオブジェクトを作成する
     * @param xrefOffset
     */
    private void createPdfTrailer(int xrefOffset) {
        PdfTrailer trailer = new PdfTrailer();
        trailer.setRoot(getRefStr());
        trailer.setCount(list.size());
        trailer.setXRefOffset(xrefOffset);

        //
        System.out.println(trailer.dumpInfo());
    }

    /**
     * PdfDocumentのバイトサイズを取得する
     * @return 取得するバイトサイズ
     */
    public int getObjSize() {
        return objLength;
    }
}