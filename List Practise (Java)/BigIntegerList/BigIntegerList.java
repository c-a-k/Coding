/*
Christian Knowles
V00874629
October 12, 2017
BigIntegerList.java
CSC 225 201709 A01 Assignment #2A
*/

import java.util.*;
import java.io.*;
/* This class is for creating a singly linked class of big integer nodes. */
/* Hand this in together with your LinkedList.java. */
class BigIntegerList
{
   int n; // Number of items in the list.
   BigIntegerNode start;
   BigIntegerNode rear;

// You can set debug=true when you are debugging and want
// to print out what your program is doing.
// Please leave your debugging messages in your code when
// you submit it. Change debug back to false before
// submitting your code.
   static final boolean debug= false;

   public BigIntegerList(){

       n=0;
       start= null;
       rear= null;
   }
   //this method reads a number of big integers and stores them in a list
   public static BigIntegerList readBigIntegerList(Scanner in){

        BigIntegerList x = new BigIntegerList();

        if(!in.hasNext()){ //if no further input
        return null;
      }
        int n_digit = LinkedList.readInteger(in);
        BigIntegerNode curr;
        x.start = new BigIntegerNode(LinkedList.readBigInteger(in), null);
        curr = x.start;
        x.n = n_digit;
        for(int i=1; i<n_digit; i++){
            curr.next = new BigIntegerNode(LinkedList.readBigInteger(in), null);
            curr = curr.next;
            //curr.next is updated and curr is set to its next node
        }
        x.rear = curr;
        //rear points to curr
        return x;

   }
   //this method prints the list of big integers
   public void printBigIntegerList(){

    BigIntegerNode curr = start;
       for(int i=1; i<n; i++){
            if(i%10==1 && i>1){
                //every 10 values it creates a new line
                System.out.println();
            }
            curr.x.printBigInteger(8);
            curr = curr.next;
       }
       curr.x.printBigInteger(8);
       System.out.println();
   }

   //this method sorts the Big Integer list recursively
   //by dividing the list into three sublists
   //one greater than start, one equal to start, and one less than start
   //once base case is reached, all three sublists are connected
   public void quickSort(int level){
       
       if(n<=1){ //base case
            return;
       }

       BigIntegerList list1 = new BigIntegerList();
       BigIntegerList list2 = new BigIntegerList();
       BigIntegerList list3 = new BigIntegerList();

       int comp = 0;
       //use comp to determine how lists are made
       list2.start = start;
       list2.rear = start;
       list2.n++;
       //create first node of list2 (start)
       BigIntegerNode curr = start.next;
       while(curr!=null){
            comp = curr.x.compare(start.x);
            if(comp==-1){
                if(list1.n==0){ 
                    list1.start = curr;
                    list1.rear = curr;
                    //if list is empty set start and rear nodes
                } else {
                    list1.rear.next = curr;
                    list1.rear = curr;
                    //rear is set to current node
                }
                list1.n++;
            }
            else if(comp==1){
                if(list3.n==0){
                    list3.start = curr;
                    list3.rear = curr;
                    //if list is empty set start and rear nodes
                } else {
                    list3.rear.next = curr;
                    list3.rear = curr;
                    //rear is set to current node
                }
                list3.n++;
            } else {
                list2.rear.next = curr;
                list2.rear = curr;
                list2.n++;
            }
            curr = curr.next;
       }
       if(list1.n>0){
            list1.rear.next = null;
       }

       list2.rear.next = null;

        if(list3.n>0){
            list3.rear.next = null;
        }

        //call quickSort recursively
       list1.quickSort(level+1);
       list3.quickSort(level+1);

       //marrying all three lists together
       if(list1.n==0){
            start = list2.start;
            //if list1 is empty don't connect it to the start
       } else {
            start = list1.start;
            list1.rear.next = list2.start;
       }

       if(list3.n==0){
            rear = list2.rear;
            //if list3 is empty don't connect it to the rear
       } else {
            rear = list3.rear;
            list2.rear.next = list3.start;
       }

   }
}
