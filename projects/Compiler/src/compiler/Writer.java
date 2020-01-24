package compiler;

import java.io.BufferedWriter;

public abstract class Writer implements WriterContract {
    public BufferedWriter writer;
    
    public Writer(BufferedWriter writer) {
        this.writer = writer;
    }
    
    public void close() throws Exception {
        writer.close();
    };
    protected void write(String string) throws Exception {        
        writer.write(string + "\n");
    } 
}
