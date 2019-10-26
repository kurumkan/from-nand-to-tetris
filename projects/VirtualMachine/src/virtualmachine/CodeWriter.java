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
        String result = arithmeticCommands.get(command);
        if(result == null) {
            throw new Exception("Unknown command " + command);
        }        
                
        this.writer.write(String.format(result, this.commandCounter));
        this.commandCounter++;
    }
    
    public void writePushPop(String command, String segment, int index) throws Exception {        
        String result = "";
        String asmTemplate = "";
        
        if(command.equals("push")) {
            asmTemplate = pushCommands.get(segment);            
        } else if(command.equals("pop")) {
            asmTemplate = popCommands.get(segment);            
        } else {
            throw new Exception("Unknown command: " + command + " " + segment + " " + index);
        }
        
        result = String.format(asmTemplate, index);
        this.writer.write(result);
    }
    
    public void writeInit() {
    }
    
    public void writeLabel(String label) throws Exception {
        String result = branchingCommands.get("label");        
        this.writer.write(String.format(result, label));
    }
    
    public void writeGoto(String label) throws Exception {
        String result = branchingCommands.get("goto");
        this.writer.write(String.format(result, label));
    }
    
    public void writeIf(String label) {
    }
    
    public void writeCall(String functionName, int numArgs) {
    }
    
    public void writeReturn() {
    }
    
    public void writeFunction(String functionName, int numLocals) {
    }
    
    public void close() throws Exception {
        this.writer.close();
    }
}

