/*
Christian Knowles
V00874629
October 12, 2017
LinkedList.java
CSC 225 201709 A01 Assignment #2A
*/
import java.util.*;
import java.io.*;
public class LinkedList{

   int n;
   ListNode start;
   ListNode rear;

// You can set debug=true when you are debugging and want
// to print out what your program is doing.
// Please leave your debugging messages in your code when
// you submit it. Change debug back to false before
// submitting your code.
   static final boolean debug= false;

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

   public void reverse(int level){

       Test.checkList(this);

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

/*
    This method returns:
    -1 if the bigInteger associated with the method is less than y
     0 if the bigInteger associated with the method is equal to y
    +1 if the bigInteger associated with the method is greater than y
*/
    public int compare(LinkedList y){
        reverse(0);
        y.reverse(0);
        //reverse the lists first
        int xcount = n;
        int ycount = y.n;
        ListNode curr = start;
        ListNode ycurr = y.start;
        while(xcount>1 && curr.data==0){
            curr = curr.next;
            xcount--;
            //eliminate leading zeroes by skipping them
        }
        while(ycount>1 && ycurr.data==0){
            ycurr = ycurr.next;
            ycount--;
            //eliminate leading zeroes by skipping them
        }
        if(xcount>ycount){
            //if x has more digits than y
            reverse(0);
            y.reverse(0);
            return 1;
        }
        else if(ycount>xcount){
            //if y has more digits than x
            reverse(0);
            y.reverse(0);
            return -1;
        } else {
            for(int i=1; i<xcount; i++){
                //loop will compare each node of the list
                if(curr.data>ycurr.data){
                    reverse(0);
                    y.reverse(0);
                    return 1;
                }
                if(curr.data<ycurr.data){
                    reverse(0);
                    y.reverse(0);
                    return -1;
                }
                curr = curr.next;
                ycurr = ycurr.next;
            }
            if(curr.data>ycurr.data){
                    reverse(0);
                    y.reverse(0);
                    return 1;
                }
            else if(curr.data<ycurr.data){
                    reverse(0);
                    y.reverse(0);
                    return -1;
                } else { //if all nodes are the same
                    reverse(0);
                    y.reverse(0);
                    return 0;
                }
        }
    }

// Tries to read in a non-negative integer from the input stream.
// If it succeeds, the integer read in is returned. 
// Otherwise the method returns -1.
   public static int readInteger(Scanner in)
   {
       int n;

       try{
           n= in.nextInt();
           if (n >=0) return(n);
           else return(-1);
       }
       catch(Exception e)
       {
//        We are assuming legal integer input values are >= zero.
          return(-1);
       }
   }

// You can use this in order to get nicer output
// (lined up in columns).

   public void printBigInteger(int nDigit){

        boolean leadingZero;
        ListNode current;
        int i;
        int n_extra;

        reverse(0);
        leadingZero= true;
        if (n < nDigit)
        {
            for (i=n; i < nDigit; i++)
                System.out.print(" ");
        }
        n_extra= n- nDigit;
        current= start;
        while (current != null)
        {
            if (leadingZero)
            {
                if (current.data != 0)
                   leadingZero= false;
            }
            if (leadingZero)
            {
               if (current.next == null) // value is 0.
                   System.out.print(current.data);
               else if (n_extra <= 0)
                   System.out.print(" ");
            }
            else
            {
                System.out.print(current.data);
            }
            n_extra--;
            current= current.next;
        }
        reverse(0);
   }
} 
