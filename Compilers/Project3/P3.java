
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;


public class P3 {
	
		
	static HashMap<String, HashMap<String, List<String>>> lookup = new HashMap<String, HashMap<String, List<String>>>();
	
	static Queue<String> myTokens = new LinkedList<String>();
	static Queue<String> myTokens2 = new LinkedList<String>();
	
	static boolean check1 = false;
	static boolean check2 = false;
		
	@SuppressWarnings("unchecked")
	public static void main (String[] args) throws FileNotFoundException{
		
		String filePath = args[0];
		
		CodeTokenizer tokenizer = new CodeTokenizer();
		try {
			myTokens = tokenizer.read(filePath);
		} catch (Exception e) {
			//print error
		}		
		
		initializeLookup();		
//		System.out.println(myTokens);
		myTokens2 = (Queue<String>) ((LinkedList<String>) myTokens).clone();   //copy tokens for analysis
		acceptOrReject();
		if (check1){
			analysis();
		}
		if (check1 && check2){
			System.out.println("ACCEPT");
		}
		else{
			System.out.println("REJECT");
		}
	}
	
	@SuppressWarnings("unchecked")
	static void analysis(){
		
	boolean isFunc = false;
	boolean isVoid = false;
	
	ArrayList<String> scope = new ArrayList<String>();
	ArrayList<String> allVariables = new ArrayList<String>();
	ArrayList<String> checkForAfterScope= new ArrayList<String>();
	ArrayList<String> allTheFloats= new ArrayList<String>();
	ArrayList<String> allTheInts= new ArrayList<String>();
	ArrayList<String> checkForDuplicateslayer0= new ArrayList<String>();   // my shenanigans of a symbol table since I programmed before I knew what it was
	ArrayList<String> checkForDuplicateslayer1= new ArrayList<String>();
	ArrayList<String> checkForDuplicateslayer2= new ArrayList<String>();
	ArrayList<String> checkForDuplicateslayer3= new ArrayList<String>();
	ArrayList<String> checkForDuplicateslayer4= new ArrayList<String>();
	ArrayList<String> checkForDuplicateslayer5= new ArrayList<String>();
	
		while (myTokens2.peek()!= null){
			int parenCount = 0;
			int firstRun = 0;
			if (myTokens2.peek() == "h" || myTokens2.peek() == "f"){  // checks if Functions has a return
				parenCount = 0;
				Queue<String> checkFunc = new LinkedList<String>();
				checkFunc = (Queue<String>) ((LinkedList<String>) myTokens2).clone();
				while (checkFunc.peek() != null){
					if (checkFunc.peek() == "m"){
						if (firstRun > 0){
							firstRun = 0;    //prevent from being popped twice
							break;
						}
					}
					firstRun++;
					((LinkedList<String>) checkFunc).pop(); ((LinkedList<String>) checkFunc).pop();
					if (checkFunc.peek() == "i"){
						while (checkFunc.peek() != null){
							if (checkFunc.peek() == "l"){
								parenCount++;
							}
							if (checkFunc.peek() == "s"){
								((LinkedList<String>) myTokens2).pop();
								isFunc = true;
								break;
							}
							else if (checkFunc.peek() == "m"){
								parenCount--;
								if (parenCount == 0){
									check2 = false;
									return;
								}
							}
							((LinkedList<String>) checkFunc).pop();
						}
					}
					else{
						break;
					}
				}
			}
			
			if (myTokens2.peek() == "g"){  // checks Void for no return
				Queue<String> checkFunc1 = new LinkedList<String>();
				checkFunc1 = (Queue<String>) ((LinkedList<String>) myTokens2).clone();
				while (checkFunc1.peek() != null){
					((LinkedList<String>) checkFunc1).pop(); ((LinkedList<String>) checkFunc1).pop();
					if (checkFunc1.peek() == "i"){
						isVoid = true;
						while (checkFunc1.peek() != null){
							if (checkFunc1.peek() == "l"){
								parenCount++;
							}
							if (checkFunc1.peek() == "s"){
								((LinkedList<String>) checkFunc1).pop();
								if (checkFunc1.peek() == "b"){
									check2 = true;
									break;
								}
								else{
									check2 = false;
									((LinkedList<String>) myTokens2).pop();
									return;
								}
							}
							else if (checkFunc1.peek() == "m"){
								parenCount--;
								if (parenCount == 0){
									check2 = true;
									break;
									
								}
							}
							((LinkedList<String>) checkFunc1).pop();
						}
						break;
					}
					else{
						break;
					}
				}
			}
			
			((LinkedList<String>) myTokens2).pop();
		}
		
		/////////////////////////////////////////////  scan here for scope
//		System.out.println(CodeTokenizer.array1());
		int layer = 0;
		boolean checkFunction = false;
		int inFunctionCheck = 0; //when leave scope of function set back to 0 otherwise within set to 1;
		
		Set<String> killDuplicateFloat = new HashSet<>();
		Set<String> killDuplicateInt = new HashSet<>();
		
		for ( int i = 0; i < CodeTokenizer.array1().size(); i++){
			if(CodeTokenizer.array1().get(i).equals("int") || CodeTokenizer.array1().get(i).equals("float") || CodeTokenizer.array1().get(i).equals("void")){ 
				i++;
				if (CodeTokenizer.array1().get(i + 1) == ";"){
					if (CodeTokenizer.array1().get(i - 1).equals("float")){
						allTheFloats.add(CodeTokenizer.array1().get(i));		//add to float list
					}
					if (CodeTokenizer.array1().get(i - 1).equals("int")){
						allTheInts.add(CodeTokenizer.array1().get(i));		//add to int list
					}
					if (CodeTokenizer.array1().get(i - 1).equals("void")){
//						System.out.println("void called before");
						check2 = false;
						return;
					}
					scope.add(CodeTokenizer.array1().get(i - 1));
					allVariables.add(CodeTokenizer.array1().get(i - 1));
					scope.add(CodeTokenizer.array1().get(i));
					allVariables.add(CodeTokenizer.array1().get(i));
					
					if (layer == 0){
						if (checkForDuplicateslayer0.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer0.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 1){
						if (checkForDuplicateslayer1.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer1.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 2){
						if (checkForDuplicateslayer2.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer2.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 3){
						if (checkForDuplicateslayer3.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer3.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 4){
						if (checkForDuplicateslayer4.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer4.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 5){
						if (checkForDuplicateslayer5.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer5.add(CodeTokenizer.array1().get(i));
						}
					}
				}
				else if (CodeTokenizer.array1().get(i + 1) == "("){
					scope.add(CodeTokenizer.array1().get(i - 1));
					allVariables.add(CodeTokenizer.array1().get(i - 1));
					scope.add(CodeTokenizer.array1().get(i));
					allVariables.add(CodeTokenizer.array1().get(i));
					i++;
					inFunctionCheck++;
					for (; i < CodeTokenizer.array1().size(); i++ ){
						if (CodeTokenizer.array1().get(i) == ")"){
							scope.add(CodeTokenizer.array1().get(i));
							allVariables.add(CodeTokenizer.array1().get(i));
							break;
						}
						if (inFunctionCheck == 1){
							checkForDuplicateslayer1.clear();
							checkForDuplicateslayer2.clear();
							checkForDuplicateslayer3.clear();
							checkForDuplicateslayer4.clear();
							checkForDuplicateslayer5.clear();
							inFunctionCheck = 0;
						}
						
						if (CodeTokenizer.array1().get(i).equals("int") || CodeTokenizer.array1().get(i).equals("void") || CodeTokenizer.array1().get(i).equals("float")
								|| CodeTokenizer.array1().get(i).equals("(")); //do nothing
						else if (layer + 1 == 1){
							if (checkForDuplicateslayer1.contains(CodeTokenizer.array1().get(i))){
//								System.out.println("in kill function");
								check2 = false;
								return;
							}
							else{
								checkForDuplicateslayer1.add(CodeTokenizer.array1().get(i));
							}
						}
						else if (layer + 1 == 2){
							if (checkForDuplicateslayer2.contains(CodeTokenizer.array1().get(i))){
//								System.out.println("in kill function");
								check2 = false;
								return;
							}
							else{
								checkForDuplicateslayer2.add(CodeTokenizer.array1().get(i));
							}
						}
						else if (layer + 1 == 3){
							if (checkForDuplicateslayer3.contains(CodeTokenizer.array1().get(i))){
//								System.out.println("in kill function");
								check2 = false;
								return;
							}
							else{
								checkForDuplicateslayer3.add(CodeTokenizer.array1().get(i));
							}
						}
						else if (layer + 1 == 4){
							if (checkForDuplicateslayer4.contains(CodeTokenizer.array1().get(i))){
//								System.out.println("in kill function");
								check2 = false;
								return;
							}
							else{
								checkForDuplicateslayer4.add(CodeTokenizer.array1().get(i));
							}
						}
						else if (layer + 1 == 5){
							if (checkForDuplicateslayer5.contains(CodeTokenizer.array1().get(i))){
//								System.out.println("in kill function");
								check2 = false;
								return;
							}
							else{
								checkForDuplicateslayer5.add(CodeTokenizer.array1().get(i));
							}
						}
						scope.add(CodeTokenizer.array1().get(i));
						allVariables.add(CodeTokenizer.array1().get(i));
					}
				}
				else if(CodeTokenizer.array1().get(i + 1).equals("#")){ // int x[10]; 
					if (CodeTokenizer.array1().get(i + 2) == ";"){
						if (CodeTokenizer.array1().get(i - 1).equals("float")){
//							System.out.println("void called before");
							check2 = false;
							return;
						}
						if (CodeTokenizer.array1().get(i - 1).equals("int")){
							allTheInts.add(CodeTokenizer.array1().get(i));		//add to int list
						}
						if (CodeTokenizer.array1().get(i - 1).equals("void")){
//							System.out.println("void called before");
							check2 = false;
							return;
						}
						scope.add(CodeTokenizer.array1().get(i - 1));
						allVariables.add(CodeTokenizer.array1().get(i - 1));
						scope.add(CodeTokenizer.array1().get(i));
						allVariables.add(CodeTokenizer.array1().get(i));
						scope.add(CodeTokenizer.array1().get(i + 1));
						allVariables.add(CodeTokenizer.array1().get(i + 1));
					}
					if (layer == 0){
						if (checkForDuplicateslayer0.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer0.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 1){
						if (checkForDuplicateslayer1.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer1.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 2){
						if (checkForDuplicateslayer2.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer2.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 3){
						if (checkForDuplicateslayer3.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer3.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 4){
						if (checkForDuplicateslayer4.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer4.add(CodeTokenizer.array1().get(i));
						}
					}
					else if (layer == 5){
						if (checkForDuplicateslayer5.contains(CodeTokenizer.array1().get(i))){
//							System.out.println("in kill function");
							check2 = false;
							return;
						}
						else{
							checkForDuplicateslayer5.add(CodeTokenizer.array1().get(i));
						}
					}
				}
				else if(CodeTokenizer.array1().get(i + 1).equals("%")){
//					System.out.println("Error there is a float in an array");
					check2 = false;
					return;
				}
		  }
			else if(CodeTokenizer.array1().get(i).equals("{")){
				layer++;
				scope.add(CodeTokenizer.array1().get(i));
			}
			else if(CodeTokenizer.array1().get(i).equals("}")){
				layer--;
				int size = 0;
				size = scope.size();
				if (layer == 0){
					for(;size != 0; size--){
						scope.remove(scope.size() -1);
						if(scope.get(scope.size() - 1).equals("(")){
							scope.remove(scope.size() -1);
							break;
						}
					}
				}
				else if ( layer > 0){
					for(;size != 0; size--){
						if(scope.get(scope.size() - 1).equals("{")){
							scope.remove(scope.size() -1);
							break;
						}
						scope.remove(scope.size() -1);
					}
				}
			}
			else if(CodeTokenizer.array1().get(i).equals("(")){
				int index = 0;
				int codeJumper = i + 1;
				scope.add(CodeTokenizer.array1().get(i));
				if (scope.contains(CodeTokenizer.array1().get(i - 1))){					//gets function dog( 5, 3) checks to be valid
					index = allVariables.indexOf(CodeTokenizer.array1().get(i)) + 1;
					while (!allVariables.get(index).equals(")")){
						if (allVariables.get(index).equals("int")){
							if (CodeTokenizer.array1().get(codeJumper).equals("#")){
								
							}
							else if (CodeTokenizer.array1().get(codeJumper).equals("%")){
//								System.out.println("broken");
								check2 = false;
								return;
							}
							else{
								if (scope.get(scope.indexOf(CodeTokenizer.array1().get(codeJumper)) - 1).equals("float")){
//									System.out.println("Function taking bad combo...float or int");
									check2 = false;
									return;
								}
							}
						}
						else if (allVariables.get(index).equals("float")){
							if (CodeTokenizer.array1().get(codeJumper).equals("#")){
//								System.out.println("broken");
								check2 = false;
								return;
							}
							else if (CodeTokenizer.array1().get(codeJumper).equals("%")){
							}
							else{
								if (scope.get(scope.indexOf(CodeTokenizer.array1().get(codeJumper)) - 1).equals("int")){
//									System.out.println("Function taking bad combo...float or int");
									check2 = false;
									return;
								}
							}
						}
						index += 2;
						codeJumper++;
					}
					if (CodeTokenizer.array1().get(i + 1).equals(")")){
						
					}
					else if (!CodeTokenizer.array1().get(codeJumper).equals(")")){
//						System.out.println("To many floats or Ints");
						check2 = false;
						return;
					}
					}
			}
			else if(CodeTokenizer.array1().get(i).equals(")")){
			}
			else if(CodeTokenizer.array1().get(i).equals(";")){
			}
			else if(CodeTokenizer.array1().get(i).equals("return")){
				if (scope.get(scope.indexOf("(") - 2).equals("int")){ 		//check scope which should only have ( at the function and looks 2 before
					if (allTheFloats.contains(CodeTokenizer.array1().get(i + 1))){
//						System.out.println("Bad return statement");
						check2 = false;
						return;
					}
					else if (CodeTokenizer.array1().get(i + 1).equals(";")){
//						System.out.println("int function should be void ;");
						check2 = false;
						return;
					}
					
				}
				else if (scope.get(scope.indexOf("(") - 2).equals("float")){	//same as above comment
					if (allTheInts.contains(CodeTokenizer.array1().get(i + 1))){
//						System.out.println("Bad return statement");
						check2 = false;
						return;
					}
					else if (CodeTokenizer.array1().get(i + 1).equals(";")){
//						System.out.println("int function should be void ;");
						check2 = false;
						return;
					}
				}
				else if (scope.get(scope.indexOf("(") - 2).equals("void")){	//same as above comment
					if(!CodeTokenizer.array1().get(i + 1).equals(";")){
//						already handled
					}
				}
				
			}
			else if (CodeTokenizer.array1().get(i).equals("*") || CodeTokenizer.array1().get(i).equals("/")|| CodeTokenizer.array1().get(i).equals("!=") ||
					CodeTokenizer.array1().get(i).equals("=") || CodeTokenizer.array1().get(i).equals("==") || CodeTokenizer.array1().get(i).equals("+") || CodeTokenizer.array1().get(i).equals("-") ||
					CodeTokenizer.array1().get(i).equals("<") || CodeTokenizer.array1().get(i).equals(">") || CodeTokenizer.array1().get(i).equals("<=") || CodeTokenizer.array1().get(i).equals(">=")){
				boolean flag = false;           //false for int true for float
				int index = 0;
				if (scope.contains(CodeTokenizer.array1().get(i -1))){
					index = scope.indexOf(CodeTokenizer.array1().get(i -1));
					if (scope.get(index - 1).equals("int")){
						flag = false;
					}
					else if(scope.get(index - 1).equals("float")){
						flag = true;
					}
				}
				else{
					if (CodeTokenizer.array1().get(i -1).equals("#")){
						flag = false;
					}
					else if (CodeTokenizer.array1().get(i -1).equals("%")){
						flag = true;
					}
					else{
//						System.out.println("broken");
						check2 = false;
						return;
					}
					
				}
				if (scope.contains(CodeTokenizer.array1().get(i + 1))){
					index = scope.indexOf(CodeTokenizer.array1().get(i + 1));
					if (scope.get(index - 1).equals("int")){
						if (flag){
//							System.out.println("broken");
							check2 = false;
							return;
						}
					}
					else if(scope.get(index - 1).equals("float")){
						if (flag){
//							System.out.println("broken");
							check2 = false;
							return;
						}
					}
				}
				else{
					if (CodeTokenizer.array1().get(i + 1).equals("#")){
						if (flag){
//							System.out.println("broken #");
							check2 = false;
							return;
						}
					}
					else if (CodeTokenizer.array1().get(i + 1).equals("%")){
						if (!flag){
//							System.out.println("broken %");
							check2 = false;
							return;
						}
					}
					else{
//						System.out.println("broken %");
						check2 = false;
						return;
					}
				}
			}
			else if (CodeTokenizer.array1().get(i).equals("#")){
				
			}
			else if (CodeTokenizer.array1().get(i).equals("[") || CodeTokenizer.array1().get(i).equals("]")){
				
			}
			else if (CodeTokenizer.array1().get(i).equals("%")){
				
			}
			else if(!scope.contains(CodeTokenizer.array1().get(i))){
				if (!allVariables.contains(CodeTokenizer.array1().get(i))){
//					System.out.println("Function not declared yet");
					check2 = false;
					return;
				}
				if (CodeTokenizer.array1().get(i + 1) == "("){
					checkForAfterScope.add(CodeTokenizer.array1().get(i));
					checkFunction = true;
				}
				else{
//					System.out.println("DOES NOT EXIST" + " " + CodeTokenizer.array1().get(i));
					check2 = false;
					return;
				}
			}
			else if(scope.contains(CodeTokenizer.array1().get(i))){
				for (int m = scope.size(); m != 0; m--){
					if (scope.get(m - 1).equals(CodeTokenizer.array1().get(i))){
						
					}
						if (scope.get(m - 1).equals("float")){
							allTheFloats.add(scope.get(m));
							killDuplicateFloat.addAll(allTheFloats);
							allTheFloats.clear();
							allTheFloats.addAll(killDuplicateFloat);
						}
						else if (scope.get(m - 1).equals("int")){
							allTheInts.add(scope.get(m));
							killDuplicateInt.addAll(allTheInts);
							allTheInts.clear();
							allTheInts.addAll(killDuplicateInt);
						}
				}
			}
		}
		
//		System.out.println(checkForDuplicateslayer1);
//		System.out.println(scope);
//		System.out.println(allVariables);
//		System.out.println(checkForAfterScope);
//		System.out.println(allTheInts);
//		System.out.println(allTheFloats);
		
		if (checkFunction){
			for (int k = 0; k < checkForAfterScope.size(); k++){
				if (!allVariables.contains(checkForAfterScope.get(k))){
//					System.out.println("func doesnt exist");
					check2 = false;
					return;
				}
			}
		}
		
		if (!allVariables.get(allVariables.indexOf("main") + 2).equals("void")){
//			System.out.println("void not declared after main");
			check2 = false;
			return;
		}
		
		if (!CodeTokenizer.wasMain()){
			check2 = false;
			return;
		}
		if (isFunc || isVoid){
			check2 = true;
		}
		else{
			check2 = false;
		}
	}
	
	static void initializeLookup() throws FileNotFoundException {
		Scanner input = new Scanner (new File("lookup.txt"));
		
		while( input.hasNextLine()){
			String line = input.nextLine();
			String[] splitVals = line.split(",");
			
			String key = splitVals[0].trim();
			String subKey = splitVals[1].trim();
			String[] splitStateVals = splitVals[2].trim().split(" ");
			List<String> stateVals = Arrays.asList(splitStateVals);
			Collections.reverse(stateVals);
		
			if (!lookup.containsKey(key)) {
				lookup.put(key, new HashMap<String, List<String>>());
			}
			
			HashMap<String, List<String>> subMap = lookup.get(key);
			
			if (!subMap.containsKey(subKey)) {
				lookup.put(subKey, new HashMap<String, List<String>>());
			}
			
			subMap.put(subKey, stateVals);
		}
		
	}
	
	static void acceptOrReject(){
		///////////////////////////////////////////////////////////////////
		myTokens.add("$");
		
		Stack<String> stateStack = new Stack<String>();
		
		stateStack.add("$");
		stateStack.add("A");
		
		for  ( int num = 0; num < 2147483646; num++){           // add max int so program will time out when nothing is done
//			System.out.println(stateStack + "\t\t" + myTokens);
			
			String tempTopStack = (String) stateStack.peek();
			String tempQueueValue = (String) myTokens.peek();
			
			if(tempTopStack.equals(tempQueueValue)){
				if (tempTopStack.contentEquals("$")){
					check1 = true;
					break;
				}
				else{
					stateStack.pop();									// have a match 
					((LinkedList<String>) myTokens).pop();
				}
			}
			////////////////////////////////////////////////////STATES///////////////////////
			else{
				boolean reject = true;
				String key = tempTopStack;
				String subKey = tempQueueValue;
				
				if (lookup.containsKey(key)) {
					HashMap<String, List<String>> subMap = lookup.get(key);
					
					if (subMap.containsKey(subKey)) {
						reject = false;
						List<String> states = subMap.get(subKey);						
						
						stateStack.pop();
						
						for (String state : states) {
							if (!state.equals("@")) {
								stateStack.add(state);
							}
						}
						
					}
				}

				if (reject) {
					check1 = false;
					break;
				}
			}
		}
	}
}