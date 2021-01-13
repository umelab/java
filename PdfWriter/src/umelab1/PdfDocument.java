package umelab;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
        add(new PdfHeader());
        add(this);
        setRefID(ai.getAndIncrement());
        pages = new PdfPages();
        //必要
        entry.put(PdfConstant.PDF_PAGES, pages.getRefStr());
        entry.put(PdfConstant.PDF_VERSION, "/1.4");
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
        String filename = "c:\\resource\\test.pdf";
        Iterator<PdfObject> iterator = list.iterator();
        String dumpInfo = "";
        int incOffset = 0;
        FileOutputStream fos = null;
        ByteArrayOutputStream out = null;
        
        try {
            out = new ByteArrayOutputStream();
            while(iterator.hasNext()) {
                PdfObject o = iterator.next();
                //dumpInfo += o.dumpInfo();
                out.write(o.dumpInfo());
                incOffset += o.getObjSize();
                xrefList.add(incOffset);
            }
            //System.out.print(dumpInfo);
            
            //dumpInfo += createXRef();
            out.write(createXRef());
            //dumpInfo += createPdfTrailer(incOffset);
            out.write(createPdfTrailer(incOffset));
            out.flush();

            fos = new FileOutputStream(filename);
            out.writeTo(fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            }catch (Exception e){}
        }
        //System.out.println("");
    }

    /**
     * PdfDocumentの情報を出力する
     */
    public byte[] dumpInfo() {
        //char binMark = '\x7f';
        /*
        byte[] bytes = { 0x7f, 0x7f, 0x7f, 0x7f };
        String binStr = null;
        try {
            binStr = new String(bytes, "UTF-16");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str = "%PDF-1.4" + PdfConstant.PDF_LF;
        str += "%" + binStr + PdfConstant.PDF_LF;
        */
        String str = "";
        str += String.valueOf(getRefID()) + " 0 obj " + PdfConstant.PDF_LF;
        str += PdfConstant.PDF_OP_BRACKET + PdfConstant.PDF_LF;
        for (String key : entry.keySet()) {
            str += key + " " + entry.get(key) + PdfConstant.PDF_LF;
        }
        str += PdfConstant.PDF_CL_BRACKET + PdfConstant.PDF_LF;
        str += "endobj " + PdfConstant.PDF_LF;

        objLength = str.length();
        return str.getBytes();
    }

    /**
     * XRefオブジェクトを作成する
     */
    private byte[] createXRef() {
        byte[] xrefInfo = null;
        PdfXref xref = new PdfXref();
        xref.setCount(list.size());
        xref.setTable(xrefList);

        xrefInfo = xref.dumpInfo();
        //
        System.out.print(xrefInfo);

        return xrefInfo;
    }

    /**
     * Trailerオブジェクトを作成する
     * @param xrefOffset
     */
    private byte[] createPdfTrailer(int xrefOffset) {
        byte[] trailerInfo = null;
        PdfTrailer trailer = new PdfTrailer();
        trailer.setRoot(getRefStr());
        trailer.setCount(list.size());
        trailer.setXRefOffset(xrefOffset);
        trailerInfo = trailer.dumpInfo();
        //
        System.out.println(trailerInfo);

        return trailerInfo;
    }

    /**
     * PdfDocumentのバイトサイズを取得する
     * @return 取得するバイトサイズ
     */
    public int getObjSize() {
        return objLength;
    }
}