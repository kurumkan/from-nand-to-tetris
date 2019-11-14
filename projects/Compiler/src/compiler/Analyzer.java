package compiler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Analyzer {
    public static void main(String[] args) {
        try {
            String inputPath = args[0].trim();
            File src = new File(inputPath);
            CompilationEngine engine;  
            Tokenizer tokenizer;
            BufferedWriter writer;
                    
            if(src.isDirectory()) {
                if(!new File(inputPath, "Main.jack").exists()) {
                    throw new Exception("Folder must contain Main.jack file");
                }
                
                String outputPath = (inputPath.endsWith("/") ? inputPath : inputPath + "/")  + src.getName() + ".xml";

                writer = new BufferedWriter(new FileWriter(outputPath));                
                File[] files = src.listFiles((d, fileName) -> fileName.endsWith(".jack"));
                        
                for(int i = 0; i < files.length; i += 1) {
                    File file = files[i];                    
                    parser = new Parser(file);
                    writer.setFileName(file.getName());
                    processCommands(parser, writer);
                }
            } else {
                String outputPath = inputPath.substring(0, inputPath.length() - 5) + ".xml";
                tokenizer = new Tokenizer(src);                
                writer = new BufferedWriter(new FileWriter(outputPath));    
                
                engine = new CompilationEngine(tokenizer, writer);                
                engine.compileClass();
            }           
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
