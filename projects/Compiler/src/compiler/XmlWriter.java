package compiler;

import java.io.BufferedWriter;

public class XmlWriter extends Writer {    
    public XmlWriter(BufferedWriter writer) throws Exception {
        super(writer);
        writeStart("code");
    }
    public void writePush(String label, int index) throws Exception {
        String segment = "";
        switch(label) {
            case "STATIC": {
                segment = "static";
                break;
            }
            case "FIELD": {
                segment = "this";
                break;
            }
            case "VAR": {
                segment = "local";
                break;
            }
            case "ARG": {
                segment = "argument";
                break;
            }            
            case "constant": 
            case "temp": 
            case "that": 
            case "pointer": {
                segment = label;
                break;
            }             
            default: {
                throw new Exception("Unknown segment " + label);
            }
        }
       
        write("push", segment);
    }
    public void writePop(String label, int index) throws Exception {
        String segment = "";
        switch(label) {
            case "STATIC": {
                segment = "static";
                break;
            }
            case "FIELD": {
                segment = "this";
                break;
            }
            case "VAR": {
                segment = "local";
                break;
            }
            case "ARG": {
                segment = "argument";
                break;
            }
            case "constant": 
            case "that": 
            case "temp": 
            case "pointer": {
                segment = label;
                break;
            }             
            default: {
                throw new Exception("Unknown segment " + label);
            }
        }
        
        write("pop", segment);
    }
    public void writeArithmetic(String operation) throws Exception {
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
            case "add":
            case "not":
            case "neg": {
                command = operation;
                break;
            }
            default: {                    
                throw new Exception("Illegal operation " + operation);
            }
        }                        

        write("arithmetic", command);
    }
    public void writeUnary(String operation) throws Exception {
        String command = "";
        switch(operation) {
            case "-": {
                command = "neg";
                break;
            }
            case "~": {
                command = "not";                    
                break;
            }
            default: {
                throw new Exception("Illegal operation " + operation);
            }
        }
        
        write("unary", command);
    }
    public void writeLabel(String label) throws Exception {
        write("label", label);
    }
    public void writeGoto(String label) throws Exception {
        write("goto", label);
    }
    public void writeIf(String label) throws Exception {
        write("if", label);
    }
    public void writeCall(String name, int nArgs) throws Exception {
        write("call", name);
    }
    public void writeFunction(String name, int nLocals) throws Exception {
        write("function", name);
    }
    public void writeReturn() throws Exception {        
        write("return");
    }
    @Override
    public void close() throws Exception {
        writeEnd("code");
        
        super.close();
    };
    protected void write(String tag, String content) throws Exception {        
        super.write("<" + tag + ">" + content + "</" + tag + ">");
    }    
    protected void write(String tag) throws Exception {
        super.write("<" + tag + " />");
    }  
    protected void writeStart(String tag) throws Exception {
        super.write("<" + tag + ">");
    }  
    protected void writeEnd(String tag) throws Exception {
        super.write("</" + tag + ">");
    }  
}
