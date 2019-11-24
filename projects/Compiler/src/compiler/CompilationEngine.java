package compiler;
import static compiler.TokenTypes.*;
import java.io.BufferedWriter;

public class CompilationEngine {
    // Gets input from tokenizer
    // Write parsed structure to the output stream
    private Tokenizer tokenizer;
    private VmWriter vmWriter;
    private SymbolTable symbolTable;
    private BufferedWriter writer;
    private String className;    
    private int labelId;
    
    public CompilationEngine(Tokenizer tokenizer, BufferedWriter writer, VmWriter vmWriter) throws Exception {
        this.tokenizer = tokenizer;
        this.writer = writer;
        this.vmWriter = vmWriter;
    }
    public void compileClass() throws Exception {
        symbolTable = new SymbolTable();        
        advance();        
        eat("class");
        
        className = current();
        eat(className);        
        
        eat("{");
        
        while(tokenizer.tokenType().equals(KEYWORD)) {
            String first = current();
            if(first.equals("static") || first.equals("field")) {
                compileClassVarDec();
            } else if(first.equals("constructor") || first.equals("function") || first.equals("method")) {
                compileSubroutine();
            } else {
                throw new Exception("Unexpected token");
            }            
        }        

        eat("}");
    }   
    
    public void compileClassVarDec() throws Exception {
        String specifier = current();
        eat("static", "field");
        
        String type = current();
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            eat(type);
        } else {
            eat("int", "char", "boolean");
        }
        
        boolean iterate = true;
        do {
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                String varName = current();            
                // todo rewrite
                String kind = specifier.equals("static") ? "STATIC" : "FIELD";
                symbolTable.define(varName, type, kind);                
                eat(varName);                            
            } 
            
            String delim = current();
            eat(",", ";");
            
