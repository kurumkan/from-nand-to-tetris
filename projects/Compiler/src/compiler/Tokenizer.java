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
                line = line.trim();
                if(line.isEmpty()) {
                    continue;
                }
                System.out.println(line);
                
                if(mutlilineCommentOpened) {
                    int multiLineCommentEnd = line.indexOf("*/");
                    if(multiLineCommentEnd < 0) {
                        continue;
                    }
                    mutlilineCommentOpened = false;
                    line = line.substring(multiLineCommentEnd + 2).trim();
                }
                
                int singleCommentStart = line.indexOf("//");            
                int multilineCommentStart = line.indexOf("/*");      
                
                if(singleCommentStart > -1 && multilineCommentStart > -1) {
                    if(singleCommentStart < multilineCommentStart) {
                        line = line.substring(0, singleCommentStart).trim();
                    } else {
                        int multiLineCommentEnd = line.indexOf("*/");
                        if(multiLineCommentEnd < 0) {
                            line = line.substring(0, multilineCommentStart).trim();
                            mutlilineCommentOpened = true;
                        } else {
                            line = line.substring(0, multilineCommentStart).trim() + line.substring(multiLineCommentEnd + 2).trim();                        
                        }
                    }
                } else if(singleCommentStart > -1 && multilineCommentStart < 0) {
                    line = line.substring(0, singleCommentStart).trim();                    
                } else if(singleCommentStart < 0 && multilineCommentStart > -1) {
                    int multiLineCommentEnd = line.indexOf("*/");
                    if(multiLineCommentEnd < 0) {
                        line = line.substring(0, multilineCommentStart).trim();
                        mutlilineCommentOpened = true;
                    } else {
                        line = line.substring(0, multilineCommentStart).trim() + line.substring(multiLineCommentEnd + 2).trim();                        
                    }
                }
                                
                if (!line.isEmpty()) {                    
                    break;
                } 
            }                    
            
            if(line == null) {                
                return false;
            }
            
            String nextChunk = line;                       
            while (nextChunk.length() > 0){                                
                String patternStr = "\"([^\"]*)\"{1,}|[A-Za-z0-9_]{1,}|[^A-Za-z0-9_\\s]{1}";
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
        
        if(currentTokenArray.isEmpty()) {            
            return true;
        }
        
        this.nextToken = this.currentTokenArray.remove(0);
        System.out.println("TOKEN" +this.nextToken);
        
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
