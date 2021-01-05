package umelab;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Locale;

public class PdfXref extends PdfObject {

    private int count;
    private ArrayDeque<Integer> xrefList = null;
    
    /**
     * Constructor
     */
    public PdfXref() {

    }

    /**
     * 登録されているオブジェクト数を設定する
     * @param count number of pdf obj
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * XRef情報を出力する
     * @return 出力するXRef情報
     */
    public String dumpInfo() {
        String str = "";
        str += PdfConstant.PDF_XREF + " \n";
        str += "0 " + String.valueOf(count+1) + " \n";
        str += getXRefTable();
        return str;
    }

    /**
     * Xrefテーブル情報を構築する
     * @return  構築するXrefテーブル
     */
    private String getXRefTable() {
        String table = "";
        String fmt = "%010d";
        Iterator<Integer> iterator = xrefList.iterator();
        table = PdfConstant.PDF_XREF_HEADER + " \n";
        while(iterator.hasNext()) {
            int xrefContext = iterator.next();
            table += String.format(Locale.ENGLISH, fmt, xrefContext) + " 00000 n \n";
        }
        return table;
    }

    /**
     * Xrefテーブル情報を設定する
     * @param table ArrayDequeue
     */
    public void setTable(ArrayDeque<Integer> table) {
        this.xrefList = table;
    } 


    public int getObjSize() {
        return 0;
    }
}
