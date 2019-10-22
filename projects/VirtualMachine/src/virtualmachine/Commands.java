package virtualmachine;

import java.util.HashMap;

public class Commands {
    public static final HashMap<String, String>  arithmeticMap;
    public static final HashMap<String, String>  stackMap;
    
    static {
        // arithmetic operations
        arithmeticMap = new HashMap<String, String>();        
        arithmeticMap.put("add", 
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
        arithmeticMap.put("sub", 
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
        arithmeticMap.put("neg", 
            "// neg\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=-M\n"
        );
        arithmeticMap.put("eq",  
            "// eq\n" +
            "	@SP\n" +
            "	D=M-1\n" +
            "	@SP\n" +
            "	M=D \n" +
            "	@SP\n" +
            "	A=M\n" +
            "	D=M\n" +
            "	@SP  \n" +
            "	A=M-1\n" +
            "	D=M-D\n" +
            "	@EQ_%1$d\n" +
            "	D;JEQ\n" +
            "	@SP  \n" +
            "	A=M-1 \n" +
            "	M=0\n" +
            "	@END_EQ_%1$d\n" +
            "	0;JMP\n" +
            "(EQ_%1$d)	\n" +
            "	@SP  \n" +
            "	A=M-1 \n" +
            "	M=-1\n" +
            "(END_EQ_%1$d)\n"
        );
        arithmeticMap.put("gt",  
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
            "	@SP  \n" +
            "	A=M-1 \n" +
            "	M=0\n" +
            "	@END_GT_%1$d\n" +
            "	0;JMP\n" +
            "(GT_%1$d)\n" +
            "	@SP  \n" +
            "	A=M-1 \n" +
            "	M=-1\n" +
            "(END_GT_%1$d)\n"
        );
        arithmeticMap.put("lt",  
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
            "	@SP  \n" +
            "	A=M-1 \n" +
            "	M=0\n" +
            "	@END_LT_%1$d\n" +
            "	0;JMP\n" +
            "(LT_%1$d)\n" +
            "	@SP  \n" +
            "	A=M-1 \n" +
            "	M=-1\n" +
            "(END_LT_%1$d)\n"
        );
        arithmeticMap.put("and",  
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
        arithmeticMap.put("or",  
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
        arithmeticMap.put("not",  
            "// not\n" +
            "	@SP\n" +
            "	A=M-1\n" +
            "	M=!M\n"
        );
        
        
        // stack operations
        stackMap = new HashMap<String, String>();
        stackMap.put("push",  
            ""
        );
        stackMap.put("pop",  
            ""
        );
       
    }    
}
