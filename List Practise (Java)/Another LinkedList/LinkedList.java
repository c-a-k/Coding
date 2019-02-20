/*
Christian Knowles
V00874629
September 23, 2017
LinkedList.java
CSC 225 201709 A01 Assignment #1A
*/

import java.util.*;
import java.io.*;
public class LinkedList{
   int n;
   ListNode start;
   ListNode rear;

   public LinkedList(){
	   n= 0;
	   start= null;
	   rear= null;
   }

   public LinkedList(int size, ListNode first, ListNode last){
	   n= size;
	   start= first;
	   rear= last;
   }

//this method takes an input and constructs a LinkedList based on its values

   public static LinkedList readBigInteger(Scanner in){

       LinkedList x;

      if(!in.hasNext()){
        return null;
      }

      int n_digit = readInteger(in);

      if(n_digit==-1){
        return null;
      }
      //set number of digits
      ListNode current = new ListNode(readInteger(in), null);
      ListNode newNode;
      x= new LinkedList(n_digit, current, current);
      //set start and rear to first digit
      if(n_digit>1){
        for(int i=1; i<n_digit; i++){
          newNode = new ListNode(readInteger(in), current);
          //adds new node in front of current
          current = newNode;
          //sets current to new digit
      }
      }
        x.start = current;
       return(x);
    }

//this method takes the LinkedList and prints it node by node
//any leading zeroes will be skipped

   public void printBigInteger(){
		if(n==0){
			return; //if list is empty
		}
	   reverse(0);
	   ListNode current = start;
	   if(start.data ==0 && n>1){ //if leading zero
	   while(current.data==0){
			current = current.next;
	   } //traverses list until non-zero
	  }
	   while(current!=null){
			System.out.print(current.data);
			current = current.next;
	   }
	   reverse(0);
   }

//this method reverses the LinkedLists recurvisevly
//it does so by dividing the list in half, and swapping the order of the lists
//once base case of n=1 is reached, the two sublists from the previous level are joined

   public void reverse(int level){

	   Test.checkList(this);
	   //for debugging purposes only

	   //base case
	   if(n==1){ 
			return;
	   }

		ListNode current = start;

		for(int i=1; i<n/2; i++){
			current = current.next;
			//traverse list until halfway point
		}

		LinkedList list1 = new LinkedList(n/2, start, current);
		LinkedList list2 = new LinkedList(n-(n/2), current.next, rear);
		list1.rear.next = null;
		list2.rear.next = null;

		list1.reverse(level+1);
		list2.reverse(level+1);
		//call recursively

		list2.rear.next = list1.start;
		start = list2.start;
		rear = list1.rear;
		//marry the two lists
	}

//takes the current LinkedList and adds one to the starting node
//if the starting node reaches 10, then the 1 is carried over

   public void plus_plus(){
	  start.data++;
	  ListNode curr = start;
	  if(start.data==10){
		  start.data = 0;
		  while(curr.next != null && curr.next.data==9){
		  	//carrying over the 1
			curr.data = 0;
			curr = curr.next;
		  }
		  curr.data = 0;
		  if(curr == rear){
		  	//if the 1 carries over all the way to end of list, a new node is created
			  curr.next = new ListNode(1, null);
			  rear = curr.next;
		  } else {
			curr.next.data++;
		  }
	  }
   }

//This method takes two linked and adds their data node by node
//The resulting third list is the summation of the two big integers

   public LinkedList plus(LinkedList y){

	   LinkedList z = new LinkedList(Math.max(n,y.n), null, null);
	   z.start = new ListNode(0, null);

	   ListNode currentX = start;
	   ListNode currentY = y.start;
	   ListNode currentZ = z.start;
	   //use these nodes to track the positions

	   int count = Math.min(n, y.n);
	   //number of times x and y lists are added to z

	   for(int i=1; i<count; i++){
			currentZ.data += currentX.data + currentY.data;
			if(currentZ.data >= 10){
				//if a digit needs to be carried
				currentZ.data = currentZ.data - 10;
				currentZ.next = new ListNode(1, null);
			} else {
				currentZ.next = new ListNode(0, null);
			}

			currentZ = currentZ.next;
			currentX = currentX.next;
			currentY = currentY.next;
			//all current nodes are updated
	   }
	   //last addition is separate so as to avoid null pointer exceptions
	   currentZ.data += currentX.data + currentY.data;
		if(currentZ.data >= 10){
			currentZ.data = currentZ.data - 10;
			currentZ.next = new ListNode(1, null);
			if(n == y.n){
			z.n++;
			currentZ = currentZ.next;
			}
		}

		if(y.n>n){
			//if list y is greater than list x
			if(currentZ.next==null){
				currentZ.next = new ListNode(0, null);
			}
			currentZ = currentZ.next;
			currentY = currentY.next;
			for(int i=1; i<y.n-count; i++){
			currentZ.data += currentY.data;
			if(currentZ.data >= 10){
				//if a digit needs to be carried
				currentZ.data = currentZ.data - 10;
				currentZ.next = new ListNode(1, null);
			} else {
				currentZ.next = new ListNode(0, null);
			}

			currentZ = currentZ.next;
			currentY = currentY.next;
			}
			//last addition is separate so as to avoid null pointer exceptions
			currentZ.data += currentY.data;
			if(currentZ.data >= 10){
				currentZ.data = currentZ.data - 10;
				currentZ.next = new ListNode(1, null);
				z.n++;
				currentZ = currentZ.next;
			}
	   }

	   if(n>y.n){
			//if list x is greater than list y
	   		if(currentZ.next==null){
	   			currentZ.next = new ListNode(0, null);
	   		}
			currentZ = currentZ.next;
			currentX = currentX.next;
			for(int i=1; i<n-count; i++){
			currentZ.data += currentX.data;
			if(currentZ.data >= 10){
				//if a digit needs to be carried
				currentZ.data = currentZ.data - 10;
				currentZ.next = new ListNode(1, null);
			} else {
				currentZ.next = new ListNode(0, null);
			}

			currentZ = currentZ.next;
			currentX = currentX.next;
			}
			//last addition is separate so as to avoid null pointer exceptions
			currentZ.data += currentX.data;
			if(currentZ.data >= 10){
				currentZ.data = currentZ.data - 10;
				currentZ.next = new ListNode(1, null);
				z.n++;
				currentZ = currentZ.next;
			}
	   }

	   
	   z.rear = currentZ;
	   return(z);
   }

// You can use these routines for this assignment:

// Tries to read in a non-negative integer from the input stream.
// If it succeeds, the integer read in is returned. 
// Otherwise the method returns -1.
   public static int readInteger(Scanner in){
	   int n;

	   try{
		   n= in.nextInt();
		   if (n >=0) return(n);
		   else return(-1);
	   }
	   catch(Exception e){
//        We are assuming legal integer input values are >= zero.
		  return(-1);
	   }
   }

// Use this for debugging only.

   public void printList(){
	   ListNode current;

	   int count=0;

	   current= start;

	   while (current != null){

		   count++;
		   
		   System.out.println("Item " + count + " in the list is " 
							+ current.data);
		   current= current.next;
	   }
   }
}
