
public class HashTable {
	
	private HashObject [] table;
	
	public HashTable(int size){
		
		table = new HashObject[size];
	}
	
	public int hash(String name) {				//Find ASCII number method
		int ascii = 0;
		char []temp = name.toCharArray();
		for (int i = 0; i < temp.length; i++) {
			int temp2 = (int) temp[i];
			if (temp2 > 'A')
				ascii = ascii + temp2;
		}
		return ascii % table.length;
	}
	
	public boolean add(String key, int value, int format){
		int i = hash(key);
		if (find(key) != null){
			return false;
		}
		while (table [i] != null){
			i = (i + 1) % table.length;
		}
		table[i] = new HashObject (key , value, format);
		return true;
	}
	
	public boolean add(String key, int value){
		return add(key, value, -1);
	}
	public void printTable() {
		System.out.println();
		System.out.println("Hash Loc:     Label:    Address:  Format:");
		for (int i = 0; i < table.length; i++)
			if (table[i] != null)
				System.out.printf("%4d %15s %9X %9d\n", i, table[i].key, table[i].value, table[i].getFormat());
	}
	
	public HashObject find (String key){
		int i = hash(key);
		while (table [i] != null){
			if (key.equals(table[i].key)) {
				return table[i];
			}
			i = (i + 1) % table.length;
		}
		
		return null;
	}
	
	public int getValue(int i){
		if (table[i] != null){
			return table[i].value;
		}
		return Integer.MAX_VALUE;
	}
	
}