            if(delim.equals(";")) {
                iterate = false;
            }            
        } while(iterate);
    }
    public void compileSubroutine() throws Exception {
        symbolTable.startSubroutine();        
        String name;        
                
        String subroutineType = current();
        boolean isFunction = subroutineType.equals("function");
        eat("constructor", "function", "method");
                
        String returnType = current();                
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            eat(returnType);            
        } else {
            eat("void", "int", "char", "boolean");            
        }         
        
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            name = current();
            eat(name);                        
        } else {
            throw new Exception("Expected to find subroutine name");
        }
        
        String fullSubroutineName = className + "." + name;  
        
        eat("(");
        
        if(subroutineType.equals("method")) {
            symbolTable.define("this", returnType, "ARG");
        }
        
        if(subroutineType.equals("constructor")) {
            symbolTable.define("this", returnType, "pointer");
        }
        
        compileParameterList();
                
        eat(")");       
        
        
        eat("{");
        
        
        while(current().equals("var")) {            
            compileVarDec();                                               
        }        
        
        int nLocals = symbolTable.varCount("VAR");        
        
        if(symbolTable.indexOf(fullSubroutineName) > -1) {
            throw new Exception("Function '" + name + "' has already been declared.");            
        }
        
        vmWriter.writeFunction(fullSubroutineName, nLocals);        
        if(subroutineType.equals("constructor")) {            
            int nClassVars = symbolTable.varCount("FIELD");  
            
            vmWriter.writePush("constant", nClassVars);
            vmWriter.writeCall("Memory.alloc", 1);
            vmWriter.writePop("pointer", 0);            
        }
        
        if(subroutineType.equals("method")) {            
            vmWriter.writePush("ARG", 0);            
            vmWriter.writePop("pointer", 0);            
        }
        
        compileStatements();                      
     
        eat("}");        
    }
    public void compileParameterList() throws Exception {
        while(true) {
            String type = current();                
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                eat(type);                                                          
            } else if(tokenizer.tokenType().equals(KEYWORD)) {
                eat("int", "char", "boolean");                                 
            } else {
                break;
            }
            String paramName;
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                paramName = current();            
                eat(paramName);                
            } else {
                throw new Exception("Expected to find parameter name");
            }
            
            symbolTable.define(paramName, type, "ARG");
            
            
            String symbol = current();            
            if(symbol.equals(")")) {
                break;
            }
            eat(",");                                     
        }                             
    }
    public void compileVarDec() throws Exception {           
        eat("var");                                   
        
        String type = current();                
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            eat(type);                                                     
        } else if(tokenizer.tokenType().equals(KEYWORD)) {
            eat("int", "char", "boolean");            
        }
        
        while(true) {                        
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                String varName = current();                        
                eat(varName);      
                if(symbolTable.indexOf(varName) > -1) {
                    throw new Exception("Variable '" + varName + "' already declared");
                }
                
                symbolTable.define(varName, type, "VAR");                
            } else {
                throw new Exception("Expected to find class variable name");
            }
            
            String symbol = current();                        
            eat(",", ";");                         
            
            if(symbol.equals(";")) {
                break;
            }
        }               
    }
    public void compileStatements() throws Exception {
        while(tokenizer.tokenType().equals(KEYWORD)) {
            String first = tokenizer.keyWord();                     
            if(first.equals("let")) {
                compileLet();
            } else if(first.equals("if")) {
                compileIf();
            } else if(first.equals("while")) {
                compileWhile();
            } else if(first.equals("do")) {
                compileDo();
            } else if(first.equals("return")) {
                compileReturn();
            } else {
                throw new Exception("Unexpected token");
            }            
        }
    }    
    
    public void compileDo() throws Exception {        
        eat("do");
        
        String name = "";
        int nArgs = 0;
        
        if(!tokenizer.tokenType().equals(IDENTIFIER)) {
            throw new Exception("Expected to find identifier, but found " + current());
        }
        
        String token = current();
        eat(token);
        name = "";
                        
        String dotOrBracket = current();
        eat(".", "(");        
        
        if(dotOrBracket.equals("(")) {            
            vmWriter.writePush("pointer", 0);
            nArgs = compileExpressionList() + 1;
            
            name = className + "." + token;
            
            eat(")");            
        } else {
            if(!tokenizer.tokenType().equals(IDENTIFIER)) {
                throw new Exception("Expected to find identifier, but found " + current());
            }
            String subroutineName = current();
            eat(subroutineName);                        

            int index = symbolTable.indexOf(token);

            if(index > -1) {
                String segment = symbolTable.kindOf(token);
                String type = symbolTable.typeOf(token);
                vmWriter.writePush(segment, index);
                nArgs = 1;
                name = type;                            
            } else {
                name = token;
            }

            eat("(");            

            nArgs += compileExpressionList();

            name += "." + subroutineName;            

            eat(")");        
        }        
        
        vmWriter.writeCall(name, nArgs);           
        
        vmWriter.writePop("temp", 0);
        
        eat(";");                
    }
    public void compileLet() throws Exception {        
        eat("let");   
        
        if(!tokenizer.tokenType().equals(IDENTIFIER)) { 
            throw new Exception("Expected to find variable name");
        }
        
        String varName = current();            
        eat(varName);            

        if(symbolTable.indexOf(varName) < 0) {
            throw new Exception("Unknown variable: '" + varName + "'");
        }            
        
        boolean isArray = current().equals("[");
        
        if(isArray) {  
            // Array
            eat("[");            
            compileExpression();
            eat("]");            
            String segment = symbolTable.kindOf(varName);
            int index = symbolTable.indexOf(varName);   
            vmWriter.writePush(segment, index);
            vmWriter.writeArithmetic("add");
        } else {
            
        }
       
        eat("=");                
        compileExpression();        
        eat(";");

        if(isArray) {
            vmWriter.writePop("temp", 0);
            vmWriter.writePop("pointer", 1);
            vmWriter.writePush("temp", 0);
            vmWriter.writePop("that", 0);
        } else {
            String segment = symbolTable.kindOf(varName);
            int index = symbolTable.indexOf(varName);   
            vmWriter.writePop(segment, index);
        }        
    }
    public void compileWhile() throws Exception {
        String beginLabel = getLabel("WHILE_BEGIN");        
        String endLabel = getLabel("WHILE_END");
                
        eat("while");        
        eat("(");
        vmWriter.writeLabel(beginLabel);        
        compileExpression();         
        vmWriter.writeArithmetic("not");
        vmWriter.writeIf(endLabel);        
        eat(")");        
        eat("{");        
        compileStatements();
        vmWriter.writeGoto(beginLabel);        
        eat("}");
        
        vmWriter.writeLabel(endLabel);        
    }
    public void compileReturn() throws Exception {
        eat("return");
        
        boolean isVoid = true;
        if(!current().equals(";")) {            
            compileExpression();            
            isVoid = false;
        }         
        eat(";");
        
        if(isVoid) {                        
            vmWriter.writePush("constant", 0);
        }
        
//        if(symbolTable.indexOf("this") > -1) {            
//            vmWriter.writePush("pointer", 0);
//        }        
        vmWriter.writeReturn();
    }
    public void compileIf() throws Exception {                
        String endLabel = getLabel("IF_END");
        String elseLabel = getLabel("ELSE_BEGIN");        
        
        eat("if");                
        eat("(");                        
        compileExpression();                    
        vmWriter.writeArithmetic("not");
        vmWriter.writeIf(elseLabel);        
        eat(")");
                
        eat("{");
        compileStatements();        
        eat("}");        
        vmWriter.writeGoto(endLabel);        
        
        if(current().equals("else")) {
            vmWriter.writeLabel(elseLabel);            
            eat("else");                        
            eat("{");
            compileStatements();            
            eat("}");            
        } else {
            vmWriter.writeLabel(elseLabel);            
            vmWriter.writeGoto(endLabel);        
        }
        vmWriter.writeLabel(endLabel);
    }
    public void compileExpression() throws Exception {
        compileTerm();
                
        while(tokenizer.tokenType().equals(SYMBOL) && !current().equals(";") && !current().equals(")") && !current().equals("]") && !current().equals(",")) {
            String operation = current();
            eat("+", "-", "*", "/", "&", "|", "<", ">", "=");            
            String command = "";
            // todo: move logic to writearithmetic
            switch(operation) {
                case "+": {
                    command = "add";
                    break;
                }
                case "-": {
                    command = "sub";                    
                    break;
                }
                case "*": {
                    command = "call Math.multiply 2";                    
                    break;
                }
                case "/": {
                    command = "call Math.divide 2";                    
                    break;
                }
                case "&": {
                    command = "and";                    
                    break;
                }
                case "|": {
                    command = "or";                    
                    break;
                }
                case "<": {
                    command = "lt";                    
                    break;
                }
                case ">": {
                    command = "gt";                    
                    break;
                }
                case "=": {
                    command = "eq";                                        
                    break;
                }
                default: {                    
                    break;
                }
            }
            
            compileTerm();         
            
            vmWriter.writeArithmetic(command);
        }                                
    }
    public void compileTerm() throws Exception {
        String token = current();
        switch(tokenizer.tokenType()) {
            case INT_CONST: {                
                eat(token);                
                vmWriter.writePush("constant", Integer.parseInt(token));
                break;
            }
            case STRING_CONST: {                
                eat(token);
                String string = token.substring(1, token.length() - 1);
                int len = string.length();
                vmWriter.writePush("constant", len);
                vmWriter.writeCall("String.new", 1);
                for(int i = 0; i < len; i += 1) {
                    vmWriter.writePush("constant", string.charAt(i));
                    vmWriter.writeCall("String.appendChar", 2);
                }                
                break;
            }
            case KEYWORD: {                                
                eat("true", "false", "null", "this");
                if(token.equals("true")) {
                    vmWriter.writePush("constant", 1);
                    vmWriter.writeArithmetic("neg");
                }
                if(token.equals("false") || token.equals("null")) {
                    vmWriter.writePush("constant", 0);                    
                }
                if(token.equals("this")) {      
                    if(symbolTable.indexOf(token) < 0) {
                        throw new Exception("Undeclared variable: " + token);
                    }

                    vmWriter.writePush("pointer", 0);                    
                }    
                
                break;
            }
            case SYMBOL: {
                if(token.equals("(")) {
                    eat("(");                    

                    compileExpression();

                    eat(")");                    
                } else {
                    // unary operator
                    String command = "";
                    if(token.equals("-")) {
                        command = "neg";
                    } else {
                        command = "not";
                    }
                    eat("-", "~");                    

                    compileTerm();
                    vmWriter.writeArithmetic(command);                    
                }                
                break;
            }
            case IDENTIFIER: {                
                eat(token);                
                
                String nextToken = current();                
                if(nextToken.equals("[")) {
                    // Array
                    if(symbolTable.indexOf(token) < 0) {
                        throw new Exception("Undeclared variable: " + token);
                    }                    

                    eat("[");                    
                    
                    compileExpression();
                    
                    eat("]");                    
                    
                    String segment = symbolTable.kindOf(token);
                    int index = symbolTable.indexOf(token);                    
                    
                    vmWriter.writePush(segment, index);                    
                    vmWriter.writeArithmetic("add");                    
                    vmWriter.writePop("pointer", 1);                                        
                    vmWriter.writePush("that", 0);     
                } else if(nextToken.equals("(") || nextToken.equals(".")) {       
                    int nArgs = 0;
                    String name = "";                    
                        
                    String dotOrBracket = current();
                    eat(".", "(");        

                    if(dotOrBracket.equals("(")) {
                        name = token;
                        nArgs = compileExpressionList();
                        eat(")");            
                    } else {
                        // TODO there is duplicate in compiledo
                        if(!tokenizer.tokenType().equals(IDENTIFIER)) {
                            throw new Exception("Expected to find identifier, but found " + current());
                        }
                        String subroutineName = current();
                        eat(subroutineName);                        
                        
                        int index = symbolTable.indexOf(token);
                        
                        if(index > -1) {
                            String segment = symbolTable.kindOf(token);
                            String type = symbolTable.typeOf(token);
                            vmWriter.writePush(segment, index);
                            nArgs = 1;
                            name = type;                            
                        } else {
                            name = token;
                        }
                        
                        eat("(");            

                        nArgs += compileExpressionList();
                        
                        name += "." + subroutineName;            

                        eat(")");            
                    }

                    vmWriter.writeCall(name, nArgs);   
                } else {
                    // single identifier
                    if(symbolTable.indexOf(token) < 0) {
                        throw new Exception("Undeclared variable: " + token);
                    }

                    String segment = symbolTable.kindOf(token);
                    int index = symbolTable.indexOf(token);

                    vmWriter.writePush(segment, index);
                }
                break;
            }
            default: {
                break;
            }
        }
    }
    public int compileExpressionList() throws Exception {
        int numberOfExpressions = 0;
        while(isNextExpression()) {
            compileExpression();
            numberOfExpressions += 1;
            
            if(current().equals(",")) {
                eat(",");                
            }
        }
        
        return numberOfExpressions;
    }
    private boolean isNextExpression() throws Exception {
        String term = current();
        String type = tokenizer.tokenType();
        
        return  type.equals(INT_CONST) || 
                type.equals(STRING_CONST) || 
                (term.equals("true") || term.equals("false") || term.equals("null") || term.equals("this"))|| 
                type.equals(IDENTIFIER) ||
                (term.equals("-") || term.equals("~") || term.equals("("));                
    }    
    private String current() throws Exception {        
        String token = null;
        switch(tokenizer.tokenType()) {
            case KEYWORD: {
                token = tokenizer.keyWord();                
                break;
            }
            case SYMBOL: {
                token = tokenizer.symbol() + "";                
                break;
            }
            case INT_CONST: {
                token = tokenizer.intVal() + "";                
                break;
            }
            case STRING_CONST: {
                token = tokenizer.stringVal();                
                break;
            }
            case IDENTIFIER: {
                token = tokenizer.identifier();                
                break;
            }                        
            default: {
                break;
            }   
        }
        return token;
    }
    private void eat(String string) throws Exception {        
        if(!string.equals(current())) {            
            throw new Exception("Expected to find: '" + string + "', but found '" + current() + "'");
        }
        advance();
    }
    private void eat(String ...strings) throws Exception {        
        boolean hasMatch = false;
        
        String toPrint = "";
        for(String s:strings) {
            if(s.equals(current())) {
                hasMatch = true;         
            }
            toPrint += "'" + s + "', ";
        }
        
        if(!hasMatch) {
            throw new Exception("Expected to find one of: " + toPrint + " but found '" + current() + "'");
        }
        advance();
    }
    private void advance() throws Exception {
        if(tokenizer.hasMoreTokens()) {
            tokenizer.advance();
        }
    }            
    private String getLabel(String labelType) throws Exception {                
        String result = "";
        // todo replace string literals with constants
        switch(labelType) {
            case "WHILE_BEGIN":             
            case "WHILE_END":             
            case "IF_END":
            case "ELSE_BEGIN": {
                result = labelType;
                break;
            }
            default: {
                throw new Exception("Unknown label type: " + labelType);
            }
        }
        
        return result + this.labelId++;
    }
}
