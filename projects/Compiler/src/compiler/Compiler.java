package compiler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Compiler {
    public static void main(String[] args) {
        try {
            String inputPath = args[0].trim();
            File src = new File(inputPath);
            CompilationEngine engine;  
            Tokenizer tokenizer;            
                    
            if(src.isDirectory()) {
                if(!new File(inputPath, "Main.jack").exists()) {
                    throw new Exception("Folder must contain Main.jack file");
                }
                
                BufferedWriter writer;
                VmWriter vmWriter;
                
                File[] files = src.listFiles((d, fileName) -> fileName.endsWith(".jack"));
                        
                for(int i = 0; i < files.length; i += 1) {                    
                    File file = files[i];                    
                    tokenizer = new Tokenizer(file);         
                    String filePath = file.getAbsolutePath();
                    String outputPath = filePath.substring(0, filePath.length() - 5) + ".vm";;
                    writer = new BufferedWriter(new FileWriter(outputPath));
                    vmWriter = new VmWriter(writer);
                    engine = new CompilationEngine(tokenizer, vmWriter);                
                    engine.compileClass();                    
                    writer.close();
                    vmWriter.close();
                }
            } else {
                String outputPath = inputPath.substring(0, inputPath.length() - 5) + ".vm";
                tokenizer = new Tokenizer(src);                
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath)); 
                VmWriter vmWriter = new VmWriter(writer);
                engine = new CompilationEngine(tokenizer, vmWriter);                
                engine.compileClass();                
                writer.close();
                vmWriter.close();
            }                       
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
