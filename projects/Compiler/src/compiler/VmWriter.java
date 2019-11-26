package compiler;

import java.io.BufferedWriter;

public class VmWriter {
    BufferedWriter writer;
    
    public VmWriter(BufferedWriter writer) {
        this.writer = writer;
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
        
        String command = String.format("push %s %d", segment, index);
        write(command);
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
        
        String command = String.format("pop %s %d", segment, index);
        write(command);
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

        write(command);
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
        
        write(command);
    }
    public void writeLabel(String label) throws Exception {
        String command = String.format("label %s", label);
        write(command);
    }
    public void writeGoto(String label) throws Exception {
        String command = String.format("goto %s", label);
        write(command);
    }
    public void writeIf(String label) throws Exception {
        String command = String.format("if-goto %s", label);
        write(command);
    }
    public void writeCall(String name, int nArgs) throws Exception {
        String command = String.format("call %s %d", name, nArgs);
        write(command);
    }
    public void writeFunction(String name, int nLocals) throws Exception {
        String command = String.format("function %s %d", name, nLocals);
        write(command);
    }
    public void writeReturn() throws Exception {        
        write("return");
    }
    public void close() throws Exception {
        writer.close();
    }
    private void write(String string) throws Exception {        
        writer.write(string + "\n");
    }    
}
