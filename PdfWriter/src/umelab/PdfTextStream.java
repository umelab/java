package umelab;

import java.util.ArrayDeque;

/**
 * @author e.umeda
 * @date 2020/12/14
 * 
 * setFont -> create Resource obj
 * add specified Page obj with /Resources
 * 
 * beginText -> BT
 * endText   -> ET
 * 
 * setTextPos -> Td
 * setText    -> Tj
 * 
 */
public class PdfTextStream {
    
    /**
     * LF Operator
     */
    private static final String LF = " \n";

    /**
     * Open brackets
     */
    private static final String OP_BRACKET = "(";

    /**
     * Close brackets
     */
    private static final String CL_BRACKET = ")";

    /**
     * BT Operator
     */
    private static final String BT = "BT";

    /**
     * ET Operator
     */
    private static final String ET = "ET";

    /**
     * Tj Operator
     */
    private static final String TJ = "Tj";

    /**
     * Td Operator
     */
    private static final String TD = "Td";

    /**
     * Tf Operator 
     */
    private static final String TF = "Tf";

    /**
     * cm Operator
     */
    private static final String CM = "cm";

    private PdfDocument pdfDoc;
    private PdfPage pdfPage;
    private PdfFont pdfFont;

    private ArrayDeque<String> queue = new ArrayDeque<>();
    private int xStartPos;
    private int yStartPos;

    public PdfTextStream() {
        this(null, null);
    }

    public PdfTextStream(PdfDocument doc, PdfPage page) {
        this.pdfDoc = doc;
        this.pdfPage = page;
    }

    public void beginText() {
        queue.add(BT);
    }

    public void endText() {
        queue.add(ET);
    }

    /**
     * set specified text 
     * @param text
     */
    public void setText(String text) {
        String pdfText;
        pdfText = OP_BRACKET + text + CL_BRACKET + " " +TJ;
        queue.add(pdfText);
    }

    /**
     * set text position with x and y coordinate
     * Td Operator
     * @param x specified x-coordinate
     * @param y specified y-coordinate
     */
    public void setTextPos(int x, int y) {
        String pdfTextPos;
        pdfTextPos = String.valueOf(x) + " " + String.valueOf(-y) + " " + TD;
        queue.add(pdfTextPos);
    }

    /**
     * set font for text
     * @param font
     */
    public void setFont(PdfFont font) {
        String pdfFontStr;
        //ページオブジェクトに/Resourceタグを追加
        if (pdfPage != null) {
            pdfPage.setPdfFont(font);
        }
        //フォントオブジェクトをドキュメントに追加
        if (pdfDoc != null) {
            pdfDoc.setPdfFont(font);
        }
        // Tf operator
        // /F0 26. Tf
        pdfFontStr = font.RefLabel() + " " + String.valueOf(font.getSize()) + ". " + TF;
        queue.add(pdfFontStr);
    }

    /**
     * flush all of text stream object.
     * ex)
     * 5 0 obj
     * <<                       % <-- head start
     * >>
     * stream                   % <-- head end
     * 1. 0. 0. 1. 50. 790. cm % specifid transformation
     * BT
     * /F0 26. Tf
     * 0 -20 Td
     * (Hello world!) Tj
     * ET
     * endstream                % <-- tail start end
     * endobj
     * @return
     */
    public String doOutput() {
        String output;
        output = "<<" + LF;
        output+= ">>" + LF;
        output+= "stream" + LF;
        output+= findBasePosition() + LF; //1. 0. 0. dx. dy. cm
        for (String param : queue) {
            output += param + LF;
        }
        output += "endstream" + LF;
        return output;
    }

    /**
     * implements basic transformation, no rotation and no scale
     * 1 0 0 1 dx dy            :translation    o
     * sx 0 0 sy 0 0            :scale          x
     * cosq sinq -sinq cosq 0 0 :rotation       x
     * @return
     */
    private String findBasePosition() {
        String basePos;
        String marginX = "0."; //= String.valueOf(xStartPos)+".";
        String marginY = "0."; //= String.valueOf(yStartPos)+".";
        if (pdfPage != null) {
            marginX = String.valueOf(0) + ".";
            marginY = pdfPage.getHeight() + ".";
        }
        basePos = "1. 0. 0. 1. " + marginX + " " + marginY + " " + CM;
        return basePos;
    }

    /**
     * this method is to do an unit test
     * @return
     */
    protected ArrayDeque<String> getQueue() {
        return queue;
    }
}
