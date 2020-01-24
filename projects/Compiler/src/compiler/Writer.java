package compiler;

import java.io.BufferedWriter;

public abstract class Writer {
    public BufferedWriter writer;
    
    public Writer(BufferedWriter writer) {
        this.writer = writer;
    }
    
    public abstract void writePush(String label, int index) throws Exception;
    public abstract void writePop(String label, int index) throws Exception;
    public abstract void writeArithmetic(String operation) throws Exception;
    public abstract void writeUnary(String operation) throws Exception;
    public abstract void writeLabel(String label) throws Exception;
    public abstract void writeGoto(String label) throws Exception;
    public abstract void writeIf(String label) throws Exception;
    public abstract void writeCall(String name, int nArgs) throws Exception;
    public abstract void writeFunction(String name, int nLocals) throws Exception;
    public abstract void writeReturn() throws Exception;
    
    public void close() throws Exception {
        writer.close();
    };
    protected void write(String string) throws Exception {        
        writer.write(string + "\n");
    } 
}
