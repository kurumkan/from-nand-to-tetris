package virtualmachine;

import java.util.HashMap;

public class CommandTypes {
    private CommandTypes() { } 
    
    public static final HashMap<String, String>  commandTypesMap;
    public static final String C_ARITHMETIC = "C_ARITHMETIC";
    public static final String C_PUSH = "C_PUSH";
    public static final String C_POP = "C_POP";
    public static final String C_LABEL = "C_LABEL";
    public static final String C_GOTO = "C_GOTO";
    public static final String C_IF = "C_IF";
    public static final String C_FUNCTION = "C_FUNCTION";
    public static final String C_RETURN = "C_RETURN";
    public static final String C_CALL = "C_CALL";
    
    static {
        commandTypesMap = new HashMap<String, String>();        
        
        commandTypesMap.put("add", C_ARITHMETIC);
        commandTypesMap.put("sub", C_ARITHMETIC);
        commandTypesMap.put("neg", C_ARITHMETIC);
        commandTypesMap.put("eq", C_ARITHMETIC);
        commandTypesMap.put("gt", C_ARITHMETIC);
        commandTypesMap.put("lt", C_ARITHMETIC);
        commandTypesMap.put("and", C_ARITHMETIC);
        commandTypesMap.put("or", C_ARITHMETIC);
        commandTypesMap.put("not", C_ARITHMETIC);
        
        commandTypesMap.put("push", C_PUSH);
        commandTypesMap.put("pop", C_POP);
        commandTypesMap.put("label", C_LABEL);
        commandTypesMap.put("goto", C_GOTO);
        commandTypesMap.put("if-goto", C_IF);
        commandTypesMap.put("function", C_FUNCTION);
        commandTypesMap.put("return", C_RETURN);
        commandTypesMap.put("call", C_CALL); 
    }    
}