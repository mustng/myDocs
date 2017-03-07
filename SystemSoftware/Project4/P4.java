
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class P4 {
	private static ArrayList<Integer> addresses;
	private static final String TEMP_FILE = "temp.txt";
	private static int numBangs = 0;
	private static ArrayList<Integer> startingAddresses;
	private static int codeStart = -1;
	
	public static void main (String[] args){
		addresses = new ArrayList<Integer>();
		startingAddresses = new ArrayList<Integer>();
		
		HashTable opTable = readOpTable("SicOps.txt");
		HashTable symTable = contructSymTable (args[0], opTable);
//		symTable.printTable();
		makeObj(symTable, opTable, args[0]);
	}
	
	private static HashTable contructSymTable(String path, HashTable opTable){
		HashTable symTable = new HashTable(300); //This size is subject to change
		int address = 0;
		try {
			Scanner input = new Scanner (new File(path));
			PrintWriter writer = new PrintWriter(new File(TEMP_FILE));
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
				String ops = "";
				String operands = "", comment = "";
				if (line.length() >= 17){
					ops = line.substring(8, 17);
					if (line.length() >= 29) {
						operands = line.substring(17, 29);
						comment = line.substring(29);
					} else {
						operands = line.substring(17);
					}
				}
				else {
					ops = line.substring(8);
				}
				if (operands.trim().length() > 0 && operands.trim().charAt(0) == '='){
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
//						System.out.println("Error on line for a duplicate symbol");
						continue;
					}
				}
				addresses.add(address);
//				System.out.printf("%3d  ", lineNumber);
//				System.out.printf("%-6X   ", address);
//				System.out.println(label + ops + operands + comment);
				writer.println(label + ops + operands + comment);
				HashObject opObj = opTable.find(ops.trim());
				if (opObj != null){
					address += opObj.getFormat();
				}
				else if (ops.trim().equalsIgnoreCase("RESW")){
					numBangs++;
					address = ((Integer.parseInt(operands.trim())) * 3) + address;
					startingAddresses.add(address);
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
//						System.out.println("Invalid Byte value at line " + lineNumber);
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
							addresses.add(address);
							symTable.add(lit, address);
							if (stackCount != 1){
//								System.out.printf(" *%d* ", stackCount);
//								System.out.printf("%-6X   ", address);
								address += bytes;
							}
							else{
//							System.out.printf(" *%d* ", stackCount);
//							System.out.printf("%-6X   ", address);
							address += temp;
							}
							while (lit.length() < 8)
								lit += " ";
//							System.out.print(lit);
							writer.print(lit);
							String lit1 = lit.substring(1);
//							System.out.println("  BYTE    " + lit1);
							writer.println("  BYTE    " + lit1);
							stackCount++;
						}
						else{
//							System.out.println("Invalid Byte values used");
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
					if (!operands.trim().equals("")){
						codeStart = symTable.find(operands.trim()).value;
					}
					while (!literalStack.empty()){
						String lit = literalStack.pop();
						int bytes = bCount(lit.substring(1));
						if (bytes != -1){
							addresses.add(address);
							symTable.add(lit, address);
//							System.out.printf(" *%d* ", stackCount);
//							System.out.printf("%-6X   ", address);
							while (lit.length() < 8)
								lit += " ";
//							System.out.print(lit);
							writer.print(lit);
							String lit1 = lit.substring(1);
//							System.out.println("  BYTE    " + lit1);
							writer.println("  BYTE    " + lit1);
							stackCount++;
							if (stackCount != 1){
								address += bytes;
							}
						}
						else{
//							System.out.println("Invalid Byte values used");
							continue;
						}
					}
					break;
				}
				else if (!ops.trim().equalsIgnoreCase("START")){
//					System.out.println("Error invalid numonic at line " + lineNumber);
				}
				lineNumber += 1;
			}
			writer.close();
			input.close();
		} catch (FileNotFoundException e) {
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
	
	//////////////////// PASS 2////////////////////////////////////////////////////////
	public static void makeObj ( HashTable symTable, HashTable opTable, String fileName){
		int start = 0;
		int startNum = 0;
		try {
			Scanner input = new Scanner (new File(TEMP_FILE));
			PrintWriter lstWriter = new PrintWriter(new File(fileName + ".lst")); 							//creates lst file
			PrintWriter objWriter = new PrintWriter(new File(fileName) + ".obj");							// creates obj file
			lstWriter.println("Loc     Object Code       Source Code");
			lstWriter.println("---     -----------       -----------");					// print top of of obj file
			lstWriter.println();
			boolean firstLine = true;
			int lineNumber = 0;
			String base = "";
			while(input.hasNextLine()){													// re read files
				lineNumber++;
				String line = input.nextLine();
				String label = line.substring(0, 8);
				String ops = "";
				String operands = "", comment = "";
				if (line.length() >= 17){
					ops = line.substring(8, 17);
					if (line.length() >= 29) {
						operands = line.substring(17, 29);
						comment = line.substring(29);
					} else {
						operands = line.substring(17);
					}
				}
				else {
					ops = line.substring(8);
				}
				if (firstLine){
					firstLine = false;
					if (ops.trim().equalsIgnoreCase("START")){
						start = Integer.parseInt(operands.trim(), 16);
						objWriter.printf("%06X", start);
						objWriter.println();
						if (numBangs > 0 ){
							objWriter.printf("%06X", 0);
							objWriter.println();
						}
						//////////////////////////////////////////
						if (numBangs-- == 0){
							if (codeStart == -1){
								objWriter.printf("%06X", start);
							}
							else {
								objWriter.printf("%06X", codeStart);
							}
							objWriter.println();
						}
						
						
						////////////////////////////////////////////////
					}
					lstWriter.printf("%-6X   ", addresses.get(lineNumber - 1));
					lstWriter.printf("           ");
					lstWriter.println(line);
					continue;
				}
				if (line.charAt(0) == '.' ){
					continue;
				}
				int ins = 0;
				int xbpe = 0;   		//Calc xbpe
				int addr = 0;		
				
				HashObject obj = opTable.find(ops.trim());
				boolean extended = false;
				boolean insFound = false;
				boolean isWord = false, isByte = false;
//				System.out.println(lineNumber + ":    "  + line);
				if ( obj != null){
					insFound = true;
					ins = Integer.parseInt(obj.value + "");
					if (obj.getFormat() == 2){
						String[] registers = operands.trim().split(",");
						if ( registers.length != 2){
							lstWriter.println("ERROR: Must provide 2 registers");
						}
						int r1 = opTable.find(registers[0]).value;
						int r2 = opTable.find(registers[1]).value;
						lstWriter.printf("%-6X   ", addresses.get(lineNumber - 1));
						lstWriter.printf("%02X%X%X\n", ins, r1, r2);
						continue;
					}
					String arg = "";
					boolean isNumber = false;
					if (operands.length() > 0 && operands.trim().charAt(0) == '#'){
						ins += 1;
						arg = operands.trim().substring(1);
						try{
							Integer.parseInt(arg);
							isNumber = true;
						}
						catch (NumberFormatException nfex){}
					}
					else if (operands.length() > 0 && operands.trim().charAt(0) == '@'){
						ins += 2;
						arg = operands.trim().substring(1);
					}
					else if (operands.length() > 0 && operands.trim().charAt(0) == '='){
						arg = operands.trim();
						ins += 3;
					}
					else{
						ins += 3;
					}
					// xbpe
					
					
					if (operands.trim().contains(",")){
						String[] args = operands.trim().split(",");
						if ( args.length != 2){
							lstWriter.println("ERROR: Must provide 2 arguments");
						}
						if (!args[1].equalsIgnoreCase("x")){
							lstWriter.println("ERROR: X register must be included");
						}
						arg = args[0];
						xbpe += 8;
					}
					else {
						if (arg.equals(""))
							arg = operands.trim();
					}
					if (arg.length() != 0){
						int pc = addresses.get(lineNumber);
						HashObject argObj = symTable.find(arg);
						int disp = 0; 
						if (argObj == null){
							if (!isNumber)
								lstWriter.println("Invalid numonic " + arg);
							else
								lstWriter.println("ERROR: Invalid argument");
						} else if (!isNumber && obj.getFormat() != 4){
							disp = argObj.value - pc;
						}
						else if ( obj.getFormat() == 4){
							extended = true;
						}
						
						if (!isNumber && !extended) {
							if ( isInPCRange(disp)){
								xbpe += 2;
								addr = disp;
							}
							else {
								xbpe += 4;
								if (!base.equals("")){
									HashObject baseObj = symTable.find(base);
									if (baseObj != null){
										disp = argObj.value - baseObj.value;
										if (isBase(disp)){
											addr = disp;
										}
										else {											
											lstWriter.println("ERROR: Argument is out of range");
										}
									}
									else {
										lstWriter.println("ERROR: Invalid base provided");
									}
								}
								else {									
									lstWriter.println("ERROR: No base provided");
								}
							}
						}
						else if( !extended){
							addr = Integer.parseInt(arg);
						}
						else {
							xbpe += 1;
							addr = argObj.value;
						}
					}
					
 				} // assembler directives
				else if (ops.trim().equalsIgnoreCase("base")){
					base = operands.trim();
				}
				else if (ops.trim().equalsIgnoreCase("nobase")){
					base = "";
				}
				else if (ops.trim().equalsIgnoreCase("word")){
					isWord = true;
				}
				else if (ops.trim().equalsIgnoreCase("resw")){
					objWriter.println("!");
					objWriter.printf("%06X", startingAddresses.get(startNum++));
					objWriter.println();
					if (numBangs-- == 0){
						if (codeStart == -1){
							objWriter.printf("%06X", start);
						}
						else {
							objWriter.printf("%06X", codeStart);
						}
						objWriter.println();
					}
					else {
						objWriter.println("000000");
					}
				}
				else if (ops.trim().equalsIgnoreCase("BYTE")){
					isByte = true;
				}
				else if (ops.trim().equalsIgnoreCase("END")){
				}
				
				try {
					lstWriter.printf("%-6X   ", addresses.get(lineNumber - 1));
				} catch (Exception e) {
					lstWriter.printf("         ");
				}
				
				if (!extended && insFound){
					if (addr < 0) {
						String negAddr = String.format("%03X", addr);
						lstWriter.printf("%02X%X%s     ", ins, xbpe, negAddr.substring(negAddr.length() - 3));
						objWriter.printf("%02X%X%s", ins, xbpe, negAddr.substring(negAddr.length() - 3));
						objWriter.println();
					}
					else {
						lstWriter.printf("%02X%X%03X     ", ins, xbpe, addr);
						objWriter.printf("%02X%X%03X", ins, xbpe, addr);
						objWriter.println();
					}
				}
				else if (insFound){	
					if (addr < 0) {
						String negAddr = String.format("%05X", addr);
						lstWriter.printf("%02X%X%s   ", ins, xbpe, negAddr.substring(negAddr.length() - 3));
						objWriter.printf("%02X%X%s", ins, xbpe, negAddr.substring(negAddr.length() - 3));
						objWriter.println();
					}
					else {
						lstWriter.printf("%02X%X%05X   ", ins, xbpe, addr);
						objWriter.printf("%02X%X%05X", ins, xbpe, addr);
						objWriter.println();
					}
				}
				else if (isWord) {
					int val = Integer.parseInt(operands.trim());
					lstWriter.printf("%06X     ", val);
					objWriter.printf("%06X", val);
					objWriter.println();
				}
				else if (isByte) {
					String val =  operands.trim().substring(2,operands.trim().length()-1);
					if (operands.trim().charAt(0) == 'X'){
						lstWriter.printf("%6s     ", val);
						objWriter.printf("%6s", val);
						objWriter.println();
						
					}
					else if (operands.trim().charAt(0) == 'C'){
						String value = "";
						for (int i = 0; i < val.length(); i++){
							char c = val.charAt(i);
							int cval = (int)c;
							value += String.format("%X", cval);
						}
						lstWriter.printf("%6s     ", value);
						objWriter.printf("%6s", value);
						objWriter.println();
					}
				}
				else {
					lstWriter.printf("           ");
				}
				
				lstWriter.println(line);
			}
			objWriter.println("!");
			input.close();
			lstWriter.close();
			objWriter.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static boolean isInPCRange ( int x){
		return (x >= -2048 && x < 2048);
	}
	
	private static boolean isBase( int x){
		return (x >= 0 && x <= 4096);
	}
	
}


		
