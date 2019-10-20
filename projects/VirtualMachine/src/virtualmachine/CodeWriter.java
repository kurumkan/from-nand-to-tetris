package virtualmachine;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

public class CodeWriter {    
    private BufferedWriter writer;
    private String inputFileName;
    
    private HashMap<String, String>  arithmeticMap;
    private HashMap<String, String>  stackMap;    
    
    CodeWriter(String outputFileName) throws Exception {
        this.writer = new BufferedWriter(new FileWriter(outputFileName)); 
        this.initCommandsMap();
    }
    
    private void initCommandsMap() {
        // arithmetic operations
        this.arithmeticMap = new HashMap<String, String>();        
        this.arithmeticMap.put("add", 
            "// add\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=D+M\n"
        );
        this.arithmeticMap.put("sub", 
            "// sub\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D \n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=M-D\n"
        );
        this.arithmeticMap.put("neg", 
            "// neg\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=-M\n"
        );
        this.arithmeticMap.put("eq",  
            "// eq\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	D=M-D\n" +
            "	@EQ\n" +
            "	D;JEQ\n" +
            "	A\n" +
            "	M=0\n" +
            "	@END_EQ\n" +
            "	0;JMP\n" +
            "(EQ)\n" +
            "	A\n" +
            "	M=-1\n" +
            "(END_EQ)\n"
        );
        this.arithmeticMap.put("gt",  
            "// gt\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	D=M-D\n" +
            "	@GT\n" +
            "	D;JGT\n" +
            "	A\n" +
            "	M=0\n" +
            "	@END_GT\n" +
            "	0;JMP\n" +
            "(GT)\n" +
            "	A\n" +
            "	M=-1\n" +
            "(END_GT)\n"
        );
        this.arithmeticMap.put("lt",  
            "// lt\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	D=M-D\n" +
            "	@LT\n" +
            "	D;JLT\n" +
            "	A\n" +
            "	M=0\n" +
            "	@END_LT\n" +
            "	0;JMP\n" +
            "(LT)\n" +
            "	A\n" +
            "	M=-1\n" +
            "(END_LT)\n"
        );
        this.arithmeticMap.put("and",  
            "// and\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=D&M\n"
        );
        this.arithmeticMap.put("or",  
            "// or\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=D|M\n"
        );
        this.arithmeticMap.put("not",  
            "// not\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=!M\n"
        );
        
        
        // stack operations
        this.stackMap = new HashMap<String, String>();
        this.stackMap.put("push",  
            ""
        );
        this.stackMap.put("pop",  
            ""
        );
       
    }
    
    public void setFileName(String fileName) {
        this.inputFileName = fileName;
    }
    
    public void writeArithmetic(String command) throws Exception {
        String result = this.arithmeticMap.get(command);
        if(result == null) {
            throw new Exception("Unknown command " + command);
        }
        
        this.writer.write(result);
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

