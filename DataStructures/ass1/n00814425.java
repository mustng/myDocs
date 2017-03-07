// COP3530, Dr. Kenneth E. Martin, David Hughes, n00814425, 01/17/15
// Added a getMax and getMin to the HighArray class


class HighArray {
private long[] a;
private int nElems;
//-----------------------------------------------------
public HighArray(int max) {
a = new long[max];
nElems = 0;
}
//-----------------------------------------------------
public boolean find(long searchKey) { //find specified value in array
int j;
for(j=0; j<nElems; j++)
if(a[j] == searchKey)
break; //exits the loop if searchKey is found
if(j == nElems)
return false;
else
return true;
}
//------------------------------------------------------
public void insert(long value) { //puts element into array
a[nElems] = value;
nElems++;
}
//------------------------------------------------------
public boolean delete(long value) { //deletes element from array
int j;
for(j=0; j<nElems; j++)
if(value == a[j]) //look through all indices for value
break; //once found or loop complete break
if(j == nElems) //if j equals nElems, it means it wasn't found
return false;
else {
for (int k=j; k<nElems; k++) //it was found and we need to shift all other values down
a[k] = a[k+1];
nElems--;
return true;
}
}
//---------------------------------------------------------
public void display() { //displays array contents
for(int j=0; j<nElems; j++)
System.out.print(a[j] + " ");
System.out.println(" ");
}
public long getMax(){	
	if (nElems <= 0)
		return -1;
	long currMax = 0;
	for ( int k = 0; k < nElems; k++){
		if (a[k]>currMax)
			currMax = a[k];
	}
	//System.out.println("Max= " +currMax);
	return currMax;	
	}	

public long getMin(){	
	if (nElems <= 0)
		return -1;
	long currMin = 999999999;
	for ( int k = 0; k < nElems; k++){
		if (a[k]<currMin)
			currMin = a[k];
	}
	//System.out.println("Min= " +currMin);
	return currMin;	
	}	
}
//----------------------------------------------------------

public class n00814425 {
public static void main(String[] args) {
int maxSize = 100;

HighArray arr;
arr = new HighArray(maxSize); //creates the array
//-------------------------------------------------------------
arr.insert(77);
arr.insert(99);
arr.insert(44);
arr.insert(55);
arr.insert(22);
arr.insert(88);
arr.insert(11);
arr.insert(00);
arr.insert(66);
arr.insert(33);
//--------------------------------------------------------------
arr.display(); //display the array items
//---------------------------------------------------------------
int searchKey = 35; //calling my find method with searchKey of 35
if(arr.find(searchKey) )
System.out.println("Found " + searchKey);
else
System.out.println("Can't find " + searchKey);
//----------------------------------------------------------------
arr.delete(00);
arr.delete(55);
arr.delete(99);
//----------------------------------------------------------------
arr.display(); 

long y = arr.getMax(); 
System.out.println(y);
long z = arr.getMin();
System.out.println(z);
} // end main()
} //end class HighArrayApp
/////////////////////////////////////////////////////////////////////////////////////////////