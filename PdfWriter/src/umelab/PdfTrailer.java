package umelab;

/**
 * @author e.umeda
 * @date 2020/12/11
 * 
 * an example description is following:
 * trailer          <---tailer label
 * <<
 * /Root 1 0 R      <---dictionary part
 * /Size 5
 * >>
 * startxref        <---start cross ref offset
 * 305              <---offset value
 * %%EOF            <---end label
 * 
 */
public class PdfTrailer {

    private static final String TRAIL_LABEL = "trailer";
    private static final String XREF_LABEL  = "startxref";
    private static final String LF = " \n";
    private static final String EOF = "%%EOF";

    private int xrefPos = 0;
    private PdfDictionary trailDict = null;
    
    /**
     * コンストラクタ
     */
    public PdfTrailer() {
        this(null, 0);
    }

    /**
     * コンストラクタ
     * @param trailDict
     * @param xrefPos
     */
    public PdfTrailer(PdfDictionary trailDict, int xrefPos) {
        this.trailDict = trailDict;
        this.xrefPos = xrefPos;
    }

    /**
     * Trailerオブジェクト設定する
     * @param Trailerに設定する辞書オブジェクト
     * @param Trailerに設定する相互参照リンクオフセット
     */
    public void setTrailerProp(PdfDictionary trailDict, int xrefPos) {
        this.trailDict = trailDict;
        this.xrefPos = xrefPos;
    }

    /**
     * Trailerオブジェクトに登録されている情報を出力する
     */
    public String doOutput() {
        String output = "";
        output = TRAIL_LABEL + LF;
        output+= trailDict.doOutput();
        output+= XREF_LABEL + LF;
        output+= String.valueOf(xrefPos) + LF;
        output+= EOF;
        return output;
    }

    public int getXRefPos() {
        return this.xrefPos;
    }

    public PdfDictionary getDictionary() {
        return this.trailDict;
    }


}