
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class p12 {
	public static void main (String[] args){
		HashTable table = new HashTable(50);
		try {
			Scanner input = new Scanner (new File(args[0]));
			while( input.hasNextLine()){
				String line = input.nextLine();
				if (isNumber(line)){
					String [] a = line.split(" ");
					table.add(a[0], Integer.parseInt(a[1]));
				}
				else {
					int i;
					if((i = table.find(line.trim())) == -1){
						System.out.println("Did not find " + line );
					} else {
						System.out.println("Found " + line + " " + table.getValue(i) + " at " + i);
					}
				}
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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

class HashTable{
	
	private HashObject [] table;
	
	public HashTable(int size){
		
		table = new HashObject[size];
	}
	
	public int hash(String name) {				//Find ASCII number method
		int ascii = 0;
		char []temp = name.toCharArray();
		for (int i = 0; i < temp.length; i++) {
			int temp2 = (int) temp[i];
			if (temp2 > 96)
				ascii = ascii + temp2;
		}
		return ascii % table.length;
	}
	
	public void add(String key, int value){
		int i = hash(key);
		if (find(key) != -1){
			System.out.println("ERROR " + key + " already exists at location "+ i);
			return;
		}
		while (table [i] != null){
			System.out.println(key + " Collided with "+ table[i].key);
			i++;
		}
		table[i] = new HashObject (key , value);
		System.out.println("Stored "+ key + " " + value + " at location " + i);
	}
	
	public void printTable() {
		for (int i = 0; i < table.length; i++)
			if (table[i] != null)
				System.out.println(i + ": " + table[i].key + ": " + table[i].value);
	}
	
	public int find (String key){
		int i = hash(key);
		while (table [i] != null){
			if (key.equals(table[i].key)) {
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	public int getValue(int i){
		if (table[i] != null){
			return table[i].value;
		}
		return Integer.MAX_VALUE;
	}
	
}

class HashObject{
	public String key;
	public int value;
	
	public HashObject(String k, int v){
		
		key = k;
		value = v;
		
	}
}

//String[] str = new String[50];
//int [] in = new int [50];
//ArrayList test = new ArrayList<Objects>();
//java.io.File file = new java.io.File("p1test.txt");
//Scanner input = new Scanner (file);
//int x = 0;
//do{
//	test.add(input.next(x));
//	Systsem.out.println(test.get(x));
//	x++;
//	if (test.get (x).getType() == String)
		
