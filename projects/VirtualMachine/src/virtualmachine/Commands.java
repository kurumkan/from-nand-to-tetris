package virtualmachine;

import java.util.HashMap;

public class Commands {
    public static final HashMap<String, String>  arithmeticCommands;
    public static final HashMap<String, String>  pushCommands;
    public static final HashMap<String, String>  popCommands;
    public static final HashMap<String, String>  branchingCommands;
    public static final HashMap<String, String>  functionCommands;    
    public static final String bootstrapCommand;
    
    private Commands() { } 
    
    static {    
        // arithmetic operations
        arithmeticCommands = new HashMap<String, String>();        
        arithmeticCommands.put("add", 
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
        arithmeticCommands.put("sub", 
            "// sub\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=M-D\n"
        );
        arithmeticCommands.put("neg", 
            "// neg\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=-M\n"
        );
        arithmeticCommands.put("eq",  
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
            "	@EQ_%1$d\n" +
            "	D;JEQ\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=0\n" +
            "	@END_EQ_%1$d\n" +
            "	0;JMP\n" +
            "(EQ_%1$d)\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=-1\n" +
            "(END_EQ_%1$d)\n"
        );
        arithmeticCommands.put("gt",  
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
            "	@GT_%1$d\n" +
            "	D;JGT\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=0\n" +
            "	@END_GT_%1$d\n" +
            "	0;JMP\n" +
            "(GT_%1$d)\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=-1\n" +
            "(END_GT_%1$d)\n"
        );
        arithmeticCommands.put("lt",  
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
            "	@LT_%1$d\n" +
            "	D;JLT\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=0\n" +
            "	@END_LT_%1$d\n" +
            "	0;JMP\n" +
            "(LT_%1$d)\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=-1\n" +
            "(END_LT_%1$d)\n"
        );
        arithmeticCommands.put("and",  
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
        arithmeticCommands.put("or",  
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
        arithmeticCommands.put("not",  
            "// not\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=!M\n"
        );
        
        
        // stack operations
        pushCommands = new HashMap<String, String>();        
        pushCommands.put("constant", 
            "// push constant %1$d\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n" 
        );
        pushCommands.put("argument", 
            "// push argument %1$d\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@ARG\n" +
            "	A=M+D\n" +
            "	D=M\n" +                
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +                
            "	@SP\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n"
        );
        pushCommands.put("local", 
            "// push local %1$d\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@LCL\n" +
            "	A=M+D\n" +
            "	D=M\n" +                
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +                
            "	@SP\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n"
        );
        pushCommands.put("this", 
            "// push this %1$d\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@THIS\n" +
            "	A=M+D\n" +
            "	D=M\n" +                
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +                
            "	@SP\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n"
        );
        pushCommands.put("that", 
            "// push that %1$d\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@THAT\n" +
            "	A=M+D\n" +
            "	D=M\n" +                
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +                
            "	@SP\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n"
        );
        pushCommands.put("pointer", 
            "// push pointer %1$d\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@R3\n" +
            "	A=A+D\n" +
            "	D=M\n" +                
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +                
            "	@SP\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n"
        );
        pushCommands.put("temp", 
            "// push temp %1$d\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@R5\n" +
            "	A=A+D\n" +
            "	D=M\n" +                
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +                
            "	@SP\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n"
        );
        pushCommands.put("static", 
            "// push static %1$d\n" +
            "   @%2$s.%1$d\n" +
            "   D=M\n" +
            "   @SP\n" +
            "   A=M\n" +
            "   M=D\n" +
            "   @SP\n" +
            "   D=M+1\n" +
            "   @SP\n" +
            "   M=D\n"
        );

        popCommands = new HashMap<String, String>();       
        popCommands.put("argument", 
            "// pop argument %1$d\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@ARG\n" +
            "	D=M+D\n" +
            "	@R13\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@R13\n" +
            "	A=M\n" +
            "	M=D\n"
        );
        popCommands.put("local", 
            "// pop local %1$d\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@LCL\n" +
            "	D=M+D\n" +
            "	@R13\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@R13\n" +
            "	A=M\n" +
            "	M=D\n"
        );
        popCommands.put("this", 
            "// pop this %1$d\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@THIS\n" +
            "	D=M+D\n" +
            "	@R13\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@R13\n" +
            "	A=M\n" +
            "	M=D\n"
        );
        popCommands.put("that", 
            "// pop that %1$d\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@THAT\n" +
            "	D=M+D\n" +
            "	@R13\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@R13\n" +
            "	A=M\n" +
            "	M=D\n"
        );
        popCommands.put("pointer", 
            "// pop pointer %1$d\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@R3\n" +
            "	D=A+D\n" +
            "	@R13\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@R13\n" +
            "	A=M\n" +
            "	M=D\n"
        );
        popCommands.put("temp", 
            "// pop temp %1$d\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@%1$d\n" +
            "	D=A\n" +
            "	@R5\n" +
            "	D=A+D\n" +
            "	@R13\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@R13\n" +
            "	A=M\n" +
            "	M=D\n"
        );
        popCommands.put("static",
            "// pop static %1$d\n" +
            "   @SP\n" +
            "   D=M-1\n" +
            "   @SP\n" +
            "   M=D\n" +            
            "   @SP\n" +
            "   A=M\n" +
            "   D=M\n" +
            "   @%2$s.%1$d\n" +
            "   M=D\n"
        );
        
        // branching commands
        branchingCommands = new HashMap<String, String>();        
        branchingCommands.put("label", 
            "// label %1$s\n" +
            "(%1$s)\n"
        );        
        branchingCommands.put("goto", 
            "// goto %1$s\n" +
            "	@%1$s\n" +
            "	0;JMP\n"
        );
        branchingCommands.put("if-goto", 
            "// if-goto %1$s\n" +
            "   @SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@%1$s\n" +
            "	D;JNE\n"
        );        
        
        // function commands
        functionCommands = new HashMap<String, String>();       
        
        functionCommands.put("call", 
            "// call %1$s %2$d\n" +
            "	@RETURN_ADDRESS_%3$d\n" +
            "	D=A\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +
            "	@LCL\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +
            "	@ARG\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +
            "	@THIS\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +
            "	@THAT\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +
            "	@5\n" +
            "	D=A\n" +
            "	@%2$d\n" +
            "	D=D+A\n" +
            "	@SP\n" +
            "	D=M-D\n" +
            "	@ARG\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	D=M\n" +
            "	@LCL\n" +
            "	M=D\n" +
            "	@%1$s\n" +
            "	0;JMP\n" +
            "(RETURN_ADDRESS_%3$d)\n"
        );        
        functionCommands.put("return", 
            "// return\n" +
            "	@LCL\n" +
            "	D=M\n" +
            "	@R14\n" +
            "	M=D\n" +            
            "	@R14\n" +
            "	D=M\n" +
            "	@5\n" +
            "	A=D-A\n" +
            "	D=M\n" +
            "	@R15\n" +
            "	M=D\n" +            
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@ARG\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@ARG\n" +
            "	D=M+1\n" +
            "	@SP\n" +
            "	M=D\n" +            
            "	@R14\n" +
            "	D=M\n" +            
            "	@1\n" +
            "	A=D-A\n" +
            "	D=M\n" +
            "	@THAT\n" +
            "	M=D\n" +            
            "	@R14\n" +
            "	D=M\n" +            
            "	@2\n" +
            "	A=D-A\n" +
            "	D=M\n" +
            "	@THIS\n" +
            "	M=D\n" +            
            "	@R14\n" +
            "	D=M\n" +            
            "	@3\n" +
            "	A=D-A\n" +
            "	D=M\n" +
            "	@ARG\n" +
            "	M=D\n" +            
            "	@R14\n" +
            "	D=M\n" +            
            "	@4\n" +
            "	A=D-A\n" +
            "	D=M\n" +
            "	@LCL\n" +
            "	M=D\n" +            
            "	@R15\n" +
            "	A=M\n" +
            "	0;JMP\n"
        );
        functionCommands.put("declareFunction", 
            "// function %1$s %2$d\n" +
            "(%1$s)\n"             
        );
        functionCommands.put("prepareLocalVars",
            "   @0\n" +
            "	D=A\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n"
        );        
        
        // Bootstrap code
        bootstrapCommand = 
            "// bootstrap\n" +
            "   @256\n" +
            "	D=A\n" +
            "	@SP\n" +
            "	M=D\n" +           
            "	@RETURN_ADDRESS_BOOTSTRAP\n" +
            "	D=A\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +            
            "	@LCL\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +            
            "	@ARG\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +            
            "	@THIS\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +            
            "	@THAT\n" +
            "	D=M\n" +
            "	@SP\n" +
            "	A=M\n" +
            "	M=D\n" +
            "	@SP\n" +
            "	M=M+1\n" +            
            "	@5\n" +
            "	D=A\n" +
            "	@n\n" +
            "	D=D+A\n" +
            "	@SP\n" +
            "	D=M-D\n" +
            "	@ARG\n" +
            "	M=D\n" +            
            "	@SP\n" +
            "	D=M\n" +
            "	@LCL\n" +
            "	M=D\n" +            
            "	@Sys.init\n" +
            "	0;JMP\n" +            
            "(RETURN_ADDRESS_BOOTSTRAP)\n"; 
    }    
}
