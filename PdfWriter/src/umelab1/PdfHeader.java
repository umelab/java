package umelab;

import java.io.UnsupportedEncodingException;

public class PdfHeader extends PdfObject {
    private int objLength;

    public byte[] dumpInfo() {
        String header = "";
 
        //char binMark = '\x7f';
        byte[] bytes = {(byte)0xf6, (byte)0xe4, (byte)0xfc, (byte)0xdf};//{ 0x7f, 0x7f, 0x7f, 0x7f };
        byte[] headByte = null;
        byte[] endByte = null;
        byte[] headerData = new byte[15];
        String binStr = null;
        int pos = 0;
        try {
            binStr = new String(bytes, "SJIS");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        header = "%PDF-1.4" + PdfConstant.PDF_LF + "%";
        headByte = header.getBytes();
        System.arraycopy(headByte, 0, headerData, 0, headByte.length);
        System.arraycopy(bytes, 0, headerData, headByte.length, bytes.length);
        pos = headByte.length + bytes.length;
        endByte = PdfConstant.PDF_LF.getBytes();
        System.arraycopy(endByte, 0, headerData, pos, endByte.length);
        //length = length + 2 + 4;
        //header += "%" + binStr + PdfConstant.PDF_LF;

        objLength = headerData.length;
        //objLength = header.length();
        //objLength = header.getBytes().length;

        return headerData;
    }

    public int getObjSize() {
        return objLength;
    }

    
}
