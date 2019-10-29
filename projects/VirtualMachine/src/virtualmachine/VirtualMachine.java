package virtualmachine;
import java.io.File;
import static virtualmachine.CommandTypes.*;

public class VirtualMachine {
    public static void main(String[] args) {
        try {
            String inputPath = args[0].trim();             
            
            System.out.println("Input path: " + inputPath);
            
            File src = new File(inputPath);
            CodeWriter writer;  
            Parser parser;
            
            if(src.isDirectory()) {
                if(!new File(inputPath, "Sys.vm").exists()) {
                    throw new Exception("Folder must contain Sys.vm file");
                }
                
                String outputPath = (inputPath.endsWith("/") ? inputPath : inputPath + "/")  + src.getName() + ".asm";

                writer = new CodeWriter(outputPath);                
                File[] files = src.listFiles((d, fileName) -> fileName.endsWith(".vm"));
                        
                for(int i = 0; i < files.length; i += 1) {
                    File file = files[i];                    
                    parser = new Parser(file);
                    writer.setFileName(file.getName());
                    processCommands(parser, writer);
                }                
            } else {
                String outputPath = inputPath.substring(0, inputPath.length() - 3) + ".asm";
                                
                parser = new Parser(new File(inputPath));
                writer = new CodeWriter(outputPath);
                processCommands(parser, writer);
            }       
            
            writer.close();            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
    
    public static void processCommands(Parser parser, CodeWriter writer) throws Exception {
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
                case C_GOTO: {
                    String label = parser.arg1();
                    writer.writeGoto(label);
                    System.out.println("goto");
                    break;
                }
                case C_IF: {
                    String label = parser.arg1();
                    writer.writeIf(label);
                    System.out.println("if-goto");
                    break;
                }
                case C_CALL: {
                    String functionName = parser.arg1();
                    int nArgs = parser.arg2();
                    writer.writeCall(functionName, nArgs);
                    System.out.println("call");
                    break;
                }
                case C_RETURN: {                        
                    writer.writeReturn();
                    System.out.println("return");
                    break;
                }
                case C_FUNCTION: {
                    String functionName = parser.arg1();
                    int nLocals = parser.arg2();
                    writer.writeFunction(functionName, nLocals);
                    System.out.println("function");
                    break;
                }
                default: {
                    break;
                }   
            }                
        }
    }    
}
