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
    
    public void close() throws Exception {
        this.writer.close();
    }
}

