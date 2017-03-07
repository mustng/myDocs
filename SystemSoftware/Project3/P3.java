
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class P3 {
	public static void main (String[] args){
		HashTable opTable = readOpTable("SicOps.txt");
		HashTable symTable = contructSymTable (args[0], opTable);
		symTable.printTable();
	}
	
	private static HashTable contructSymTable(String path, HashTable opTable){
		HashTable symTable = new HashTable(300); //This size is subject to change
		int address = 0;
		try {
			Scanner input = new Scanner (new File(path));
			boolean firstLine = true;
			Stack<String> literalStack = new Stack<String>();
			int lineNumber = 1;
			while(input.hasNextLine()){
				String line = input.nextLine();
				if (line.length() == 0){
					continue;
				}
				else if (line.charAt(0) == '.'){	// this is for comments
					continue;
				}
				String label = line.substring(0, 8);
				String ops = line.substring(9, 17);
				String operands = "", comment = "";
				if (line.length() >= 29) {
					operands = line.substring(18, 29);
					comment = line.substring(29);
				} else {
					operands = line.substring(18);
				}
				if (operands.charAt(0) == '='){
					literalStack.push(operands.trim());
				}
				if (firstLine){
					firstLine = false;
					if (ops.trim().equalsIgnoreCase("START")){
						address = Integer.parseInt(operands.trim(), 16);
					}
				}
				if (!label.trim().equals("")){
					String symbol = label.trim();
					if (symTable.add(symbol , address) == false){
						System.out.println("Error on line for a duplicate symbol");
						continue;
					}
				}
				System.out.printf("%3d  ", lineNumber);
				System.out.printf("%-6X   ", address);
				System.out.println(label + ops + operands + comment);
				HashObject opObj = opTable.find(ops.trim());
				if (opObj != null){
					address += opObj.getFormat();
				}
				else if (ops.trim().equalsIgnoreCase("RESW")){
					address = ((Integer.parseInt(operands.trim())) * 3) + address;
				}
				else if (ops.trim().equalsIgnoreCase("WORD")){
					address = address + 3;
				}
				else if (ops.trim().equalsIgnoreCase("RESB")){
					address = ((Integer.parseInt(operands.trim()))) + address;
				}
				else if (ops.trim().equalsIgnoreCase("BYTE")){
					int bytes = bCount(operands.trim());
					if (bytes == -1){
						System.out.println("Invalid Byte value at line " + lineNumber);
						continue;
					}
					else address += bytes;
				}
				else if (ops.trim().equalsIgnoreCase("LTORG")){
					int stackCount = 1;
					int bytes = 0;
					int temp = 0;
					while (!literalStack.empty()){
						String lit = literalStack.pop();
						bytes = bCount(lit.substring(1));
						temp = bytes;
						if (bytes != -1){
							symTable.add(lit, address);
							if (stackCount != 1){
								System.out.printf(" *%d* ", stackCount);
								System.out.printf("%-6X   ", address);
								address += bytes;
							}
							else{
							System.out.printf(" *%d* ", stackCount);
							System.out.printf("%-6X   ", address);
							address += temp;
							}
							System.out.print(lit);
							String lit1 = lit.substring(1);
							System.out.println(" BYTE " + lit1);
							stackCount++;
						}
						else{
							System.out.println("Invalid Byte values used");
							continue;
						}
					}
				}
				else if (ops.trim().equalsIgnoreCase("BASE")){
					// don't add anything to address
				}
				else if (ops.trim().equalsIgnoreCase("NOBASE")){
					// don't add anything to address
				}
				else if (ops.trim().equalsIgnoreCase("END")){
					int stackCount = 1;
					while (!literalStack.empty()){
						String lit = literalStack.pop();
						int bytes = bCount(lit.substring(1));
						if (bytes != -1){
							symTable.add(lit, address);
							System.out.printf(" *%d* ", stackCount);
							System.out.printf("%-6X   ", address);
							System.out.print(lit);
							String lit1 = lit.substring(1);
							System.out.println(" BYTE " + lit1);
							stackCount++;
							if (stackCount != 1){
								address += bytes;
							}
						}
						else{
							System.out.println("Invalid Byte values used");
							continue;
						}
					}
					break;
				}
				else if (!ops.trim().equalsIgnoreCase("START")){
					System.out.println("Error invalid numonic at line " + lineNumber);
				}
				lineNumber += 1;
			}
			
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return symTable;
	}
	
	private static int bCount(String literal){
		if (literal.charAt(0) == 'X'){
			String value =  literal.substring(2,literal.length()-1);
			if (value.length() % 2 == 0){
			return value.length()/2;
			}
			else
				return -1;
		}
		else if (literal.charAt(0) == 'C'){
			String value =  literal.substring(2,literal.length()-1);
			return value.length();
		}
		else 
			return -1;
	}
	
	private static HashTable readOpTable(String path){
		HashTable opTable = new HashTable(150);
		try {
			Scanner input = new Scanner (new File(path));
			while(input.hasNextLine()){
				String line = input.nextLine();
				String [] data = line.split("\\s+");
				opTable.add(data[0], Integer.parseInt(data[1], 16), Integer.parseInt(data[2]));
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opTable;
	}

	
	public static boolean isNumber(String name){
		char []temp = name.toCharArray();
		for (int i = 0; i < temp.length; i++) {		//returns true if there is a number in array
			int temp2 = (int) temp[i];
			if (temp2 > 47 && temp2 < 58 )
				return true;
			
		}
		return false;
	}
}

		
