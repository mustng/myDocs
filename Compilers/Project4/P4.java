
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;


public class P4 {
	
	static Queue<String> myTokens = new LinkedList<String>();
	static int counter = 1;					//used in printout to print number
	static int tempNumber = 1;				//used to print out temp numbers
	static int[] indexToAdd = new int[20];	//used with scope to store value
	static boolean firstRun = true; 		//used way down in calcString function to tell if its the first run or not;
	static ArrayList<String> printOut= new ArrayList<String>(); //everything is printed out from here
	
	public static void main (String[] args) throws FileNotFoundException{
		
		
		String filePath = args[0];
		
		CodeTokenizer tokenizer = new CodeTokenizer();
		try {
			myTokens = tokenizer.read(filePath);
		} catch (Exception e) {
			//print error
		}		
		
		codeGeneration();  
		
		StringBuilder builder = new StringBuilder();		//format Arraylist for print
		for (String value : printOut) {
		    builder.append(value);
		}
		String text = builder.toString();
		System.out.println("-------------------------------------------------------------");
		System.out.println(text);
		System.out.println("-------------------------------------------------------------");
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static void codeGeneration(){
		
		ArrayList<String> stringToCalc = new ArrayList<String>();
		ArrayList<String> functionsCalled = new ArrayList<String>();
		
		int numFunctions = 1;
		int scope = 0;
		boolean isMain = false;
		boolean inIf[] = new boolean[20];
		boolean inElseStatement[] = new boolean[20];
		String tempName = "";
		String intToString = "";
		String BRreturn = "";
		
//		System.out.println(CodeTokenizer.array1());
		for ( int i = 0; i < CodeTokenizer.array1().size(); i++){
			if(CodeTokenizer.array1().get(i).equals("int") || CodeTokenizer.array1().get(i).equals("float") || CodeTokenizer.array1().get(i).equals("void")){ 
				i++;
				if (CodeTokenizer.array1().get(i + 1) == ";"){
					if (CodeTokenizer.array1().get(i - 1).equals("float")){
						printOut.add(counter + "\t alloc \t\t4 \t\t  \t\t" + CodeTokenizer.array1().get(i) + "\n");
						counter++;
						i++;
					}
					if (CodeTokenizer.array1().get(i - 1).equals("int")){
						printOut.add(counter + "\t alloc \t\t4 \t\t  \t\t" + CodeTokenizer.array1().get(i) + "\n");
						counter++;
						i++;
					}
					
				}
				else if (CodeTokenizer.array1().get(i + 1) == "("){
					if (CodeTokenizer.array1().get(i - 1).equals("float")){
						printOut.add(counter + "\t func \t\t" + CodeTokenizer.array1().get(i)+ "\t\tfloat\t\t" + numFunctions + "\n");
						tempName += CodeTokenizer.array1().get(i);
						functionsCalled.add(CodeTokenizer.array1().get(i));
						intToString += numFunctions;
						functionsCalled.add(intToString);
						intToString = "";
						counter++;
						numFunctions++;
						i++;
					}
					else if (CodeTokenizer.array1().get(i - 1).equals("int")){
						printOut.add(counter + "\t func \t\t" + CodeTokenizer.array1().get(i)+ "\t\tint\t\t" + numFunctions + "\n");
						tempName += CodeTokenizer.array1().get(i);
						functionsCalled.add(CodeTokenizer.array1().get(i));
						intToString += numFunctions;
						functionsCalled.add(intToString);
						intToString = "";
						counter++;
						numFunctions++;
						i++;
					}
					else if (CodeTokenizer.array1().get(i - 1).equals("void")){
						if (CodeTokenizer.array1().get(i).equals("main")){
							printOut.add(counter + "\t func \t\tmain \t\tvoid \t\t0 \n");
							isMain = true;
							counter++;
							i++;
						}
						else{
							printOut.add(counter + "\t func \t\t" + CodeTokenizer.array1().get(i)+ "\t\t void \t\t" + numFunctions + "\n");
							tempName += CodeTokenizer.array1().get(i);
							functionsCalled.add(CodeTokenizer.array1().get(i));
							intToString += numFunctions;
							functionsCalled.add(intToString);
							intToString = "";
							counter++;
							numFunctions++;
							i++;
						}
					}
					
				}
				else if(CodeTokenizer.array1().get(i + 1).equals("[")){ // intx[10] 
					if (CodeTokenizer.array1().get(i + 2).equals("]")){
						if (CodeTokenizer.array1().get(i - 1).equals("float")){
							printOut.add(counter + "\t alloc \t\t4\t\t  \t\t" + CodeTokenizer.array1().get(i) + "\n");
							counter++;
							i++;
						}
						if (CodeTokenizer.array1().get(i - 1).equals("int")){
							printOut.add(counter + "\t alloc \t\t4\t\t  \t\t" + CodeTokenizer.array1().get(i) + "\n");
							counter++;
							i++;
						}
					}
					else{
						int multipyByFour = Integer.parseInt(CodeTokenizer.array1().get(i + 2)) * 4;
						if (CodeTokenizer.array1().get(i - 1).equals("float")){
							printOut.add(counter + "\t alloc \t\t"+ multipyByFour + "\t\t  \t\t" + CodeTokenizer.array1().get(i) + "\n");
							counter++;
							i++;
						}
						if (CodeTokenizer.array1().get(i - 1).equals("int")){
							printOut.add(counter + "\t alloc \t\t" + multipyByFour + "\t\t  \t\t" + CodeTokenizer.array1().get(i) + "\n");
							counter++;
							i++;
						}
					}
				}
				else if (CodeTokenizer.array1().get(i).equals(")")){			//keep this to prevent errors with (
				}
				else{
					printOut.add(counter + "\t param\n");
					counter++;
					printOut.add(counter + "\t alloc \t\t4 \t\t  \t\t" + CodeTokenizer.array1().get(i) + "\n");
					counter++;
					i++;
				}
				
		  }
			else if(CodeTokenizer.array1().get(i).equals("{")){
				scope++;
				if (scope > 1){
					printOut.add(counter + "\t block \n");
					counter++;	
				}
			}
			else if(CodeTokenizer.array1().get(i).equals("}")){
				scope--;
				if (scope == 0){
					if (isMain){
						printOut.add(counter + "\t end \t\tfunc\t\tmain\n");
						isMain = false;
						counter++;
					}
					else{
						printOut.add(counter + "\t end \t\tfunc\t\t" + tempName + "\n");
						tempName = "";
						counter++;
					}
				}
				if (indexToAdd[scope] != 0){
					if (inIf[scope]){
						inIf[scope] = false;
						Collections.reverse(printOut);
						int arrayToBR = printOut.indexOf("\t BR \t\t\t\t\t\t\n");										//reverses and adds to BR array
						printOut.remove(arrayToBR);
						printOut.set(arrayToBR, printOut.get(arrayToBR) +"\t BR \t\t\t\t\t\t" + (counter + 1) + "\n");
						Collections.reverse(printOut);
						counter++;
					}
					else if (inElseStatement[scope]){
						inElseStatement[scope] = false;
						Collections.reverse(printOut);
						int arrayToBR = printOut.indexOf("\t BR \t\t\t\t\t\t\n");										//reverses and adds to BR array
						printOut.remove(arrayToBR);
						printOut.set(arrayToBR, printOut.get(arrayToBR) +"\t BR \t\t\t\t\t\t" + (counter + 1) + "\n");
						Collections.reverse(printOut);
						counter++;
					}
					else{
						Collections.reverse(printOut);
						int arrayToBR = printOut.indexOf("\t BR \t\t\t\t\t\t\n");										//reverses and adds to BR array
						printOut.remove(arrayToBR);
						printOut.set(arrayToBR, printOut.get(arrayToBR) +"\t BR \t\t\t\t\t\t" + (counter + 1) + "\n");
						Collections.reverse(printOut);
						printOut.add(counter + "\t BR \t\t\t\t\t\t" + (indexToAdd[scope]) + "\n");
						counter++;
					}
				}
				if (scope > 0){
					printOut.add(counter + "\t end \t\tblock\n");
					counter++;	
				}
			}
			else if(CodeTokenizer.array1().get(i).equals("(")){
				while(CodeTokenizer.array1().get(i) != ";"){
					stringToCalc.add(CodeTokenizer.array1().get(i));
					i++;
				}
				calcString(stringToCalc, functionsCalled, scope);
				stringToCalc.clear();
			}
			else if(CodeTokenizer.array1().get(i).equals(")")){
			}
			else if(CodeTokenizer.array1().get(i).equals(";")){
			}
			else if(CodeTokenizer.array1().get(i).equals("while")){
				int parenCounter = 0;
				indexToAdd[scope] = counter;
				i += 2;
				while(CodeTokenizer.array1().get(i) != ")" || parenCounter != 0){
					if (CodeTokenizer.array1().get(i).equals("(")){
						parenCounter++;
					}
					if (CodeTokenizer.array1().get(i).equals(")")){
						parenCounter--;
					}
					stringToCalc.add(CodeTokenizer.array1().get(i));
					i++;
				}
				if (stringToCalc.contains(">")){
					BRreturn = "BRG";
				}
				else if (stringToCalc.contains("<")){
					BRreturn = "BRL";
				}
				else if (stringToCalc.contains(">=")){
					BRreturn = "BRGEQ";
				}
				else if (stringToCalc.contains("<=")){
					BRreturn = "BRLEQ";
				}
				else if (stringToCalc.contains("==")){
					BRreturn = "BREEQ";
				}
				else if (stringToCalc.contains("!=")){
					BRreturn = "BRNEQ";
				}
				calcString(stringToCalc, functionsCalled, scope);
				stringToCalc.clear();
				printOut.add(counter + "\t "+ BRreturn + " \t\tt" + (tempNumber - 1) +  "\t\t\t\t" + (counter + 2) + "\n");
				counter++;
				printOut.add(counter + "");
				printOut.add("\t BR \t\t\t\t\t\t\n");
				counter++;
				BRreturn = "";
			}
			else if(CodeTokenizer.array1().get(i).equals("if")){
				inIf[scope] = true;
				int parenCounter = 0;
				indexToAdd[scope] = counter;
				i += 2;
				while(CodeTokenizer.array1().get(i) != ")" || parenCounter != 0){
					if (CodeTokenizer.array1().get(i).equals("(")){
						parenCounter++;
					}
					if (CodeTokenizer.array1().get(i).equals(")")){
						parenCounter--;
					}
					stringToCalc.add(CodeTokenizer.array1().get(i));
					i++;
				}
				if (stringToCalc.contains(">")){
					BRreturn = "BRG";
				}
				else if (stringToCalc.contains("<")){
					BRreturn = "BRL";
				}
				else if (stringToCalc.contains(">=")){
					BRreturn = "BRGEQ";
				}
				else if (stringToCalc.contains("<=")){
					BRreturn = "BRLEQ";
				}
				else if (stringToCalc.contains("==")){
					BRreturn = "BREEQ";
				}
				else if (stringToCalc.contains("!=")){
					BRreturn = "BRNEQ";
				}
				calcString(stringToCalc, functionsCalled, scope);
				stringToCalc.clear();
				printOut.add(counter + "\t "+ BRreturn + " \t\tt" + (tempNumber - 1) +  "\t\t\t\t" + (counter + 2) + "\n");
				counter++;
				printOut.add(counter + "");
				printOut.add("\t BR \t\t\t\t\t\t\n");
				counter++;
				BRreturn = "";
			}
			else if(CodeTokenizer.array1().get(i).equals("else")){
				inElseStatement[scope] = true;
				indexToAdd[scope] = counter;
				printOut.add(counter + "");
				printOut.add("\t BR \t\t\t\t\t\t\n");
				counter++;
			}
			else if(CodeTokenizer.array1().get(i).equals("return")){
				i += 2;
				if (CodeTokenizer.array1().get( i+ 1).equals(")")){
					printOut.add(counter + "\t return \t\t\t\t\t" + CodeTokenizer.array1().get(i) + "\n");
					counter++;
				}
				else{
					while(CodeTokenizer.array1().get(i) != ")"){
						stringToCalc.add(CodeTokenizer.array1().get(i));
						i++;
					}
					calcString(stringToCalc, functionsCalled, scope);
					stringToCalc.clear();
					printOut.add(counter + "\t return \t\t\t\t\tt" + (tempNumber - 1) + "\n");
					counter++;
				}
			}
			else if (CodeTokenizer.array1().get(i).equals("*") || CodeTokenizer.array1().get(i).equals("/")|| CodeTokenizer.array1().get(i).equals("!=") ||
					CodeTokenizer.array1().get(i).equals("=") || CodeTokenizer.array1().get(i).equals("==") || CodeTokenizer.array1().get(i).equals("+") || CodeTokenizer.array1().get(i).equals("-") ||
					CodeTokenizer.array1().get(i).equals("<") || CodeTokenizer.array1().get(i).equals(">") || CodeTokenizer.array1().get(i).equals("<=") || CodeTokenizer.array1().get(i).equals(">=")){
				stringToCalc.add(CodeTokenizer.array1().get(i - 1));
				while(CodeTokenizer.array1().get(i) != ";"){
					stringToCalc.add(CodeTokenizer.array1().get(i));
					i++;
				}
				calcString(stringToCalc, functionsCalled, scope);
				stringToCalc.clear();
				
			}
		}
	}
	
	
	
	
	static void calcString( ArrayList<String> stringToCalc, ArrayList<String> functionsCalled, int scope){  //runs through string and adds to Array based on order of what it contains
		int indexToPlay = 0;
		ArrayList<String> tempArray = new ArrayList<String>();
		
		while  (stringToCalc.size() != 1){
		
			if (stringToCalc.contains("(")){
				 int[] parenScope= new int[20];
				 int parenCounter = 0;
				 for (int q = 0; stringToCalc.size()!= 1; q++){
					 if (stringToCalc.get(q).equals("(")){
						 parenScope[parenCounter] = q;
						 parenCounter++;
					 }
					 else if (stringToCalc.get(q).equals(")")){
						 parenCounter--;
						 for (int m = parenScope[parenCounter]; m <= q; m++){
								 tempArray.add(stringToCalc.get(m));			//build array
						 }
						 for (int m = parenScope[parenCounter]; m < q; m++){ //break down
							 stringToCalc.remove(parenScope[parenCounter]);
						 }
						 tempArray.remove(0);   //remove (
						 tempArray.remove(tempArray.size() - 1); // remove )
						 calcString( tempArray, functionsCalled, scope);  //continue here temp array
						 stringToCalc.set(parenScope[parenCounter], tempArray.get(0));
						 if(stringToCalc.size() != 1 && functionsCalled.contains(stringToCalc.get(parenScope[parenCounter] - 1))){
							 printOut.add(counter + "\t arg \t\t\t\t\t\t" + stringToCalc.get(parenScope[parenCounter]) + "\n");
							 counter++;
							 printOut.add(counter + "\t call \t\t" + stringToCalc.get(parenScope[parenCounter] -1) +  "\t\t" + 
							 functionsCalled.get(functionsCalled.indexOf(stringToCalc.get(parenScope[parenCounter] -1))+ 1) + "\t\tt" + tempNumber + "\n");
							 counter++;
							 stringToCalc.remove(parenScope[parenCounter]);
							 stringToCalc.add(parenScope[parenCounter], "t" + tempNumber);
							 stringToCalc.remove(parenScope[parenCounter] - 1);
							 tempNumber++;
						 }
						 q = -1;										//reset back at beginning to calc
						 tempArray.clear();
					 }
					 else if ( q == (stringToCalc.size() - 1)){
						 break;
					 }
				 }
			}

			else if (stringToCalc.contains("[")){
				indexToPlay = stringToCalc.indexOf("[");
				int multipyByFour = Integer.parseInt(stringToCalc.get(indexToPlay + 1)) * 4;
				printOut.add(counter + "\t disp \t\t" + stringToCalc.get(indexToPlay - 1) +  "\t\t" + multipyByFour + "\t\tt" + tempNumber + "\n");
				stringToCalc.remove(indexToPlay); stringToCalc.remove(indexToPlay); //remove [30]
				stringToCalc.set(indexToPlay - 1, "t" + tempNumber);
				stringToCalc.remove(indexToPlay);
				counter++;
				tempNumber++;
			}
			else if (stringToCalc.contains("/")){
				if (stringToCalc.contains("*")){
					if (stringToCalc.indexOf("*") < stringToCalc.indexOf("/")){					//check index order to make sure its going left to right
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"mult", "*");
					}
					else{
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"div", "/");
					}
				}
				else{
					calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"div", "/");
				}
			}
			else if (stringToCalc.contains("*")){
				if (stringToCalc.contains("/")){
					if (stringToCalc.indexOf("/") < stringToCalc.indexOf("*")){					//check index order to make sure its going left to right
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"div", "/");
					}
					else{
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"mult", "*");
					}
				}
				else{
					calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"mult", "*");
				}
			}
			else if (stringToCalc.contains("+")){
				if (stringToCalc.contains("-")){
					if (stringToCalc.indexOf("-") < stringToCalc.indexOf("+")){					//check index order to make sure its going left to right
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"sub", "-");
					}
					else{
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"add", "+");
					}
				}
				else{
					calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"add", "+");
				}
			}
			else if (stringToCalc.contains("-")){
				if (stringToCalc.contains("+")){
					if (stringToCalc.indexOf("+") < stringToCalc.indexOf("-")){					//check index order to make sure its going left to right
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"add", "+");
					}
					else{
						calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"sub", "-");
					}
				}
				else{
					calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"sub", "-");
				}
			}
			else if (stringToCalc.contains(">")){
				calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"comp", ">");
			}
			else if (stringToCalc.contains(">=")){
				calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"comp", ">=");
			}
			else if (stringToCalc.contains("<")){
				calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"comp", "<");
			}
			else if (stringToCalc.contains("<=")){
				calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"comp", "<=");
			}
			else if (stringToCalc.contains("==")){
				calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"ISEQ", "==");
			}
			else if (stringToCalc.contains("!=")){
				calcSomeMoreToSaveLineOfCode (stringToCalc,scope,"NOTEQ", "!=");
			}
			else if (stringToCalc.contains("=")){
				indexToPlay = stringToCalc.indexOf("=");
					printOut.add(counter + "\t assign\t\t" + stringToCalc.get(indexToPlay + 1) +  "\t\t\t\t" + stringToCalc.get(indexToPlay - 1)  + "\n");
					stringToCalc.remove(indexToPlay); stringToCalc.remove(indexToPlay);
					counter++;
			}
			else{
//				System.out.println(stringToCalc + " here is something left going into else area so removing for no reason!!!");
				stringToCalc.remove(indexToPlay);
			}
		}
		firstRun = true; 
	}
	static ArrayList<String> calcSomeMoreToSaveLineOfCode (ArrayList<String> stringToCalc, int scope, String name, String var){
		int indexToPlay = 0;
		indexToPlay = stringToCalc.indexOf(var);
		if (firstRun){
			printOut.add(counter + "\t "+ name + "\t\t" + stringToCalc.get(indexToPlay - 1) +  "\t\t" + stringToCalc.get(indexToPlay + 1) + "\t\tt" + tempNumber + "\n");
			stringToCalc.remove(indexToPlay); stringToCalc.remove(indexToPlay);
			stringToCalc.set(indexToPlay - 1, "t" + tempNumber);
			firstRun = false;
			tempNumber++;
			counter++;
		}
		else{
			printOut.add(counter + "\t " + name + "\t\t" + stringToCalc.get(indexToPlay - 1) +  "\t\t" + stringToCalc.get(indexToPlay + 1) + "\t\tt" + tempNumber + "\n");
			stringToCalc.remove(indexToPlay); stringToCalc.remove(indexToPlay);
			stringToCalc.set(indexToPlay - 1, "t" + tempNumber);
			counter++;
			tempNumber++;
		}
		
		return stringToCalc;
	}
}
