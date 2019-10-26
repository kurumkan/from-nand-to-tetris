package virtualmachine;
import static virtualmachine.CommandTypes.*;

public class VirtualMachine {
    public static void main(String[] args) {
        try {
            String inputFile = args[0].trim();
            
            if(!inputFile.endsWith(".vm")) {
                throw new Error("Expected file type: *.vm");
            }
            
            String outputFile = inputFile.substring(0, inputFile.length() - 2) + "asm";

            System.out.println("Input file: " + inputFile);
            System.out.println("Output file: " + outputFile);
            
            Parser parser = new Parser(inputFile);            
            CodeWriter writer = new CodeWriter(outputFile);            
                    
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
                    case C_LABEL: {                        
                        String label = parser.arg1();                        
                        writer.writeLabel(label);
                        System.out.println("label");
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
