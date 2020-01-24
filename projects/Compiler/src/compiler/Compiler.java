package compiler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Compiler {
    public static void main(String[] args) {
        try {
            String inputPath = args[0].trim();
            File src = new File(inputPath);
            String outputType = args[1].trim();
            
            CompilationEngine engine;  
            Tokenizer tokenizer;            
                    
            if(src.isDirectory()) {
                if(!new File(inputPath, "Main.jack").exists()) {
                    throw new Exception("Folder must contain Main.jack file");
                }
                
                BufferedWriter bufferedWriter;
                Writer writer;
                
                File[] files = src.listFiles((d, fileName) -> fileName.endsWith(".jack"));
                        
                for(int i = 0; i < files.length; i += 1) {                    
                    File file = files[i];                    
                    tokenizer = new Tokenizer(file);         
                    String filePath = file.getAbsolutePath();
                    String outputPath = filePath.substring(0, filePath.length() - 5) + "." + outputType;
                    bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
                    writer = outputType.equals("xml") ? new XmlWriter(bufferedWriter) : new VmWriter(bufferedWriter);
                    engine = new CompilationEngine(tokenizer, writer);                
                    engine.compileClass();                    
                    writer.close();
                }
            } else {
                String outputPath = inputPath.substring(0, inputPath.length() - 5) + "." + outputType;
                tokenizer = new Tokenizer(src);                
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath)); 
                Writer writer = outputType.equals("xml") ? new XmlWriter(bufferedWriter) : new VmWriter(bufferedWriter);
                engine = new CompilationEngine(tokenizer, writer);                
                engine.compileClass();                
                writer.close();
            }                       
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
