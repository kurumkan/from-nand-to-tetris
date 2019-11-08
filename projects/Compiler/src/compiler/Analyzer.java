package compiler;
import static compiler.TokenTypes.*;
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
            
            if(src.isDirectory()) {
                // handle directory input
            } else {
                String outputPath = inputPath.substring(0, inputPath.length() - 5) + "T.xml";
                tokenizer = new Tokenizer(src);
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));    
                writer.write("<tokens>");
                while(tokenizer.hasMoreTokens()) {                                        
                    tokenizer.advance();
//                    System.out.println(tokenizer.tokenType());
                    
                    switch(tokenizer.tokenType()) {
                        case KEYWORD: {
                            String token = tokenizer.keyWord();
                            writer.write(getResultString("keyword", token));                            
                            break;
                        }
                        case SYMBOL: {
                            char token = tokenizer.symbol();
                            writer.write(getResultString("symbol", token + ""));                            
                            break;
                        }
                        case INT_CONST: {
                            int token = tokenizer.intVal();
                            writer.write(getResultString("integerConstant", token + ""));                            
                            break;
                        }
                        case STRING_CONST: {
                            String token = tokenizer.stringVal();
                            writer.write(getResultString("stringConstant", token));                            
                            break;
                        }
                        case IDENTIFIER: {
                            String token = tokenizer.identifier();
                            writer.write(getResultString("identifier", token));                            
                            break;
                        }                        
                        default: {
                            break;
                        }   
                    }
                }
                writer.write("</tokens>");
                writer.close();
            }           
            
            
            
//            String inputPath = args[0].trim();
//            File src = new File(inputPath);
//            CompilationEngine engine;  
//            Tokenizer tokenizer;
//            
//            if(src.isDirectory()) {
//                // handle directory input
//            } else {
//                String outputPath = inputPath.substring(0, inputPath.length() - 4) + ".xml";
//                tokenizer = new Tokenizer(src);
//                engine = new CompilationEngine(tokenizer, new File(outputPath));
//            }       
        } catch(Exception e) {
            e.printStackTrace();
        }
    }    
    private static String getResultString(String tag, String token) {
        return String.format("<%1$s> %2$s </%1$s>", tag, token);
    }
}
