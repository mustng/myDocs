import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CodeTokenizer {
	private int commentDepth = 0;
	private boolean isFloat = false;
	
	CodeTokenizer() {
	}
	
	Queue<String> read(String filePath) {
		Queue<String> myTokens = new LinkedList<String>();
		try {
			Scanner input = new Scanner (new File(filePath));
			while( input.hasNextLine()){
				String line = input.nextLine();
				
//				System.out.println("INPUT: " + line);							//print line by line
				
				for (int i = 0; i < line.length(); i++){
					String num = "";
					String alpha = "";
					String punc = "";
				    char c = line.charAt(i); 
				    
				    if (commentDepth != 0){											//If isComment set to true print input until */
				    	while (i < line.length()){
		    				punc += c;
		    				if (i == line.length() - 1){
//			    					System.out.println("COMMENT: " + punc);
		    					break;
		    				}
		    				if (c == '*'){
		    					if( line.charAt(i+1) == '/'){
		    						punc += "/";
		    						i++;
//			    						System.out.println("COMMENT: " + punc);
		    						commentDepth--;
			    					break;
		    					}
		    				}
		    				i++;
	    					if (i >= line.length()){
//		    						System.out.println("COMMENT: " + punc);
	    						break; 
	    					}
		    				c = line.charAt(i);
		    			}
				    }
				    else if (isSpecial(c)){									//Scan in punctuation
				    	if (c == ';'){
				    		myTokens.add("b");
				    	}
				    	else if (c == '['){
				    		myTokens.add("c");
				    	}
				    	else if (c == ']'){
				    		myTokens.add("e");
				    	}
				    	else if (c == '('){
				    		myTokens.add("i");
				    	}
				    	else if (c == ')'){
				    		myTokens.add("j");
				    	}
				    	else if (c == ','){
				    		myTokens.add("k");
				    	}
				    	else if (c == '{'){
				    		myTokens.add("l");
				    	}
				    	else if (c == '}'){
				    		myTokens.add("m");
				    	}
				    	else if (c == '+'){
				    		myTokens.add("v");
				    	}
				    	else if (c == '-'){
				    		myTokens.add("v");
				    	}
				    	else if (c == '*'){
				    		myTokens.add("w");
				    	}
				    		
				    	if(i == line.length() - 1){
			    			punc += c;
			    			if (c == '!'){
//			    				System.out.println("Error: " + punc);
			    			}
			    			else if (c == '>' || c == '<'){
					    		myTokens.add("u");
					    	}
			    			else if (c == '/'){
					    		myTokens.add("w");
					    	}
			    			else{
//			    				System.out.println(punc);
			    			}
			    		}
				    	
				    	else if (c == '/' ){				//check /*
				    		if (line.charAt(i+1) == '*'){
				    			commentDepth++;
				    			punc += "/*";
				    			i += 2;
	    						if (i >= line.length()){
//		    							System.out.println("COMMENT: " + punc);
	    							break; 
	    						}
				    			c = line.charAt(i);
				    			while (i < line.length()){
				    				if (c == '/' ){
				    					if (i == line.length() -1){														// prevent collapse if nothing after variable (<= -1 not work)
						    				punc += c;
					    					if (commentDepth == 0){
					    						break;
					    					}
					    					i++;
					    					if (i >= line.length()){
					    						break; 
					    					}
					    					c = line.charAt(i);
				    					}
				    					else if (line.charAt(i+1) == '*'){
							    			commentDepth++;
							    			punc += "/*";
							    			i += 2;
				    						if (i >= line.length()){
				    							break; 
				    						}
							    			c = line.charAt(i);
				    					}
				    					else{
						    				punc += c;
					    					if (commentDepth == 0){
					    						break;
					    					}
					    					i++;
					    					if (i >= line.length()){
					    						break; 
					    					}
					    					c = line.charAt(i);
				    					}
				    				}
				    				else if (c == '*') {
				    					if (i == line.length() -1){									// prevent collapse if nothing after variable (<= -1 not work)
						    				punc += c;
					    					if (commentDepth == 0){
					    						break;
					    					}
					    					i++;
					    					if (i >= line.length()){
					    						break; 
					    					}
					    					c = line.charAt(i);
				    					}
				    					else if (line.charAt(i+1) == '/'){
							    			commentDepth--;
							    			punc += "*/";
							    			if (commentDepth == 0){
							    				i++;
					    						if (i >= line.length()){
					    							break; 
					    						}
							    				c = line.charAt(i);
							    				break;
							    			}
							    			i += 2;
				    						if (i >= line.length()){
				    							break; 
				    						}
							    			c = line.charAt(i);
				    					}
				    					else{
						    				punc += c;
					    					if (commentDepth == 0){
					    						break;
					    					}
					    					i++;
					    					if (i >= line.length()){
					    						break; 
					    					}
					    					c = line.charAt(i);
				    					}
				    				}
				    				else {
				    					if (commentDepth == 0){
				    						break;
				    					}
				    					punc += c;
				    					i++;
				    					if (i >= line.length()){
				    						break; 
				    					}
				    					c = line.charAt(i);
				    				}			
				    			}
//					    			System.out.println("COMMENT: " + punc);
				    		}
				    		else if(line.charAt(i+1) == '/'){
				    			punc += "//";
				    			i += 2;
		    					if (i >= line.length()){
//			    						System.out.println("COMMENT: " + punc);
		    						break; 
		    					}
				    			c = line.charAt(i);
				    			while (i < line.length()){
				    				punc += c;
				    				i++;
			    					if (i >= line.length()){
//				    						System.out.println("COMMENT: " + punc);
			    						break; 
			    					}
				    				c = line.charAt(i);
				    			}
//					    			System.out.println("COMMENT: " + punc);
				    		}
				    		else{
				    			if (c == '/'){
						    		myTokens.add("w");
						    	}
				    			punc += c;
//					    			System.out.println(punc);
				    		}
				    	}
				    	
				    	/////////////////////////////

				    	else if( c == '<'){									//Check if < or <=
				    		myTokens.add("u");
				    		punc += c;
				    		if (line.charAt(i+1) == '='){
				    			punc+= '=';
				    			i++;
				    		}
//					    		System.out.println(punc);
				    	}
				    	else if( c == '>'){									//Check if > or >=
				    		myTokens.add("u");
				    		punc += c;
				    		if (line.charAt(i+1) == '='){
				    			punc+= '=';
				    			i++;
				    		}
//					    		System.out.println(punc);
				    	}
				    	else if( c == '='){									//Check if = or ==
				    		punc += c;
				    		if (line.charAt(i+1) == '='){
				    			punc+= '=';
				    			myTokens.add("u");
				    			i++;
				    		}
				    		else{
				    			myTokens.add("t");
				    		}
//					    		System.out.println(punc);
				    	}
				    	else if( c == '!'){									//Check if != or "! " error
				    		punc += c;
				    		if (line.charAt(i+1) == '='){
				    			punc+= '=';
				    			i++;
				    			myTokens.add("u");
//					    			System.out.println(punc);
				    		}
				    		else if (line.charAt(i+1) == '!'){
//				    			System.out.println(punc);
//				    			System.out.println(punc);
				    			i++;
				    		}
				    		else{
				    			i++;
				    			c = line.charAt(i);
				    			punc+= c;
				    			i++;
				    			if (line.length() == i){
//					    				System.out.println("ERROR: " + punc);
						    		break;
							    }
							    c = line.charAt(i);
//					    			System.out.println("ERROR: " + punc);
				    			i--;
				    		}
				    	}
				    else{
				    	if (c == '/'){
				    		myTokens.add("w");
				    	}
				    		punc += c;
//					    		System.out.println(punc);
				    	}
				    }
				    /////////////////////////////////////////////////////////////////////////////////////////
				    else if (Character.isDigit(c) || c == '.'){							//Scan in digits
				    	int killFloat  = 0, toManyE = 0;
				    	
				    	while(Character.isDigit(c) || c == '.' || c == 'E'){
				    		if (line.charAt(i) == '.'){
				    			killFloat++;
				    			isFloat = true;
				    			num += c;
				    			if (killFloat > 1){
				    				break;
				    			}
				    			if (line.charAt(i + 1) == ' '){
				    				killFloat = 5;
				    				break;
				    			}
				    			i++;
						    	if (line.length() == i){
					    		break;
						    	}
						    	c = line.charAt(i);
				    		}
				    		else if (c == 'E'){						//Add E to digits
				    			
				    			num += c;
				    			isFloat = true;
				    			if (toManyE >= 1){
				    				killFloat = 5;				//Saves space and throws error
				    				break;
				    			}
				    			toManyE++;
				    			i++;
				    			
						    	if (line.length() == i){
					    		break;
						    	}
				    			c = line.charAt(i);
				    			if (c == '-'){
					    			num += c;
					    			i++;
							    	if (line.length() == i){
						    		break;
							    	}
					    			c = line.charAt(i);
					    		}
				    			if (c == '+'){
					    			num += c;
					    			i++;
							    	if (line.length() == i){
						    		break;
							    	}
					    			c = line.charAt(i);
					    		}
				    		}
				    		else {
				    			num += c;
				    			i++;
						    	if (line.length() == i){
					    		break;
						    	}
						    	c = line.charAt(i);
				    		}
				    	}
		    			if (killFloat > 1 ){
		    				while (i < line.length()){
		    					if ( c == ' ' || i == line.length() - 1){
		    						num += c;
//			    						System.out.println("ERROR: " + num);
		    						break;
		    					}
		    					else{
		    						i++;
		    						num += c;
		    						if (line.length() == i){
							    		break;
								    }
								    c = line.charAt(i);
		    					}
		    				}
		    			}
		    			else if (isFloat){
		    				myTokens.add("d");
//			    				System.out.println("FLOAT: " + num);
		    				i--;
		    				isFloat = false;
		    			}
		    			else if (isError(c)){
		    				num += c;
//			    				System.out.println("ERROR: " + num);
		    			}
		    			else{
		    				myTokens.add("d");
//			    				System.out.println("NUM: " + num);
		    				i--;
		    			}
				    }
				    
				    /////////////////////////////////////////////////////////////////////////////////////////
				    else if (Character.isLowerCase(c)){						//Scan in lower case
				    	int count = i;
				    	while(Character.isLowerCase(c)){
				    		alpha += c;
				    		count++;
				    		if (line.length() == count){
				    			break;
				    		}
				    		c = line.charAt(count);
				    		}
				    	i = count;
				    	if (alpha.equals("int")){
				    		i -= 1;
				    		myTokens.add("f");
//					    		System.out.println("Keyword: " + alpha);
				    	}
				    	else if (alpha.equals("else")){
				    		i -= 1;
//					    		System.out.println("Keyword: " + alpha);
				    		myTokens.add("q");
				    	}
				    	else if (alpha.equals("if")){
				    		i -= 1;
//					    		System.out.println("Keyword: " + alpha);
				    		myTokens.add("p");
				    	}
				    	else if (alpha.equals("return")){
				    		i -= 1;
//					    		System.out.println("Keyword: " + alpha);
				    		myTokens.add("s");
				    	}
				    	else if (alpha.equals("void")){
				    		i -= 1;
//					    		System.out.println("Keyword: " + alpha);
				    		myTokens.add("g");
				    	}
				    	else if (alpha.equals("while")){
				    		i -= 1;
//					    		System.out.println("Keyword: " + alpha);
				    		myTokens.add("r");
				    	}
				    	else if (alpha.equals("float")){
				    		i -= 1;
//					    		System.out.println("Keyword: " + alpha);
				    		myTokens.add("h");
				    	}
				    	else{
//					    		System.out.println("ID: " + alpha);
				    		myTokens.add("a");
				    		i -= 1;
				    	}
				    }
				    //////////////////////////////////////////////////////////////////////////////////
				    else if (isError(c)){
//					    	System.out.println("Error: " + c);
				    }
				    
				}
//					System.out.println();  //space
			}
//				System.out.println("Final Content: " + myTokens); 		
			
		} 	
		catch (FileNotFoundException e) {
				System.out.println("Error: File not found!");
		}		
		
		return myTokens;
	}
	
	String isCommentLine(String line){
		
		return line;
	}
	
	boolean isSpecial(final char c) {
		final int val = (int)c;
		return val >= 40 && val <= 45
		|| val >= 60 && val <= 62
		|| val == 33 || val == 47
		|| val == 59 || val == 91
		|| val == 93 || val == 123
		|| val == 125;
	}
	
	boolean isError(final char c) {
		final int val = (int)c;
		return val >= 94 && val <= 96
		|| val >= 63 && val <= 90
		|| val == 58|| val == 37
		|| val == 92 || val == 124
		|| val == 126 || val == 35
		|| val == 38 || val == 34
		|| val == 36 || val == 39;
	}
}
