package compiler;

import java.util.HashMap;
import java.util.Map.Entry;

public class SymbolTable {
    private HashMap<String, SymbolTableElement> classScope;
    private HashMap<String, SymbolTableElement> subroutineScope;
    
    public SymbolTable() {
        classScope = new HashMap<String, SymbolTableElement>();        
    }
    public void startSubroutine() {
        subroutineScope = new HashMap<String, SymbolTableElement>();
    }
    public void define(String name, String type, String kind) throws Exception {
        HashMap<String, SymbolTableElement> scope;
        switch(kind) {
            case "STATIC":
            case "FIELD": {
                scope = classScope;
                break;
            }             
            case "ARG":
            case "VAR": 
            case "pointer": {
                scope = subroutineScope;
                break;
            }                        
            default: {
                throw new Exception("Unknown kind: " + kind);
            }
        }
        int index = varCount(kind);
        scope.put(name, new SymbolTableElement(type, kind, index));
    }
    public int varCount(String kind) throws Exception {
        HashMap<String, SymbolTableElement> scope;
        switch(kind) {
            case "STATIC":
            case "FIELD": {
                scope = classScope;
                break;
            }
            case "ARG":
            case "VAR":
            case "pointer": {
                scope = subroutineScope;
                break;
            }            
            default: {
                throw new Exception("Unknown kind: " + kind);
            }
        }
        
        int counter = 0;
        for (Entry<String, SymbolTableElement> current : scope.entrySet()) {
            if(current.getValue().getKind().equals(kind)) {
                counter += 1;
            }                    
        }
        
        return counter;                
    }
    public String kindOf(String name) {
        SymbolTableElement el = classScope.get(name);
        
        if(el == null) {
            el = subroutineScope.get(name);
        }
        
        if(el == null) {
            return null;
        }
        
        return el.getKind();
    }
    public String typeOf(String name) {
         SymbolTableElement el = classScope.get(name);
        
        if(el == null) {
            el = subroutineScope.get(name);
        }
        
        if(el == null) {
            return null;
        }
        
        return el.getType();
    }
    public int indexOf(String name) {
        SymbolTableElement el = classScope.get(name);
        
        if(el == null) {
            el = subroutineScope.get(name);
        }
        
        if(el == null) {
            return -1;
        }
        
        return el.getIndex();
    }
    public String toString() {
        String result = "";
        for (Entry<String, SymbolTableElement> current : classScope.entrySet()) {
            result += current.getKey() + ", " + current.toString() + "\n";
        }
        for (Entry<String, SymbolTableElement> current : subroutineScope.entrySet()) {
            result += current.getKey() + ", " + current.toString() + "\n";
        }
        return result;
    }
}
