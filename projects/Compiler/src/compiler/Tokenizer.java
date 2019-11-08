package compiler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import static compiler.KeyWords.keyWords;
import static compiler.Symbols.symbols;
import static compiler.TokenTypes.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    // Removes all space characters from input stream
    // Converts them into tokens according to the grammar
    private BufferedReader reader;
    private String nextToken;
    private String currentToken;    
        
    private List<String>  currentTokenArray;    
    
    public Tokenizer(File inputFile) throws Exception {
        this.reader = new BufferedReader(new FileReader(inputFile));  
        currentTokenArray = new LinkedList<String>();
    }
    public boolean hasMoreTokens() throws Exception {        
        if(this.currentTokenArray.isEmpty()) {            
            String line = "";
            
            boolean mutlilineCommentOpened = false;
            while ((line = reader.readLine()) != null) {
                int commentStart = line.indexOf("//");            
                line = line.substring(0, commentStart < 0 ? line.length() : commentStart).trim();
                
                commentStart = line.indexOf("/*");            
                line = line.substring(0, commentStart < 0 ? line.length() : commentStart).trim();
                                
                if (!line.isEmpty()) {                    
                    break;
                }            
            }                    
            
            if(line == null) {
                return false;
            }
            
            String nextChunk = line;                       
            while (nextChunk.length() > 0){                
                String first = nextChunk.substring(0);
                if(symbols.get(first) != null) {
                    currentTokenArray.add(first);
                    nextChunk = nextChunk.substring(1);
                    continue;
                }
                
                String patternStr = "[A-Za-z0-9_]{1,}";
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(nextChunk);
                
                if(!matcher.find()){
                    nextChunk = "";
                    continue;
                }

                String token = nextChunk.substring(matcher.start(), matcher.end());
                currentTokenArray.add(token);
                
                nextChunk = nextChunk.substring(matcher.end());
            }
        }
        
        this.nextToken = this.currentTokenArray.remove(0);
        System.out.println(this.nextToken);
        
        return this.nextToken != null;
    }
    public void advance() {
        this.currentToken = this.nextToken; 
    }
    public String tokenType() throws Exception {        
        if(keyWords.get(this.currentToken) != null) {
            return KEYWORD;
        }
        if(symbols.get(this.currentToken) != null) {
            return SYMBOL;
        }
        if(this.currentToken.matches("-?\\d+(\\.\\d+)?")) {
            return INT_CONST;
        }
        if(this.currentToken.startsWith("\"") && this.currentToken.endsWith("\"")) {
            return STRING_CONST;
        }        
        if(this.currentToken.matches("[A-Za-z][A-Za-z0-9_]*")) {
            return IDENTIFIER;
        }        
        
        throw new Exception("Invalid token: " + this.currentToken);
    }
    public String keyWord() {
        return this.currentToken;
    }
    public char symbol() {
        return this.currentToken.charAt(0);
    }    
    public int intVal() {
        return Integer.parseInt(this.currentToken);
    }
    public String stringVal() {
        return this.currentToken;
    }
    public String identifier() {
        return this.currentToken;
    }    
}
