//COP3530, Dr. Kenneth E. Martin, David Hughes, n00814425, 02/24/15
//This program uses the knapsack problem with recursion. Takes in the first number as the 
//target and adds the rest of them to see what numbers add to the target. The output 
//displays all possible values and if multiple solutions adds "and" to it.

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class n00814425{
	
	static int target;				// Target weight 
	static String output = "";		// Final output of the program
	static boolean addAnd = false;	//Adds and if there is more then one solution.
	private static ArrayList<Integer> listx = new ArrayList<Integer>();
	private static ArrayList<Integer> listy = new ArrayList<Integer>();

public static void main(String[] args){
	 
String input = JOptionPane.showInputDialog(null, "Please enter the numbers seperated by spaces"
	, "n00814425", JOptionPane.INFORMATION_MESSAGE);
String[]strData = input.split("\\s+");
for (int i = 0; i < strData.length; i++){
	if (i == 0){
		target = Integer.parseInt(strData[i]);	
	}
	else{
	int temp = Integer.parseInt(strData[i]);
	listx.add(temp);
	}
}

recurseSetup(target);
}

public static void recurseSetup(int target){
	recurse(target, 0);
	finalDisplay();
}


public static void recurse(int target, int count){
	 
   if(count == listx.size())
   {	 
       if(target == 0){
      	 if(addAnd)
      	 	output += " and ";
           for (int i = 0; i < listy.size(); i++){
           	 output += listy.get(i) + " ";     
           }
           addAnd = true;
       }
       return;
   } 
   listy.add(listx.get(count));
   recurse(target - listx.get(count), count+1);
   listy.remove(listy.size()-1);
   recurse(target, count+1);    
}

public static void finalDisplay(){
	JOptionPane.showMessageDialog(null, output, "n00814425",
			JOptionPane.INFORMATION_MESSAGE);	 
}
}