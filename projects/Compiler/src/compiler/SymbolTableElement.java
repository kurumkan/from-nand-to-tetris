package compiler;

public class SymbolTableElement {
    private String kind;
    private String type;
    private int index;
    
    public SymbolTableElement(String type, String kind, int index) {
        this.kind = kind;
        this.type = type;
        this.index = index;        
    }
    
    public String getKind() {
        return kind;
    }
    
    public String getType() {
        return type;
    }
    
    public int getIndex() {
        return index;
    }
    @Override
    public String toString() {
        return kind + ", " +  type + ", " + index;
    }
}
