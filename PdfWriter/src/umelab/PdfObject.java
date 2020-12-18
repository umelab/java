package umelab;

import java.util.ArrayList;

public abstract class PdfObject extends Object {
    
    protected abstract ArrayList<PdfObject> parseObj(ArrayList<PdfObject> list);
}
