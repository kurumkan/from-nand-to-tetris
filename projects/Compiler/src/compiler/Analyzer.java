package compiler;
import java.io.File;

public class Analyzer {
    public static void main(String[] args) {
        try {
            String inputPath = args[0].trim();
            File src = new File(inputPath);
            CompilationEngine engine;  
            Tokenizer tokenizer;
            
            if(src.isDirectory()) {
                // handle directory input
            } else {
                String outputPath = inputPath.substring(0, inputPath.length() - 4) + ".xml";
                tokenizer = new Tokenizer(src);
                engine = new CompilationEngine(tokenizer, new File(outputPath));
//              processCommands(parser, writer);
            }       
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
}
