package compiler;
import static compiler.Symbols.symbols;
import static compiler.TokenTypes.*;
import java.io.BufferedWriter;

public class CompilationEngine {
    // Gets input from tokenizer
    // Write parsed structure to the output stream
    private Tokenizer tokenizer;
    private BufferedWriter writer;    
    
    public CompilationEngine(Tokenizer tokenizer, BufferedWriter writer) throws Exception {
        this.tokenizer = tokenizer;
        this.writer = writer;
    }
    public void compileClass() throws Exception {
        writeStart("class");        
        
        advance();        
        eat("class");
        write("keyword", "class");
        
        String className = current();
        eat(className);
        write("identifier", className);
        
        eat("{");
        write("symbol", "{");
        
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
        write("symbol", current());
        
        writeEnd("class");
    }   
    
    public void compileClassVarDec() throws Exception {                
        writeStart("classVarDec");
        
        String specifier = current();
        eat("static", "field");
        write("keyword", specifier);                    
        
        String type = current();
        if(tokenizer.tokenType().equals(IDENTIFIER)) {            
            eat(type);            
            write("identifier", type);                        
        } else {
            eat("int", "char", "boolean");
            write("keyword", type);            
        }        
        
        boolean iterate = true;
        do {
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                String varName = current();            
                eat(varName);            
                write("identifier", varName);
            } 

            String delim = current();
            eat(",", ";");
            write("symbol", delim);  
            if(delim.equals(";")) {
                iterate = false;
            }            
        } while(iterate);

