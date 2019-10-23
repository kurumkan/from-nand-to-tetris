package virtualmachine;
import static virtualmachine.CommandTypes.*;

public class VirtualMachine {
    public static void main(String[] args) {
        try {
//            Parser parser = new Parser("../07/StackArithmetic/StackTest/StackTest.vm");            
//            CodeWriter writer = new CodeWriter("../07/StackArithmetic/StackTest/StackTest.asm");
//            Parser parser = new Parser("../07/StackArithmetic/SimpleAdd/SimpleAdd.vm");            
//            CodeWriter writer = new CodeWriter("../07/StackArithmetic/SimpleAdd/SimpleAdd.asm");

            Parser parser = new Parser("../07/MemoryAccess/BasicTest/BasicTest.vm");            
            CodeWriter writer = new CodeWriter("../07/MemoryAccess/BasicTest/BasicTest.asm");            
//            
//            Parser parser = new Parser("../07/MemoryAccess/PointerTest/PointerTest.vm");            
//            CodeWriter writer = new CodeWriter("../07/MemoryAccess/PointerTest/PointerTest.asm");            
            
//            Parser parser = new Parser("../07/MemoryAccess/StaticTest/StaticTest.vm");            
//            CodeWriter writer = new CodeWriter("../07/MemoryAccess/StaticTest/StaticTest.asm");            
            
            while(parser.hasMoreCommands()) {
                parser.advance();
                
                switch(parser.commandType()) {
                    case C_ARITHMETIC: {
                        String command = parser.arg1();
                        writer.writeArithmetic(command);
                        System.out.println(command);
                        break;
                    }
                    case C_PUSH: {
                        String command = "push";
                        String segment = parser.arg1();
                        int index = parser.arg2();
                        writer.writePushPop(command, segment, index);
                        System.out.println(command);
                        break;
                    }                    
                    case C_POP: {
                        String command = "pop";
                        String segment = parser.arg1();
                        int index = parser.arg2();
                        writer.writePushPop(command, segment, index);
                        System.out.println(command);
                        break;
                    }
                    default: {
                        break;
                    }   
                }                
            }
            
            writer.close();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
