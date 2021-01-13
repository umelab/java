package umelab;

/**
 * Hello world!
 *
 */
public class PdfTest
{
    public static void main( String[] args )
    {
        PdfDocument doc = new PdfDocument();
        PdfPage page1 = new PdfPage();
 //       PdfPage page2 = new PdfPage();
        doc.addPage(page1);
 //       doc.addPage(page2);             //ArrayList size -> 2


        PdfFont font1 = new PdfFont(PdfFont.HELVETICA, false, false);
 //       PdfFont font2 = new PdfFont(PdfFont.COURIER, false, false);

        page1.addFont(font1);
 //       page1.addFont(font2);
        
        PdfTextStream stream = new PdfTextStream(doc, page1);
        stream.beginText();
        stream.setFont(font1, 46);
        stream.setTextPosition(100, 200);
        stream.setText("Hello Worldt");
//        stream.setTextPosition(1, -20);
//        stream.setText("Another test in here.");
        stream.endText();

        doc.printInfo();
//        PdfFont font2 = new PdfFont();
//        PdfTextStream stream = new PdfTextStream(doc, page1);
/*
        stream.beginText();
        stream.setFont(font1, 14);
        stream.setOffset(100, 100);
        stream.setText("あいう");
        stream.setFont(font2, 20);
        stream.setOffset(150, 150);
        stream.setText("かきくけこ");
        stream.endText();
        
        doc.save("sample.pdf");
        doc.close();
*/
    }
}