        writeEnd("classVarDec");
    }
    public void compileSubroutine() throws Exception {
        writeStart("subroutineDec");
        
        String subroutineType = current();
        eat("constructor", "function", "method");
        write("keyword", subroutineType);                    
                
        String returnType = current();                
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            eat(returnType);
            write("identifier", returnType);                                            
        } else {
            eat("void", "int", "char", "boolean");
            write("keyword", returnType);                    
        }         
        
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            String subroutineName = current();
            eat(subroutineName);
            write("identifier", subroutineName); 
        } else {
            throw new Exception("Expected to find subroutine name");
        }
        
        eat("(");
        write("symbol", "(");
        
        compileParameterList();
                
        eat(")");
        write("symbol", ")");                       
        
        writeStart("subroutineBody");
        
        eat("{");
        write("symbol", "{");
          
        while(current().equals("var")) {
            compileVarDec();                                   
        }        
        
        compileStatements();                
        
        eat("}");
        write("symbol", "}");                
        
        writeEnd("subroutineBody");
        
        writeEnd("subroutineDec");
    }
    public void compileParameterList() throws Exception {
        writeStart("parameterList");        
        
        while(true) {
            String type = current();                
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                eat(type);
                write("identifier", type);                                            
            } else if(tokenizer.tokenType().equals(KEYWORD)) {
                eat("int", "char", "boolean");
                write("keyword", type);                    
            } else {
                break;
            }
            
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                String paramName = current();            
                eat(paramName);
                write("identifier", paramName);            
            } else {
                throw new Exception("Expected to find parameter name");
            }
            
            String symbol = current();            
            if(symbol.equals(")")) {
                break;
            }
            eat(",");                         
            write("symbol", symbol);            
        }
        
        writeEnd("parameterList");
    }
    public void compileVarDec() throws Exception { 
        writeStart("varDec");
        
        eat("var");                         
        write("keyword", "var");    
        
        String type = current();                
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            eat(type);
            write("identifier", type);                                            
        } else if(tokenizer.tokenType().equals(KEYWORD)) {
            eat("int", "char", "boolean");
            write("keyword", type);                    
        }
        
        while(true) {                        
            if(tokenizer.tokenType().equals(IDENTIFIER)) {
                String varName = current();                        
                eat(varName);                         
                write("identifier", varName);            
            } else {
                throw new Exception("Expected to find class variable name");
            }
            
            String symbol = current();                        
            eat(",", ";");                         
            write("symbol", symbol);  
            if(symbol.equals(";")) {
                break;
            }
        }        
        
        writeEnd("varDec");
    }
    public void compileStatements() throws Exception {
        writeStart("statements");
        
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
        
        writeEnd("statements");
    }
    public void compileDo() throws Exception {
        writeStart("doStatement");
        
        eat("do");
        write("keyword", "do");       
        
        if(!tokenizer.tokenType().equals(IDENTIFIER)) {
            throw new Exception("Expected to find identifier, but found " + current());
        }
        
        String token = current();
        eat(token);
        write("identifier", token); 
        
        String dotOrBracket = current();
        eat(".", "(");
        write("symbol", dotOrBracket);
        
        if(dotOrBracket.equals("(")) {
            compileExpressionList();
            
            eat(")");
            write("symbol", ")");
        } else {
            if(!tokenizer.tokenType().equals(IDENTIFIER)) {
                throw new Exception("Expected to find identifier, but found " + current());
            }
            String subroutineName = current();
            eat(subroutineName);
            write("identifier", subroutineName);
            
            eat("(");
            write("symbol", "(");
            
            compileExpressionList();
            
            eat(")");
            write("symbol", ")");
        }
        
        eat(";");
        write("symbol", ";");
                
        writeEnd("doStatement");
    }
    public void compileLet() throws Exception {
        writeStart("letStatement");
        
        eat("let");
        write("keyword", "let");            
        
        if(tokenizer.tokenType().equals(IDENTIFIER)) {
            String varName = current();            
            eat(varName);
            write("identifier", varName);
        } else {
            throw new Exception("Expected to find variable name");
        }
        
        if(current().equals("[")) {            
            eat("[");
            write("symbol", "[");            
            
            compileExpression();
            
            eat("]");
            write("symbol", "]");            
        }        
       
        eat("=");
        write("symbol", "=");                            
        
        compileExpression();
        
        eat(";");
        write("symbol", ";");
            
        writeEnd("letStatement");        
    }
    public void compileWhile() throws Exception {
        writeStart("whileStatement");
        
        eat("while");
        write("keyword", "while");            
        
        eat("(");
        write("symbol", "(");            
        
        compileExpression();
                
        eat(")");
        write("symbol", ")");            
        
        eat("{");
        write("symbol", "{");
        
        compileStatements();
        
        eat("}");
        write("symbol", "}");
        
        writeEnd("whileStatement");    
    }
    public void compileReturn() throws Exception {
        writeStart("returnStatement");
        
        eat("return");
        write("keyword", "return");                            
        
        if(!current().equals(";")) {
            compileExpression();
        }         
        
        eat(";");
        write("symbol", ";");        
                
        writeEnd("returnStatement");
    }
    public void compileIf() throws Exception {
        writeStart("ifStatement");
        
        eat("if");
        write("keyword", "if");            
        
        eat("(");
        write("symbol", "(");            
        
        compileExpression();
                
        eat(")");
        write("symbol", ")");            
        
        eat("{");
        write("symbol", "{");
        
        compileStatements();
        
        eat("}");
        write("symbol", "}");

        if(current().equals("else")) {
            eat("else");
            write("keyword", "else");            
            
            eat("{");
            write("symbol", "{");

            compileStatements();

            eat("}");
            write("symbol", "}");
        }
        
        writeEnd("ifStatement");        
    }
    public void compileExpression() throws Exception {
        writeStart("expression");
        
        compileTerm();
                
        while(tokenizer.tokenType().equals(SYMBOL) && !current().equals(";") && !current().equals(")") && !current().equals("]") && !current().equals(",")) {
            String operation = current();
            eat("+", "-", "*", "/", "&", "|", "<", ">", "=");
            write("symbol", symbols.get(operation));   
            
            compileTerm();         
        }                        
        
        writeEnd("expression");
    }
    public void compileTerm() throws Exception {
        writeStart("term");
        
        String token = current();
        switch(tokenizer.tokenType()) {
            case INT_CONST: {                
                eat(token);
                write("integerConstant", token);
                break;
            }
            case STRING_CONST: {                
                eat(token);
                write("stringConstant", token.substring(1, token.length() - 1));
                break;
            }
            case KEYWORD: {                                
                eat("true", "false", "null", "this");
                write("keyword", token);
                break;
            }
            case SYMBOL: {
                if(token.equals("(")) {
                    eat("(");
                    write("symbol", "(");

                    compileExpression();

                    eat(")");
                    write("symbol", ")");
                } else {                    
                    eat("-", "~");
                    write("symbol", token);

                    compileTerm();
                }                
                break;
            }
            case IDENTIFIER: {
                eat(token);
                write("identifier", token);
                
                String nextToken = current();
                if(nextToken.equals("[")) {
                    eat("[");
                    write("symbol", "[");
                    
                    compileExpression();
                    
                    eat("]");
                    write("symbol", "]");
                } else if(nextToken.equals("(")) {
                    eat("(");
                    write("symbol", "(");
                    
                    compileExpressionList();
                    
                    eat(")");
                    write("symbol", ")");
                } else if(nextToken.equals(".")) {
                    eat(".");
                    write("symbol", ".");
                    
                    if(!tokenizer.tokenType().equals(IDENTIFIER)) {
                        throw new Exception("Expected to find subroutine name");
                    }
                    String subroutineName = current();
                    eat(subroutineName);
                    write("identifier", subroutineName);
                    
                    eat("(");
                    write("symbol", "(");
                    
                    compileExpressionList();
                    
                    eat(")");
                    write("symbol", ")");
                } 
                
                break;
            }
            default: {
                break;
            }
        }
        
        writeEnd("term");
    }
    public void compileExpressionList() throws Exception {
        writeStart("expressionList");
        
        while(isNextExpression()) {
            compileExpression();
            
            if(current().equals(",")) {
                eat(",");
                write("symbol", ",");
            }
        }
        
        writeEnd("expressionList");
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
    private void writeStart(String tag) throws Exception {
        writer.write(String.format("<%s>\n", tag));
    }
    private void writeEnd(String tag) throws Exception {
        writer.write(String.format("</%s>\n", tag));
    }
    private void write(String tag, String token) throws Exception {
        writer.write(String.format("<%1$s>\n%2$s\n</%1$s>", tag, token));
    }        
}
