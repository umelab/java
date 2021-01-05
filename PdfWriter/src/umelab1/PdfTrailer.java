package umelab;

public class PdfTrailer extends PdfObject{

    private int startXrefOffset;

    public PdfTrailer() {
        this(null);
    }

    public PdfTrailer(String name) {
        super(name);
    }

    /**
     * /Rootの関節参照文字列を設定する
     * @param obj
     */
    public void setRoot(String refStr) {
        entry.put(PdfConstant.PDF_ROOT, refStr);
    }

    /**
     * /Sizeで参照オブジェクト数を設定する
     * @param objCount
     */
    public void setCount(int objCount) {
        entry.put(PdfConstant.PDF_SIZE, String.valueOf(objCount));
    }

    public String dumpInfo() {
        String str = "";
        str += PdfConstant.PDF_TRAILER + " \n";
        str += "<< \n";
        for (String key : entry.keySet()) {
            str += key + " " + entry.get(key) + " \n";
        }
        str += ">> \n";
        str += PdfConstant.PDF_STARTXREF + " \n";
        str += String.valueOf(getXRefOffset()) + " \n";
        str += PdfConstant.PDF_EOF + " \n";        

        return str;
    }

    public void setXRefOffset(int startXrefOffset) {
        this.startXrefOffset = startXrefOffset;
    }

    private int getXRefOffset() {
        return startXrefOffset;
    }

    public int getObjSize() {
        return 0;
    }
}
