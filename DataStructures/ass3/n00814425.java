// COP3530, Dr. Kenneth E. Martin, David Hughes, n00814425, 02/14/15
// Modified the firtlap app and added a nested for loop to mathematically 
// calculate the the josephus problem and stops by typing in stop.


import javax.swing.JOptionPane;
////////////////////////////////////////////////////////////////
class Link
   {
   public long dData;             // data item
   public Link next;              // next link in list
// -------------------------------------------------------------
   public Link(long dd)           // constructor
      { dData = dd; }
// -------------------------------------------------------------
   public void displayLink()      // display ourself
      { System.out.print(dData + " "); }
   }  // end class Link
////////////////////////////////////////////////////////////////
class LinkList
   {
   private Link first;            // ref to first item on list

// -------------------------------------------------------------
   public LinkList()              // constructor
      { first = null; }           // no items on list yet
// -------------------------------------------------------------
   public Link getFirst()         // get value of first
      { return first; }
// -------------------------------------------------------------
   public void setFirst(Link f)   // set first to new link
      { first = f; }
// -------------------------------------------------------------
   public boolean isEmpty()       // true if list is empty
      { return first==null; }
// -------------------------------------------------------------
   public ListIterator getIterator()  // return iterator
      {
      return new ListIterator(this);  // initialized with
      }                               //    this list
// -------------------------------------------------------------
   public void displayList()
      {
      Link current = first;       // start at beginning of list
      while(current != null)      // until end of list,
         {
         current.displayLink();   // print data
         current = current.next;  // move to next link
         }
      System.out.println("");
      }
// -------------------------------------------------------------
public static Object LinkedList() {
	// TODO Auto-generated method stub
	return null;
}
   }  // end class LinkList
////////////////////////////////////////////////////////////////
class ListIterator
   {
   private Link current;          // current link
   private Link previous;         // previous link
   private LinkList ourList;      // our linked list
//--------------------------------------------------------------
   public ListIterator(LinkList list) // constructor
      {
      ourList = list;
      reset();
      }
//--------------------------------------------------------------
   public void reset()            // start at 'first'
      {
      current = ourList.getFirst();
      previous = null;
      }
//--------------------------------------------------------------
   public boolean atEnd()         // true if last link
      { return (current.next==null); }
//--------------------------------------------------------------
   public void nextLink()         // go to next link
      {
      previous = current;
      current = current.next;
      }
//--------------------------------------------------------------
   public Link getCurrent()       // get current link
      { return current; }
//--------------------------------------------------------------
   public void insertAfter(long dd)     // insert after
      {                                 // current link
      Link newLink = new Link(dd);

      if( ourList.isEmpty() )     // empty list
         {
         ourList.setFirst(newLink);
         current = newLink;
         }
      else                        // not empty
         {
         newLink.next = current.next;
         current.next = newLink;
         nextLink();              // point to new link
         }
      }

   public long deleteCurrent()    // delete item at current
      {
      long value = current.dData;
      if(previous == null)        // beginning of list
         {
         ourList.setFirst(current.next);
         reset();
         }
      else                        // not beginning
         {
         previous.next = current.next;
         if( atEnd() )
            reset();
         else
            current = current.next;
         }
      return value;
      }
//--------------------------------------------------------------
   }  // end class ListIterator
////////////////////////////////////////////////////////////////
public class n00814425
   {
   public static void main(String[] args)
      {
	   int size = 0;
	   int start = 0;
	   int plus = 0;
	  
	  boolean control = true;
	  
	  while (control){
		
	  String input = JOptionPane.showInputDialog(null, "Please enter 3 numbers seperated by spaces or type \"stop\" to exit:"
		, "n00814425", JOptionPane.INFORMATION_MESSAGE);
	  if (input.equals("stop")){
		  control = false;
	  }
	  else
	  {
	  String[]strData = input.split("\\s+");
	
		  LinkList theList = new LinkList();           // new list
		  ListIterator iter1 = theList.getIterator();  // new iter
		  long value = 0;
		  int i = 0;
		  
		  for (i = 0; i < strData.length; i++){
			  int temp = Integer.parseInt(strData[i]);	
			  if ( i == 0 ){
				  size = temp;					//takes in numbers and assigns to size, start, and temp
			  }
			  else if (i == 1){
				  start = temp;
			  }
			  else if (i == 2){
				  plus = temp;
			  }
			  else{
			  }
		  }

		  int[] number = new int[size];
		  for ( i = 1; i <= number.length; i++){	//creates and inserts values into array list
			  int temp = i;
			  iter1.insertAfter(temp);
		  }
		  
		  
		  iter1.reset(); 
		  
		  for (i = 1; i < start; i++){				// sets starting point
			  iter1.nextLink();
		  }
		  
		  for (i = 0; iter1.getCurrent()!= null; i++){
			  for ( int j = 0; j < plus; j++){ 
				  if (iter1.atEnd()){
		 			  iter1.reset();
				  }  
				  else{
					  iter1.nextLink();
				  }
			  }
			  value = iter1.getCurrent().dData;
			  iter1.deleteCurrent();
    	  
		  }
      JOptionPane.showMessageDialog(null, "Final solution: "+value, "n00814425", JOptionPane.INFORMATION_MESSAGE);	
	  }
	  }
      }  // end main()

   }  