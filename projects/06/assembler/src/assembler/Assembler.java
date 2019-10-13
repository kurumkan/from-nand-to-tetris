package assembler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Assembler {
    public static void main(String[] args) throws Exception {        
        String inputFile = "../pong/Pong.asm";
        String outputFile = "../pong/Pong.hack";
        
        HashMap<String, String> reservedWordsMap = new HashMap<String, String>();
        reservedWordsMap.put("SP", "0");
        reservedWordsMap.put("LCL", "1");
        reservedWordsMap.put("ARG", "2");
        reservedWordsMap.put("THIS", "3");
        reservedWordsMap.put("THAT", "4");
        reservedWordsMap.put("SCREEN", "16384");
        reservedWordsMap.put("KBD", "24576");
        reservedWordsMap.put("R0", "0");
        reservedWordsMap.put("R1", "1");
        reservedWordsMap.put("R2", "2");
        reservedWordsMap.put("R3", "3");
        reservedWordsMap.put("R4", "4");
        reservedWordsMap.put("R5", "5");
        reservedWordsMap.put("R6", "6");
        reservedWordsMap.put("R7", "7");
        reservedWordsMap.put("R8", "8");
        reservedWordsMap.put("R9", "9");
        reservedWordsMap.put("R10", "10");
        reservedWordsMap.put("R11", "11");
        reservedWordsMap.put("R12", "12");
        reservedWordsMap.put("R13", "13");
        reservedWordsMap.put("R14", "14");
        reservedWordsMap.put("R15", "15");
        
        HashMap<String, String> destinationMap = new HashMap<String, String>();
        destinationMap.put("M", "001");
        destinationMap.put("D", "010");
        destinationMap.put("MD", "011");
        destinationMap.put("A", "100");
        destinationMap.put("AM", "101");
        destinationMap.put("AD", "110");
        destinationMap.put("AMD", "111");       
        
        HashMap<String, String> jumpMap = new HashMap<String, String>();
        jumpMap.put("JGT", "001");
        jumpMap.put("JEQ", "010");
        jumpMap.put("JGE", "011");
        jumpMap.put("JLT", "100");
        jumpMap.put("JNE", "101");
        jumpMap.put("JLE", "110");
        jumpMap.put("JMP", "111");        
        
        HashMap<String, String> compMap = new HashMap<String, String>();
        compMap.put("0", "101010");
        compMap.put("1", "111111");
        compMap.put("-1", "111010");
        compMap.put("D", "001100");
        compMap.put("A", "110000");
        compMap.put("M", "110000");
        compMap.put("!D", "001101");
        compMap.put("!A", "110001");
        compMap.put("!M", "110001");
        compMap.put("-D", "001111");
        compMap.put("-A", "110011");
        compMap.put("-M", "110011");
        compMap.put("D+1", "011111");
        compMap.put("A+1", "110111");
        compMap.put("M+1", "110111");
        compMap.put("D-1", "001110");
        compMap.put("A-1", "110010");
        compMap.put("M-1", "110010");
        compMap.put("D+A", "000010");
        compMap.put("D+M", "000010");
        compMap.put("D-A", "010011");
        compMap.put("D-M", "010011");
        compMap.put("A-D", "000111");
        compMap.put("M-D", "000111");
        compMap.put("D&A", "000000");
        compMap.put("D&M", "000000");
        compMap.put("D|A", "010101");
        compMap.put("D|M", "010101");        

	String result = "";              
        
        ArrayList<String> lines = new ArrayList<>();
        
        HashMap<String, String> labelsMap = new HashMap<String, String>();        
        
        String line = "";
        
        BufferedReader reader = new BufferedReader(new FileReader(inputFile)); 
        
        int lineCounter = 0;
                
        while ((line = reader.readLine()) != null) {
            int commentStart = line.indexOf("//");            
            line = line.substring(0, commentStart < 0 ? line.length() : commentStart).trim();
            boolean isEmpty = line.isEmpty();            
            
            if (isEmpty) {
                continue;
            }            
            
            if(line.startsWith("@")) {
                String suffix = line.substring(1);                
                String labelValue = reservedWordsMap.get(suffix);                
                
                if(labelValue != null) {
                    // replace reserved constants with their respective values                    
                    line = "@" + labelValue;
                } 
            }
            
            // add the label to the table
            if(line.startsWith("(") && line.endsWith(")")) {
                String label = line.substring(1, line.length() - 1);
                labelsMap.put(label, lineCounter + "");
                continue;
            }
            
            lines.add(line);
            lineCounter += 1;            
        }        
        reader.close();
    
        HashMap<String, String> variablesMap = new HashMap<String, String>();
        int nextVariableAddress = 16;
        
	for (String command : lines) {
            boolean isAInstruction = command.startsWith("@");

            if (isAInstruction) {		                                                 
                String value = command.substring(1);
                boolean isVariable = !value.matches("-?\\d+");
                String label = labelsMap.get(value);                
                
                if(label != null) {
                    value = label;
                } else if(isVariable) {
                    // add variable to table                    
                    String variableValue = variablesMap.get(value);
                    
                    if(variableValue != null) {
                        value = variableValue;
                    } else {                        
                        variablesMap.put(value, nextVariableAddress + "");
                        value = nextVariableAddress + "";
                        
                        nextVariableAddress += 1;
                    }
                }               
                
                
                
                
                int decimal = Integer.parseInt(value);       
                String binary = Integer.toString(decimal, 2);
                binary = String.format("%016d", Long.parseLong(binary));               
                
                result = result + binary + "\n";                    
            } else {
                String addressBit = "";
                String comp = "";
                String destination = "";
                String jump = "";				

                int eqPos = command.indexOf("=");
                int colPos = command.indexOf(";");

                boolean hasDestination = eqPos != -1;
                boolean shouldJump = colPos != -1;

                if (hasDestination) {
                    String destinationSubstring = command.substring(0, eqPos);
                    
                    destination = destinationMap.get(destinationSubstring);
                    
                    if(destination == null) {
                        System.out.println("Error[destination]: " + command + " - is not a valid command \n");
                    }
                } else {
                    destination = "000";
                }

                if (shouldJump) {
                    String jumpSubstring = command.substring(colPos + 1);
                    
                    jump = jumpMap.get(jumpSubstring);
                   
                    if(jump == null) {
                        System.out.println("Error[jump]: " + command + " - is not a valid command \n");
                    }
                } else {
                    jump = "000";
                }
                
                int start = eqPos > -1 ? eqPos + 1 : 0;
                int end = colPos > eqPos ? colPos : command.length();
                
                String compSubstring = command.substring(start, end);

                addressBit = compSubstring.contains("M") ? "1": "0";
                    
                comp = compMap.get(compSubstring);
                
                if(comp == null) {
                    System.out.println("Error[comp]: " + command + " - is not a valid command \n");
                }

                String binary = "111" + addressBit + comp + destination + jump;                
                result = result + binary + "\n";                
            }
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(result);

        writer.close();    
    }
}
