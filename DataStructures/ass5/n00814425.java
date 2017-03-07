//COP3530, Dr. Kenneth E. Martin, David Hughes, n00814425, 03/12/15
//This program reads in a file and loads it into a Huffman Tree
//After loaded encrypts the file into binary then decrypts back out.c:

import java.util.PriorityQueue;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class n00814425 {
	public static void main(String[] args) {
		int[] freq = new int[7];	//stores letters A-G into array 
		String[] encodings = new String [freq.length];
		String orgFile = "";	//original file 
		String encStr = "";  	// encoded string
		
		File file = new File(args[0]);
		FileInputStream fis;
		try { 
			fis = new FileInputStream(file);
			char current;
			while (fis.available() > 0) {
				current = (char) fis.read();
				switch (current){
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
					freq[current - 'A']++;
					orgFile += current;
					break;
				}
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException iox){
			iox.printStackTrace();
		}
	      PriorityQueue<Tree> pq  = new PriorityQueue<Tree>();
          
	      for (int i = 0; i < freq.length; i++){
	    	  Tree t = new Tree();
           t.insert(freq[i], (char)(i + 65));
           pq.add(t);
	      }
	      
        while (pq.size() >= 2){
            Tree t = pq.remove();
            Tree r = pq.remove();
            Tree s = new Tree();
            s.insert(t.root.freq + r.root.freq,'x' );
            s.root.leftChild = t.root;
            s.root.rightChild = r.root;
            pq.add(s);
        } 
        
        Tree huffman = pq.remove();
        huffman.find(huffman.root, encodings);
        
        for (int i = 0; i < orgFile.length(); i++){
        	encStr += encodings[orgFile.charAt(i) - 65]; //encoded string
        }

        while(true)
        {
        System.out.print("\nEnter first letter of show: ");
        System.out.print("s = show tree, k = key display, \ne = encoded file, d = decoded, o = original, q = quit: ");
        int choice = 0;
		try {
			choice = getChar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        switch(choice)					//selection to make choices.
           {
           case 's':
               huffman.displayTree();
              break;
           case 'k':
        	   System.out.println("Letter \tFreq \tBinary ");
        	   for (int i = 0; i < encodings.length; i++){
               	System.out.println( (char)('A'+ i) +" \t"+ freq[i] + " \t" + encodings[i]);
               }
              break;
           case 'e':
               for ( int i = 0; i < encStr.length(); i++){
               	if (i % 24 == 0)
               		System.out.println();
               	else if (i % 8 == 0)
               		System.out.print(" ");
               	System.out.print(encStr.charAt(i));
               }
              break;
           case 'd':
        	   System.out.println(huffman.decode(encStr));
              break;
           case 'o':
        	   System.out.println(orgFile);
              break;
           case 'q':
              return;
           default:
              System.out.print("Invalid entry\n");
           }  // end switch
        }  // end while
	}
	
	// -------------------------------------------------------------
	   public static String getString() throws IOException
	      {
	      InputStreamReader isr = new InputStreamReader(System.in);
	      BufferedReader br = new BufferedReader(isr);
	      String s = br.readLine();
	      return s;
	      }
	// -------------------------------------------------------------
	   public static char getChar() throws IOException
	      {
	      String s = getString();
	      return s.charAt(0);
	      }
	//-------------------------------------------------------------
	   public static int getInt() throws IOException
	      {
	      String s = getString();
	      return Integer.parseInt(s);
	      }
     
	   public static void tab(int n)
	   {
	       for (int i=0;i<n;i++) System.out.print(" ");
	   }

	// -------------------------------------------------------------
}

class Node
{
public int freq;             	// data item (key)
public char c;           		// data item
public Node leftChild;        	// this node's left child
public Node rightChild;        	// this node's right child
public String code;

public Node(){
	code = "";
}

@Override
public String toString()      // display ourself
   {
   return "{" + c + ": " + freq + "}";
   }
}  // end class Node
////////////////////////////////////////////////////////////////
class Tree implements Comparable<Object>
{
int comps=0;
Node root;             // first node of tree

//-------------------------------------------------------------
public Tree()                  // constructor
   { root = null; }            // no nodes in tree yet
//-------------------------------------------------------------

public void displayTree()
{
	
Stack<Node> globalStack = new Stack<Node>();
globalStack.push(root);
int nBlanks = 32;
boolean isRowEmpty = false;
System.out.println(
"......................................................");
while(isRowEmpty==false)
   {
   Stack<Node> localStack = new Stack<Node>();
   isRowEmpty = true;

   for(int j=0; j<nBlanks; j++)
      System.out.print(' ');

   while(globalStack.isEmpty()==false)
      {
      Node temp = (Node)globalStack.pop();
      if(temp != null)
         {
         System.out.print(temp.freq);
         localStack.push(temp.leftChild);
         localStack.push(temp.rightChild);

         if(temp.leftChild != null ||
                             temp.rightChild != null)
            isRowEmpty = false;
         }
      else
         {
         System.out.print("--");
         localStack.push(null);
         localStack.push(null);
         }
      for(int j=0; j<nBlanks*2-2; j++)
         System.out.print(' ');
      }  // end while globalStack not empty
   System.out.println();
   nBlanks /= 2;
   while(localStack.isEmpty()==false)
      globalStack.push( localStack.pop() );
   }  // end while isRowEmpty is false
System.out.println(
"......................................................");
}  // end displayTree()


public String decode(String enc){
	Node n = root; 
	String dec = "";
	for (int i = 0 ; i < enc.length(); i++){
		if (enc.charAt(i) == '0'){
			if (n.leftChild == null){
				dec +=n.c;
				n = root;
				i--;								//decode string here
			}
			else{
				n = n.leftChild;
			}
		}else if (enc.charAt(i) == '1'){
			if (n.rightChild == null){
				dec +=n.c;
				n = root;
				i--;
			}
			else{
				n = n.rightChild;
			}			
		}
	}
	return dec + n.c;
	
}

public void find(Node root, String[] codes) {
    if (root.leftChild != null) {
      root.leftChild.code = root.code + "0";
      find(root.leftChild, codes);
      
      root.rightChild.code = root.code + "1";
      find(root.rightChild, codes);
    }
    else {
      codes[(int)(root.c-65)] = root.code;
    }
  }

   @Override
public int compareTo(Object o) {
      return (root.freq > ((Tree) o).root.freq) ? 1 : (root.freq == ((Tree) o).root.freq) ? 0 : -1;
   }
   
//-------------------------------------------------------------
public void insert(int id, char c)
   {comps=0;
   Node newNode = new Node();    // make new node
   newNode.freq = id;           // insert data
   newNode.c = c;
   if(root==null)                // no node in root
      root = newNode;
   else                          // root occupied
      {
      Node current = root;       // start at root
      Node parent;
      while(true)                // (exits internally)
         {
         parent = current;
         comps++;
         if(id < current.freq)  // go left?
            {
            current = current.leftChild;
            if(current == null)  // if end of the line,
               {                 // insert on left
               parent.leftChild = newNode;
               return;
               }
            }  // end if go left
         else                    // or go right?
            {
            current = current.rightChild;
            if(current == null)  // if end of the line
               {                 // insert on right
               parent.rightChild = newNode;
               return;
               }
            }  // end else go right
         }  // end while
      }  // end else not root
   }  // end insert()
}