package compiler;
import java.util.HashMap;

public class KeyWords {
    private KeyWords() { } 
    
    public static final HashMap<String, String> keyWords;    
    
    public static final String CLASS = "CLASS";
    public static final String METHOD = "METHOD";
    public static final String FUNCTION = "FUNCTION";
    public static final String CONSTRUCTOR = "CONSTRUCTOR";
    public static final String INT = "INT";
    public static final String BOOLEAN = "BOOLEAN";
    public static final String CHAR = "CHAR";
    public static final String VOID = "VOID";
    public static final String VAR = "VAR";
    public static final String STATIC = "STATIC";
    public static final String FIELD = "FIELD";
    public static final String LET = "LET";
    public static final String DO = "DO";
    public static final String IF = "IF";
    public static final String ELSE = "ELSE";
    public static final String WHILE = "WHILE";
    public static final String RETURN = "RETURN";
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    public static final String NULL = "NULL";
    public static final String THIS = "THIS";
    
    static {
        keyWords = new HashMap<String, String>();        
        
        keyWords.put("class", CLASS);
        keyWords.put("method", METHOD);
        keyWords.put("function", FUNCTION);
        keyWords.put("constructor", CONSTRUCTOR);
        keyWords.put("int", INT);
        keyWords.put("boolean", BOOLEAN);
        keyWords.put("char", CHAR);
        keyWords.put("void", VOID);
        keyWords.put("var", VAR);
        keyWords.put("static", STATIC);
        keyWords.put("field", FIELD);        
        keyWords.put("let", LET);
        keyWords.put("do", DO);
        keyWords.put("if", IF);
        keyWords.put("else", ELSE);
        keyWords.put("while", WHILE);    
        keyWords.put("return", RETURN);
        keyWords.put("true", TRUE);
        keyWords.put("false", FALSE);
        keyWords.put("null", NULL);
        keyWords.put("this", THIS);        
    }    
}