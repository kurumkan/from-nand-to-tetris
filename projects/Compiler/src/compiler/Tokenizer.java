package compiler;
import java.io.File;

public class Tokenizer {
    // Removes all space characters from input stream
    // Converts them into tokens according to the grammar
    public Tokenizer(File inputFile) {
        
    }
    public boolean hasMoreTokens() {
        return true;
    }
    public void advance() {
    
    }
    public String tokenType() {
        return "KEYWORD";
    }
    public String keyWord() {
        return "CLASS";
    }
    public char symbol() {
        return 'C';
    }
    public String identifier() {
        return "someid";
    }
    public int intVal() {
        return 0;
    }
    public String stringVal() {
        return "somestring";
    }
}
