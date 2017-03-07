//COP3530, Dr. Kenneth E. Martin, David Hughes, n00814425, 04/7/15
//This program modifies the HashTable out of the book making it a abstract class.
//The LinearHash and QuadHashTable class extend Hashtable with rewritten functions to solve
//all the following results of the program. 

import java.util.Random;
import java.util.Scanner;

public class n00814425 {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		int maxValue = 0;
		int arraySize = 0;

		Random r = new Random();
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the number of items you want to be searched: ");
		maxValue = input.nextInt();
		
		int inputArray[] = new int[10000];
		int searchArray[] = new int[100];
		
		for (int i = 0; i < inputArray.length; i++) {
			inputArray[i] = r.nextInt(maxValue) + 1;
		}
		for (int i = 0; i < searchArray.length; i++) {
			searchArray[i] = r.nextInt(maxValue) + 1;
		}
		LinearHashTable[] lht = new LinearHashTable[9];
		QuadHashTable[] qht = new QuadHashTable[9];
		for (int i = 1; i < 10; i += 1) { // change to int and divide 10
			int size = (int)(inputArray.length/(i /10.0));
			LinearHashTable l = new LinearHashTable(size);
			QuadHashTable q = new QuadHashTable(size);
			lht[i - 1]= l;
			qht[i - 1]= q;
			for (int j = 0; j < inputArray.length; j++) {
				l.insert(new DataItem(inputArray[j]));
				q.insert(new DataItem(inputArray[j]));
			}
			for (int j = 0; j < searchArray.length; j++) {
				l.find(searchArray[j]);
				q.find(searchArray[j]);
			}
		}
		System.out.println("load    ES   DS   TS            EF   DF   TF\n----    --   --   --            --   --   --  ");
		for (int i = 0; i < 9; i++){
			System.out.printf(".%1d      %3d  %3d  %5.2f", i+1, lht[i].totalCompares/searchArray.length, lht[i].totalFound, (1 + (1/Math.pow(1 - ((i+1)/ 10.0), 2))) / 2);
			System.out.printf("        %3d  %3d  %5.2f\n", 0, 100 - lht[i].totalFound, (1 + (1 / (1 - ((i+1)/10.0)))) / 2);
		}
		
		System.out.println("\n\nload    ES   DS   TS            EF   DF   TF\n----    --   --   --            --   --   --  ");
		for (int i = 0; i < 9; i++){
			System.out.printf(".%1d      %3d  %3d  %5.2f", i+1, qht[i].totalCompares/searchArray.length, qht[i].totalFound, (-Math.log(1 - ((i+1)/10.0))) / ((i+1)/10.0));
			System.out.printf("        %3d  %3d  %5.2f\n", 0, 100 - qht[i].totalFound, 1 / (1 - ((i+1)/10.0)));
		}
	}


}

// //////////////////////////////////////////////////////////////
class DataItem { // (could have more data)
	private int iData; // data item (key)

	// --------------------------------------------------------------

	public DataItem(int ii) // constructor
	{
		iData = ii;
	}

	// --------------------------------------------------------------
	public int getKey() {
		return iData;
	}
	// --------------------------------------------------------------
} // end class DataItem
// //////////////////////////////////////////////////////////////

abstract class HashTable {
	protected DataItem[] hashArray; // array holds hash table
	private int arraySize;
	protected DataItem nonItem; // for deleted items
	public int totalCompares;
	public int totalFound;

	// -------------------------------------------------------------
	public HashTable(int size) // constructor
	{
		totalCompares = totalFound = 0;
		arraySize = findPrime(size);
		hashArray = new DataItem[arraySize];
		nonItem = new DataItem(-1); // deleted item key is -1
	}

	public abstract void insert(DataItem item); // insert a DataItem

	// -------------------------------------------------------------
	public void displayTable() {
		System.out.print("Table: ");
		for (int j = 0; j < arraySize; j++) {
			if (hashArray[j] != null)
				System.out.print(hashArray[j].getKey() + " ");
			else
				System.out.print("** ");
		}
		System.out.println("");
	}

	// -------------------------------------------------------------
	protected int hashFunc(int key) {
		String keyStr = key + "";
		
		if (keyStr.length() == 1)
			return key % arraySize;
		
		int c = (keyStr.charAt(0) - 48) * 10;
		int d = (keyStr.charAt(1) - 48);
		
		int idx = (c + d) % arraySize;
		
		for (int i = 2; i < keyStr.length(); i++) {
			idx *= 10;
			idx += (int)(keyStr.charAt(i) - 48);
			idx %= arraySize;
		}
		return idx;
	}

	private int findPrime(int n) {
		for (int i = n + 1; true; i++) {
			if (isPrime(i)) {
				return i;
			}
		}
	}

	private boolean isPrime(int n) {
		for (int j = 2; (j * j <= n); j++)
			if (n % j == 0)
				return false;
		return true;
	}

	public abstract DataItem find(int key);
	// -------------------------------------------------------------
} // end class HashTable
// //////////////////////////////////////////////////////////////

class LinearHashTable extends HashTable {

	public LinearHashTable(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public DataItem find(int key) {
		int hashVal = hashFunc(key); // hash the key
		// until empty cell or -1,
		while (hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1) {
			totalCompares++;
			if (hashArray[hashVal].getKey() == key){
				totalFound++;
				return hashArray[hashVal];
			}
			++hashVal; // go to next cell
			hashVal %= hashArray.length; // wrap around if necessary
		}
		return null;
	}

	public void insert(DataItem item) // insert a DataItem
	// (assumes table not full)
	{
		int key = item.getKey(); // extract key
		int hashVal = hashFunc(key); // hash the key
		// until empty cell or -1,
		while (hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1) {
			++hashVal; // go to next cell
			hashVal %= hashArray.length; // wrap around if necessary
		}
		hashArray[hashVal] = item; // insert item
	} // end insert()
}

class QuadHashTable extends HashTable {

	public QuadHashTable(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(DataItem item) {
		int key = item.getKey(); // extract key
		int index = hashFunc(key); // hash the key
		int k = 1;
		while (hashArray[index] != null) {
			index = hashFunc(key) + (k * k);
			index %= hashArray.length;
			k++;
		}
		hashArray[index] = item;
	}

	@Override
	public DataItem find(int key) {
		int index = hashFunc(key);
		int k = 1;
		while (hashArray[index] != null) {
			totalCompares++;
			if (hashArray[index].getKey() == key) {
				totalFound++;
				return hashArray[index];
			} else {
				index = hashFunc(key) + (k * k);
				index %= hashArray.length;
				k++;
			}
		}
		return null;
	}

}
