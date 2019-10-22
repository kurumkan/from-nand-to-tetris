package virtualmachine;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import static virtualmachine.Commands.*;

public class CodeWriter {    
    private BufferedWriter writer;
    private String inputFileName;
    private long commandCounter;
    
    CodeWriter(String outputFileName) throws Exception {
        this.writer = new BufferedWriter(new FileWriter(outputFileName));                 
    }
    
    public void setFileName(String fileName) {
        this.inputFileName = fileName;
    }
    
    public void writeArithmetic(String command) throws Exception {
        String result = arithmeticMap.get(command);
        if(result == null) {
            throw new Exception("Unknown command " + command);
        }        
                
        this.writer.write(String.format(result, this.commandCounter));
        this.commandCounter++;
    }
    
    public void writePushPop(String command, String segment, int index) throws Exception {
        if(command.equals("push") && segment.equals("constant")) {
            String result = String.format(
                "// push constant %d \n" +
                "	@%d\n" +
                "	D=A\n" +
                "	@SP\n" +
                "	A=M\n" +
                "	M=D\n" +
                "	@SP\n" +
                "	D=M+1\n" +
                "	@SP\n" +
                "	M=D\n", index, index);
            
            this.writer.write(result);
        }
    }
    
    public void close() throws Exception {
        this.writer.close();
    }
}

