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
                "// push constant %1$d \n" +
                "	@%1$d\n" +
                "	D=A\n" +
                "	@SP\n" +
                "	A=M\n" +
                "	M=D\n" +
                "	@SP\n" +
                "	D=M+1\n" +
                "	@SP\n" +
                "	M=D\n", index);
            
            this.writer.write(result);
        } else if(command.equals("push") || command.equals("pop")){
            String segmentAbbreviated = "";
            
            switch(segment) {
                case "argument": {
                    segmentAbbreviated = "ARG";
                    break;
                }
                case "local": {
                    segmentAbbreviated = "LCL";
                    break;
                }
                case "this": {
                    segmentAbbreviated = "THIS";
                    break;
                }
                case "that": {
                    segmentAbbreviated = "THAT";
                    break;
                }
                case "temp": {
                    segmentAbbreviated = "R5";
                    break;
                }
                default: {
                    throw new Exception("Unknown segment " + segment);
                }                    
            }
                
            String result = "";
            if(command.equals("push")) {
                result = String.format(
                "// %1$s %2$s %3$d \n" +
                "	@%3$d\n" +
                "	D=A\n" +
                "	@%2$s\n" +
                "	A=M+D\n" +
                "	D=M\n" +                
                "	@SP\n" +
                "	A=M\n" +
                "	M=D\n" +                
                "	@SP\n" +
                "	D=M+1\n" +
                "	@SP\n" +
                "	M=D\n", command, segmentAbbreviated, index);            
            } else {
                String operand = segment.equals("temp") ? "A" : "M";
                
                result = String.format(
                "// %1$s %2$s %3$d \n" +
                "	@SP\n" +
                "	D=M-1\n" +
                "	@SP\n" +
                "	M=D\n" +
                "	@%3$d\n" +
                "	D=A\n" +
                "	@%2$s\n" +
                "	D=%4$s+D\n" +
                "	@R13\n" +
                "	M=D\n" +
                "	@SP\n" +
                "	A=M\n" +
                "	D=M\n" +
                "	@R13\n" +
                "	A=M\n" +
                "	M=D\n", command, segmentAbbreviated, index, operand);
            }
            
            this.writer.write(result);
            System.out.println(command + " " + segmentAbbreviated + " " + index);            
        }
    }
    
    public void close() throws Exception {
        this.writer.close();
    }
}

