package virtualmachine;
import java.io.BufferedWriter;
import java.io.FileWriter;
import static virtualmachine.Commands.*;

public class CodeWriter {    
    private BufferedWriter writer;
    private String currentFile;
    private long commandCounter;
    
    CodeWriter(String outputFileName) throws Exception {
        this.writer = new BufferedWriter(new FileWriter(outputFileName));    
        this.writeInit();
    }
    
    public void setFileName(String fileName) {
        this.currentFile = fileName;
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
        
        if(segment.equals("static")) {
            result = String.format(asmTemplate, index, this.currentFile);
        } else {
            result = String.format(asmTemplate, index);
        }
        
        this.writer.write(result);
    }
    
    public void writeInit() throws Exception {
        this.writer.write(bootstrapCommand);        
    }
    
    public void writeLabel(String label) throws Exception {
        String result = branchingCommands.get("label");        
        this.writer.write(String.format(result, label));
    }
    
    public void writeGoto(String label) throws Exception {
        String result = branchingCommands.get("goto");
        this.writer.write(String.format(result, label));
    }
    
    public void writeIf(String label) throws Exception {
        String result = branchingCommands.get("if-goto");
        this.writer.write(String.format(result, label));
    }
    
    public void writeCall(String functionName, int numArgs) throws Exception {
        String result = functionCommands.get("call");
        this.writer.write(String.format(result, functionName, numArgs, this.commandCounter));
        this.commandCounter++;
    }
    
    public void writeReturn() throws Exception {
        String result = functionCommands.get("return");
        this.writer.write(result);
    }
    
    public void writeFunction(String functionName, int numLocals) throws Exception {
        String declare = String.format(functionCommands.get("declareFunction"), functionName, numLocals);
        String prepareLocalVars = functionCommands.get("prepareLocalVars");
        
        String result = declare;
        for(int i = 0; i < numLocals; i += 1) {
            result += prepareLocalVars;
        }
        
        this.writer.write(result);
    }
    
    public void close() throws Exception {
        this.writer.close();
    }
}

