package virtualmachine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import static virtualmachine.CommandTypes.*;

public class Parser {    
    private BufferedReader reader;
    private String nextCommand;
    private String currentCommand;    
    private String currentCommandArray [];
    
    private HashMap<String, String>  commandsMap;
    
    Parser(String filename) throws Exception {
        this.reader = new BufferedReader(new FileReader(filename)); 
        this.initCommandsMap();
    }
    
    private void initCommandsMap() {
        this.commandsMap = new HashMap<String, String>();
        
        this.commandsMap.put("add", C_ARITHMETIC);
        this.commandsMap.put("sub", C_ARITHMETIC);
        this.commandsMap.put("neg", C_ARITHMETIC);
        this.commandsMap.put("eq", C_ARITHMETIC);
        this.commandsMap.put("gt", C_ARITHMETIC);
        this.commandsMap.put("lt", C_ARITHMETIC);
        this.commandsMap.put("and", C_ARITHMETIC);
        this.commandsMap.put("or", C_ARITHMETIC);
        this.commandsMap.put("not", C_ARITHMETIC);
        
        this.commandsMap.put("push", C_PUSH);
        this.commandsMap.put("pop", C_POP);
        this.commandsMap.put("label", C_LABEL);
        this.commandsMap.put("goto", C_GOTO);
        this.commandsMap.put("if-goto", C_IF);
        this.commandsMap.put("function", C_FUNCTION);
        this.commandsMap.put("return", C_RETURN);
        this.commandsMap.put("call", C_CALL);        
    }
    
    public boolean hasMoreCommands() throws Exception {
        String line = "";
        
        while ((line = reader.readLine()) != null) {
            int commentStart = line.indexOf("//");            
            line = line.substring(0, commentStart < 0 ? line.length() : commentStart).trim();
            boolean isEmpty = line.isEmpty();            
            
            if (!isEmpty) {
                break;
            }            
        }        
         
        this.nextCommand = line;
        
        return this.nextCommand != null && !this.nextCommand.isEmpty();
    }
    
    public void advance() {
        this.currentCommand = this.nextCommand;        
        this.currentCommandArray = this.currentCommand.split(" ");
    }
    
    public String commandType() throws Exception {        
        String command = this.currentCommandArray[0];
        String type = this.commandsMap.get(command);
        
        if(type == null) {
            throw new Exception("Command does not exist: " + this.currentCommand);
        }
        
        return type;
    }
    
    public String arg1() throws Exception {        
        String type = this.commandType();
        
        if(type == "C_ARITHMETIC") {
            return this.currentCommand;
        }
        
        if(type == "C_RETURN") {
            throw new Exception("Return command cannot have arguments: " + this.currentCommand);
        }
        
        return this.currentCommandArray[1];
    }
    
    public int arg2() throws Exception {
        String type = this.commandType();
        
        if(type == "C_PUSH" || type == "C_POP" || type == "C_FUNCTION" || type == "C_CALL") {
            return Integer.parseInt(this.currentCommandArray[2]);
        }
        
        throw new Exception("Command cannot have more than 1 argument: " + this.currentCommand);
    }
}
