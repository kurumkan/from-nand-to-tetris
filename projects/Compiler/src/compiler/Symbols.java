package compiler;
import java.util.HashMap;

public class Symbols {
    private Symbols() { } 
    
    public static final HashMap<String, String> symbols;
    public static final String OPEN_Q_BR = "{";
    public static final String CLOSE_Q_BR = "}";
    public static final String OPEN_BR = "(";
    public static final String CLOSE_BR = ")";
    public static final String OPEN_SQ_BR = "[";
    public static final String CLOSE_SQ_BR = "]";
    public static final String PERIOD = ".";
    public static final String COMMA = ",";
    public static final String SEM = ";";
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULT = "*";
    public static final String DIV = "/";
    public static final String AMP = "&amp;";
    public static final String OR = "|";
    public static final String LT = "&lt;";
    public static final String GT = "&gt;";
    public static final String EQ = "=";
    public static final String NOT = "~";    
    
    static {
        symbols = new HashMap<String, String>();        
        
        symbols.put("{", OPEN_Q_BR);
        symbols.put("}", CLOSE_Q_BR);
        symbols.put("(", OPEN_BR);
        symbols.put(")", CLOSE_BR);
        symbols.put("[", OPEN_SQ_BR);
        symbols.put("]", CLOSE_SQ_BR);
        symbols.put(".", PERIOD);
        symbols.put(",", COMMA);
        symbols.put(";", SEM);
        symbols.put("+", PLUS);
        symbols.put("-", MINUS);        
        symbols.put("*", MULT);
        symbols.put("/", DIV);
        symbols.put("&", AMP);
        symbols.put("|", OR);
        symbols.put("<", LT);    
        symbols.put(">", GT);
        symbols.put("=", EQ);
        symbols.put("~", NOT);        
    }    
}