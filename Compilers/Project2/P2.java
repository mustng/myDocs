
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;


public class P2 {
	static int commentDepth = 0;
	static boolean isFloat = false;
		
	static HashMap<String, HashMap<String, List<String>>> lookup = new HashMap<String, HashMap<String, List<String>>>();	
	
	static Queue<String> myTokens = new LinkedList<String>();
		
	public static void main (String[] args) throws FileNotFoundException{
		String filePath = args[0];
		
		CodeTokenizer tokenizer = new CodeTokenizer();
		myTokens = tokenizer.read(filePath);		
		
		initializeLookup();		
		
		acceptOrReject();
		
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
		
// Print out Contents of Lookup file		
//		for (String key : lookup.keySet()) {
//			HashMap<String, String> subMap = lookup.get(key);
//			for (String subKey : subMap.keySet()) {
//				String value = subMap.get(subKey); 
//				System.out.println("Key: " + key + " - SubKey: " + subKey + " - Val: " + value);
//			}
//		}
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
					System.out.println("ACCEPT");
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
					System.out.println("REJECT");
					break;
				}
			}
		}
	}
}
